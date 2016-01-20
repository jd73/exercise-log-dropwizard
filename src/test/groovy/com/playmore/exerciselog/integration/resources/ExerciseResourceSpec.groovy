package com.playmore.exerciselog.integration.resources

import com.playmore.exerciselog.AppConfiguration
import com.playmore.exerciselog.ExerciseLogApplication
import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.GenericTypes
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

class ExerciseResourceSpec extends Specification {

    @Shared
    String resourcePath

    static DropwizardTestSupport<AppConfiguration> testSupport = new DropwizardTestSupport<AppConfiguration>(
            ExerciseLogApplication,
            ResourceHelpers.resourceFilePath("test_config.yml")
    )

    void setupSpec() {
        testSupport.before()
        resourcePath = "http://localhost:${testSupport.getLocalPort()}/exercises"

        Flyway flyway = new Flyway()
        DataSourceFactory f = testSupport.getConfiguration().getDataSourceFactory()
        flyway.setDataSource(f.getUrl(), f.getUser(), f.getPassword())
        flyway.migrate()
    }

    void cleanupSpec() {
        testSupport.after()
    }

    void 'Should be able to create, get and list an exercise'() {
        given:
        Client client = new JerseyClientBuilder(testSupport.getEnvironment()).build("test client")

        Exercise expected = exercise()

        when:
        Response postResponse = client
                .target(resourcePath)
                .request()
                .post(Entity.json(expected))

        Exercise postExercise = postResponse.readEntity(Exercise)

        then:
        postResponse.status == 201
        postExercise == expected

        when:
        Response getResponse = client
            .target(resourcePath + "/${postExercise.id}")
            .request()
            .get()

        Exercise getExercise = getResponse.readEntity(Exercise)

        then:
        getResponse.status == 200
        getExercise == expected

        when:
        Response listResponse = client
            .target(resourcePath)
            .request()
            .get()

        List<Exercise> exercises = listResponse.readEntity(GenericTypes.ExerciseList)

        then:
        listResponse.status == 200
        exercises.contains(expected)
    }

    Exercise exercise() {
        return new Exercise(
                id: 123L,
                name: 'Test exercise'
        )
    }
}
