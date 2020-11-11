package com.erw.randomrestaurant;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.erw.randomrestaurant.database.RandomRestaurantRepository;
import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.domain.ListRecyclerEntity;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizeAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {

    private Application application;
    private RandomRestaurantRepository mRepository;
    private ListRecyclerEntity chosenEntity;

    public RandomizeAsyncTask(Application application, RandomRestaurantRepository repository, ListRecyclerEntity chosenEntity) {
        this.application = application;
        mRepository = repository;
        this.chosenEntity = chosenEntity;
    }

    @Override
    protected List<Restaurant> doInBackground(Void... params) {
       return mRepository.getListWRestaurant(chosenEntity.getDbId()).restaurants;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, chosenEntity.getNumRestaurants());

        Restaurant chosenRestaurant = restaurants.get(randomNum);

        Toast.makeText(
                application,
                "Eat at: " + chosenRestaurant.getName(),
                Toast.LENGTH_LONG).show();
    }
}
