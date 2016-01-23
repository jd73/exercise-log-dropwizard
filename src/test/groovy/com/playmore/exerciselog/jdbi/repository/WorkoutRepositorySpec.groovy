package com.playmore.exerciselog.jdbi.repository

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.api.associations.WorkoutExerciseAssociation
import com.playmore.exerciselog.jdbi.dao.WorkoutDao
import com.playmore.exerciselog.jdbi.dao.WorkoutExerciseDao
import spock.lang.Specification

class WorkoutRepositorySpec extends Specification {

    WorkoutRepository repository

    void setup() {
        repository = new WorkoutRepository(
                Mock(WorkoutDao),
                Mock(WorkoutExerciseDao),
                Mock(ExerciseRepository)
        )
    }

    void 'save saves the workout and associates it with the correct exercises, saving them if they are not yet saved'() {
        given:
        Long savedExerciseId = 1L
        Exercise savedExercise = new Exercise(name: 'saved', id: savedExerciseId)
        Long unsavedExerciseId = 2L
        Exercise unsavedExercise = new Exercise(name: 'unsaved')

        Long workoutId = 123L
        Workout workout = new Workout(
                name: 'some workout',
                exercises: [
                        savedExercise,
                        unsavedExercise
                ]
        )

        WorkoutExerciseAssociation associationOne = new WorkoutExerciseAssociation(workoutId: workoutId, exerciseId: savedExerciseId)
        WorkoutExerciseAssociation associationTwo = new WorkoutExerciseAssociation(workoutId: workoutId, exerciseId: unsavedExerciseId)

        when:
        Optional<Workout> actual = repository.save(workout)

        then:
        1 * repository.workoutDao.insert(workout) >> workoutId

        1 * repository.exerciseRepository.findByName(savedExercise.name) >> Optional.of(savedExercise)
        1 * repository.exerciseRepository.findByName(unsavedExercise.name) >> Optional.empty()
        1 * repository.exerciseRepository.insert(unsavedExercise) >> unsavedExerciseId
        1 * repository.exerciseRepository.findById(unsavedExerciseId) >> Optional.of(unsavedExercise)
        1 * repository.workoutExerciseDao.insert(associationOne)
        1 * repository.workoutExerciseDao.insert(associationTwo)

        1 * repository.workoutDao.findById(workoutId) >> Optional.of(workout)
        1 * repository.workoutExerciseDao.findByWorkoutId(workoutId) >> [
                associationOne,
                associationTwo
        ]
        1 * repository.exerciseRepository.findById(savedExerciseId) >> Optional.of(savedExercise)
        0 * _

        actual.isPresent()
        actual.get() == workout
    }

    void 'findById gets workout of the specified id and loads its associated exercises'() {
        given:
        Long id = 123L
        Workout expected = new Workout(id: id)

        Exercise exerciseOne = new Exercise(name: 'one', id: 1L)
        Exercise exerciseTwo = new Exercise(name: 'two', id: 2L)

        WorkoutExerciseAssociation associationOne = new WorkoutExerciseAssociation(workoutId: id, exerciseId: exerciseOne.id)
        WorkoutExerciseAssociation associationTwo = new WorkoutExerciseAssociation(workoutId: id, exerciseId: exerciseTwo.id)

        when:
        Optional<Workout> actual = repository.findById(id)

        then:
        1 * repository.workoutDao.findById(id) >> Optional.of(expected)
        1 * repository.workoutExerciseDao.findByWorkoutId(id) >> [
                associationOne,
                associationTwo
        ]
        1 * repository.exerciseRepository.findById(exerciseOne.id) >> Optional.of(exerciseOne)
        1 * repository.exerciseRepository.findById(exerciseTwo.id) >> Optional.of(exerciseTwo)
        0 * _

        actual.isPresent()
        actual.get() == expected
        actual.get().exercises == [exerciseOne, exerciseTwo]
    }

    void 'all gets all workouts and loads their associated exercises'() {
        given:
        Long idOne = 123L
        Workout expectedOne = new Workout(id: idOne)
        Long idTwo = 456L
        Workout expectedTwo = new Workout(id: idTwo)

        Exercise exerciseOne = new Exercise(name: 'one', id: 1L)
        Exercise exerciseTwo = new Exercise(name: 'two', id: 2L)

        WorkoutExerciseAssociation associationOne = new WorkoutExerciseAssociation(workoutId: idOne, exerciseId: exerciseOne.id)
        WorkoutExerciseAssociation associationTwo = new WorkoutExerciseAssociation(workoutId: idTwo, exerciseId: exerciseTwo.id)

        when:
        List<Workout> actual = repository.all()

        then:
        1 * repository.workoutDao.all() >> [expectedOne, expectedTwo]
        1 * repository.workoutExerciseDao.findByWorkoutId(idOne) >> [associationOne]
        1 * repository.exerciseRepository.findById(exerciseOne.id) >> Optional.of(exerciseOne)
        1 * repository.workoutExerciseDao.findByWorkoutId(idTwo) >> [associationTwo]
        1 * repository.exerciseRepository.findById(exerciseTwo.id) >> Optional.of(exerciseTwo)
        0 * _

        actual.size() == 2
        actual == [expectedOne, expectedTwo]
        expectedOne.exercises == [exerciseOne]
        expectedTwo.exercises == [exerciseTwo]
    }

}
