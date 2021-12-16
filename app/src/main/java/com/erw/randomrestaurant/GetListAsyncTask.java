package com.erw.randomrestaurant;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.erw.randomrestaurant.database.RandomRestaurantRepository;
import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*public class GetListAsyncTask extends AsyncTask<Void, Void, RestaurantListWRestaurants> {

    private AsyncResponse delegateActivity;
    private RandomRestaurantRepository mRepository;
    private long listId;

    private RestaurantListWRestaurants listWItems;

    public GetListAsyncTask(AsyncResponse delegateActivity, RandomRestaurantRepository repository, long listId) {
        this.delegateActivity = delegateActivity;
        mRepository = repository;
        this.listId = listId;
    }

    @Override
    protected void doInBackground(Void... params) {
        listWItems = mRepository.getListWRestaurant(listId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostExecute(RestaurantListWRestaurants restaurants) {
        delegateActivity.processFinish(restaurants);

    }

    public interface AsyncResponse {
        void processFinish(RestaurantListWRestaurants output);
    }
}*/
