package ru.adfmp.officegym.database.repositories

import ru.adfmp.officegym.database.ExerciseInWorkout
import ru.adfmp.officegym.database.GymDao
import ru.adfmp.officegym.database.WorkoutInfo

class WorkoutRepository private constructor(private val gymDao: GymDao) {

    suspend fun insertWorkout(workoutInfo: WorkoutInfo) =
        gymDao.insert(workoutInfo)

    suspend fun insertExerciseInWorkout(exerciseInWorkout: ExerciseInWorkout) =
        gymDao.insert(exerciseInWorkout)

    fun getAllExercises() = gymDao.getAllBaseExercises()

    fun getAllWorkouts() = gymDao.getAllWorkouts()

    fun getWorkoutById(id: Long) = gymDao.getWorkoutById(id)

    companion object {

        @Volatile
        private var instance: WorkoutRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: WorkoutRepository(gymDao).also { instance = it }
            }
    }
}
