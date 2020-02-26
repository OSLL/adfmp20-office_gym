package ru.adfmp.officegym.database.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import ru.adfmp.officegym.database.AppDatabase
import ru.adfmp.officegym.database.BaseExercise
import ru.adfmp.officegym.database.ExerciseInWorkout
import ru.adfmp.officegym.database.WorkoutInfo
import ru.adfmp.officegym.database.Alarm

private val EXERCISES_DATA_FILENAME = "exercises.json"
private const val WORKOUTS_DATA_FILENAME = "workouts.json"
private const val EXERCISES_IN_WORKOUTS_DATA_FILENAME = "exercises_in_workouts.json"
private const val ALARMS_DATA_FILENAME = "alarms.json"

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(EXERCISES_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val exerciseType = object : TypeToken<List<BaseExercise>>() {}.type
                    val baseExercises: List<BaseExercise> =
                        Gson().fromJson(jsonReader, exerciseType)
                    Log.i("SeedDatabaseWorker", "load ${baseExercises.size} base exercises")
                    val database = AppDatabase.getInstance(applicationContext)
                    database.dao().insert(*baseExercises.toTypedArray())

                    Result.success()
                }
            }
            applicationContext.assets.open(WORKOUTS_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val workoutType = object : TypeToken<List<WorkoutInfo>>() {}.type
                    val workouts: List<WorkoutInfo> = Gson().fromJson(jsonReader, workoutType)
                    Log.i("SeedDatabaseWorker", "load ${workouts.size} workouts")

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
                    Log.i(
                        "SeedDatabaseWorker",
                        "load ${exercisesInWorkout.size} exercises in workout"
                    )
                    val database = AppDatabase.getInstance(applicationContext)
                    database.dao().insert(*exercisesInWorkout.toTypedArray())

                    Result.success()
                }
            }
            applicationContext.assets.open(ALARMS_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val alarmType = object : TypeToken<List<Alarm>>() {}.type
                    val alarms: List<Alarm> = Gson().fromJson(jsonReader, alarmType)
                    Log.i("SeedDatabaseWorker", "load ${alarms.size} alarms")

                    val database = AppDatabase.getInstance(applicationContext)
                    database.dao().insert(*alarms.toTypedArray())

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
