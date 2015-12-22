package com.playmore.exerciselog.resources

import com.playmore.exerciselog.api.Workout
import com.google.common.base.Optional
import com.codahale.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import java.util.concurrent.atomic.AtomicLong

@Path("/workout")
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {

    private final String defaultExercise
    private final AtomicLong counter

    public WorkoutResource(String defaultExercise) {
        this.defaultExercise = defaultExercise
        this.counter = new AtomicLong()
    }

    @GET
    @Timed
    public Workout getWorkout(@QueryParam("exercise") Optional<String>  exercise) {
        List<String> exercises = [exercise.or(defaultExercise)]
        return new Workout(
                id: counter.incrementAndGet(),
                exercises: exercises
        )
    }
}