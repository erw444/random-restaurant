package com.erw.randomrestaurant;

import com.erw.randomrestaurant.database.RestaurantList;

public class ListRecyclerEntity {

    private String title;
    private boolean showMenu = false;

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

    public ListRecyclerEntity() {
    }

    public ListRecyclerEntity(String title, int image, boolean showMenu) {
        this.title = title;
        this.showMenu = showMenu;
    }

    public ListRecyclerEntity(RestaurantList restaurantList){
        this.title = restaurantList.getName();
        this.showMenu = false;
    }
}
