package ru.adfmp.officegym.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.adfmp.officegym.database.Exercise
import ru.adfmp.officegym.database.Workout
import ru.adfmp.officegym.database.repositories.StatisticsRepository

class RunWorkoutViewModel(private val statisticsRepository: StatisticsRepository) : ViewModel() {
    val exercise = MutableLiveData<Exercise?>()
    private var index = 0
    private var workout: Workout? = null

    fun setWorkout(workout: Workout) {
        require(workout.exercises.isNotEmpty())
        this.workout = workout
        index = 0
        exercise.postValue(workout.exercises[index])
    }

    fun next() {
        checkNotNull(workout)
        if (index + 1 >= workout!!.exercises.size) {
            exercise.postValue(null)
        } else {
            index++
            exercise.postValue(workout!!.exercises[index])
        }
    }

    fun exerciseCompleted() {
        val exercise = workout!!.exercises[index]
        GlobalScope.launch(Dispatchers.IO) {
            val statistic = statisticsRepository.getStatistic()
            statistic.totalCount++
            statistic.totalDuration += exercise.duration
            statistic.totalIntensity += exercise.intensity
            statisticsRepository.insertStatistics(statistic)
        }
    }
}
