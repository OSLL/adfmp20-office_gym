package ru.adfmp.officegym.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.database.ExerciseInWorkout
import ru.adfmp.officegym.database.repositories.ExerciseInWorkoutRepository

class EditExerciseInWorkoutViewModel(
    private val exerciseInWorkoutRepository: ExerciseInWorkoutRepository,
    exerciseInWorkoutId: Long, baseExerciseId: Long, workoutId: Long
) : ViewModel() {

    var exerciseInWorkout: LiveData<ExerciseInWorkout?> =
        exerciseInWorkoutRepository.getEditableExerciseInWorkoutById(
            exerciseInWorkoutId,
            baseExerciseId,
            workoutId
        )

    var baseExercise: LiveData<BaseExercise?> =
        exerciseInWorkoutRepository.getBaseExerciseById(baseExerciseId)

    fun insertOrUpdateExercise(): Boolean {
        var status = true
        runBlocking {
            launch(coroutineContext) {
                status =
                    exerciseInWorkoutRepository.insertOrUpdateExerciseInWorkout(exerciseInWorkout.value!!)
            }
        }
        return status
    }

    fun deleteExercise() {
        runBlocking {
            launch(coroutineContext) {
                exerciseInWorkoutRepository.deleteExerciseInWorkout(exerciseInWorkout.value!!)
            }
        }
    }
}
