package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.databinding.ListItemWorkoutBinding
import ru.adfmp.officegym.ui.HomeFragmentDirections

class WorkoutViewHolder(
    private val binding: ListItemWorkoutBinding
) : BaseViewHolder<Workout>(binding) {

    override fun bind(item: Workout) {
        binding.apply {
            workout = item
            root.setOnClickListener {
                val direction = HomeFragmentDirections
                    .actionNavHomeToNavStartWorkoutFragment(item.workoutInfo.id)
                it.findNavController().navigate(direction)
            }
            executePendingBindings()
        }
    }
}

fun createWorkoutViewHolder(parent: ViewGroup) = WorkoutViewHolder(
    ListItemWorkoutBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
)
