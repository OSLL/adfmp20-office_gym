package ru.adfmp.officegym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import ru.adfmp.officegym.adapters.BaseAdapter
import ru.adfmp.officegym.adapters.callbacks.WorkoutDiffCallback
import ru.adfmp.officegym.adapters.viewholders.EditWorkoutViewHolder
import ru.adfmp.officegym.adapters.viewholders.createEditWorkoutViewHolder
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.databinding.FragmentEditAllWorkoutsBinding
import ru.adfmp.officegym.models.WorkoutsViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class EditAllWorkoutsFragment : Fragment() {

    private val viewModel: WorkoutsViewModel by viewModels {
        InjectorUtils.provideWorkoutsViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditAllWorkoutsBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getEditWorkoutAdapter()
        binding.editWorkoutsList.adapter = adapter
        subscribeUi(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun getEditWorkoutAdapter() =
        BaseAdapter({ p -> createEditWorkoutViewHolder(p) }, WorkoutDiffCallback())


    private fun subscribeUi(
        binding: FragmentEditAllWorkoutsBinding,
        adapter: BaseAdapter<Workout, EditWorkoutViewHolder>
    ) {
        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            adapter.submitList(workouts)
        }
        binding.addWorkoutButton.setOnClickListener {
            val direction = EditAllWorkoutsFragmentDirections
                .actionNavEditAllWorkoutsToNavEditWorkoutFragment()
            it.findNavController().navigate(direction)
        }
    }
}
