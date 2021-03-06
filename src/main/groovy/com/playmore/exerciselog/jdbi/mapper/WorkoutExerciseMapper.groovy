package com.playmore.exerciselog.jdbi.mapper

import com.playmore.exerciselog.api.associations.WorkoutExerciseAssociation
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

public class WorkoutExerciseMapper implements ResultSetMapper<WorkoutExerciseAssociation> {
    public WorkoutExerciseAssociation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new WorkoutExerciseAssociation(
                workoutId: r.getInt('workout_id'),
                exerciseId: r.getInt('exercise_id')
        )
    }
}