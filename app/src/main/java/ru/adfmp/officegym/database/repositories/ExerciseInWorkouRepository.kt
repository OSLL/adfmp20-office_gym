package ru.adfmp.officegym.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.database.ExerciseInWorkout
import ru.adfmp.officegym.database.GymDao

class ExerciseInWorkoutRepository private constructor(private val gymDao: GymDao) {

    suspend fun insertOrUpdateExerciseInWorkout(exercise: ExerciseInWorkout) =
        if (exercise.id == 0L)
            gymDao.insert(exercise)[0] != -1L
        else
            gymDao.update(exercise) != 0

    suspend fun deleteExerciseInWorkout(exercise: ExerciseInWorkout) = gymDao.delete(exercise)

    fun getEditableExerciseInWorkoutById(
        id: Long,
        exerciseId: Long,
        workoutId: Long
    ): LiveData<ExerciseInWorkout?> {
        if (id == 0L) {
            return MutableLiveData(
                ExerciseInWorkout(
                    exerciseId = exerciseId,
                    workoutId = workoutId
                )
            )
        }
        return gymDao.getExerciseInWorkoutById(id)
    }

    fun getBaseExerciseById(id: Long): LiveData<BaseExercise?> {
        return gymDao.getBaseExerciseById(id)
    }

    fun getAllBaseExercises(): LiveData<List<BaseExercise>> {
        return gymDao.getAllBaseExercises()
    }

    companion object {

        @Volatile
        private var instance: ExerciseInWorkoutRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: ExerciseInWorkoutRepository(gymDao).also { instance = it }
            }
    }
}
