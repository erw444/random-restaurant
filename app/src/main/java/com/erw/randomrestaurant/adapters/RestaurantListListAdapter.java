package com.erw.randomrestaurant.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.EditListActivity;
import com.erw.randomrestaurant.RandomizeActivity;
import com.erw.randomrestaurant.domain.ListRecyclerEntity;
import com.erw.randomrestaurant.R;
import com.erw.randomrestaurant.RestaurantListViewModel;
import com.erw.randomrestaurant.holders.ListViewHolder;
import com.erw.randomrestaurant.holders.MenuViewHolder;

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
            ImageView randomImage = ((MenuViewHolder)holder).itemView.findViewById(R.id.menu_random_image);
            randomImage.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    /*ListRecyclerEntity entity = getItem(holder.getAdapterPosition());
                    new RandomizeAsyncTask((Application) mContext.getApplicationContext(), mListViewModel.getRepository(), entity).execute();*/

                    ListRecyclerEntity entity = getItem(holder.getAdapterPosition());

                    Intent intent = new Intent(view.getContext(), RandomizeActivity.class);
                    intent.putExtra("listId", entity.getDbId());
                    view.getContext().startActivity(intent);
                }
            });

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
                                Toast.LENGTH_SHORT).show();
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

    public static class ListDiff extends DiffUtil.ItemCallback<ListRecyclerEntity> {

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
