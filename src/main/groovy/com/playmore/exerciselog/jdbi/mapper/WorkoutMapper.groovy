package com.playmore.exerciselog.jdbi.mapper

import com.playmore.exerciselog.api.Workout
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

public class WorkoutMapper implements ResultSetMapper<Workout> {
    public Workout map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Workout(
                id: r.getInt('id'),
                name: r.getString('name')
        )
    }
}