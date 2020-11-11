package com.erw.randomrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.erw.randomrestaurant.database.Restaurant;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText mEditListView;

    private RestaurantListViewModel mListViewModel;

    private Restaurant restaurantToBeEdited;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        long restaurantId = getIntent().getLongExtra(CodesAndStrings.EXTRA_RESTAURANT_ID, -1);
        if(restaurantId != -1){
            mListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RestaurantListViewModel.class);

            mListViewModel.getRestaurant(restaurantId).observe(this, restaurant -> {
                restaurantToBeEdited = restaurant;
                mEditListView.setText(restaurantToBeEdited.getName());
            });
        }

        mEditListView = findViewById(R.id.restaurant_name);

        final Button button = findViewById(R.id.save_add_restaurant);
        button.setOnClickListener(view -> {

            if(restaurantToBeEdited != null){
                saveEditedRestaurant();
            } else {
                returnNewRestaurant();
            }

            finish();
        });
    }

    private void saveEditedRestaurant(){
        Intent replyIntent = new Intent();
        String newRestaurantName = mEditListView.getText().toString();
        if (TextUtils.isEmpty(newRestaurantName) || newRestaurantName.equals(restaurantToBeEdited.getName())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            restaurantToBeEdited.setName(newRestaurantName);
            mListViewModel.updateRestaurant(restaurantToBeEdited);
            setResult(RESULT_OK, replyIntent);
        }
    }

    private void returnNewRestaurant(){
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditListView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String restaurantName = mEditListView.getText().toString();
            replyIntent.putExtra(CodesAndStrings.EXTRA_REPLY, restaurantName);
            setResult(RESULT_OK, replyIntent);
        }
    }
}