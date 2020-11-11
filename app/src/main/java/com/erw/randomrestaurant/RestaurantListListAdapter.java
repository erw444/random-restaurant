package com.erw.randomrestaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.database.Restaurant;
import com.erw.randomrestaurant.database.RestaurantList;
import com.erw.randomrestaurant.database.RestaurantListWRestaurants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RestaurantListListAdapter extends ListAdapter<ListRecyclerEntity, RecyclerView.ViewHolder> {
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    private RestaurantListViewModel mListViewModel;
    private Context mContext;

    public RestaurantListListAdapter(@NonNull DiffUtil.ItemCallback<ListRecyclerEntity> diffCallback, RestaurantListViewModel viewModel, Context context) {
        super(diffCallback);
        this.mListViewModel = viewModel;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        ListRecyclerEntity entity = getItem(position);
        if(entity.isShowMenu()){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType==SHOW_MENU){
            return MenuViewHolder.create(parent);
        }else{
            return ListViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListRecyclerEntity currentList = getItem(position);
        if(holder instanceof ListViewHolder){
            ((ListViewHolder)holder).bind(currentList.getTitle());
        } else if (holder instanceof MenuViewHolder){
            ImageView editImage = ((MenuViewHolder)holder).itemView.findViewById(R.id.menu_edit_image);
            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListRecyclerEntity entity = getItem(holder.getAdapterPosition());

                    Intent intent = new Intent(view.getContext(), EditListActivity.class);
                    intent.putExtra("listId", entity.getDbId());
                    view.getContext().startActivity(intent);
                }
            });

            ImageView deleteImage = ((MenuViewHolder)holder).itemView.findViewById(R.id.menu_delete_image);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListRecyclerEntity entity = getItem(holder.getAdapterPosition());

                    if(entity.getNumRestaurants() == 0){
                        mListViewModel.deleteRestaurantList(entity.getDbId());
                    } else {
                        Toast.makeText(
                                mContext,
                                R.string.no_delete_has_restaurants,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void showMenu(int position) {
        for(int i=0; i<getItemCount(); i++){
            getItem(i).setShowMenu(false);
        }
       getItem(position).setShowMenu(true);
        notifyDataSetChanged();
    }


    public boolean isMenuShown() {
        for(int i=0; i<getItemCount(); i++){
            if(getItem(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<getItemCount(); i++){
            getItem(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    static class ListDiff extends DiffUtil.ItemCallback<ListRecyclerEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull ListRecyclerEntity oldItem, @NonNull ListRecyclerEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ListRecyclerEntity oldItem, @NonNull ListRecyclerEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
}
