package ru.adfmp.officegym.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import ru.adfmp.officegym.database.converters.Converters
import ru.adfmp.officegym.database.workers.SeedDatabaseWorker


private const val DATABASE_NAME = "gym.db"
const val EXERCISES_DATA_FILENAME = "exercises.json"
const val WORKOUTS_DATA_FILENAME = "workouts.json"
const val EXERCISES_IN_WORKOUTS_DATA_FILENAME = "exercises_in_workouts.json"

@Database(
    entities = [Exercise::class, WorkoutInfo::class, ExerciseInWorkout::class,
        Alarm::class, Statistic::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): GymDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}
