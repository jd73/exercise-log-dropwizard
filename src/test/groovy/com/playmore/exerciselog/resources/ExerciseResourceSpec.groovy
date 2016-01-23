package com.playmore.exerciselog.resources

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.repository.ExerciseRepository
import spock.lang.Specification

import javax.ws.rs.core.Response

class ExerciseResourceSpec extends Specification {

    ExerciseResource resource

    void setup() {
        resource = new ExerciseResource(Mock(ExerciseRepository))
    }

    void 'list gets all exercises from the store and returns them'() {
        given:
        List<Exercise> expected = [
                new Exercise(id: 1L),
                new Exercise(id: 2L)
        ]

        when:
        List<Exercise> exercises = resource.all()

        then:
        1 * resource.exerciseStore.all() >> expected
        0 * _

        exercises == expected
    }

    void 'add creates a exercise and returns it'() {
        given:
        String name = 'some exercise'
        Long id = 1L
        Exercise exercise = new Exercise(name: name)

        when:
        Response response = resource.add(name)

        then:
        1 * resource.exerciseStore.insert(exercise) >> id
        1 * resource.exerciseStore.findById(id) >> Optional.of(exercise)
        0 * _

        (response.entity as Optional).get() == exercise
    }

    void 'find gets an exercise by id and returns it'() {
        given:
        Long id = 1L
        Exercise expected = new Exercise()

        when:
        Optional<Exercise> result = resource.find(id)

        then:
        1 * resource.exerciseStore.findById(id) >> Optional.of(expected)
        0 * _

        result.get() == expected

    }
}
