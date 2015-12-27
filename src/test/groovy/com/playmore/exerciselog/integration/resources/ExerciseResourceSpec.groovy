package com.playmore.exerciselog.integration.resources

import com.playmore.exerciselog.AppConfiguration
import com.playmore.exerciselog.ExerciseLogApplication
import com.playmore.exerciselog.api.Exercise
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers
import org.flywaydb.core.Flyway
import spock.lang.Specification

import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.core.Response

class ExerciseResourceSpec extends Specification {

    static DropwizardTestSupport<AppConfiguration> testSupport = new DropwizardTestSupport<AppConfiguration>(
            ExerciseLogApplication,
            ResourceHelpers.resourceFilePath("test_config.yml")
    )

    void setupSpec() {
        testSupport.before()
        Flyway flyway = new Flyway()
        DataSourceFactory f = testSupport.getConfiguration().getDataSourceFactory()
        flyway.setDataSource(f.getUrl(), f.getUser(), f.getPassword())
        flyway.migrate()
    }

    void cleanupSpec() {
        testSupport.after()
    }

    void 'Test some stuff'() {
        given:
        Client client = new JerseyClientBuilder(testSupport.getEnvironment()).build("test client")

        Exercise expected = exercise()

        when:
        Response response = client
                .target("http://localhost:${testSupport.getLocalPort()}/exercises")
                .request()
                .post(Entity.json(expected))

        Exercise actual = response.readEntity(Exercise)

        then:
        response.status == 201
        actual == expected
    }

    Exercise exercise() {
        return new Exercise(
                id: 123L,
                name: 'Test exercise'
        )
    }
}
