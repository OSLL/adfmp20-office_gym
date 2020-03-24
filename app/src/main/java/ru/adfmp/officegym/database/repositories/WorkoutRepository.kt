package ru.adfmp.officegym.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.adfmp.officegym.database.GymDao
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.database.WorkoutInfo

class WorkoutRepository private constructor(private val gymDao: GymDao) {

    suspend fun insertOrUpdateWorkoutInfo(workoutInfo: WorkoutInfo) =
        if (workoutInfo.id == 0L)
            gymDao.insert(workoutInfo)[0] != -1L
        else
            gymDao.update(workoutInfo) != 0

    suspend fun deleteWorkoutInfo(workoutInfo: WorkoutInfo) =
        gymDao.delete(workoutInfo)

    fun getAllWorkouts() = gymDao.getAllWorkouts()

    fun getWorkoutById(id: Long) = gymDao.getWorkoutById(id)

    suspend fun getSuspendWorkoutById(id: Long) = gymDao.getSuspendWorkoutById(id)

    fun getEditableWorkoutById(id: Long): LiveData<Workout?> {
        if (id == 0L) {
            return MutableLiveData(Workout())
        }
        return gymDao.getWorkoutById(id)
    }

    companion object {

        @Volatile
        private var instance: WorkoutRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: WorkoutRepository(gymDao).also { instance = it }
            }
    }
}
