package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.databinding.ListItemExerciseBinding

class ExerciseViewHolder(
    private val binding: ListItemExerciseBinding
) : BaseViewHolder<Exercise>(binding) {

    override fun bind(item: Exercise) {
        binding.apply {
            exercise = item
            image.setImageResource(item.resourceId)
            root.setOnClickListener {
                //TODO: add click listener
            }
            executePendingBindings()
        }
    }
}

fun createExerciseViewHolder(parent: ViewGroup) = ExerciseViewHolder(
    ListItemExerciseBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
)
