package com.playmore.exerciselog.resources

import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.jdbi.dao.WorkoutDao
import spock.lang.Specification

import javax.ws.rs.core.Response

class WorkoutResourceSpec extends Specification {

    WorkoutResource resource

    void setup() {
        resource = new WorkoutResource(Mock(WorkoutDao))
    }

    void 'list gets all workouts from the store and returns them'() {
        given:
        List<Workout> expected = [
                new Workout(id: 1L),
                new Workout(id: 2L)
        ]

        when:
        List<Workout> workouts = resource.all()

        then:
        1 * resource.workoutStore.all() >> expected
        0 * _

        workouts == expected
    }

    void 'add creates a workout and returns it'() {
        given:
        String name = 'some workout'
        Long id = 1L
        Workout expected = new Workout(name: name)

        when:
        Response response = resource.add(name)

        then:
        1 * resource.workoutStore.insert(expected) >> id
        1 * resource.workoutStore.findById(id) >> Optional.of(expected)
        0 * _

        (response.entity as Optional).get() == expected
    }

    void 'find gets an workout by id and returns it'() {
        given:
        Long id = 1L
        Workout expected = new Workout()

        when:
        Optional<Workout> result = resource.find(id)

        then:
        1 * resource.workoutStore.findById(id) >> Optional.of(expected)
        0 * _

        result.get() == expected
    }
}
