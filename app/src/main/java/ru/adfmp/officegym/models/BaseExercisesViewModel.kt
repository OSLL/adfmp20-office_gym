package ru.adfmp.officegym.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.database.repositories.BaseExerciseRepository

class BaseExercisesViewModel(exercisesRepositoryBase: BaseExerciseRepository) : ViewModel() {
    val baseExercises: LiveData<List<BaseExercise>> = exercisesRepositoryBase.getAllBaseExercises()
}
