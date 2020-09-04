package com.erw.android.exercisehistory.database;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseHistoryEntityWithExerciseName implements Parcelable {
    @Embedded public ExerciseName exerciseName;
    @Embedded public ExerciseHistoryEntity exerciseHistoryEntity;

    public ExerciseHistoryEntityWithExerciseName(ExerciseName exerciseName,
                                                 ExerciseHistoryEntity exerciseHistoryEntity){
        this.exerciseName = exerciseName;
        this.exerciseHistoryEntity = exerciseHistoryEntity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(exerciseName, 0);
        parcel.writeParcelable(exerciseHistoryEntity, 0);
    }

    public static final Parcelable.Creator<ExerciseHistoryEntityWithExerciseName> CREATOR
            = new Parcelable.Creator<ExerciseHistoryEntityWithExerciseName>() {
        public ExerciseHistoryEntityWithExerciseName createFromParcel(Parcel in) {
            return new ExerciseHistoryEntityWithExerciseName(in);
        }

        public ExerciseHistoryEntityWithExerciseName[] newArray(int size) {
            return new ExerciseHistoryEntityWithExerciseName[size];
        }
    };

    private ExerciseHistoryEntityWithExerciseName(Parcel in) {
        exerciseName = in.readParcelable(ExerciseName.class.getClassLoader());
        exerciseHistoryEntity = in.readParcelable(ExerciseHistoryEntity.class.getClassLoader());
    }
}
