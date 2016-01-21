package com.playmore.exerciselog.resources

import com.codahale.metrics.annotation.Timed
import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.jdbi.WorkoutStore

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/workouts")
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {
    private final WorkoutStore workoutStore

    WorkoutResource(WorkoutStore workoutStore) {
        this.workoutStore = workoutStore
    }

    @Timed
    @POST
    public Response add(String name) {
        return Response
                .status(Response.Status.CREATED)
                .entity(find(workoutStore.insert(name)))
                .build()
    }

    @Timed
    @GET @Path("/{id}")
    public Optional<Workout> find(@PathParam("id") Long id) {
        return workoutStore.findById(id)
    }

    @Timed
    @GET
    public List<Workout> all() {
        return workoutStore.all()
    }
}