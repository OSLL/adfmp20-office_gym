package ru.adfmp.officegym.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.ListItemExerciseBinding


class ExerciseAdapter :
    ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExerciseViewHolder(
        ListItemExerciseBinding
            .inflate(
                LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }

    class ExerciseViewHolder(
        private val binding: ListItemExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Exercise) {
            binding.apply {
                exercise = item
                root.setOnClickListener {
                    //TODO: add navigation
                }
                executePendingBindings()
            }
        }
    }
}

private class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {

    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem == newItem
}
