package ru.adfmp.officegym.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var gymDao: GymDao
    private lateinit var db: AppDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        gymDao = db.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAllExercisesTest() = runBlocking {
        val exercises = buildExercises(5)
        for (exercise in exercises) {
            gymDao.insert(exercise)
        }

        val allExercises = getValue(gymDao.getAllBaseExercises())
        Assert.assertEquals(exercises.size, allExercises.size)
        Assert.assertEquals(
            exercises.map { exercise -> exercise.name }.toSet(),
            allExercises.map { exercise -> exercise.name }.toSet()
        )
    }

    @Test
    fun getExerciseByIdTest() = runBlocking {
        val exercises = buildExercises(5)
        for (exercise in exercises) {
            gymDao.insert(exercise)
        }

        for ((id, exercise) in exercises.withIndex()) {
            Assert.assertEquals(
                exercise.name,
                getValue(gymDao.getBaseExerciseById(id.toLong() + 1))!!.name
            )
        }
    }

    @Test
    fun getAllWorkoutsTest() = runBlocking {
        val workoutDescriptions = buildWorkoutInfo(5)
        for (workoutDescription in workoutDescriptions) {
            gymDao.insert(workoutDescription)
        }

        val allWorkouts = getValue(gymDao.getAllWorkouts())
        Assert.assertEquals(workoutDescriptions.size, allWorkouts.size)
        Assert.assertEquals(
            workoutDescriptions.map { workout -> workout.name }.toSet(),
            allWorkouts.map { workout -> workout.workoutInfo.name }.toSet()
        )
    }

    @Test
    fun getWorkoutsByIdTest() = runBlocking {
        val workouts = buildWorkoutInfo(5)
        for (workout in workouts) {
            gymDao.insert(workout)
        }

        for ((id, exercise) in workouts.withIndex()) {
            Assert.assertEquals(
                exercise.name,
                getValue(gymDao.getWorkoutById(id.toLong() + 1))!!.workoutInfo.name
            )
        }
    }

    @Test
    fun getExercisesInWorkoutTest() = runBlocking {
        val workouts = buildWorkoutInfo(5)
        for (workout in workouts) {
            gymDao.insert(workout)
        }

        var baseExercises = buildExercises(5)
        for (exercise in baseExercises) {
            gymDao.insert(exercise)
        }

        baseExercises = getValue(gymDao.getAllBaseExercises())

        gymDao.insert(
            ExerciseInWorkout(
                duration = 2,
                workoutId = 1,
                exerciseId = baseExercises[0].id
            )
        )
        gymDao.insert(
            ExerciseInWorkout(
                duration = 2,
                workoutId = 1,
                exerciseId = baseExercises[1].id
            )
        )
        gymDao.insert(
            ExerciseInWorkout(
                duration = 2,
                workoutId = 1,
                exerciseId = baseExercises[2].id
            )
        )

        val exercises = getValue(gymDao.getWorkoutById(1))!!.exercises
        Assert.assertEquals(3, exercises.size)
        Assert.assertEquals(
            setOf(baseExercises[0].id, baseExercises[1].id, baseExercises[2].id),
            exercises.map { exercise -> exercise.baseId }.toSet()
        )
    }

    @Test
    fun getAlarmTest() {
        runBlocking {
            val workout = buildWorkoutInfo()
            gymDao.insert(workout)

            val alarm = buildAlarm(1, true)
            gymDao.insert(alarm)
        }
    }
}
