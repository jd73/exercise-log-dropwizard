package com.playmore.exerciselog

import com.playmore.exerciselog.resources.WorkoutResource
import groovy.util.logging.Slf4j
import io.dropwizard.Application
import io.dropwizard.setup.Environment

@Slf4j
class ExerciseLogApplication extends Application<AppConfiguration> {

    static void main(final String[] args) throws Exception {
        new ExerciseLogApplication().run(args)
    }

    @Override
    void run(final AppConfiguration configuration, final Environment environment) throws Exception {
        log.info("Application name: {}", configuration.getAppName())

        //health checks


        // resources
        final WorkoutResource resource = new WorkoutResource(
                '5x5 back squats'
        )
        environment.jersey().register(resource)
    }
}