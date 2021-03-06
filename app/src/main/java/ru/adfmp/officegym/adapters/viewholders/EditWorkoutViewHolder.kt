package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.adfmp.officegym.R
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.databinding.ListItemWorkoutBinding
import ru.adfmp.officegym.ui.EditAllWorkoutsFragmentDirections

class EditWorkoutViewHolder(
    private val binding: ListItemWorkoutBinding
) : BaseViewHolder<Workout>(binding) {

    override fun bind(item: Workout) {
        binding.apply {
            workout = item
            image.setImageResource(
                item.exercises.firstOrNull()?.resourceId ?: R.drawable.icon_005_exercise
            )
            root.setOnClickListener {
                val direction = EditAllWorkoutsFragmentDirections
                    .actionNavEditAllWorkoutsToNavEditWorkoutFragment(item.workoutInfo.id)
                it.findNavController().navigate(direction)
            }
            executePendingBindings()
        }
    }
}

fun createEditWorkoutViewHolder(parent: ViewGroup) = EditWorkoutViewHolder(
    ListItemWorkoutBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
)
