package com.erw.android.exercisehistory.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;


import java.util.List;

public class ExerciseNameRepository {
    private ExerciseNameDao mExerciseNameDao;
    private LiveData<List<ExerciseName>> mExerciseNames;

    public ExerciseNameRepository(Application app){
        AppDatabase db = AppDatabase.getInstance(app);
        mExerciseNameDao = db.getExerciseNameDao();
        mExerciseNames = mExerciseNameDao.getAllExerciseNames();
    }

    public LiveData<List<ExerciseName>> getExerciseNames(){
        return mExerciseNames;
    }

    public void insert(ExerciseName exerciseName){
        new ExerciseNameRepository.insertAsyncTask(mExerciseNameDao).execute(exerciseName);
    }

    public ExerciseName getExerciseName(String exerciseName){
        return mExerciseNameDao.findExerciseName(exerciseName);
    }

    public ExerciseName getExerciseName(int exerciseNameId){
        return mExerciseNameDao.findExerciseName(exerciseNameId);
    }

    private static class insertAsyncTask extends AsyncTask<ExerciseName, Void, Void> {
        private ExerciseNameDao mAsyncTaskDao;

        insertAsyncTask(ExerciseNameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ExerciseName... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
