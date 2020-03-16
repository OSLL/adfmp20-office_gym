package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.databinding.ListItemBaseExerciseBinding
import ru.adfmp.officegym.ui.EditAllExercisesFragmentDirections


class EditBaseExerciseViewHolder(
    private val binding: ListItemBaseExerciseBinding
) : BaseViewHolder<BaseExercise>(binding) {

    override fun bind(item: BaseExercise) {
        binding.apply {
            exercise = item
            image.setImageResource(item.resourceId)
            root.setOnClickListener {
                val direction = EditAllExercisesFragmentDirections
                    .actionNavEditAllExercisesToNavEditExerciseFragment(item.id)
                it.findNavController().navigate(direction)
            }
            executePendingBindings()
        }
    }
}

fun createBaseExerciseViewHolder(parent: ViewGroup) = EditBaseExerciseViewHolder(
    ListItemBaseExerciseBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
)
