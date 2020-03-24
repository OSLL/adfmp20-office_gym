package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.AlarmRepository
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.models.EditAlarmViewModel

class EditAlarmViewModelFactory(
    private val alarmRepository: AlarmRepository,
    private val workoutRepository: WorkoutRepository,
    private val alarmId: Long,
    private val workoutId: Long
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditAlarmViewModel(alarmRepository, workoutRepository,
            alarmId, workoutId) as T
    }
}
