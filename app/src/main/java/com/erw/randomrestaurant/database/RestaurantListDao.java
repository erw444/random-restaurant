package com.erw.randomrestaurant.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.erw.randomrestaurant.database.RestaurantList;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;

import java.util.List;

@Dao
public interface RestaurantListDao {
    @Insert
    long insert(RestaurantList RestaurantList);

    //@Query("DELETE FROM restaurant_list")
    //void deleteAll();

    @Query("SELECT * from restaurant_list ORDER BY list_id ASC")
    LiveData<List<RestaurantList>> getAllRestaurantLists();

    @Query("SELECT * from restaurant_list where name = :listName")
    RestaurantList findRestaurantList(String listName);

    @Query("SELECT * from restaurant_list where list_id = :listId")
    RestaurantList findRestaurantList(int listId);

    @Query("SELECT * FROM restaurant_list INNER JOIN restaurant ON list_id = list_id")
    public LiveData<List<RestaurantListWRestaurants>> getListWithItems();

}
