package com.erw.randomrestaurant;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizeActivity extends AppCompatActivity {

    private long mListId;

    private RestaurantListViewModel mListViewModel;
    private List<Restaurant> restaurants;
    //private GetListAsyncTask getListTask;

    private TextView mChosenRestaurantTextView;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        mListId = getIntent().getLongExtra("listId", -1);

        mChosenRestaurantTextView = findViewById(R.id.chosen_item_result);

        mListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RestaurantListViewModel.class);
        restaurants = mListViewModel.getRestaurantsFromList(mListId);

        final Button randomizeListButton = findViewById(R.id.randomize_button);
        randomizeListButton.setOnClickListener(view -> {
            if(restaurants != null && restaurants.size() != 0){
                int randomNum = ThreadLocalRandom.current().nextInt(0, restaurants.size());
                Restaurant chosenRestaurant = restaurants.get(randomNum);

                mChosenRestaurantTextView.setText(chosenRestaurant.getName() + CodesAndStrings.CHOSEN_ENDING);
            }
        });
    }

    /*public void processFinish(RestaurantListWRestaurants listData){
        this.mListData = listData;
    }*/
}
