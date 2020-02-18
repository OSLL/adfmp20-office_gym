package ru.adfmp.officegym.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.databinding.ListItemWorkoutBinding
import ru.adfmp.officegym.ui.home.HomeFragmentDirections

class WorkoutAdapter :
    ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WorkoutViewHolder(
        ListItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }

    class WorkoutViewHolder(
        private val binding: ListItemWorkoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Workout) {
            binding.apply {
                workout = item
                root.setOnClickListener {
                    val direction = HomeFragmentDirections
                        .actionNavigationHomeToNavigationStartWorkoutFragment(item.workoutInfo.id)
                    it.findNavController().navigate(direction)
                }
                executePendingBindings()
            }
        }
    }
}

private class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {

    override fun areItemsTheSame(oldItem: Workout, newItem: Workout) =
        oldItem.workoutInfo.id == newItem.workoutInfo.id

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout) = oldItem == newItem
}
