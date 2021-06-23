package com.example.ingredientz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_View extends RecyclerView.Adapter<Adapter_View.ViewHolder> {
    ArrayList<Store_Details_Variables> mList;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView store_name;
        public TextView cost;
        public TextView items;
        public TextView distance;
        public TextView store_num;
        public TextView cust_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            store_name = itemView.findViewById(R.id.store_name);
            cost = itemView.findViewById(R.id.cost);
            items = itemView.findViewById(R.id.items);
            distance = itemView.findViewById(R.id.distance);
            store_num = itemView.findViewById(R.id.store_num);
            cust_num = itemView.findViewById(R.id.cust_num);
        }
    }

    public Adapter_View(ArrayList<Store_Details_Variables> storeList,Context context) {
        mList = storeList;
        this.context = context;
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
        holder.store_num.setText(item.getNumber());
        holder.cust_num.setText(item.getCustNum());

        holder.store_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Place_Order.class);
                intent.putExtra("Store Name",item.getStore_name());
                intent.putExtra("Store Number",item.getNumber());
                intent.putExtra("Rs",item.getCost());
                intent.putExtra("Number", item.getCustNum());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
}
