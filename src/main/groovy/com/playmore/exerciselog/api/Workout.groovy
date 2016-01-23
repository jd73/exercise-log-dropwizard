package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.playmore.exerciselog.annotations.ApiEntity

@ApiEntity
class Workout {
    Long id
    private String name
    private List<Exercise> exercises

    @JsonProperty
    Long getId() {
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

    void setExercises(List<Exercise> exercises) {
        this.exercises = exercises
    }
}
