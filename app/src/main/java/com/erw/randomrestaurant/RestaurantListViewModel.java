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

    private final LiveData<List<RestaurantList>> mAllLists;

    public RestaurantListViewModel (Application application) {
        super(application);
        mRepository = new RandomRestaurantRepository(application);
        mAllLists = mRepository.getAllLists();
    }

    LiveData<List<RestaurantList>> getAllLists() { return mAllLists; }

    LiveData<RestaurantListWRestaurants> getRestaurantList(long id) {
        return mRepository.getList(id);
    }

    public void insert(RestaurantList list) { mRepository.insert(list); }

}
