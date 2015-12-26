package com.playmore.exerciselog.resources

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.ExerciseStore
import spock.lang.Specification

class ExerciseResourceSpec extends Specification {

    ExerciseResource resource

    void setup() {
        resource = new ExerciseResource(Mock(ExerciseStore))
    }

    void 'find gets an exercise by id and returns it'() {
        given:
        Long id = 1L
        Exercise expected = new Exercise()

        when:
        Exercise result = resource.find(id)

        then:
        1 * resource.exerciseStore.findById(id) >> expected
        0 * _

        result == expected

    }
}
