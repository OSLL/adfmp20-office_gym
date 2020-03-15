package ru.adfmp.officegym.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.adfmp.officegym.database.repositories.StatisticsRepository
import ru.adfmp.officegym.models.StatisticsViewModel

class StatisticsViewModelFactory(
    private val statisticsRepository: StatisticsRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatisticsViewModel(statisticsRepository) as T
    }
}
