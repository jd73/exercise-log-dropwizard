package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.playmore.exerciselog.annotations.ApiEntity

import java.time.Duration
import java.time.LocalDateTime

@ApiEntity
class Workout {
    private long id
    private String name
    private LocalDateTime date
    private Duration duration
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
    LocalDateTime getDate() {
        return date
    }

    @JsonProperty
    Duration getDuration() {
        return duration
    }

    @JsonProperty
    List<Exercise> getExercises() {
        return exercises
    }
}
