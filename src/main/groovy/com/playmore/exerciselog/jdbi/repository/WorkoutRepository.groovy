package com.playmore.exerciselog.jdbi.repository

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.api.associations.WorkoutExercise
import com.playmore.exerciselog.jdbi.dao.WorkoutDao
import com.playmore.exerciselog.jdbi.dao.WorkoutExerciseDao

class WorkoutRepository {
    private WorkoutDao workoutDao
    private WorkoutExerciseDao workoutExerciseDao
    private ExerciseRepository exerciseRepository

    WorkoutRepository(
            WorkoutDao workoutDao,
            WorkoutExerciseDao workoutExerciseDao,
            ExerciseRepository exerciseRepository
    ) {
        this.workoutDao = workoutDao
        this.workoutExerciseDao = workoutExerciseDao
        this.exerciseRepository = exerciseRepository
    }

    Optional<Workout> save(Workout workout) {
        workout.id = workoutDao.insert(workout)
        saveExercises(workout)
        return findById(workout.id)
    }

    Optional<Workout> findById(Long id) {
        Optional<Workout> workout = workoutDao.findById(id)
        if (workout.isPresent()) {
            populateExercises(workout.get())
        }
        return workout
    }

    List<Workout> all() {
        List<Workout> workouts = workoutDao.all()
        workouts.each { populateExercises(it) }
        return workouts
    }

    private void saveExercises(Workout workout) {
        List<Exercise> exercises = workout.exercises

        exercises.each { Exercise exercise ->
            WorkoutExercise relation = new WorkoutExercise(
                    workoutId: workout.id,
                    exerciseId: exercise.id
            )

            workoutExerciseDao.insert(relation)
        }
    }

    private void populateExercises(Workout workout) {
        List<WorkoutExercise> workoutExerciseList = workoutExerciseDao.findByWorkoutId(workout.id)

        workout.exercises = workoutExerciseList.collect { WorkoutExercise workoutExercise ->
            Optional<Exercise> exercise = exerciseRepository.findById(workoutExercise.exerciseId)
            return exercise.get()
        }.grep()
    }
}
