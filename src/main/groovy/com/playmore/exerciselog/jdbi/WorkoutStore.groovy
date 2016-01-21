package com.playmore.exerciselog.jdbi

import com.playmore.exerciselog.api.Workout
import com.playmore.exerciselog.jdbi.mapper.WorkoutMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult

@RegisterMapper(WorkoutMapper)
interface WorkoutStore {
    @SqlUpdate("insert into workout (name) values (:name)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name)

    @SingleValueResult
    @SqlQuery("select * from workout where id = :id")
    Optional<Workout> findById(@Bind("id") long id)

    @SqlQuery("select * from workout")
    List<Workout> all()
}