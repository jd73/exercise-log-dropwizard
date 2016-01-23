package com.playmore.exerciselog.resources

import com.codahale.metrics.annotation.Timed
import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.repository.ExerciseRepository

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/exercises")
@Produces(MediaType.APPLICATION_JSON)
public class ExerciseResource {
    private final ExerciseRepository exerciseStore

    ExerciseResource(ExerciseRepository exerciseStore) {
        this.exerciseStore = exerciseStore
    }

    @Timed
    @POST
    public Response add(String name) {
        return Response
                .status(Response.Status.CREATED)
                .entity(find(exerciseStore.insert(name)))
                .build()
    }

    @Timed
    @GET @Path("/{id}")
    public Optional<Exercise> find(@PathParam("id") Long id) {
        return exerciseStore.findById(id)
    }

    @Timed
    @GET
    public List<Exercise> all() {
        return exerciseStore.all()
    }
}