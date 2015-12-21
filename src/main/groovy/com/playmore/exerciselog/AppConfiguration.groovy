package com.playmore.exerciselog

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import org.hibernate.validator.constraints.NotEmpty

public class AppConfiguration extends Configuration {

    @NotEmpty
    private String appName

    @JsonProperty
    String getAppName() {
        return appName
    }

    @JsonProperty
    void setAppName(final String appName) {
        this.appName = appName
    }
}