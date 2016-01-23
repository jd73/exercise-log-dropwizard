package com.playmore.exerciselog.jdbi.dao

import com.playmore.exerciselog.api.associations.WorkoutExercise
import com.playmore.exerciselog.jdbi.mapper.WorkoutExerciseMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.BindBean
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

// Example: https://github.com/jd73/jdbi-examples/blob/master/src/main/java/no/kodemaker/ps/jdbiapp/repository/TeamDaoJdbi.java

@RegisterMapper(WorkoutExerciseMapper)
interface WorkoutExerciseDao {
    /*
    @SqlUpdate("insert into workout_exercise (workout_d, exercise_id) values (:workout_id, exercise_id)")
    void insert(@Bind("workout_id") Long workoutId, @Bind("exercise_id") Long exerciseId)

    @SingleValueResult
    @SqlQuery("select * from workout_exercise where workout_id = :workout_id and exercise_id = :exercise_id")
    Optional<WorkoutExercise> findByIds(@Bind("workout_id") long workoutId, @Bind("exercise_id") long exerciseId)

    @SqlQuery("select * from workout_exercise")
    List<WorkoutExercise> all()
    */

    @SqlUpdate("insert into workout_exercise (workout_id, exercise_id) values (:workoutId, :exerciseId)")
    @GetGeneratedKeys
    long insert(@BindBean WorkoutExercise workoutExercise)

    @SqlQuery("select * from workout_exercise where workout_id = :workout_id")
    List<WorkoutExercise> findByWorkoutId(@Bind("workout_id") Long workoutId)

    @SqlUpdate("delete from workout_exercise where workout_id = :workoutId and exercise_id = :exerciseId")
    void delete(@BindBean WorkoutExercise teamPerson)
}