package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.models.WorkoutViewModel

class WorkoutViewModelFactory(
    private val workoutRepository: WorkoutRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkoutViewModel(workoutRepository) as T
    }
}
