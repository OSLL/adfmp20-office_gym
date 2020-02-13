package ru.adfmp.officegym.database

import androidx.lifecycle.LiveData
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

val random = Random()


@Throws(InterruptedException::class)
fun <T> getValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    liveData.observeForever { o ->
        data[0] = o
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}

private fun getRandomString() = UUID.randomUUID().toString()

private fun getRandomInt() = random.nextInt()


fun buildExercise(): Exercise {
    return Exercise(
        name = getRandomString(),
        description = getRandomString(),
        recommendedDuration = getRandomInt(),
        intensity = getRandomInt()
    )
}

fun buildExercises(count: Int): List<Exercise> {
    return List(count) { buildExercise() }
}

fun buildWorkoutInfo(): WorkoutInfo {
    return WorkoutInfo(
        name = getRandomString()
    )
}

fun buildWorkoutInfo(count: Int): List<WorkoutInfo> {
    return List(count) { buildWorkoutInfo() }
}

fun buildAlarm(workoutId: Long, repeat: Boolean): Alarm {
    return if (repeat)
        Alarm(
            name = getRandomString(),
            workoutId = workoutId,
            days = arrayOf(true, true, false, false, false, false, false),
            start = getRandomInt(),
            repeat = true,
            frequency = getRandomInt(),
            finish = getRandomInt()
        ) else
        Alarm(
            name = getRandomString(),
            workoutId = workoutId,
            days = arrayOf(true, true, false, false, false, false, false),
            start = getRandomInt(),
            repeat = false
        )
}
