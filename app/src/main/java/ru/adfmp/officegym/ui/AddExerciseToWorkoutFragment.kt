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
import ru.adfmp.officegym.adapters.callbacks.BaseExerciseDiffCallback
import ru.adfmp.officegym.adapters.viewholders.AddExerciseToWorkoutViewHolder
import ru.adfmp.officegym.adapters.viewholders.createAddExerciseToWorkoutViewHolder
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.databinding.FragmentAddExerciseToWorkoutBinding
import ru.adfmp.officegym.models.BaseExercisesViewModel
import ru.adfmp.officegym.utils.InjectorUtils

class AddExerciseToWorkoutFragment : Fragment() {

    private val args: AddExerciseToWorkoutFragmentArgs by navArgs()

    private val viewModel: BaseExercisesViewModel by viewModels {
        InjectorUtils.provideBaseExercisesViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddExerciseToWorkoutBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = getBaseExerciseAdapter()
        binding.editExercisesList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun getBaseExerciseAdapter() =
        BaseAdapter(
            { p -> createAddExerciseToWorkoutViewHolder(p, args.workoutId) },
            BaseExerciseDiffCallback()
        )


    private fun subscribeUi(
        adapter: BaseAdapter<BaseExercise, AddExerciseToWorkoutViewHolder>
    ) {
        viewModel.baseExercises.observe(viewLifecycleOwner) { exercise ->
            adapter.submitList(exercise)
        }
    }
}
