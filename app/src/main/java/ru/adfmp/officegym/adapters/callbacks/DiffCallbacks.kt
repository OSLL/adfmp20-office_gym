package ru.adfmp.officegym.adapters.callbacks

import androidx.recyclerview.widget.DiffUtil
import ru.adfmp.officegym.database.Alarm
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.database.Workout

class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {

    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) = oldItem == newItem
}

class BaseExerciseDiffCallback : DiffUtil.ItemCallback<BaseExercise>() {

    override fun areItemsTheSame(oldItem: BaseExercise, newItem: BaseExercise) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BaseExercise, newItem: BaseExercise) =
        oldItem == newItem
}

class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {

    override fun areItemsTheSame(oldItem: Workout, newItem: Workout) =
        oldItem.workoutInfo.id == newItem.workoutInfo.id

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout) = oldItem == newItem
}

class AlarmDiffCallback : DiffUtil.ItemCallback<Alarm>() {

    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm) = oldItem == newItem
}
