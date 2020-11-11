package com.erw.randomrestaurant;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.erw.randomrestaurant.database.RandomRestaurantRepository;
import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantList;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;

import java.util.List;

public class RestaurantListViewModel extends AndroidViewModel {
    private RandomRestaurantRepository mRepository;

    private final LiveData<List<RestaurantListWRestaurants>> mAllLists;

    public RestaurantListViewModel (Application application) {
        super(application);
        mRepository = new RandomRestaurantRepository(application);
        mAllLists = mRepository.getAllLists();
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
        RestaurantListWRestaurants listWithRestaurants = mRepository.getListWRestaurant(id);
        return listWithRestaurants.restaurants;
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
