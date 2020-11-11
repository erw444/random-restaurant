package com.erw.randomrestaurant;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.adapters.RestaurantListAdapter;
import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;
import com.erw.randomrestaurant.domain.RestaurantRecyclerEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.erw.randomrestaurant.R.color.colorPrimary;

public class EditListActivity extends AppCompatActivity {

    private long mListId;

    private EditText mEditListView;

    private RestaurantListViewModel mListViewModel;

    private RestaurantListAdapter adapter;

    private RestaurantListWRestaurants mListContainer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        mListId = getIntent().getLongExtra("listId", -1);

        mEditListView = findViewById(R.id.current_list_name);

        mListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RestaurantListViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_restaurant_view);
        adapter = new RestaurantListAdapter(new RestaurantListAdapter.ListDiff(), mListViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mListViewModel.getRestaurantList(mListId).observe(this, listContainer -> {
            mListContainer = listContainer;
            mEditListView.setText(listContainer.list.getName());

            List<RestaurantRecyclerEntity> restaurantEntities = new ArrayList<RestaurantRecyclerEntity>();
            for(Restaurant dbRestaurant: listContainer.restaurants){
                restaurantEntities.add(new RestaurantRecyclerEntity(dbRestaurant));
            }

            // Update the cached copy of the Restaurants in the adapter.
            adapter.submitList(restaurantEntities);
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

        addMenu(recyclerView);

        FloatingActionButton fab = findViewById(R.id.add_restaurant_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditListActivity.this, AddRestaurantActivity.class);
                startActivityForResult(intent, CodesAndStrings.NEW_RESTAURANT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addMenu(RecyclerView recyclerView){
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(colorPrimary));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.showMenu(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                adapter.closeMenu();
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
