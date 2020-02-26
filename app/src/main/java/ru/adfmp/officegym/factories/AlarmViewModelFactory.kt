package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import ru.adfmp.officegym.database.repositories.AlarmRepository
import ru.adfmp.officegym.models.AlarmViewModel
import androidx.lifecycle.ViewModelProvider

class AlarmViewModelFactory (
    private val alarmRepository: AlarmRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmViewModel(alarmRepository) as T
    }
}
