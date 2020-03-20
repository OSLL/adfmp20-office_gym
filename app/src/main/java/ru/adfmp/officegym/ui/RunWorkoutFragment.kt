package ru.adfmp.officegym.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_run_workout.*
import kotlinx.android.synthetic.main.fragment_run_workout.view.*
import ru.adfmp.officegym.R
import ru.adfmp.officegym.accelerometer.Accelerometer
import ru.adfmp.officegym.accelerometer.StrategyFactory
import ru.adfmp.officegym.accelerometer.StrategyStatus
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.FragmentRunWorkoutBinding
import ru.adfmp.officegym.models.RunWorkoutViewModel
import ru.adfmp.officegym.utils.Audio
import ru.adfmp.officegym.utils.InjectorUtils
import ru.adfmp.officegym.utils.MyVibrator
import ru.adfmp.officegym.utils.PlayPauseTimer
import ru.adfmp.officegym.utils.PlayPauseTimer.Companion.MILLIS_IN_SECOND

class RunWorkoutFragment : Fragment() {
    private var mute = false
    private var timer: PlayPauseTimer? = null
    private var accelerometer: Accelerometer? = null
    private var audio: Audio? = null
    private val runWorkoutModel: RunWorkoutViewModel by activityViewModels {
        InjectorUtils.provideRunWorkoutViewModelFactory(this)
    }
    private val isTimerPaused = MutableLiveData(false)
    private lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRunWorkoutBinding.inflate(inflater, container, false)

        subscribeUI(binding)

        binding.root.next_exercise_button.setOnClickListener {
            next()
        }
        binding.root.pause_button.setOnClickListener {
            createAudio()
            if (isTimerPaused.value!!) {
                timer?.play()
                accelerometer?.start()
            } else {
                timer?.pause()
                accelerometer?.pause()
            }
            isTimerPaused.postValue(!isTimerPaused.value!!)
        }
        binding.root.mute_button.setOnClickListener {
            createAudio()
            mute = !mute
            val resource = if (mute) R.drawable.ic_sound_icon else R.drawable.ic_mute_icon
            mute_button.setImageResource(resource)
        }
        application = activity!!.application
        return binding.root
    }

    private fun subscribeUI(binding: FragmentRunWorkoutBinding) {
        val navController = findNavController()
        runWorkoutModel.exercise.observe(viewLifecycleOwner, Observer { exercise ->
            timer?.cancel()
            timer = null
            accelerometer?.stop()
            accelerometer = null
            audio?.shutdown()
            isTimerPaused.postValue(false)
            if (exercise == null) {
                navController.popBackStack()
            } else {
                bindUI(binding, exercise)
            }
        })
    }

    private fun bindUI(binding: FragmentRunWorkoutBinding, exercise: Exercise) {
        binding.progress.progress = 0f
        timer = createTimer(binding, exercise.duration)
        accelerometer = createAccelerometer(exercise)
        binding.exercise = exercise
        binding.image.setImageResource(exercise.resourceId)
        binding.isPaused = isTimerPaused
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        binding.timeText.text = ""
        binding.muteButton.isEnabled = false
        audio = Audio(context!!) {
            binding.muteButton.isEnabled = true
            makeAudio("${exercise.name!!}. ${exercise.description}..... Start!") {
                MyVibrator.vibrate(context!!, MyVibrator.LENGTH_LONG)
                timer?.start()
                if (!isTimerPaused.value!!) {
                    accelerometer?.start()
                }
            }
        }
    }

    private var oneSaid = false
    private var twoSaid = false
    private var threeSaid = false

    private fun oneInfo() {
        if (!oneSaid) {
            oneSaid = true
            MyVibrator.vibrate(context!!, MyVibrator.LENGTH_LONG)
            makeAudio("One")
        }
    }

    private fun twoInfo() {
        if (!twoSaid) {
            twoSaid = true
            MyVibrator.vibrate(context!!, MyVibrator.LENGTH_LONG)
            makeAudio("Two")
        }
    }

    private fun threeInfo() {
        if (!threeSaid) {
            threeSaid = true
            MyVibrator.vibrate(context!!, MyVibrator.LENGTH_LONG)
            makeAudio("Three")
        }
    }

    private fun createTimer(binding: FragmentRunWorkoutBinding, durationSeconds: Int): PlayPauseTimer {
        oneSaid = false
        twoSaid = false
        threeSaid = false
        return PlayPauseTimer(
            millisInFuture = durationSeconds * MILLIS_IN_SECOND,
            onFinish = {
                val onComplete = {
                    runWorkoutModel.exerciseCompleted()
                    next()
                }
                if (context == null) {
                    onComplete()
                } else {
                    Audio(context!!) {
                        MyVibrator.vibrate(context!!, MyVibrator.LENGTH_LONG)
                        if (!mute) {
                            it.speak("Finish", onComplete)
                        } else {
                            onComplete()
                        }
                    }
                }
            },
            onTick = { millisUntilFinished, percent ->
                val secondsUntilFinished = millisUntilFinished / MILLIS_IN_SECOND
                binding.progress.progress = percent.toFloat()
                binding.timeText.text = secondsUntilFinished.toInt().toString()
                when {
                    millisUntilFinished < 1700 -> oneInfo()
                    millisUntilFinished < 2700 -> twoInfo()
                    millisUntilFinished < 3700 -> threeInfo()
                    else -> {
                    }
                }
            }
        )
    }


    private fun createAccelerometer(exercise: Exercise) = Accelerometer(
        application,
        StrategyFactory.create(exercise.strategyX, exercise.phaseX, exercise.amplitudeX),
        StrategyFactory.create(exercise.strategyY, exercise.phaseY, exercise.amplitudeY),
        StrategyFactory.create(exercise.strategyZ, exercise.phaseZ, exercise.amplitudeZ),
        evalStrategy
    )

    private val evalStrategy: (strategy: StrategyStatus) -> Unit = {
        if (it != StrategyStatus.OK && !oneSaid && !twoSaid && !threeSaid) {
            if (context != null) MyVibrator.vibrate(context!!, MyVibrator.LENGTH_SHORT)
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            makeAudio(it.message)
        }
    }

    private fun next() {
        Log.d("Accelerometer", "Count=${accelerometer?.counter}")
        runWorkoutModel.next()
    }

    private fun createAudio() {
        audio?.shutdown()
        audio = Audio(context!!)
    }

    private fun makeAudio(text: String, onFinish: () -> Unit = {}) {
        if (mute) {
            onFinish()
            return
        }
        audio?.speak(text, onFinish)
    }

    override fun onStart() {
        super.onStart()
        timer?.start()
        accelerometer?.start()
        audio = Audio(context!!)
    }

    override fun onStop() {
        super.onStop()
        timer?.pause()
        accelerometer?.pause()
        audio?.shutdown()
    }
}

