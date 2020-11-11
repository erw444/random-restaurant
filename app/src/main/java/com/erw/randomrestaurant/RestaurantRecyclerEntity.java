package com.erw.randomrestaurant;

import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantList;

public class RestaurantRecyclerEntity {

    private long dbId;
    private String name;
    private boolean showMenu = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public RestaurantRecyclerEntity() {
    }

    public RestaurantRecyclerEntity(String name, boolean showMenu) {
        this.name = name;
        this.showMenu = showMenu;
    }

    public RestaurantRecyclerEntity(Restaurant dbRestaurant){
        this.dbId = dbRestaurant.getRestaurantId();
        this.name = dbRestaurant.getName();
        this.showMenu = false;
    }
}
