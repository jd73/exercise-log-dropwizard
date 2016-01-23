package com.playmore.exerciselog.resources

import com.codahale.metrics.annotation.Timed
import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.jdbi.dao.WorkoutDao
import com.playmore.exerciselog.jdbi.repository.ExerciseRepository
import com.playmore.exerciselog.jdbi.repository.WorkoutRepository

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/workouts")
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {
    private final WorkoutRepository workoutRepository
    private final ExerciseRepository exerciseRepository

    WorkoutResource(
            WorkoutRepository workoutRepository,
            ExerciseRepository exerciseRepository
    ) {
        this.workoutRepository = workoutRepository
        this.exerciseRepository = exerciseRepository
    }

    @Timed
    @POST
    public Response add(Workout workout) {
        workout.exercises = workout.exercises*.id.collect { exerciseRepository.findById(it) }*.orElse(null).grep()

        return Response
                .status(Response.Status.CREATED)
                .entity(workoutRepository.save(workout))
                .build()
    }

    @Timed
    @GET @Path("/{id}")
    public Optional<Workout> find(@PathParam("id") Long id) {
        return workoutRepository.findById(id)
    }

    @Timed
    @GET
    public List<Workout> all() {
        return workoutRepository.all()
    }
}