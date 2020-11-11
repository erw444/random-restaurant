package com.erw.randomrestaurant.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.erw.randomrestaurant.R;

public class ListViewHolder extends RecyclerView.ViewHolder{
    private final TextView listItemView;

    private ListViewHolder(View itemView) {
        super(itemView);
        listItemView = itemView.findViewById(R.id.list_item_name);
    }

    public void bind(String text) {
        listItemView.setText(text);
    }

    public static ListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_list_item, parent, false);
        return new ListViewHolder(view);
    }
}
