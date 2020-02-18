package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.models.StartWorkoutViewModel

class StartWorkoutViewModelFactory(
    private val workoutRepository: WorkoutRepository,
    private val workoutId: Long
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StartWorkoutViewModel(workoutRepository, workoutId) as T
    }
}
