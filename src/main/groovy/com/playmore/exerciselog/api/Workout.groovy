package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.playmore.exerciselog.annotations.ApiEntity

@ApiEntity
class Workout {
    private long id
    private String name
    private List<Exercise> exercises

    @JsonProperty
    long getId() {
        return id
    }

    @JsonProperty
    String getName() {
        return name
    }

    @JsonProperty
    List<Exercise> getExercises() {
        return exercises
    }
}
