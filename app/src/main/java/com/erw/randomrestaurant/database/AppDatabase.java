package com.erw.randomrestaurant.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {RestaurantList.class, Restaurant.class}, version = 1, exportSchema = false)
@TypeConverters(com.erw.android.exercisehistory.database.DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "exerciseHistoryDb";
    private static volatile AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .addCallback(sRoomDatabaseCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract ExerciseHistoryDao getExerciseHistoryDao();

    public abstract ExerciseNameDao getExerciseNameDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(sInstance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ExerciseNameDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.getExerciseNameDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            ExerciseName name = new ExerciseName("Push Ups");
            mDao.insert(name);
            name = new ExerciseName("Sit Ups");
            mDao.insert(name);
            return null;
        }
    }
}
