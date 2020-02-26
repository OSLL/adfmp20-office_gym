package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.BaseExerciseRepository
import ru.adfmp.officegym.models.BaseExercisesViewModel

class EditAllExercisesViewModelFactory(
    private val baseExerciseRepository: BaseExerciseRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BaseExercisesViewModel(baseExerciseRepository) as T
    }
}
