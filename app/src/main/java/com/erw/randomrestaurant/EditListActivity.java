package com.erw.randomrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.database.RestaurantList;

import java.util.ArrayList;
import java.util.List;

public class EditListActivity extends AppCompatActivity {
    private long mListId;

    private EditText mEditListView;

    private RestaurantListViewModel mListViewModel;

    private RestaurantListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        long mListId = getIntent().getLongExtra("listId", -1);

        mEditListView = findViewById(R.id.current_list_name);

        RecyclerView recyclerView = findViewById(R.id.recycler_restaurant_view);
        adapter = new RestaurantListAdapter(new RestaurantListAdapter.ListDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RestaurantListViewModel.class);

        mListViewModel.getRestaurantList(mListId).observe(this, listContainer -> {

            mEditListView.setText(listContainer.list.getName());
            // Update the cached copy of the Restaurants in the adapter.
            adapter.submitList(listContainer.restaurants);
        });


        final Button button = findViewById(R.id.button_edit_list_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditListView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}
