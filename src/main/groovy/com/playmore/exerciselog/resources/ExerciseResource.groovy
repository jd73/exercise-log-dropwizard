package com.playmore.exerciselog.resources

import com.codahale.metrics.annotation.Timed
import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.ExerciseDao
import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.Handle

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/exercise")
@Produces(MediaType.APPLICATION_JSON)
public class ExerciseResource {
    private final ExerciseDao dao

    public ExerciseResource(DBI dbi) {
        this.dao = dbi.onDemand(ExerciseDao)

        Handle handle
        try {
            handle = dbi.open()
            handle.execute("create table exercise (id int primary key auto_increment, name varchar(100))")
            List<String> exercies = ["Back Squat", "Bench Press", "Overhead Press", "Deadlift"]
            exercies.each { String exercise ->
                handle.insert("insert into exercise (name) values (?)", exercise)
            }
        } finally {
            handle.close()
        }
    }

    @Timed
    @POST @Path("/add")
    public Exercise add(String name) {
        return find(dao.insert(name))
    }

    @Timed
    @GET @Path("/item/{id}")
    public Exercise find(@PathParam("id") Integer id) {
        return dao.findById(id)
    }

    @Timed
    @GET @Path("/all")
    public List<Exercise> all(@PathParam("id") Integer id) {
        return dao.all()
    }
}