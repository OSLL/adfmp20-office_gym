package ru.adfmp.officegym.database.repositories

import ru.adfmp.officegym.database.GymDao
import ru.adfmp.officegym.database.Statistic
import java.text.SimpleDateFormat
import java.util.*

class StatisticsRepository private constructor(private val gymDao: GymDao) {

    suspend fun getStatistic(date: Long = currentDay()) =
        gymDao.selectStatistic(date) ?: Statistic(date, 0, 0, 0)

    fun getAllStatistics() = gymDao.getAllStatistic()

    suspend fun insertStatistics(vararg statistic: Statistic) = gymDao.insert(*statistic)

    companion object {
        fun currentDay(): Long {
            val localCalendar = GregorianCalendar.getInstance()
            val year = localCalendar.get(Calendar.YEAR)
            val month = localCalendar.get(Calendar.MONTH)
            val day = localCalendar.get(Calendar.DAY_OF_MONTH)
            return dateToLong(year, month, day)
        }

        private fun dateToLong(year: Int, month: Int, day: Int): Long {
            val utcCalendar = GregorianCalendar(TimeZone.getTimeZone("UTC"))
            utcCalendar.set(Calendar.YEAR, year)
            utcCalendar.set(Calendar.MONTH, month)
            utcCalendar.set(Calendar.DAY_OF_MONTH, day)
            utcCalendar.set(Calendar.HOUR_OF_DAY, 12)
            utcCalendar.set(Calendar.MINUTE, 0)
            utcCalendar.set(Calendar.SECOND, 0)
            utcCalendar.set(Calendar.MILLISECOND, 0)
            return utcCalendar.timeInMillis
        }

        private val dateFormat = SimpleDateFormat("MMM-dd", Locale.ENGLISH)
        fun dateToString(date: Long): String = dateFormat.format(Date(date))
        fun dateToYearMonthDay(date: Long): List<Int> {
            val utcCalendar = GregorianCalendar(TimeZone.getTimeZone("UTC"))
            utcCalendar.timeInMillis = date
            val year = utcCalendar.get(Calendar.YEAR)
            val month = utcCalendar.get(Calendar.MONTH)
            val day = utcCalendar.get(Calendar.DAY_OF_MONTH)
            return listOf(year, month, day)
        }


        @Volatile
        private var instance: StatisticsRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: StatisticsRepository(gymDao).also { instance = it }
            }
    }
}
