package com.playmore.exerciselog.api.associations

import com.playmore.exerciselog.annotations.ApiEntity

@ApiEntity
class WorkoutExerciseAssociation {
    Long exerciseId
    Long workoutId
}
