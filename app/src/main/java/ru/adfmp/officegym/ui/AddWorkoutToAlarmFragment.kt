package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import ru.adfmp.officegym.adapters.BaseAdapter
import ru.adfmp.officegym.adapters.callbacks.WorkoutDiffCallback
import ru.adfmp.officegym.adapters.viewholders.AddWorkoutToAlarmViewHolder
import ru.adfmp.officegym.adapters.viewholders.createAddWorkoutToAlarmViewHolder
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.databinding.FragmentAddWorkoutToAlarmBinding
import ru.adfmp.officegym.models.EditAlarmViewModel

class AddWorkoutToAlarmFragment : Fragment() {

    private val viewModel: EditAlarmViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddWorkoutToAlarmBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getEditWorkoutAdapter()
        binding.workoutsList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun getEditWorkoutAdapter() =
        BaseAdapter({ p -> createAddWorkoutToAlarmViewHolder(p, viewModel) }, WorkoutDiffCallback())


    private fun subscribeUi(
        adapter: BaseAdapter<Workout, AddWorkoutToAlarmViewHolder>
    ) {
        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            adapter.submitList(workouts)
        }
    }
}
