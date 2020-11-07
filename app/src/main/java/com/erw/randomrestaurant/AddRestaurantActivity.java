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
import androidx.navigation.fragment.NavHostFragment;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText mEditListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        mEditListView = findViewById(R.id.restaurant_name);

        final Button button = findViewById(R.id.save_add_restaurant);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditListView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String restaurantName = mEditListView.getText().toString();
                replyIntent.putExtra(CodesAndStrings.EXTRA_REPLY, restaurantName);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}