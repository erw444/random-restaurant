package com.erw.randomrestaurant.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.R;

public class MenuViewHolder extends RecyclerView.ViewHolder{
        public MenuViewHolder(View view){
            super(view);
        }

        public static MenuViewHolder create(ViewGroup parent){
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_menu, parent, false);

            return new MenuViewHolder(v);
        }
}
