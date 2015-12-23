package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty

public class Exercise {
    @JsonProperty public final int id
    @JsonProperty public final String name

    public Exercise(int id, String name) {
        this.id = id
        this.name = name
    }
}