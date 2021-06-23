package com.example.ingredientz;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Store_Login extends AppCompatActivity {

    ListView custom_orders;
    String store_num, customer_num;
    DatabaseReference reference;
    ArrayList<String> list2;
    ArrayAdapter<String> orders;

    Button refresh_list;

    String address, number, mode;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent_on_back_pressed = getIntent();
                    store_num = intent_on_back_pressed.getStringExtra("Store_Number");
                    Log.d("Store NumberAc", store_num);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_login);

        Intent login_intent = getIntent();
        store_num = login_intent.getStringExtra("Number");

        custom_orders = findViewById(R.id.customer_orders);

        list2 = new ArrayList<>();

        refresh_list = findViewById(R.id.refresh_list);

        reference = FirebaseDatabase.getInstance().getReference("OrderPlaced").child(store_num);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                customer_num = Objects.requireNonNull(snapshot.child("number").getValue()).toString();
                list2.add(customer_num);
                orders = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, list2);

                if(orders.isEmpty()) {
                    //do nothing
                }

                else {
                    custom_orders.setAdapter(orders);
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

        refresh_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list2.clear();
                orders = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, list2);
                reference = FirebaseDatabase.getInstance().getReference("OrderPlaced").child(store_num);
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        customer_num = Objects.requireNonNull(snapshot.child("number").getValue()).toString();
                        list2.add(customer_num);
                        orders = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, list2);

                        if(orders.isEmpty()) {
                            //do nothing
                        }

                        else {
                            custom_orders.setAdapter(orders);
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
            }
        });

        custom_orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        try {
                            address = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(orders.getItem(position))).child("address").getValue()).toString();
                            mode = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(orders.getItem(position))).child("mode").getValue()).toString();
                            number = Objects.requireNonNull(snapshot.child(Objects.requireNonNull(orders.getItem(position))).child("number").getValue()).toString();

                            openSomeActivityForResult();
                        }

                        catch (Exception e) {
                            
                        }
                    }

                    public void openSomeActivityForResult() {
                        Intent intent = new Intent(Store_Login.this, Customer_Order_Details.class);
                        intent.putExtra("Number", number);
                        intent.putExtra("Mode", mode);
                        intent.putExtra("Address", address);
                        intent.putExtra("Store_Number", store_num);
                        someActivityResultLauncher.launch(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}