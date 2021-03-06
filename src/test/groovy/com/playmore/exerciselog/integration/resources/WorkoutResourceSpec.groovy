package com.playmore.exerciselog.integration.resources

import com.playmore.exerciselog.AppConfiguration
import com.playmore.exerciselog.ExerciseLogApplication
import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.jdbi.helper.GenericTypes
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers
import org.flywaydb.core.Flyway
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.core.Response

class WorkoutResourceSpec extends Specification {

    @Shared
    String resourcePath

    static DropwizardTestSupport<AppConfiguration> testSupport = new DropwizardTestSupport<AppConfiguration>(
            ExerciseLogApplication,
            ResourceHelpers.resourceFilePath("test_config.yml")
    )

    void setupSpec() {
        testSupport.before()
        resourcePath = "http://localhost:${testSupport.getLocalPort()}/workouts"

        Flyway flyway = new Flyway()
        DataSourceFactory f = testSupport.getConfiguration().getDataSourceFactory()
        flyway.setDataSource(f.getUrl(), f.getUser(), f.getPassword())
        flyway.migrate()
    }

    void cleanupSpec() {
        testSupport.after()
    }

    void 'Should be able to create, get and list an workout'() {
        given:
        Client client = new JerseyClientBuilder(testSupport.getEnvironment()).build("test client")
        Workout workout = workout()

        when:
        Response postResponse = client
                .target(resourcePath)
                .request()
                .post(Entity.json(workout))

        Workout postWorkout = postResponse.readEntity(Workout)

        then:
        postResponse.status == 201
        postWorkout.name == workout.name
        postWorkout.exercises == workout.exercises

        when:
        Response getResponse = client
            .target(resourcePath + "/${postWorkout.id}")
            .request()
            .get()

        Workout getWorkout = getResponse.readEntity(Workout)

        then:
        getResponse.status == 200
        getWorkout.name == workout.name
        getWorkout.exercises == workout.exercises

        when:
        Response listResponse = client
            .target(resourcePath)
            .request()
            .get()

        List<Workout> workouts = listResponse.readEntity(GenericTypes.WorkoutList)

        then:
        listResponse.status == 200
        workouts.find { it.name == workout.name && it.exercises == workout.exercises }
    }

    Workout workout() {
        return new Workout(
                name: 'Test workout',
                exercises: [
                        new Exercise(name: 'Exercise one'),
                        new Exercise(name: 'Exercise two')
                ]
        )
    }
}
