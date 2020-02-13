package ru.adfmp.officegym.database.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import ru.adfmp.officegym.database.*


class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(EXERCISES_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val exerciseType = object : TypeToken<List<Exercise>>() {}.type
                    val exercises: List<Exercise> = Gson().fromJson(jsonReader, exerciseType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.dao().insert(*exercises.toTypedArray())

                    Result.success()
                }
            }
            applicationContext.assets.open(WORKOUTS_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val workoutType = object : TypeToken<List<WorkoutInfo>>() {}.type
                    val workouts: List<WorkoutInfo> = Gson().fromJson(jsonReader, workoutType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.dao().insert(*workouts.toTypedArray())

                    Result.success()
                }
            }
            applicationContext.assets.open(EXERCISES_IN_WORKOUTS_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val exerciseInWorkoutType =
                        object : TypeToken<List<ExerciseInWorkout>>() {}.type
                    val exercisesInWorkout: List<ExerciseInWorkout> = Gson()
                        .fromJson(jsonReader, exerciseInWorkoutType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.dao().insert(*exercisesInWorkout.toTypedArray())

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}
