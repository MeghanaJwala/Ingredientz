package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class Place_Order extends AppCompatActivity {

    TextView summary, store_name, total_cost;
    ListView final_cart_list;
    String num, store, cost, store_num;
    DatabaseReference reference;
    ArrayAdapter<String> cart_items, temp;
    String Name, Quantity;
    ArrayList<String> list2;
    Button final_place_order, refresh, confirm_mode, confirm_address;
    FirebaseDatabase rootNode;
    RadioGroup order_option;
    String selectedRadioButtonText, entered_address, mode;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        summary = findViewById(R.id.summary);
        store_name = findViewById(R.id.store_name);
        total_cost = findViewById(R.id.total_cost);

        final_cart_list = findViewById(R.id.final_cart_list);

        list2 = new ArrayList<>();

        final_place_order = findViewById(R.id.final_place_order);
        refresh = findViewById(R.id.refresh);
        confirm_mode = findViewById(R.id.confirm_mode);
        confirm_address = findViewById(R.id.confirm_address);
        confirm_address.setVisibility(View.GONE);
        refresh.setVisibility(View.INVISIBLE);
        final_place_order.setVisibility(View.GONE);

        order_option = findViewById(R.id.order_option);

        address = findViewById(R.id.address);

        Intent store_class = getIntent();
        num = store_class.getStringExtra("Number");
        Log.d("num: ", num);
        store = store_class.getStringExtra("Store Name");
        cost = store_class.getStringExtra("Rs");
        store_num = store_class.getStringExtra("Store Number");

        store_name.setText("Store Name: " + store);
        total_cost.setText(cost);

        reference = FirebaseDatabase.getInstance().getReference("Cart").child(num);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Name = Objects.requireNonNull(snapshot.child("item_name").getValue()).toString();
                Quantity = Objects.requireNonNull(snapshot.child("quantity").getValue()).toString();
                list2.add(Name + "(" + Quantity + ")");
                cart_items = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, list2);

                if(cart_items.isEmpty()) {
                    //do nothing
                }

                else {
                    final_cart_list.setAdapter(cart_items);
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

        confirm_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_mode.setVisibility(View.INVISIBLE);
                order_option.setVisibility(View.INVISIBLE);

                int radioSelectedId = order_option.getCheckedRadioButtonId();

                if(radioSelectedId != -1){
                    RadioButton selectedRadioButton = findViewById(radioSelectedId);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                }

                if(selectedRadioButtonText.equals("Delivery")){
                    entered_address = address.getText().toString();
                    address.setVisibility(View.VISIBLE);
                    confirm_address.setVisibility(View.VISIBLE);
                    mode = "Delivery";
                }

                else{
                    entered_address = "NA";
                    mode = "Pickup";
                    final_place_order.setVisibility(View.VISIBLE);
                }

                summary.setText("");
                final_cart_list.setAdapter(temp);
            }
        });

        confirm_address.setOnClickListener(v -> {
            entered_address = address.getText().toString();
            if(entered_address.equals("")){
                Toast.makeText(Place_Order.this, "Address cannot be empty!", Toast.LENGTH_SHORT).show();
            }

            else{
                entered_address = address.getText().toString();
                address.setVisibility(View.INVISIBLE);
                Toast.makeText(Place_Order.this, "Address added!", Toast.LENGTH_SHORT).show();
                confirm_address.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                final_place_order.setVisibility(View.VISIBLE);
            }
        });

        Log.d("Number: ", store_num);

        final_place_order.setOnClickListener(v -> {
            final DatabaseReference[] reference = {FirebaseDatabase.getInstance().getReference().child("OrderPlaced").child(store_num)};
            Query checkItem = reference[0].orderByChild(String.valueOf("number")).equalTo(num);

            checkItem.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        final DatabaseReference[] reference = {FirebaseDatabase.getInstance().getReference().child("OrderPlaced").child(store_num)};
                        Query checkItem = reference[0].orderByChild(String.valueOf("accepted")).equalTo("true");

                        checkItem.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    refresh.setVisibility(View.INVISIBLE);
                                    store_name.setText("You have a pending order which has been accepted by the store!");
                                    total_cost.setText("Please place your next order after the completion of the pending order.");
                                    Toast.makeText(Place_Order.this, "Cannot place more than one order at a time!", Toast.LENGTH_SHORT).show();
                                }

                                else{
                                    store_name.setText("You have a pending order, which hasn't been accepted by the store!");
                                    total_cost.setText("Waiting for store to accept your order!");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    else{
                        refresh.setVisibility(View.VISIBLE);
                        ViewGroup.LayoutParams params = refresh.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        refresh.setLayoutParams(params);

                        ViewGroup.LayoutParams params2 = final_place_order.getLayoutParams();
                        params2.height = 0;
                        final_place_order.setLayoutParams(params2);
                        final_place_order.setVisibility(View.INVISIBLE);

                        store_name.setText("Your order has been placed.");
                        total_cost.setText("Waiting for store to accept your order!");
                        rootNode = FirebaseDatabase.getInstance();
                        reference[0] = rootNode.getReference().child("OrderPlaced").child(store_num);

                        Order_Place_Helper_Class something = new Order_Place_Helper_Class(num, store, "false", entered_address, mode);
                        reference[0].child(String.valueOf(num)).setValue(something);

                        Toast.makeText(Place_Order.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference[] reference = {FirebaseDatabase.getInstance().getReference().child("OrderPlaced").child(store_num)};
                Query checkItem = reference[0].orderByChild(String.valueOf("accepted")).equalTo("true");

                checkItem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            total_cost.setText("Your order has been accepted by the store!");
                            refresh.setVisibility(View.INVISIBLE);
                        }

                        else{
                            Toast.makeText(Place_Order.this, "Not yet accepted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}