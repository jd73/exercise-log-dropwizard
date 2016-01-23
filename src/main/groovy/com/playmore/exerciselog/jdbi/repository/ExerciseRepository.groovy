package com.playmore.exerciselog.jdbi.repository

import com.playmore.exerciselog.api.Exercise
import com.playmore.exerciselog.jdbi.mapper.ExerciseMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.BindBean
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult

@RegisterMapper(ExerciseMapper)
interface ExerciseRepository {
    @SqlUpdate("insert into exercise (name) values (:name)")
    @GetGeneratedKeys
    int insert(@BindBean Exercise exercise)

    @SingleValueResult
    @SqlQuery("select * from exercise where id = :id")
    Optional<Exercise> findById(@Bind("id") long id)

    @SingleValueResult
    @SqlQuery("select * from exercise where name = :name")
    Optional<Exercise> findByName(@Bind("name") String name)

    @SqlQuery("select * from exercise")
    List<Exercise> all()
}