package com.erw.randomrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditListActivity extends AppCompatActivity {

    private long mListId;

    private EditText mEditListView;

    private RestaurantListViewModel mListViewModel;

    private RestaurantListAdapter adapter;

    private RestaurantListWRestaurants mListContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        mListId = getIntent().getLongExtra("listId", -1);

        mEditListView = findViewById(R.id.current_list_name);

        RecyclerView recyclerView = findViewById(R.id.recycler_restaurant_view);
        adapter = new RestaurantListAdapter(new RestaurantListAdapter.ListDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RestaurantListViewModel.class);

        mListViewModel.getRestaurantList(mListId).observe(this, listContainer -> {
            mListContainer = listContainer;
            mEditListView.setText(listContainer.list.getName());
            // Update the cached copy of the Restaurants in the adapter.
            adapter.submitList(listContainer.restaurants);
        });


        final Button saveListButton = findViewById(R.id.button_edit_list_save);
        saveListButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            String newListName = mEditListView.getText().toString();
            boolean notChanged = mListContainer.list.getName().equals(newListName);
            if (TextUtils.isEmpty(newListName) || notChanged) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                mListContainer.list.setName(newListName);
                mListViewModel.updateList(mListContainer.list);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        FloatingActionButton fab = findViewById(R.id.add_restaurant_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditListActivity.this, AddRestaurantActivity.class);
                startActivityForResult(intent, CodesAndStrings.NEW_RESTAURANT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CodesAndStrings.NEW_RESTAURANT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Restaurant newRestaurant = new Restaurant(mListContainer.list.getListId(), data.getStringExtra(CodesAndStrings.EXTRA_REPLY));

            mListViewModel.insertRestaurant(newRestaurant);

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }

    }

}
