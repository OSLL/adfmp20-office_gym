package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.BaseExerciseRepository
import ru.adfmp.officegym.models.EditExerciseViewModel

class EditExerciseViewModelFactory(
    private val baseExerciseRepository: BaseExerciseRepository,
    private val exerciseId: Long
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditExerciseViewModel(baseExerciseRepository, exerciseId) as T
    }
}
