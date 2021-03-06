package ru.adfmp.officegym.models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.adfmp.officegym.database.BaseAlarm
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.database.converters.Converters
import ru.adfmp.officegym.database.repositories.AlarmRepository
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.utils.alarm.MyAlarmManager

class EditAlarmViewModel(
    private val editAlarmRepository: AlarmRepository,
    private val workoutRepository: WorkoutRepository,
    alarmId: Long,
    private val workoutId: Long
) :
    ViewModel() {
    var baseAlarm: LiveData<BaseAlarm?> =
        editAlarmRepository.getAlarmById(alarmId)

    var workout: Workout? = null

    var workouts: LiveData<List<Workout>> =
        workoutRepository.getAllWorkouts()

    init {
        runBlocking {
            launch(coroutineContext) {
                workout = workoutRepository.getSuspendWorkoutById(workoutId)
            }
        }
    }

    fun insertOrUpdateAlarm(days: Map<Converters.DayOfWeek, Boolean>): Boolean {
        var status = true
        runBlocking {
            launch(coroutineContext) {
                if (workout == null) {
                    status = false
                    return@launch
                }
                baseAlarm.value!!.workoutId = workout!!.workoutInfo.id
                baseAlarm.value!!.days = days
                status = editAlarmRepository.insertOrUpdateAlarm(baseAlarm.value!!)
            }
        }
        return status
    }

    fun addAlarmNotification(context: Context) {
        val name = baseAlarm.value!!.name
        runBlocking {
            launch(coroutineContext) {
                val alarm = editAlarmRepository.getAlarmByName(name)!!
                MyAlarmManager.getInstance(context).addAlarm(context, alarm)
            }
        }
    }

    fun deleteAlarm() {
        runBlocking {
            launch(coroutineContext) {
                editAlarmRepository.deleteAlarm(baseAlarm.value!!)
            }
        }
    }
}
