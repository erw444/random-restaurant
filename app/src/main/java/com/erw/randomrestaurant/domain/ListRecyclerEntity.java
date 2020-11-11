package com.erw.randomrestaurant.domain;

import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantList;

import java.util.List;

public class ListRecyclerEntity {

    private long dbId;
    private String title;
    private boolean showMenu = false;
    private int numRestaurants = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public int getNumRestaurants() {
        return numRestaurants;
    }

    public void setNumRestaurants(int numRestaurants) {
        this.numRestaurants = numRestaurants;
    }

    public ListRecyclerEntity() {
    }

    public ListRecyclerEntity(String title, boolean showMenu) {
        this.title = title;
        this.showMenu = showMenu;
    }

    public ListRecyclerEntity(RestaurantList restaurantList, List<Restaurant> restaurants){
        this.dbId = restaurantList.getListId();
        this.title = restaurantList.getName();
        this.showMenu = false;
        this.numRestaurants = restaurants.size();
    }
}
