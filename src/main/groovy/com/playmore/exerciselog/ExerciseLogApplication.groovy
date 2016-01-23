package com.playmore.exerciselog

import com.playmore.exerciselog.health.DefaultHealthCheck
import com.playmore.exerciselog.jdbi.dao.WorkoutExerciseDao
import com.playmore.exerciselog.jdbi.repository.ExerciseRepository
import com.playmore.exerciselog.jdbi.dao.WorkoutDao
import com.playmore.exerciselog.jdbi.repository.WorkoutRepository
import com.playmore.exerciselog.resources.ExerciseResource
import com.playmore.exerciselog.resources.WorkoutResource
import groovy.util.logging.Slf4j
import io.dropwizard.Application
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.flyway.FlywayBundle
import io.dropwizard.flyway.FlywayFactory
import io.dropwizard.java8.Java8Bundle
import io.dropwizard.java8.jdbi.DBIFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

@Slf4j
class ExerciseLogApplication extends Application<AppConfiguration> {

    static void main(final String[] args) throws Exception {
        new ExerciseLogApplication().run(args)
    }

    @Override
    void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.addBundle(new Java8Bundle())
        bootstrap.addBundle(new FlywayBundle<AppConfiguration>() {
            @Override
            DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
                return configuration.getDataSourceFactory()
            }

            @Override
            FlywayFactory getFlywayFactory(AppConfiguration configuration) {
                return configuration.getFlywayFactory()
            }
        })

    }

    @Override
    void run(final AppConfiguration configuration, final Environment environment) throws Exception {
        log.info("Application name: {}", configuration.getAppName())

        // database
        DBI dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "db")

        // health checks
        final DefaultHealthCheck healthCheck = new DefaultHealthCheck()
        environment.healthChecks().register("default", healthCheck)

        // data access
        WorkoutDao workoutDao = dbi.onDemand(WorkoutDao)
        WorkoutExerciseDao workoutExerciseDao = dbi.onDemand(WorkoutExerciseDao)

        ExerciseRepository exerciseRepository = dbi.onDemand(ExerciseRepository)
        WorkoutRepository workoutRepository = new WorkoutRepository(
                workoutDao,
                workoutExerciseDao,
                exerciseRepository
        )

        // resources
        environment.jersey().register(new ExerciseResource(exerciseRepository))
        environment.jersey().register(new WorkoutResource(
                workoutRepository,
                exerciseRepository
        ))
    }
}