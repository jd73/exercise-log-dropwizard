package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.playmore.exerciselog.annotations.ApiEntity

import java.time.Duration

@ApiEntity
class ExerciseSet {
    private long id
    private Integer repetitions
    private Duration duration
    private Double pounds

    @JsonProperty
    long getId() {
        return id
    }

    @JsonProperty
    Integer getRepetitions() {
        return repetitions
    }

    @JsonProperty
    Duration getDuration() {
        return duration
    }

    @JsonProperty
    Double getPounds() {
        return pounds
    }
}
