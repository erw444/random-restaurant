package com.erw.randomrestaurant.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.erw.randomrestaurant.database.RestaurantList;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;

import java.util.List;

@Dao
public interface RestaurantListDao {
    @Insert
    long insert(RestaurantList RestaurantList);

    @Query("SELECT * from restaurant_list ORDER BY list_id ASC")
    LiveData<List<RestaurantListWRestaurants>> getAllRestaurantListsWithItems();

    @Query("SELECT * from restaurant_list where list_name = :listName")
    RestaurantList findRestaurantList(String listName);

    @Query("SELECT * from restaurant_list where list_id = :listId")
    RestaurantList findRestaurantList(int listId);

    @Transaction
    @Query("SELECT * FROM restaurant_list where restaurant_list.list_id = :listId")
    LiveData<RestaurantListWRestaurants> getListWithItemsAsync(long listId);

    @Transaction
    @Query("SELECT * FROM restaurant_list where restaurant_list.list_id = :listId")
    RestaurantListWRestaurants getListWithItems(long listId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(RestaurantList restaurant);

    @Query("DELETE FROM restaurant_list where list_id = :listId")
    void deleteByListId(long listId);
}
