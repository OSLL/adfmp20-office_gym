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
import ru.adfmp.officegym.adapters.BaseAdapter
import ru.adfmp.officegym.adapters.callbacks.ExerciseDiffCallback
import ru.adfmp.officegym.adapters.viewholders.EditExerciseInWorkoutViewHolder
import ru.adfmp.officegym.adapters.viewholders.createEditExerciseInWorkoutViewHolder
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.FragmentEditWorkoutBinding
import ru.adfmp.officegym.models.EditWorkoutViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class EditWorkoutFragment : Fragment() {

    private val args: EditWorkoutFragmentArgs by navArgs()

    private val viewModel: EditWorkoutViewModel by viewModels {
        InjectorUtils.provideEditWorkoutViewModelFactory(this, args.workoutId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditWorkoutBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getExerciseAdapter()
        binding.editExerciseList.adapter = adapter
        subscribeUi(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun getExerciseAdapter() = BaseAdapter(
        { p -> createEditExerciseInWorkoutViewHolder(p) },
        ExerciseDiffCallback()
    )

    private fun subscribeUi(
        binding: FragmentEditWorkoutBinding,
        adapter: BaseAdapter<Exercise, EditExerciseInWorkoutViewHolder>
    ) {
        viewModel.workout.observe(viewLifecycleOwner) { workout ->
            binding.workout = workout
            adapter.submitList(workout?.exercises ?: emptyList())
        }
        binding.save.setOnClickListener {
            if (viewModel.insertWorkout()) {
                exit()
            } else {
                Toast.makeText(
                    context, "Workout with such name has already exists.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.cancel.setOnClickListener {
            exit()
        }
        binding.delete.setOnClickListener {
            viewModel.deleteWorkout()
            exit()
        }
        binding.addExerciseButton.setOnClickListener {
            val direction = EditWorkoutFragmentDirections.actionNavEditWorkoutToAddExerciseToWorkoutFragment(args.workoutId)
            findNavController().navigate(direction)
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
