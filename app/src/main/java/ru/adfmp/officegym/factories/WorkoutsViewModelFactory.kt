package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.models.WorkoutsViewModel

class WorkoutsViewModelFactory(
    private val workoutRepository: WorkoutRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkoutsViewModel(workoutRepository) as T
    }
}
