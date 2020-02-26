package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.ExerciseInWorkoutRepository
import ru.adfmp.officegym.models.EditExerciseInWorkoutViewModel

class EditExerciseInWorkoutViewModelFactory(
    private val exerciseInWorkoutRepository: ExerciseInWorkoutRepository,
    private val exerciseInWorkoutId: Long,
    private val baseExerciseId: Long,
    private val workoutId: Long
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditExerciseInWorkoutViewModel(
            exerciseInWorkoutRepository,
            exerciseInWorkoutId,
            baseExerciseId,
            workoutId
        ) as T
    }
}
