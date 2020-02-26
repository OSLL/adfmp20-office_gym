package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import ru.adfmp.officegym.adapters.BaseAdapter
import ru.adfmp.officegym.adapters.callbacks.WorkoutDiffCallback
import ru.adfmp.officegym.adapters.viewholders.WorkoutViewHolder
import ru.adfmp.officegym.adapters.viewholders.createWorkoutViewHolder
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.databinding.FragmentHomeBinding
import ru.adfmp.officegym.models.WorkoutsViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class HomeFragment : Fragment() {

    private val viewModel: WorkoutsViewModel by viewModels {
        InjectorUtils.provideWorkoutsViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getWorkoutAdapter()
        binding.workoutList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getWorkoutAdapter() =
        BaseAdapter(
            { p -> createWorkoutViewHolder(p) },
            WorkoutDiffCallback()
        )

    private fun subscribeUi(adapter: BaseAdapter<Workout, WorkoutViewHolder>) {
        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            adapter.submitList(workouts)
        }
    }
}
