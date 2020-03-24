package ru.adfmp.officegym.utils.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.adfmp.officegym.database.BaseAlarm
import ru.adfmp.officegym.database.converters.Converters
import java.util.*

class MyAlarmManager private constructor(context: Context) {
    private val alarmManager = getAlarmManager(context)

    fun addAlarm(context: Context, alarm: BaseAlarm) {
        val id = alarm.id
        val workoutId = alarm.workoutId
        val intentAlarm = Intent(context, AlarmNotification::class.java)
        intentAlarm.putExtra("workout_id", workoutId)
        for (day in alarm.days.filterValues { it }.keys) {
            val operation = PendingIntent.getBroadcast(
                context,
                id.toInt() * 7 + day.ordinal,
                intentAlarm,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val time = dayTimeToMillis(day, alarm.start_h * 60 + alarm.start_m)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, MILLIS_IN_WEEK, operation)
        }
    }

    private fun getAlarmManager(context: Context) = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun dayTimeToMillis(day: Converters.DayOfWeek, timeMin: Int): Long {
        val timeMs = timeMin * 60L * 1000
        val calendar = GregorianCalendar(TimeZone.getDefault())
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return getNextDay(day, calendar) + timeMs
    }

    private fun getNextDay(day: Converters.DayOfWeek, calendar: Calendar): Long {
        while (getCurrentDay(calendar) != day) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return calendar.timeInMillis
    }

    companion object {
        @Volatile
        private var instance: MyAlarmManager? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: MyAlarmManager(context).also { instance = it }
        }


        private const val MILLIS_IN_WEEK = 1000L * 60 * 60 * 24 * 7
        private val daysArray = arrayOf(
            Converters.DayOfWeek.SUNDAY,
            Converters.DayOfWeek.MONDAY,
            Converters.DayOfWeek.TUESDAY,
            Converters.DayOfWeek.WEDNESDAY,
            Converters.DayOfWeek.THURSDAY,
            Converters.DayOfWeek.FRIDAY,
            Converters.DayOfWeek.SATURDAY
        )
    }

    private fun getCurrentDay(calendar: Calendar = Calendar.getInstance()): Converters.DayOfWeek {
        val day = calendar[Calendar.DAY_OF_WEEK] - 1
        return daysArray[day]
    }
}
