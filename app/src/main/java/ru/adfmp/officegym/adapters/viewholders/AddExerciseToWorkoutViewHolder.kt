package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.databinding.ListItemBaseExerciseBinding
import ru.adfmp.officegym.ui.AddExerciseToWorkoutFragmentDirections


class AddExerciseToWorkoutViewHolder(
    private val binding: ListItemBaseExerciseBinding,
    private val workoutId: Long
) : BaseViewHolder<BaseExercise>(binding) {

    override fun bind(item: BaseExercise) {
        binding.apply {
            exercise = item
            root.setOnClickListener {
                val direction = AddExerciseToWorkoutFragmentDirections
                    .actionAddExerciseToWorkoutToNavEditExerciseInWorkoutFragment(
                        workoutId = workoutId,
                        baseExerciseId = item.id
                    )
                it.findNavController().navigate(direction)
            }
            executePendingBindings()
        }
    }
}

fun createAddExerciseInWorkoutViewHolder(parent: ViewGroup, workoutId: Long) =
    AddExerciseToWorkoutViewHolder(
        ListItemBaseExerciseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), workoutId
    )
