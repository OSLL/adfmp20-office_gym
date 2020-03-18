package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.AlarmRepository
import ru.adfmp.officegym.models.AlarmViewModel

class AlarmViewModelFactory(
    private val alarmRepository: AlarmRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmViewModel(alarmRepository) as T
    }
}
