package com.erw.randomrestaurant.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {RestaurantList.class, Restaurant.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
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

    public abstract RestaurantListDao getRestaurantListDao();

    public abstract RestaurantDao getRestaurantDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(sInstance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RestaurantDao restaurantDao;
        private final RestaurantListDao listDao;

        PopulateDbAsync(AppDatabase db) {
            restaurantDao = db.getRestaurantDao();
            listDao = db.getRestaurantListDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            RestaurantList list = new RestaurantList("Elijah");
            Restaurant restaurant1 = new Restaurant(1, "Chipotle");
            Restaurant restaurant2 = new Restaurant(1, "Salata");
            listDao.insert(list);
            restaurantDao.insert(restaurant1);
            restaurantDao.insert(restaurant2);
            return null;
        }
    }
}
