package ru.adfmp.officegym.models

import androidx.lifecycle.ViewModel
import ru.adfmp.officegym.database.repositories.StatisticsRepository

class StatisticsViewModel(statisticsRepository: StatisticsRepository) : ViewModel() {
    val statistics = statisticsRepository.getAllStatistics()
}
