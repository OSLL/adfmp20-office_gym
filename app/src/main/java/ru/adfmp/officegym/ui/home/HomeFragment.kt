package ru.adfmp.officegym.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import ru.adfmp.officegym.adapters.WorkoutAdapter
import ru.adfmp.officegym.databinding.FragmentHomeBinding
import ru.adfmp.officegym.models.WorkoutViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class HomeFragment : Fragment() {

    private val viewModel: WorkoutViewModel by viewModels {
        InjectorUtils.provideWorkoutViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = WorkoutAdapter()
        binding.workoutList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: WorkoutAdapter) {
        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            adapter.submitList(workouts)
        }
    }
}
