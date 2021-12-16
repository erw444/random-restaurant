package com.erw.randomrestaurant;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.erw.randomrestaurant.database.RandomRestaurantRepository;
import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantList;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RestaurantListViewModel extends AndroidViewModel {
    private RandomRestaurantRepository mRepository;

    private final LiveData<List<RestaurantListWRestaurants>> mAllLists;

    private List<RestaurantListViewModel> mLoadedList;

    public RestaurantListViewModel (Application application) {
        super(application);
        ExecutorService executorService = ((RandomRestaurantApplication) application).executorService;
        mRepository = new RandomRestaurantRepository(application, executorService);
        mAllLists = mRepository.getAllLists();
        mLoadedList = new ArrayList<RestaurantListViewModel>();
    }

    public RandomRestaurantRepository getRepository() {
        return mRepository;
    }

    LiveData<List<RestaurantListWRestaurants>> getAllLists() { return mAllLists; }

    LiveData<RestaurantListWRestaurants> getRestaurantList(long id) {
        return mRepository.getListWRestaurantAsync(id);
    }

    LiveData<Restaurant> getRestaurant(long id) {
        return mRepository.getRestaurant(id);
    }

    public List<Restaurant> getRestaurantsFromList(long id){
        RestaurantListWRestaurants listWithRestaurants = mRepository.getListWRestaurant(id,
                new RandomRestaurantRepository.RepositoryCallback<RestaurantListWRestaurants>() {
            @Override
            public void onComplete(RestaurantListWRestaurants result) {
                mLoadedList = 
            }
        });
    }

    public void insertList(RestaurantList list) { mRepository.insertList(list); }

    public void insertRestaurant(Restaurant restaurant) { mRepository.insertRestaurant(restaurant); }

    public void updateList(RestaurantList list) { mRepository.updateList(list); }

    public void updateRestaurant(Restaurant restaurant) { mRepository.updateRestaurant(restaurant); }

    public void deleteRestaurantList(long listId){
        mRepository.deleteList(listId);
    }

    public void deleteRestaurant(long restaurantId){
        mRepository.deleteRestaurant(restaurantId);
    }

}
