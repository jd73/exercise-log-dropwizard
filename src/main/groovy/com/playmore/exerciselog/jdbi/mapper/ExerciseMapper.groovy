package com.playmore.exerciselog.jdbi.mapper

import com.playmore.exerciselog.api.Exercise
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

public class ExerciseMapper implements ResultSetMapper<Exercise> {
    public Exercise map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Exercise(r.getInt("id"), r.getString("name"))
    }
}