package com.erw.randomrestaurant.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurant")
public class Restaurant implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "restaurant_id")
    private long restaurantId;

    @ColumnInfo(name = "list_id")
    private long listId;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;


    public Restaurant(){}

    @Ignore
    public Restaurant(int restaurantId, String name){
        this.restaurantId = restaurantId;
        this.name = name;
    }

    @Ignore
    public Restaurant(String name){
        this.name = name;
    }

    @Ignore
    private Restaurant(Parcel in) {
        restaurantId = in.readLong();
        listId = in.readLong();
        name = in.readString();
    }

    public long getRestaurantId() {
        return restaurantId;
    }


    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String toString(){
        return getName();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(restaurantId);
        parcel.writeLong(listId);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };


}
