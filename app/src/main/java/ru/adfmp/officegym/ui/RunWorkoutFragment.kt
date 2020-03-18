package ru.adfmp.officegym.ui

import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Vibrator
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
import kotlinx.android.synthetic.main.fragment_run_workout.view.*
import ru.adfmp.officegym.accelerometer.Accelerometer
import ru.adfmp.officegym.accelerometer.StrategyFactory
import ru.adfmp.officegym.accelerometer.StrategyStatus
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.FragmentRunWorkoutBinding
import ru.adfmp.officegym.models.RunWorkoutViewModel
import ru.adfmp.officegym.utils.InjectorUtils
import ru.adfmp.officegym.utils.PlayPauseTimer
import ru.adfmp.officegym.utils.PlayPauseTimer.Companion.MILLIS_IN_SECOND

class RunWorkoutFragment : Fragment() {
    private var timer: PlayPauseTimer? = null
    private var accelerometer: Accelerometer? = null
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
            if (isTimerPaused.value!!) {
                timer?.play()
                accelerometer?.start()
            } else {
                timer?.pause()
                accelerometer?.pause()
            }
            isTimerPaused.postValue(!isTimerPaused.value!!)
        }
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        application = activity!!.application
        return binding.root
    }

    private fun subscribeUI(binding: FragmentRunWorkoutBinding) {
        val navController = findNavController()
        runWorkoutModel.exercise.observe(viewLifecycleOwner, Observer { exercise ->
            timer?.cancel()
            accelerometer?.stop()
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
        timer!!.start()
        accelerometer = createAccelerometer(exercise)
        accelerometer!!.start()
        binding.exercise = exercise
        binding.image.setImageResource(exercise.resourceId)
        binding.isPaused = isTimerPaused
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    private fun createTimer(binding: FragmentRunWorkoutBinding, durationSeconds: Int) =
        PlayPauseTimer(
            millisInFuture = durationSeconds * MILLIS_IN_SECOND,
            onFinish = {
                runWorkoutModel.exerciseCompleted()
                next()
            },
            onTick = { millisUntilFinished, percent ->
                val secondsUntilFinished = millisUntilFinished / MILLIS_IN_SECOND
                binding.progress.progress = percent.toFloat()
                binding.timeText.text = secondsUntilFinished.toInt().toString()
            }
        )

    private fun createAccelerometer(exercise: Exercise) =
        Accelerometer(
            application,
            StrategyFactory.create(exercise.strategyX, exercise.phaseX, exercise.amplitudeX),
            StrategyFactory.create(exercise.strategyY, exercise.phaseY, exercise.amplitudeY),
            StrategyFactory.create(exercise.strategyZ, exercise.phaseZ, exercise.amplitudeZ),
            evalStrategy
        )

    private val evalStrategy: (strategy: StrategyStatus) -> Unit = {
        if (it != StrategyStatus.OK) {
            val vibratorService = application
                .getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(longArrayOf(0, 200), -1)
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun next() {
        Log.d("Accelerometer", "Count=${accelerometer?.counter}")
        runWorkoutModel.next()
    }

    override fun onStart() {
        super.onStart()
        timer?.start()
        accelerometer?.start()
    }

    override fun onStop() {
        super.onStop()
        timer?.pause()
        accelerometer?.pause()
    }
}
