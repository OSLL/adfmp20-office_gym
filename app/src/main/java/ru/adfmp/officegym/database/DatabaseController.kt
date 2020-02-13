package ru.adfmp.officegym.database

class DatabaseController(private val gymDao: GymDao) {

    companion object {
        @Volatile
        private var instance: DatabaseController? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: DatabaseController(gymDao).also { instance = it }
            }
    }

    suspend fun insertExercise(exercise: Exercise) = gymDao.insert(exercise)

    suspend fun insertWorkout(workoutInfo: WorkoutInfo) =
        gymDao.insert(workoutInfo)

    suspend fun insertExerciseInWorkout(exerciseInWorkout: ExerciseInWorkout) =
        gymDao.insert(exerciseInWorkout)

    fun getAllExercises() = gymDao.getAllExercises()

    fun getAllWorkouts() = gymDao.getAllWorkouts()
}
