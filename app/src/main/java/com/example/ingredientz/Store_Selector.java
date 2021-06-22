package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Objects;

public class Store_Selector extends AppCompatActivity {

    ArrayList<String> store_names, cart_items;
    ArrayList<Integer> number_of_items, total_cost, distance;
    FirebaseDatabase data, data2, data3;
    String num, choice;
    int temp = 0, temp2 = 0;
    String temp_store_name_holder;
    Boolean isDone = false;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Store_Details_Variables> store_details_variables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_selector);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        num = i.getStringExtra("Number");
        choice = i.getStringExtra("Choice");

        data = FirebaseDatabase.getInstance();
        data2 = FirebaseDatabase.getInstance();
        data3 = FirebaseDatabase.getInstance();
        store_names = new ArrayList<>();
        total_cost = new ArrayList<>();
        number_of_items = new ArrayList<>();
        cart_items = new ArrayList<>();
        distance = new ArrayList<>();

        DatabaseReference databaseReference = data2.getReference("Cart").child(num);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    cart_items.add(Objects.requireNonNull(ds.child("item_name").getValue()).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query nearest_stores = data.getReference("Stores").orderByChild("distance").endAt(3);
        nearest_stores.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//1
                try {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            temp_store_name_holder = ds.getKey();
                            store_names.add(temp_store_name_holder);
                        }
                    }
                }

                catch (Exception e){
                    Toast.makeText(Store_Selector.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new Handler().postDelayed(() -> {
            int check = 0;
            check = loadData(0, 0);
            if(check == 1){
                isDone = true;
            }
        }, 5000);

        new Handler().postDelayed(() -> {
            if(isDone) {
                for (int addToView = 0; addToView < store_names.size(); addToView++) {
                    store_details_variables.add(new Store_Details_Variables(store_names.get(addToView), "Total Cost: " + total_cost.get(addToView), "Items Available: " + number_of_items.get(addToView) + "/" + cart_items.size(), "Distance: " + distance.get(addToView) + " km"));
                }

                new Handler().postDelayed(() -> {
                    mRecyclerView = findViewById(R.id.recyclerView);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(Store_Selector.this);
                    mAdapter = new Adapter_View(store_details_variables);

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }, 3000);
            }

            else{
                new Handler().postDelayed(() -> {
                    for (int addToView = 0; addToView < store_names.size(); addToView++) {
                        store_details_variables.add(new Store_Details_Variables(store_names.get(addToView), "Total Cost: " + total_cost.get(addToView), "Items Available: " + number_of_items.get(addToView) + "/" + cart_items.size(), "Distance: " + distance.get(addToView) + " km"));
                    }

                    new Handler().postDelayed(() -> {
                        mRecyclerView = findViewById(R.id.recyclerView);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(Store_Selector.this);
                        mAdapter = new Adapter_View(store_details_variables);

                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                    }, 3000);
                }, 3000);
            }
        }, 8000);
    }

    public int loadData(int counter, int store_counter){

        int []returnStatement = {0, 0 , 0, 0};
        final int count_twin = counter;
        final int store_count_twin = store_counter;

        DatabaseReference reference2 = data3.getReference("Stores").child(store_names.get(store_counter));
        Query itemIsThere = reference2.orderByChild("item_name").equalTo(cart_items.get(counter));
        itemIsThere.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (count_twin < cart_items.size()) {
                        temp += temp + snapshot.child(cart_items.get(count_twin)).child("price").getValue(Integer.class);
                        temp2 += 1;
                        returnStatement[0] = 1; // Add 1 to counter.
                    }
                }
                else if ((count_twin == cart_items.size() - 1) && !(snapshot.exists())) {
                    total_cost.set(store_count_twin, temp);
                    number_of_items.set(store_count_twin, temp2);
                    distance.set(store_count_twin, snapshot.child("distance").getValue(Integer.class));
                    temp = 0;
                    temp2 = 0;
                    returnStatement[1] = 1; //Add 1 to store_counter and make counter 0.
                }
                else if (!(snapshot.exists()) && (count_twin < cart_items.size() - 1)) {
                    returnStatement[2] = 1; //Add 1 to counter.
                }

                else if(store_count_twin == store_names.size()){
                    returnStatement[3] = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(returnStatement[0] == 1 && returnStatement[1] == 0 && returnStatement[2] == 0 && returnStatement[3] == 0){
            returnStatement[0] = 0;
            return loadData(counter + 1, store_counter);
        }

        else if(returnStatement[1] == 1 && returnStatement[2] == 0 && returnStatement[0] == 0 && returnStatement[3] == 0){
            returnStatement[1] = 0;
            return  loadData(0, store_counter + 1);
        }

        else if(returnStatement[2] == 1 && returnStatement[1] == 0 && returnStatement[0] == 0 && returnStatement[3] == 0){
            returnStatement[2] = 0;
            return loadData(counter + 1, store_counter);
        }

        else if(returnStatement[3] == 1 && returnStatement[1] == 0 && returnStatement[0] == 0 && returnStatement[2] == 0){
            returnStatement[3] = 0;
            return 1;
        }

        else{
            return loadData(counter, store_counter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Store_Selector.this, cart.class);
        i.putExtra("Mobile", num);
        i.putExtras(getIntent());
        startActivity(i);
        finish();
    }
}

/*
Toast.makeText(Store_Selector.this, selected_store_name, Toast.LENGTH_SHORT).show();
               Intent finalorder = new Intent(Store_Selector.this, final_order.class);
               finalorder.putExtra("Mobile", num);
               finalorder.putExtra("Store", selected_store_name);
               finalorder.putExtra("Price", Integer.toString(total_cost));
               finalorder.putExtra("Items", Integer.toString(number_of_items));
               finalorder.putExtra("Count", Integer.toString(count));
               finalorder.putExtras(getIntent());
               startActivity(finalorder);
               finish();
*/