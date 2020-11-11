package com.erw.randomrestaurant.adapters;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.AddRestaurantActivity;
import com.erw.randomrestaurant.CodesAndStrings;
import com.erw.randomrestaurant.EditListActivity;
import com.erw.randomrestaurant.R;
import com.erw.randomrestaurant.RestaurantListViewModel;
import com.erw.randomrestaurant.domain.ListRecyclerEntity;
import com.erw.randomrestaurant.domain.RestaurantRecyclerEntity;
import com.erw.randomrestaurant.holders.ListViewHolder;
import com.erw.randomrestaurant.holders.MenuViewHolder;

public class RestaurantListAdapter extends ListAdapter<RestaurantRecyclerEntity, RecyclerView.ViewHolder> {

    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    private RestaurantListViewModel mListViewModel;

    public RestaurantListAdapter(@NonNull DiffUtil.ItemCallback<RestaurantRecyclerEntity> diffCallback,  RestaurantListViewModel viewModel) {
        super(diffCallback);

        mListViewModel = viewModel;
    }

    @Override
    public int getItemViewType(int position) {
        RestaurantRecyclerEntity entity = getItem(position);
        if(entity.isShowMenu()){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==SHOW_MENU){
            return MenuViewHolder.create(parent);
        }else{
            return ListViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RestaurantRecyclerEntity restaurant = getItem(position);
        if(holder instanceof ListViewHolder){
            ((ListViewHolder)holder).bind(restaurant.getName());
        } else if (holder instanceof MenuViewHolder) {
            ImageView randomImage = ((MenuViewHolder)holder).itemView.findViewById(R.id.menu_random_image);
            randomImage.setVisibility(View.INVISIBLE);

            ImageView editImage = ((MenuViewHolder) holder).itemView.findViewById(R.id.menu_edit_image);
            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RestaurantRecyclerEntity entity = getItem(holder.getAdapterPosition());

                    Intent intent = new Intent(view.getContext(), AddRestaurantActivity.class);
                    intent.putExtra(CodesAndStrings.EXTRA_RESTAURANT_ID, entity.getDbId());
                    view.getContext().startActivity(intent);
                }
            });

            ImageView deleteImage = ((MenuViewHolder) holder).itemView.findViewById(R.id.menu_delete_image);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RestaurantRecyclerEntity entity = getItem(holder.getAdapterPosition());
                    mListViewModel.deleteRestaurant(entity.getDbId());
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

    public static class ListDiff extends DiffUtil.ItemCallback<RestaurantRecyclerEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull RestaurantRecyclerEntity oldItem, @NonNull RestaurantRecyclerEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RestaurantRecyclerEntity oldItem, @NonNull RestaurantRecyclerEntity newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
