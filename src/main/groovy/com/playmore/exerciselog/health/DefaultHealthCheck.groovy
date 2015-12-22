package com.playmore.exerciselog.health

import com.codahale.metrics.health.HealthCheck

import static com.codahale.metrics.health.HealthCheck.Result

public class DefaultHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy()
    }
}