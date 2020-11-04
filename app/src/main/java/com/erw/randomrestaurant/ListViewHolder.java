package com.erw.randomrestaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder{
    private final TextView listItemView;

    private ListViewHolder(View itemView) {
        super(itemView);
        listItemView = itemView.findViewById(R.id.list_item_name);
    }

    public void bind(String text) {
        listItemView.setText(text);
    }

    static ListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_list_item, parent, false);
        return new ListViewHolder(view);
    }
}
