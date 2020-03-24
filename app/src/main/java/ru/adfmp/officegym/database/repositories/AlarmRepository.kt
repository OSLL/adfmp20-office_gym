package ru.adfmp.officegym.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.adfmp.officegym.database.BaseAlarm
import ru.adfmp.officegym.database.GymDao

class AlarmRepository private constructor(private val gymDao: GymDao) {

    fun getAllAlarms() = gymDao.getAllAlarms()

    fun getAllAlarmViews() = gymDao.getAllAlarmViews()

    suspend fun insertOrUpdateAlarm(baseAlarm: BaseAlarm) =
        if (baseAlarm.id == 0L)
            gymDao.insert(baseAlarm)[0] != -1L
        else
            gymDao.update(baseAlarm) != 0

    suspend fun deleteAlarm(baseAlarm: BaseAlarm) =
        gymDao.delete(baseAlarm)

    suspend fun getAlarmByName(name: String) = gymDao.getAlarmByName(name)

    fun getAlarmById(id: Long): LiveData<BaseAlarm?> {
        if (id == 0L) {
            if (id == 0L) {
                return MutableLiveData(
                    BaseAlarm()
                )
            }
        }
        return gymDao.getAlarmById(id)
    }

    companion object {

        @Volatile
        private var instance: AlarmRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: AlarmRepository(gymDao).also { instance = it }
            }
    }
}
