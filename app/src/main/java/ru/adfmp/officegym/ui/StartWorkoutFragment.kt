package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import ru.adfmp.officegym.adapters.BaseAdapter
import ru.adfmp.officegym.adapters.callbacks.ExerciseDiffCallback
import ru.adfmp.officegym.adapters.viewholders.ExerciseViewHolder
import ru.adfmp.officegym.adapters.viewholders.createExerciseViewHolder
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.FragmentStartWorkoutBinding
import ru.adfmp.officegym.models.StartWorkoutViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class StartWorkoutFragment : Fragment() {

    private val args: StartWorkoutFragmentArgs by navArgs()

    private val viewModel: StartWorkoutViewModel by viewModels {
        InjectorUtils.provideStartViewModelFactory(this, args.workoutId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStartWorkoutBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getExerciseAdapter()
        binding.exerciseList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getExerciseAdapter() = BaseAdapter(
        { p -> createExerciseViewHolder(p) },
        ExerciseDiffCallback()
    )

    private fun subscribeUi(adapter: BaseAdapter<Exercise, ExerciseViewHolder>) {
        viewModel.workout.observe(viewLifecycleOwner) { workout ->
            adapter.submitList(workout!!.exercises)
        }
    }
}
