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
import ru.adfmp.officegym.databinding.FragmentEditExerciseBinding
import ru.adfmp.officegym.models.EditExerciseViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class EditExerciseFragment : Fragment() {

    private val args: EditExerciseFragmentArgs by navArgs()

    private val viewModel: EditExerciseViewModel by viewModels {
        InjectorUtils.provideEditExerciseViewModelFactory(this, args.exerciseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditExerciseBinding.inflate(inflater, container, false)
        context ?: return binding.root

        subscribeUi(binding)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentEditExerciseBinding) {
        binding.recommendedDurationPicker.minValue = 1
        binding.recommendedDurationPicker.maxValue = 300
        binding.intensityPicker.minValue = 1
        binding.intensityPicker.maxValue = 10
        viewModel.baseExercise.observe(viewLifecycleOwner) { exercise ->
            binding.exercise = exercise
            binding.editExercisesImage.setImageResource(
                exercise?.resourceId ?: R.drawable.icon_005_exercise
            )
            if (exercise != null && exercise.recommendedDuration == 0) {
                exercise.recommendedDuration = 30
                binding.recommendedDurationPicker.value = 30
                exercise.intensity = 3
                binding.intensityPicker.value = 3
            }
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
