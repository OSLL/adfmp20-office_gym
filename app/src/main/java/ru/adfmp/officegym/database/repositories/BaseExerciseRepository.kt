package ru.adfmp.officegym.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.adfmp.officegym.R
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.database.GymDao

class BaseExerciseRepository private constructor(private val gymDao: GymDao) {

    suspend fun insertOrUpdateBaseExercise(baseExercise: BaseExercise) =
        if (baseExercise.id == 0L)
            gymDao.insert(baseExercise)[0] != -1L
        else
            gymDao.update(baseExercise) != 0

    suspend fun deleteBaseExercise(baseExercise: BaseExercise) = gymDao.delete(baseExercise)

    fun getAllBaseExercises() = gymDao.getAllBaseExercises()

    fun getEditableBaseExerciseById(id: Long): LiveData<BaseExercise?> {
        if (id == 0L) {
            return MutableLiveData(BaseExercise(resourceId = R.drawable.icon_005_exercise))
        }
        return gymDao.getBaseExerciseById(id)
    }

    companion object {

        @Volatile
        private var instance: BaseExerciseRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: BaseExerciseRepository(gymDao).also { instance = it }
            }
    }
}
