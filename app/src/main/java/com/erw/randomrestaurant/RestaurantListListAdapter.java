package com.erw.randomrestaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.database.RestaurantList;

public class RestaurantListListAdapter extends ListAdapter<ListRecyclerEntity, RecyclerView.ViewHolder> {
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public RestaurantListListAdapter(@NonNull DiffUtil.ItemCallback<ListRecyclerEntity> diffCallback) {
        super(diffCallback);
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

    //Our menu view
    public static class MenuViewHolder extends RecyclerView.ViewHolder{
        public MenuViewHolder(View view){
            super(view);
        }

        static MenuViewHolder create(ViewGroup parent){
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_menu, parent, false);
            return new MenuViewHolder(v);
        }
    }
}
