package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.ListItemExerciseBinding
import ru.adfmp.officegym.ui.EditWorkoutFragmentDirections


class EditExerciseInWorkoutViewHolder(
    private val binding: ListItemExerciseBinding
) : BaseViewHolder<Exercise>(binding) {

    override fun bind(item: Exercise) {
        binding.apply {
            exercise = item
            root.setOnClickListener {
                val direction = EditWorkoutFragmentDirections
                    .actionNavEditWorkoutToNavEditExerciseInWorkoutFragment(
                        item.id,
                        item.baseId,
                        item.workoutId
                    )
                it.findNavController().navigate(direction)
            }
            executePendingBindings()
        }
    }
}

fun createEditExerciseInWorkoutViewHolder(parent: ViewGroup) = EditExerciseInWorkoutViewHolder(
    ListItemExerciseBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
)
