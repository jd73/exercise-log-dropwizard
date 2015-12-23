package com.playmore.exerciselog.jdbi

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.mapper.ExerciseMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(ExerciseMapper)
interface ExerciseDao {
    @SqlUpdate("insert into exercise (name) values (:name)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name)

    @SqlQuery("select * from exercise where id = :id")
    Exercise findById(@Bind("id") int id)

    @SqlQuery("select * from exercise")
    List<Exercise> all()
}