package com.erw.randomrestaurant.database;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "restaurant_list")
public class RestaurantList implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    private long listId;

    @ColumnInfo(name = "name")
    private String name;

    public RestaurantList() {}

    @Ignore
    public RestaurantList(String name){
       this.name = name;
    }

    @Ignore
    public RestaurantList(int listId, String name) {
        this.listId = listId;
        this.name = name;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(listId);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<RestaurantList> CREATOR = new Parcelable.Creator<RestaurantList>() {
        public RestaurantList createFromParcel(Parcel in) {
            return new RestaurantList(in);
        }

        public RestaurantList[] newArray(int size) {
            return new RestaurantList[size];
        }
    };

    private RestaurantList(Parcel in) {
        listId = in.readLong();
        name = in.readString();
    }
}
