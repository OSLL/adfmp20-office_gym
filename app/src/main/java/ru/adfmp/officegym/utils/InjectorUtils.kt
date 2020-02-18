package ru.adfmp.officegym.utils

import android.content.Context
import androidx.fragment.app.Fragment
import ru.adfmp.officegym.database.AppDatabase
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.factories.StartWorkoutViewModelFactory
import ru.adfmp.officegym.factories.WorkoutViewModelFactory

object InjectorUtils {

    private fun getWorkoutRepository(context: Context): WorkoutRepository {
        return WorkoutRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).dao())
    }

    fun provideWorkoutViewModelFactory(fragment: Fragment): WorkoutViewModelFactory {
        val repository = getWorkoutRepository(fragment.requireContext())
        return WorkoutViewModelFactory(repository)
    }

    fun provideStartViewModelFactory(fragment: Fragment, workoutId: Long): StartWorkoutViewModelFactory {
        val repository = getWorkoutRepository(fragment.requireContext())
        return StartWorkoutViewModelFactory(repository, workoutId)
    }
}
