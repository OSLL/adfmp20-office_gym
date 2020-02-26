package ru.adfmp.officegym.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.database.repositories.WorkoutRepository

class EditWorkoutViewModel(private val workoutRepository: WorkoutRepository, workoutId: Long) :
    ViewModel() {
    var workout: LiveData<Workout?> = workoutRepository.getEditableWorkoutById(workoutId)

    fun insertWorkout(): Boolean {
        var status = true
        runBlocking {
            launch(coroutineContext) {
                status = workoutRepository.insertOrUpdateWorkoutInfo(workout.value!!.workoutInfo)
            }
        }
        return status
    }

    fun deleteWorkout() {
        runBlocking {
            launch(coroutineContext) {
                workoutRepository.deleteWorkoutInfo(workout.value!!.workoutInfo)
            }
        }
    }
}
