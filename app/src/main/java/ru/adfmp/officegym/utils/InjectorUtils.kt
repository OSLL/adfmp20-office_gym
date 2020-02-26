package ru.adfmp.officegym.utils

import android.content.Context
import androidx.fragment.app.Fragment
import ru.adfmp.officegym.database.AppDatabase
import ru.adfmp.officegym.database.repositories.BaseExerciseRepository
import ru.adfmp.officegym.database.repositories.ExerciseInWorkoutRepository
import ru.adfmp.officegym.database.repositories.WorkoutRepository
import ru.adfmp.officegym.factories.*

object InjectorUtils {

    private fun getWorkoutRepository(context: Context): WorkoutRepository {
        return WorkoutRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).dao()
        )
    }

    private fun getBaseExerciseRepository(context: Context): BaseExerciseRepository {
        return BaseExerciseRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).dao()
        )
    }

    private fun getExerciseInWorkoutRepository(context: Context): ExerciseInWorkoutRepository {
        return ExerciseInWorkoutRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).dao()
        )
    }

    fun provideWorkoutsViewModelFactory(fragment: Fragment): WorkoutsViewModelFactory {
        val repository = getWorkoutRepository(fragment.requireContext())
        return WorkoutsViewModelFactory(repository)
    }

    fun provideBaseExercisesViewModelFactory(fragment: Fragment): EditAllExercisesViewModelFactory {
        val repository = getBaseExerciseRepository(fragment.requireContext())
        return EditAllExercisesViewModelFactory(repository)
    }

    fun provideStartViewModelFactory(
        fragment: Fragment,
        workoutId: Long
    ): StartWorkoutViewModelFactory {
        val repository = getWorkoutRepository(fragment.requireContext())
        return StartWorkoutViewModelFactory(repository, workoutId)
    }

    fun provideEditExerciseViewModelFactory(
        fragment: Fragment,
        exerciseId: Long
    ): EditExerciseViewModelFactory {
        val repository = getBaseExerciseRepository(fragment.requireContext())
        return EditExerciseViewModelFactory(repository, exerciseId)
    }

    fun provideEditWorkoutViewModelFactory(
        fragment: Fragment,
        workoutId: Long
    ): EditWorkoutViewModelFactory {
        val repository = getWorkoutRepository(fragment.requireContext())
        return EditWorkoutViewModelFactory(repository, workoutId)
    }

    fun provideEditExerciseInWorkoutFactory(
        fragment: Fragment,
        exerciseInWorkoutId: Long,
        baseExerciseId: Long,
        workoutId: Long
    ): EditExerciseInWorkoutViewModelFactory {
        val repository = getExerciseInWorkoutRepository(fragment.requireContext())
        return EditExerciseInWorkoutViewModelFactory(repository, exerciseInWorkoutId, baseExerciseId, workoutId)
    }
}
