package com.erw.randomrestaurant.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantListWRestaurants {
    @Embedded public RestaurantList list;
    @Relation(
            parentColumn = "list_id",
            entityColumn = "list_id"
    )
    public List<Restaurant> restaurants;
}
