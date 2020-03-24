package ru.adfmp.officegym.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import ru.adfmp.officegym.R
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.databinding.ListItemWorkoutBinding
import ru.adfmp.officegym.models.EditAlarmViewModel


class AddWorkoutToAlarmViewHolder(
    private val binding: ListItemWorkoutBinding,
    private val viewModel: EditAlarmViewModel
) : BaseViewHolder<Workout>(binding) {

    override fun bind(item: Workout) {
        binding.apply {
            workout = item
            image.setImageResource(
                item.exercises.firstOrNull()?.resourceId ?: R.drawable.icon_005_exercise
            )
            root.setOnClickListener {
                viewModel.workout = item
                it.findNavController().popBackStack()
            }
            executePendingBindings()
        }
    }
}

fun createAddWorkoutToAlarmViewHolder(parent: ViewGroup, viewModel: EditAlarmViewModel) =
    AddWorkoutToAlarmViewHolder(
        ListItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), viewModel
    )
