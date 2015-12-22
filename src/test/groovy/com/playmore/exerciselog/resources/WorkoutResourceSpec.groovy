package com.playmore.exerciselog.resources

import com.playmore.exerciselog.api.Workout
import spock.lang.Specification
import com.google.common.base.Optional

class WorkoutResourceSpec extends Specification {

    WorkoutResource resource

    void setup() {
        resource = new WorkoutResource('default')
    }

    void 'getWorkout returns a workout with the specified exercise'() {
        given:
        String exercise = 'some exercise'

        when:
        Workout result = resource.getWorkout(Optional.of(exercise))

        then:
        result.exercises == [exercise]
    }
}
