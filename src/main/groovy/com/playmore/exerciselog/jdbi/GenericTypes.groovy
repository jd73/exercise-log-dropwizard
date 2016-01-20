package com.playmore.exerciselog.jdbi

import com.playmore.exerciselog.api.Exercise

import javax.ws.rs.core.GenericType

class GenericTypes {
    static final GenericType<List<Exercise>> ExerciseList = new GenericType<List<Exercise>>() {}
}
