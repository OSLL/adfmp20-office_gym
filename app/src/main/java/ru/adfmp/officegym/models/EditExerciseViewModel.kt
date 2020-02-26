package ru.adfmp.officegym.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.database.repositories.BaseExerciseRepository

class EditExerciseViewModel(
    private val baseExerciseRepository: BaseExerciseRepository,
    exerciseId: Long
) :
    ViewModel() {
    val baseExercise: LiveData<BaseExercise?> =
        baseExerciseRepository.getEditableBaseExerciseById(exerciseId)

    fun insertOrUpdateExercise(): Boolean {
        var status = true
        runBlocking {
            launch(coroutineContext) {
                status = baseExerciseRepository.insertOrUpdateBaseExercise(baseExercise.value!!)
            }
        }
        return status
    }

    fun deleteExercise() {
        runBlocking {
            launch(coroutineContext) {
                baseExerciseRepository.deleteBaseExercise(baseExercise.value!!)
            }
        }
    }
}
