package ru.adfmp.officegym.database

import android.content.Context

class DatabaseController(context: Context) {
    private val db = AppDatabase.getInstance(context).dao()

    suspend fun insertExercise(exercise: Exercise) = db.insert(exercise)

    suspend fun insertWorkout(workoutInfo: WorkoutInfo) =
        db.insert(workoutInfo)

    suspend fun insertExerciseInWorkout(exerciseInWorkout: ExerciseInWorkout) =
        db.insert(exerciseInWorkout)

    fun getAllExercises() = db.getAllExercises()

    fun getAllWorkouts() = db.getAllWorkouts()
}
