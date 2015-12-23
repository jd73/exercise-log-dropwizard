package com.playmore.exerciselog

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import org.hibernate.validator.constraints.NotEmpty

import javax.validation.Valid
import javax.validation.constraints.NotNull

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

    @Valid @NotNull @JsonProperty private DataSourceFactory database = new DataSourceFactory()
    public DataSourceFactory getDataSourceFactory() {
        return database
    }
}