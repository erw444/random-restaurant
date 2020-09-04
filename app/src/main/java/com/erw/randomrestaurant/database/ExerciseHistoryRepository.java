package com.erw.android.exercisehistory.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ExerciseHistoryRepository {
    private ExerciseNameDao mExerciseNameDao;
    private ExerciseHistoryDao mExerciseHistoryDao;
    private LiveData<List<ExerciseHistoryEntityWithExerciseName>> mExerciseHistory;

    public ExerciseHistoryRepository(Application app){
        AppDatabase db = AppDatabase.getInstance(app);
        mExerciseNameDao = db.getExerciseNameDao();
        mExerciseHistoryDao = db.getExerciseHistoryDao();
        mExerciseHistory = mExerciseHistoryDao.getExerciseHistory();
    }

    public LiveData<List<ExerciseHistoryEntityWithExerciseName>> getExerciseHistory(){
        return mExerciseHistory;
    }

    public void insert(ExerciseHistoryEntityWithExerciseName exerciseEntry){
        new insertAsyncTask(mExerciseNameDao, mExerciseHistoryDao).execute(exerciseEntry);
    }

    private static class insertAsyncTask extends AsyncTask<ExerciseHistoryEntityWithExerciseName, Void, Void>{
        private ExerciseNameDao mAsyncENTaskDao;
        private ExerciseHistoryDao mAsyncEHTaskDao;

        insertAsyncTask(ExerciseNameDao ENDao, ExerciseHistoryDao EHdao) {
            mAsyncENTaskDao = ENDao;
            mAsyncEHTaskDao = EHdao;
        }

        @Override
        protected Void doInBackground(final ExerciseHistoryEntityWithExerciseName... params) {
            ExerciseName exerciseName = params[0].exerciseName;
            ExerciseHistoryEntity exerciseHistoryEntity = params[0].exerciseHistoryEntity;
            if(exerciseName.getId() == 0) {
               long newNameId = mAsyncENTaskDao.insert(exerciseName);
               exerciseHistoryEntity.setExerciseNameId((int)newNameId);
            }
            mAsyncEHTaskDao.insert(exerciseHistoryEntity);
            return null;
        }
    }
}
