package com.playmore.exerciselog.jdbi.helper

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.api.Workout

import javax.ws.rs.core.GenericType

class GenericTypes {
    static final GenericType<List<Exercise>> ExerciseList = new GenericType<List<Exercise>>() {}

    static final GenericType<List<Workout>> WorkoutList = new GenericType<List<Workout>>() {}
}
