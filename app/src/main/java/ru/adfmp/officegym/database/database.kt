package ru.adfmp.officegym.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.adfmp.officegym.database.converters.Converters
import ru.adfmp.officegym.database.converters.Converters.DayOfWeek


@Entity(indices = [Index(value = ["name"], unique = true)])
data class BaseExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = "",
    var description: String = "",
    var intensity: Int = 0,
    @ColumnInfo(name = "recommended_duration")
    var recommendedDuration: Int = 0,
    @ColumnInfo(name = "strategy_x")
    val strategyX: Converters.Strategy = Converters.Strategy.IGNORE,
    @ColumnInfo(name = "phase_x")
    val phaseX: Double = 0.0,
    @ColumnInfo(name = "amplitude_x")
    val amplitudeX: Double = 0.0,
    @ColumnInfo(name = "strategy_y")
    val strategyY: Converters.Strategy = Converters.Strategy.IGNORE,
    @ColumnInfo(name = "phase_y")
    val phaseY: Double = 0.0,
    @ColumnInfo(name = "amplitude_y")
    val amplitudeY: Double = 0.0,
    @ColumnInfo(name = "strategy_z")
    val strategyZ: Converters.Strategy = Converters.Strategy.IGNORE,
    @ColumnInfo(name = "phase_z")
    val phaseZ: Double = 0.0,
    @ColumnInfo(name = "amplitude_z")
    val amplitudeZ: Double = 0.0,
    @ColumnInfo(name = "resource_id")
    val resourceId: Int
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BaseExercise::class,
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
    var duration: Int = 0,
    @ColumnInfo(name = "exercise_id")
    var exerciseId: Long = 0,
    @ColumnInfo(name = "workout_id")
    var workoutId: Long = 0
)

@Entity(indices = [Index(value = ["name"], unique = true)])
data class WorkoutInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = ""
)

data class Workout(
    @Embedded
    val workoutInfo: WorkoutInfo = WorkoutInfo(),
    @Relation(
        parentColumn = "id",
        entityColumn = "workout_id"
    )
    var exercises: List<Exercise> = emptyList()
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
data class BaseAlarm(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = "",
    @ColumnInfo(name = "workout_id")
    var workoutId: Long = 0L,
    @TypeConverters(Converters::class)
    var days: Map<DayOfWeek, Boolean> = DayOfWeek.values().associate { it to false },
    var start_h: Int = 0,
    var start_m: Int = 0,
    var repeat: Boolean = false,
    var frequency: Int? = null,
    var finish: Int? = null
)

@DatabaseView(
    """
    SELECT baseExercise.id AS base_id, exerciseInWorkout.id AS id, baseExercise.name, baseExercise.description, baseExercise.intensity,
    baseExercise.recommended_duration, exerciseInWorkout.duration, exerciseInWorkout.workout_id, baseExercise.resource_id,
    baseExercise.strategy_x, baseExercise.phase_x, baseExercise.amplitude_x,
    baseExercise.strategy_y, baseExercise.phase_y, baseExercise.amplitude_y,
    baseExercise.strategy_z, baseExercise.phase_z, baseExercise.amplitude_z
    FROM exerciseInWorkout
    JOIN baseExercise ON exerciseInWorkout.exercise_id = baseExercise.id
    """
)
data class Exercise(
    @ColumnInfo(name = "base_id")
    val baseId: Long = 0,
    val id: Long = 0,
    val name: String?,
    val description: String,
    var intensity: Int,
    @ColumnInfo(name = "recommended_duration")
    var recommendedDuration: Int,
    var duration: Int,
    @ColumnInfo(name = "workout_id")
    val workoutId: Long,
    @ColumnInfo(name = "resource_id")
    val resourceId: Int,
    @ColumnInfo(name = "strategy_x")
    val strategyX: Converters.Strategy = Converters.Strategy.IGNORE,
    @ColumnInfo(name = "phase_x")
    val phaseX: Double = 0.0,
    @ColumnInfo(name = "amplitude_x")
    val amplitudeX: Double = 0.0,
    @ColumnInfo(name = "strategy_y")
    val strategyY: Converters.Strategy = Converters.Strategy.IGNORE,
    @ColumnInfo(name = "phase_y")
    val phaseY: Double = 0.0,
    @ColumnInfo(name = "amplitude_y")
    val amplitudeY: Double = 0.0,
    @ColumnInfo(name = "strategy_z")
    val strategyZ: Converters.Strategy = Converters.Strategy.IGNORE,
    @ColumnInfo(name = "phase_z")
    val phaseZ: Double = 0.0,
    @ColumnInfo(name = "amplitude_z")
    val amplitudeZ: Double = 0.0
)

@DatabaseView(
    """
    SELECT baseAlarm.id AS id, baseAlarm.name AS name, baseAlarm.workout_id AS workout_id, workoutInfo.name AS workout_name
    FROM baseAlarm
    JOIN workoutInfo ON baseAlarm.workout_id = workoutInfo.id
    """
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
    var name: String?,
    @ColumnInfo(name = "workout_id")
    var workoutId: Long,
    @ColumnInfo(name = "workout_name")
    var workoutName: String?
)

@Dao
interface GymDao {

    /** Exercise queries. */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg baseExercise: BaseExercise): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(vararg baseExercise: BaseExercise): Int

    @Delete
    suspend fun delete(baseExercise: BaseExercise)

    @Query("SELECT * FROM BaseExercise WHERE id = :id")
    fun getBaseExerciseById(id: Long): LiveData<BaseExercise?>

    @Query("SELECT * FROM BaseExercise")
    fun getAllBaseExercises(): LiveData<List<BaseExercise>>

    @Query("SELECT * FROM Exercise")
    fun getExerciseById(): LiveData<Exercise?>


    /** Workout/WorkoutDescription queries. */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg workoutInfo: WorkoutInfo): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(vararg workoutInfo: WorkoutInfo): Int

    @Delete
    suspend fun delete(workoutInfo: WorkoutInfo)

    @Transaction
    @Query("SELECT * FROM WorkoutInfo WHERE id = :id")
    fun getWorkoutById(id: Long): LiveData<Workout?>

    @Transaction
    @Query("SELECT * FROM WorkoutInfo WHERE id = :id")
    suspend fun getSuspendWorkoutById(id: Long): Workout?

    @Transaction
    @Query("SELECT * FROM WorkoutInfo")
    fun getAllWorkouts(): LiveData<List<Workout>>


    /** ExerciseInWorkout queries. */

    @Insert
    suspend fun insert(vararg exerciseInWorkout: ExerciseInWorkout): List<Long>

    @Update
    suspend fun update(vararg exerciseInWorkout: ExerciseInWorkout): Int

    @Delete
    suspend fun delete(exerciseInWorkout: ExerciseInWorkout)

    @Query("SELECT * FROM ExerciseInWorkout WHERE id = :id")
    fun getExerciseInWorkoutById(id: Long): LiveData<ExerciseInWorkout?>


    /** Alarm queries. */

    @Insert
    suspend fun insert(vararg baseAlarm: BaseAlarm): List<Long>

    @Update
    suspend fun update(vararg baseAlarm: BaseAlarm): Int

    @Delete
    suspend fun delete(baseAlarm: BaseAlarm)

    @Query("SELECT * FROM BaseAlarm WHERE id = :id")
    fun getAlarmById(id: Long): LiveData<BaseAlarm?>

    @Query("SELECT * FROM BaseAlarm WHERE name = :name")
    suspend fun getAlarmByName(name: String): BaseAlarm?

    @Transaction
    @Query("SELECT * FROM BaseAlarm")
    fun getAllAlarms(): LiveData<List<BaseAlarm>>

    @Transaction
    @Query("SELECT * FROM Alarm")
    fun getAllAlarmViews(): LiveData<List<Alarm?>>


    /** Statistic queries. */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg statistic: Statistic)

    @Delete
    suspend fun delete(statistic: Statistic)

    @Query("SELECT * FROM Statistic WHERE date = :date")
    fun getStatisticByDate(date: Long): LiveData<List<Statistic>>

    @Query("SELECT * FROM Statistic WHERE date = :date")
    suspend fun selectStatistic(date: Long): Statistic?

    @Query("SELECT * FROM Statistic ORDER BY date")
    fun getAllStatistic(): LiveData<List<Statistic>>
}
