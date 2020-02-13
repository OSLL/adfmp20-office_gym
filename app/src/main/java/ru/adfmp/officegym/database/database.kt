package ru.adfmp.officegym.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.adfmp.officegym.database.converters.Converters


@Entity(indices = [Index(value = ["name"], unique = true)])
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var description: String,
    var intensity: Int,
    @ColumnInfo(name = "recommended_duration")
    var recommendedDuration: Int
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutInfo::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index("exercise_id"), Index("workout_id")]
)
data class ExerciseInWorkout(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var duration: Int,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(name = "workout_id")
    val workoutId: Long
)

@Entity(indices = [Index(value = ["name"], unique = true)])
data class WorkoutInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String
)

data class Workout(
    @Embedded
    val workoutInfo: WorkoutInfo,
    @Relation(
        parentColumn = "id",
        entityColumn = "workout_id"
    )
    val exercises: List<ExerciseInWorkout>
)

@Entity
data class Statistic(
    @PrimaryKey
    val date: Long,
    @ColumnInfo(name = "total_duration")
    var totalDuration: Int,
    @ColumnInfo(name = "total_intensity")
    var totalIntensity: Int,
    @ColumnInfo(name = "total_count")
    var totalCount: Int
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = WorkoutInfo::class,
        parentColumns = ["id"],
        childColumns = ["workout_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["name"], unique = true), Index("workout_id")]
)
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    @ColumnInfo(name = "workout_id")
    val workoutId: Long,
    @TypeConverters(Converters::class)
    var days: Array<Boolean> = Array(7, { i -> false }),
    var start: Int,
    var repeat: Boolean,
    var frequency: Int? = null,
    var finish: Int? = null
)

@Dao
interface GymDao {

    /** Exercise queries. */

    @Insert
    suspend fun insert(vararg exercise: Exercise)

    @Update
    suspend fun update(vararg exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Query("SELECT * FROM Exercise WHERE id = :id")
    fun getExerciseById(id: Long): LiveData<Exercise?>

    @Query("SELECT * FROM Exercise")
    fun getAllExercises(): LiveData<List<Exercise>>


    /** Workout/WorkoutDescription queries. */

    @Insert
    suspend fun insert(vararg workoutInfo: WorkoutInfo)

    @Update
    suspend fun update(vararg workoutInfo: WorkoutInfo)

    @Delete
    suspend fun delete(workoutInfo: WorkoutInfo)

    @Transaction
    @Query("SELECT * FROM WorkoutInfo WHERE id = :id")
    fun getWorkoutById(id: Long): LiveData<Workout?>

    @Transaction
    @Query("SELECT * FROM WorkoutInfo")
    fun getAllWorkouts(): LiveData<List<Workout>>


    /** ExerciseInWorkout queries. */

    @Insert
    suspend fun insert(vararg exerciseInWorkout: ExerciseInWorkout)

    @Update
    suspend fun update(vararg exerciseInWorkout: ExerciseInWorkout)

    @Delete
    suspend fun delete(exerciseInWorkout: ExerciseInWorkout)


    /** Alarm queries. */

    @Insert
    suspend fun insert(vararg alarm: Alarm)

    @Update
    suspend fun update(vararg alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Query("SELECT * FROM Alarm WHERE id = :id")
    fun getAlarmById(id: Long): LiveData<Alarm?>

    @Query("SELECT * FROM Alarm")
    fun getAllAlarms(): LiveData<List<Alarm>>


    /** Statistic queries. */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg statistic: Statistic)

    @Delete
    suspend fun delete(statistic: Statistic)

    @Query("SELECT * FROM Statistic WHERE date = :date")
    fun getStatisticByDate(date: Long): LiveData<List<Statistic>>

    @Query("SELECT * FROM Statistic")
    fun getAllStatistic(): LiveData<List<Statistic>>
}
