package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty

class ExerciseSeries {
    private long id
    private Exercise exercise
    private List<ExerciseSet> sets

    @JsonProperty
    long getId() {
        return id
    }

    @JsonProperty
    Integer getExercise() {
        return exercise
    }

    @JsonProperty
    Integer getSets() {
        return sets
    }
}
