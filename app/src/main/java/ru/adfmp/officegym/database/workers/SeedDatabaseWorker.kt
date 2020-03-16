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

private const val EXERCISES_DATA_FILENAME = "exercises.json"
private const val WORKOUTS_DATA_FILENAME = "workouts.json"
private const val EXERCISES_IN_WORKOUTS_DATA_FILENAME = "exercises_in_workouts.json"
private const val ALARMS_DATA_FILENAME = "alarms.json"
private const val DRAWABLE_RESOURCE = "drawable"

private fun resourceId(context: Context, name: String): Int {
    val id = context.resources.getIdentifier(name, DRAWABLE_RESOURCE, context.packageName)
    check(id != 0)
    return id
}

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private inner class BaseExerciseTemporary(
        val id: Long = 0,
        val name: String = "",
        val description: String = "",
        val intensity: Int = 0,
        val recommendedDuration: Int = 0,
        val resource: String
    ) {
        fun toBaseExercise(context: Context) =
            BaseExercise(id, name, description, intensity, recommendedDuration, resourceId(context, resource))
    }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(EXERCISES_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val exerciseType = object : TypeToken<List<BaseExerciseTemporary>>() {}.type
                    val baseExercises: List<BaseExerciseTemporary> =
                        Gson().fromJson(jsonReader, exerciseType)
                    Log.i("SeedDatabaseWorker", "load ${baseExercises.size} base exercises")
                    val database = AppDatabase.getInstance(applicationContext)
                    database.dao().insert(*baseExercises.map { it.toBaseExercise(applicationContext) }.toTypedArray())

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
