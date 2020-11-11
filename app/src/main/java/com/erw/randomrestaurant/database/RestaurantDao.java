package com.erw.randomrestaurant.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM restaurant")
    LiveData<List<Restaurant>> getRestaurants();

    @Insert
    void insert(Restaurant restaurant);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Restaurant restaurant);

    @Query("DELETE FROM restaurant where restaurant_id = :restaurantId")
    void deleteByRestaurantId(long restaurantId);

    @Query("SELECT * FROM restaurant WHERE restaurant_id = :id")
    LiveData<Restaurant> getRestaurantById(long id);


}