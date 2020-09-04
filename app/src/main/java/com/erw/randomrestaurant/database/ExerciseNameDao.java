package com.erw.android.exercisehistory.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseNameDao {
    @Insert
    long insert(ExerciseName exerciseName);

    @Query("DELETE FROM exercise_names")
    void deleteAll();

    @Query("SELECT * from exercise_names ORDER BY exercise_name_id ASC")
    LiveData<List<ExerciseName>> getAllExerciseNames();

    @Query("SELECT * from exercise_names where exercise_name = :exerciseName")
    ExerciseName findExerciseName(String exerciseName);

    @Query("SELECT * from exercise_names where exercise_name_id = :exerciseNameId")
    ExerciseName findExerciseName(int exerciseNameId);

}
