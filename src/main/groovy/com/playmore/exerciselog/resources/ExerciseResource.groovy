package com.playmore.exerciselog.resources

import com.codahale.metrics.annotation.Timed
import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.ExerciseStore

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/exercise")
@Produces(MediaType.APPLICATION_JSON)
public class ExerciseResource {
    private final ExerciseStore exerciseStore

    ExerciseResource(ExerciseStore exerciseStore) {
        this.exerciseStore = exerciseStore
    }

    @Timed
    @POST @Path("/add")
    public Exercise add(String name) {
        return find(exerciseStore.insert(name))
    }

    @Timed
    @GET @Path("/item/{id}")
    public Exercise find(@PathParam("id") Long id) {
        return exerciseStore.findById(id)
    }

    @Timed
    @GET @Path("/all")
    public List<Exercise> all() {
        return exerciseStore.all()
    }
}