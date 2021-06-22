package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.AsyncTask;
import android.view.View;
import java.lang.ref.WeakReference;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class Store_Selector_Final extends AppCompatActivity {

    ArrayList<String> store_names, cart_items, store_num;
    ArrayList<Integer> number_of_items, total_cost, distance;
    FirebaseDatabase data, data2, data3;
    String temp_store_name_holder;
    String num, choice;
    Button start;
    int temp = 0, temp2 = 0, cart_size;
    float w1 = 0.45f, w2 = 0.85f, w3 = 0.0015f, temp_total_wt; //w1 = No of matched cart items, w2 = distance, w3 = price

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Store_Details_Variables> store_details_variables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_selector_final);

        Intent i = getIntent();
        num = i.getStringExtra("Number");
        choice = i.getStringExtra("Choice");
        Log.d("intent choice: ", choice);

        LinearLayout layout = (LinearLayout)findViewById(R.id.store);


        data = FirebaseDatabase.getInstance();
        data2 = FirebaseDatabase.getInstance();
        data3 = FirebaseDatabase.getInstance();
        store_names = new ArrayList<>();
        total_cost = new ArrayList<>();
        number_of_items = new ArrayList<>();
        cart_items = new ArrayList<>();
        distance = new ArrayList<>();

        float[][] weights; // Array Declared
        weights = new float[4][2];

        store_names.add("Ghanshyam");
        store_names.add("Heritage");
        store_names.add("Modi Enterprises");
        store_names.add("RK General Store");

        distance.add(5);
        distance.add(7);
        distance.add(2);
        distance.add(4);

        number_of_items.add(5);
        number_of_items.add(4);
        number_of_items.add(5);
        number_of_items.add(3);

        total_cost.add(number_of_items.get(0) * 45);
        total_cost.add(number_of_items.get(1) * 35);
        total_cost.add(number_of_items.get(2) * 40);
        total_cost.add(number_of_items.get(3) * 25);

        store_num.add("9849912344");
        store_num.add("8179971915");
        store_num.add("8019234091");
        store_num.add("9575812345");



        String hi1 = "Hi after the background process.";
        Log.d("total cost: ", number_of_items.get(0).toString());

        Log.d("choice:", choice);

        if (choice.equals("Distance")) {
            Log.d("if choice", choice);
            for (int j = 0; j < 4; j++) {
                weights[j][0] = w2 * distance.get(j);
                weights[j][1] = j;
            }

        } else {
            Log.d(" else choice", choice);
            for (int j = 0; j < 4; j++) {
                weights[j][0] = w2 * total_cost.get(j);
                weights[j][1] = j;
            }
        }

        Sort2DArrayBasedOnColumnNumber(weights, 0, choice);


        for (int j = 0; j < 4; j++) {
            int index = (int)weights[j][1];
            Log.d("the", "index" + index);
            store_details_variables.add(new Store_Details_Variables(store_names.get(index), "Total Cost: " + total_cost.get(index), "Items Available: " + number_of_items.get(index), "Distance: " + distance.get(index) + " km"));
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(Store_Selector_Final.this);
        mAdapter = new Adapter_View(store_details_variables);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        try {

//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = mRecyclerView.getChildAdapterPosition(null);
//                store_details_variables.get(position);
//
//                Log.d("position: ", store_details_variables.get(0).get(position));
//
//                Intent order = new Intent(Store_Selector_Final.this, Priority_Page.class);
//                order.putExtra("Store_Name", );
//                order.putExtra("Store_Number", )
//                order.putExtras(getIntent());
//                startActivity(order);
//                finish();
//
//            }
//        });
    }

    public static  void Sort2DArrayBasedOnColumnNumber (float[][] array, final int columnNumber, String choice) {
        Log.d("sort choice: ", choice);
        if (choice.equals("Distance")) {
            Arrays.sort(array, new Comparator<float[]>() {
                @Override
                public int compare(float[] first, float[] second) {
                    if (first[columnNumber] > second[columnNumber]) return 1;
                    else return -1;
                }
            });

            Log.d("if sort choice: ", choice);
        }

        else {
            Arrays.sort(array, new Comparator<float[]>() {
                @Override
                public int compare(float[] first, float[] second) {
                    if (first[columnNumber] < second[columnNumber]) return 1;
                    else return -1;
                }
            });
            Log.d("else sort choice: ", choice);
        }
    }
}


