package com.playmore.exerciselog

import com.playmore.exerciselog.health.DefaultHealthCheck
import com.playmore.exerciselog.jdbi.ExerciseStore
import com.playmore.exerciselog.resources.ExerciseResource
import groovy.util.logging.Slf4j
import io.dropwizard.Application
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

@Slf4j
class ExerciseLogApplication extends Application<AppConfiguration> {

    static void main(final String[] args) throws Exception {
        new ExerciseLogApplication().run(args)
    }

    @Override
    void run(final AppConfiguration configuration, final Environment environment) throws Exception {
        log.info("Application name: {}", configuration.getAppName())

        // database
        DBI dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "db")

        // health checks
        final DefaultHealthCheck healthCheck = new DefaultHealthCheck()
        environment.healthChecks().register("default", healthCheck)

        // resources
        environment.jersey().register(new ExerciseResource(dbi.onDemand(ExerciseStore)))

    }
}