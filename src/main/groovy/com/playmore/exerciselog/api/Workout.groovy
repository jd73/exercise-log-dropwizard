package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty

class Workout {
    private long id

    private List<String> exercises

    @JsonProperty
    long getId() {
        return id
    }

    @JsonProperty
    List<String> getExercises() {
        return exercises
    }
}
