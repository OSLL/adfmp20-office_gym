package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.models.EditWorkoutViewModel

class EditWorkoutViewModelFactory(
    private val workoutRepository: WorkoutRepository,
    private val workoutId: Long
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditWorkoutViewModel(workoutRepository, workoutId) as T
    }
}
