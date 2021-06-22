package com.example.ingredientz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_View extends RecyclerView.Adapter<Adapter_View.ViewHolder> {
    ArrayList<Store_Details_Variables> mList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView store_name;
        public TextView cost;
        public TextView items;
        public TextView distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store_name = itemView.findViewById(R.id.store_name);
            cost = itemView.findViewById(R.id.cost);
            items = itemView.findViewById(R.id.items);
            distance = itemView.findViewById(R.id.distance);
        }
    }

    public Adapter_View(ArrayList<Store_Details_Variables> storeList) {
        mList = storeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_selector, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store_Details_Variables item = mList.get(position);

        holder.store_name.setText(item.getStore_name());
        holder.cost.setText(item.getCost());
        holder.items.setText(item.getItems());
        holder.distance.setText(item.getDistance());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
