package ru.adfmp.officegym.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.database.repositories.WorkoutRepository

class StartWorkoutViewModel(workoutRepository: WorkoutRepository, workoutId: Long) : ViewModel() {
    val workout: LiveData<Workout?> = workoutRepository.getWorkoutById(workoutId)
}
