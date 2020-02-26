package ru.adfmp.officegym.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.database.Workout

class RunWorkoutViewModel : ViewModel() {
    val exercise = MutableLiveData<Exercise?>()
    private var index = 0
    private var workout: Workout? = null

    fun setWorkout(workout: Workout) {
        require(workout.exercises.isNotEmpty())
        this.workout = workout
        index = 0
        exercise.postValue(workout.exercises[index])
    }

    fun next() {
        checkNotNull(workout)
        if (index + 1 >= workout!!.exercises.size) {
            exercise.postValue(null)
        } else {
            index++
            exercise.postValue(workout!!.exercises[index])
        }
    }
}
