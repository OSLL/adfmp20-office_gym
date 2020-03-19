package ru.adfmp.officegym.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.adfmp.officegym.R
import ru.adfmp.officegym.databinding.FragmentEditExerciseInWorkoutBinding
import ru.adfmp.officegym.models.EditExerciseInWorkoutViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class EditExerciseInWorkoutFragment : Fragment() {

    private val args: EditExerciseInWorkoutFragmentArgs by navArgs()

    private val viewModel: EditExerciseInWorkoutViewModel by viewModels {
        InjectorUtils.provideEditExerciseInWorkoutFactory(
            this,
            args.exerciseInWorkoutId,
            args.baseExerciseId,
            args.workoutId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditExerciseInWorkoutBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(binding)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentEditExerciseInWorkoutBinding) {
        binding.durationPicker.minValue = 1
        binding.durationPicker.maxValue = 300
        viewModel.exerciseInWorkout.observe(viewLifecycleOwner) { exercise ->
            binding.exerciseInWorkout = exercise
            viewModel.baseExercise.observe(viewLifecycleOwner) { baseExercise ->
                if (exercise != null && exercise.duration == 0) {
                    if (baseExercise != null) {
                        exercise.duration = baseExercise.recommendedDuration
                        binding.durationPicker.value = baseExercise.recommendedDuration
                    }
                }
            }
        }
        viewModel.baseExercise.observe(viewLifecycleOwner) { exercise ->
            binding.exercise = exercise
            binding.editExercisesImage.setImageResource(
                exercise?.resourceId ?: R.drawable.icon_005_exercise
            )
        }
        binding.save.setOnClickListener {
            if (viewModel.insertOrUpdateExercise()) {
                exit()
            } else {
                Toast.makeText(
                    context, "Exercise with such name has already exists.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.cancel.setOnClickListener {
            exit()
        }
        binding.delete.setOnClickListener {
            viewModel.deleteExercise()
            exit()
        }
    }

    private fun exit() {
        findNavController().popBackStack()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard(activity!!.window.decorView.rootView)
    }

    private fun hideKeyboard(view: View) =
        getInputMethodManager().hideSoftInputFromWindow(view.windowToken, 0)

    private fun getInputMethodManager() =
        context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}
