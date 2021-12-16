package com.erw.randomrestaurant.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

public class RandomRestaurantRepository {

    private RestaurantListDao mListDao;
    private RestaurantDao mRestaurantDao;
    private LiveData<List<RestaurantListWRestaurants>> mAllLists;
    private Executor mExecutor;

    public RandomRestaurantRepository(Application application, Executor executor) {
        AppDatabase db = AppDatabase.getInstance(application);
        mListDao = db.getRestaurantListDao();
        mRestaurantDao = db.getRestaurantDao();
        mAllLists = mListDao.getAllRestaurantListsWithItems();
        mExecutor = executor;
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<RestaurantListWRestaurants>> getAllLists() {
        return mAllLists;
    }

    public LiveData<RestaurantListWRestaurants> getListWRestaurantAsync(long id) {
        return mListDao.getListWithItemsAsync(id);
    }

    interface RepositoryCallback<T> {
        void onComplete(RestaurantListWRestaurants result);
    }


    public void getListWRestaurant(long id,
                                   final RepositoryCallback<RestaurantListWRestaurants> callback) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                RestaurantListWRestaurants foundList = mListDao.getListWithItems(id);
                callback.onComplete(foundList);
            }
        });
    }

    public LiveData<Restaurant> getRestaurant(long id) {
        return mRestaurantDao.getRestaurantById(id);
    }

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
