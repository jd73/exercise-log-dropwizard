package com.playmore.exerciselog.resources

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.jdbi.repository.ExerciseRepository
import com.playmore.exerciselog.jdbi.repository.WorkoutRepository
import spock.lang.Specification

import javax.ws.rs.core.Response

class WorkoutResourceSpec extends Specification {

    WorkoutResource resource

    void setup() {
        resource = new WorkoutResource(
                Mock(WorkoutRepository),
                Mock(ExerciseRepository)
        )
    }

    void 'add saves the passed in workout'() {
        given:
        Exercise unsaved = new Exercise(name: 'new exercise')

        Workout workout = new Workout(
                name: 'some workout',
                exercises: [unsaved]
        )

        when:
        Response response = resource.add(workout)

        then:
        1 * resource.workoutRepository.save(workout) >> Optional.of(workout)
        0 * _

        (response.entity as Optional).get() == workout
    }

    void 'all gets all workouts from the store and returns them'() {
        given:
        List<Workout> expected = [
                new Workout(id: 1L),
                new Workout(id: 2L)
        ]

        when:
        List<Workout> workouts = resource.all()

        then:
        1 * resource.workoutRepository.all() >> expected
        0 * _

        workouts == expected
    }

    void 'find gets a workout by id and returns it'() {
        given:
        Long id = 1L
        Workout expected = new Workout()

        when:
        Optional<Workout> result = resource.find(id)

        then:
        1 * resource.workoutRepository.findById(id) >> Optional.of(expected)
        0 * _

        result.get() == expected
    }
}
