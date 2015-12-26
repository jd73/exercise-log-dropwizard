package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty

public class Exercise {
    private long id
    private String name

    @JsonProperty
    long getId() {
        return id
    }

    @JsonProperty
    String getName() {
        return name
    }
}