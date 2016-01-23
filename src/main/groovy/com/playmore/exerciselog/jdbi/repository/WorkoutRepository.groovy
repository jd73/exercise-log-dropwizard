package com.playmore.exerciselog.jdbi.repository

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.api.associations.WorkoutExerciseAssociation
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
        workout.exercises.each { Exercise exercise ->
            Optional<Exercise> existing = exerciseRepository.findByName(exercise.name)
            Long exerciseId = existing.isPresent() ? existing.get().id : exerciseRepository.insert(exercise)

            WorkoutExerciseAssociation relation = new WorkoutExerciseAssociation(
                    workoutId: workout.id,
                    exerciseId: exerciseId
            )

            workoutExerciseDao.insert(relation)
        }
    }

    private void populateExercises(Workout workout) {
        List<WorkoutExerciseAssociation> workoutExerciseList = workoutExerciseDao.findByWorkoutId(workout.id)

        workout.exercises = workoutExerciseList.collect { WorkoutExerciseAssociation workoutExercise ->
            Optional<Exercise> exercise = exerciseRepository.findById(workoutExercise.exerciseId)
            return exercise.orElse(null)
        }.grep()
    }
}
