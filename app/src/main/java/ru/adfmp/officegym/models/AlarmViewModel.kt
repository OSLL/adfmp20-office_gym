package ru.adfmp.officegym.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.adfmp.officegym.database.Alarm
import ru.adfmp.officegym.database.repositories.AlarmRepository

class AlarmViewModel(alarmRepository: AlarmRepository) : ViewModel() {
    val alarms: LiveData<List<Alarm>> = alarmRepository.getAllAlarms()
}
