package ru.adfmp.officegym.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.database.repositories.WorkoutRepository

class WorkoutViewModel(workoutRepository: WorkoutRepository) : ViewModel() {
    val workouts: LiveData<List<Workout>> = workoutRepository.getAllWorkouts()
}
