package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Customer_Order_Details extends AppCompatActivity {

    TextView customer_name_view, customer_num_view, customer_mode_view, customer_address_view;
    ListView customer_final_order;
    DatabaseReference reference;
    String store_num, customer_number, customer_mode, customer_address, customer_name;
    ArrayList<String> list2;
    ArrayAdapter<String> order_list;
    Button accept, reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent store = getIntent();
        customer_number = store.getStringExtra("Number");
        customer_mode = store.getStringExtra("Mode");
        customer_address = store.getStringExtra("Address");
        store_num = store.getStringExtra("Store_Number");

        customer_name_view = findViewById(R.id.customer_name_view);
        customer_num_view = findViewById(R.id.customer_num_view);
        customer_mode_view = findViewById(R.id.customer_mode_view);
        customer_address_view = findViewById(R.id.customer_address_view);

        customer_num_view.setText(customer_number);
        customer_mode_view.setText(customer_mode);
        customer_address_view.setText("Address: " + customer_address);

        customer_final_order = findViewById(R.id.customer_final_order);

        list2 = new ArrayList<>();

        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);

        reference = FirebaseDatabase.getInstance().getReference("Cart").child(customer_number);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String temp = Objects.requireNonNull(snapshot.child("brand_name").getValue()).toString();
                String temp2 = Objects.requireNonNull(snapshot.child("item_name").getValue()).toString();
                String temp3 = Objects.requireNonNull(snapshot.child("quantity").getValue()).toString();

                list2.add(temp + " " + temp2 + "(" + temp3 + ")");
                order_list = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, list2);

                if(order_list.isEmpty()) {
                    //do nothing
                }

                else {
                    customer_final_order.setAdapter(order_list);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("number_singup").equalTo(customer_number);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    customer_name = snapshot.child(customer_number).child("name_signup").getValue(String.class);
                }
                customer_num_view.setText(customer_number);
                customer_mode_view.setText(customer_mode);
                customer_address_view.setText("Address: " + customer_address);
                customer_name_view.setText(customer_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
                reference = FirebaseDatabase.getInstance().getReference("OrderPlaced").child(store_num).child(customer_number);
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("accepted", "true");
                reference.updateChildren(hashmap);
                Toast.makeText(Customer_Order_Details.this, "Accepted Order", Toast.LENGTH_SHORT).show();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);

                reference = FirebaseDatabase.getInstance().getReference("OrderPlaced");
                Query deleteItem = reference.child(store_num).orderByChild("number").equalTo(customer_number);
                deleteItem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            for (DataSnapshot delSnapshot : snapshot.getChildren()) {
                                delSnapshot.getRef().removeValue();
                            }
                        }

                        catch (Exception e){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
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
        Intent i = new Intent(this, Store_Login.class);
        i.putExtra("Store_Number", store_num);
        i.putExtras(getIntent());
        startActivity(i);
        finish();
    }
}