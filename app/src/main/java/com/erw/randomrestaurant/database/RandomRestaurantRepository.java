package com.erw.randomrestaurant.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RandomRestaurantRepository {

    private RestaurantListDao mListDao;
    private RestaurantDao mRestaurantDao;
    private LiveData<List<RestaurantListWRestaurants>> mAllLists;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public RandomRestaurantRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mListDao = db.getRestaurantListDao();
        mRestaurantDao = db.getRestaurantDao();
        mAllLists = mListDao.getAllRestaurantListsWithItems();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<RestaurantListWRestaurants>> getAllLists() {
        return mAllLists;
    }

    public LiveData<RestaurantListWRestaurants> getList(long id) {
        return mListDao.getListWithItems(id);
    }

    public LiveData<Restaurant> getRestaurant(long id) {
        return mRestaurantDao.getRestaurantById(id);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertList(RestaurantList list) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mListDao.insert(list);
        });
    }

    public void insertRestaurant(Restaurant restaurant) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mRestaurantDao.insert(restaurant);
        });
    }

    public void updateList(RestaurantList list){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mListDao.update(list);
        });
    }

    public void updateRestaurant(Restaurant restaurant) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mRestaurantDao.update(restaurant);
        });
    }

    public void deleteList(long listId){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mListDao.deleteByListId(listId);
        });
    }

    public void deleteRestaurant(long restaurantId){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mRestaurantDao.deleteByRestaurantId(restaurantId);
        });
    }
}
