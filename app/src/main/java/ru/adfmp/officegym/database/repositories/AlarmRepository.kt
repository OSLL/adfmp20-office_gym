package ru.adfmp.officegym.database.repositories

import ru.adfmp.officegym.database.GymDao

class AlarmRepository private constructor(private val gymDao: GymDao) {

    fun getAllAlarms() = gymDao.getAllAlarms()

    companion object {

        @Volatile
        private var instance: AlarmRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: AlarmRepository(gymDao).also { instance = it }
            }
    }
}
