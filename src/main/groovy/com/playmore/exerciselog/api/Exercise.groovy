package com.playmore.exerciselog.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.playmore.exerciselog.annotations.ApiEntity

@ApiEntity
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