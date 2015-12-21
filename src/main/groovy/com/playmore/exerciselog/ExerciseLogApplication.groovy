package com.playmore.exerciselog

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory

class ExerciseLogApplication extends Application<AppConfiguration> {

    static final Logger LOGGER = LoggerFactory.getLogger(ExerciseLogApplication.class)

    static void main(final String[] args) throws Exception {
        new ExerciseLogApplication().run(args)
    }

    @Override
    void run(final AppConfiguration configuration, final Environment environment)
            throws Exception {

        LOGGER.info("Application name: {}", configuration.getAppName())
    }
}