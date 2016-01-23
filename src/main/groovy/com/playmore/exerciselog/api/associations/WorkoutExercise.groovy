package com.playmore.exerciselog.api.associations

import com.playmore.exerciselog.annotations.ApiEntity

@ApiEntity
class WorkoutExercise {
    Long exerciseId
    Long workoutId
}
