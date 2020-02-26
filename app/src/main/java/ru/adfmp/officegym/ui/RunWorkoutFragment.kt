package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_run_workout.view.*
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.FragmentRunWorkoutBinding
import ru.adfmp.officegym.models.RunWorkoutViewModel
import ru.adfmp.officegym.utils.PlayPauseTimer
import ru.adfmp.officegym.utils.PlayPauseTimer.Companion.MILLIS_IN_SECOND

class RunWorkoutFragment : Fragment() {
    private var timer: PlayPauseTimer? = null
    private val runWorkoutModel: RunWorkoutViewModel by activityViewModels()
    private val isTimerPaused = MutableLiveData(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRunWorkoutBinding.inflate(inflater, container, false)

        subscribeUI(binding)

        binding.root.next_exercise_button.setOnClickListener {
            runWorkoutModel.next()
        }
        binding.root.pause_button.setOnClickListener {
            if (isTimerPaused.value!!) {
                timer?.play()
            } else {
                timer?.pause()
            }
            isTimerPaused.postValue(!isTimerPaused.value!!)
        }

        return binding.root
    }

    private fun subscribeUI(binding: FragmentRunWorkoutBinding) {
        val navController = findNavController()
        runWorkoutModel.exercise.observe(viewLifecycleOwner, Observer { exercise ->
            timer?.cancel()
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
        binding.exercise = exercise
        binding.isPaused = isTimerPaused
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    private fun createTimer(binding: FragmentRunWorkoutBinding, durationSeconds: Int) =
        PlayPauseTimer(
            millisInFuture = durationSeconds * MILLIS_IN_SECOND,
            onFinish = { runWorkoutModel.next() },
            onTick = { millisUntilFinished, percent ->
                val secondsUntilFinished = millisUntilFinished / MILLIS_IN_SECOND
                binding.progress.progress = percent.toFloat()
                binding.timeText.text = secondsUntilFinished.toInt().toString()
            }
        )

}
