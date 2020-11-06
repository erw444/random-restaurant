package com.erw.randomrestaurant.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RandomRestaurantRepository {

    private RestaurantListDao mListDao;
    private LiveData<List<RestaurantList>> mAllLists;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public RandomRestaurantRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mListDao = db.getRestaurantListDao();
        mAllLists = mListDao.getAllRestaurantLists();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<RestaurantList>> getAllLists() {
        return mAllLists;
    }

    public LiveData<RestaurantListWRestaurants> getList(long id) {
        return mListDao.getListWithItems(id);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(RestaurantList list) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mListDao.insert(list);
        });
    }
}
