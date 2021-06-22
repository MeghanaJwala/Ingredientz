package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.Quota;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Objects;

public class cart extends AppCompatActivity {

    ListView lv;
    String num, name;
    TextView name_of_user, number_of_user;
    DatabaseReference reference;
    ArrayList<String> list2, name_list;
    ArrayAdapter<String> cart_items;
    String Name, Quantity;
    Button place_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        num = i.getStringExtra("Number");
        name = i.getStringExtra("Name");

        final String number = num;
        final String name_final = name;

        lv = findViewById(R.id.lv);
        list2 = new ArrayList<>();
        name_list = new ArrayList<>();
        name_of_user = findViewById(R.id.name_of_user);
        number_of_user =findViewById(R.id.number_of_user);
        place_order = findViewById(R.id.place_order);

        name_of_user.setText(name_final);
        number_of_user.setText(number);
        reference = FirebaseDatabase.getInstance().getReference("Cart").child(number);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Name = Objects.requireNonNull(snapshot.child("item_name").getValue()).toString();
                list2.add(Name);
                cart_items = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, list2);

                if(cart_items.isEmpty()) {
                    //do nothing
                }

                else {
                    lv.setAdapter(cart_items);
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder caution = new AlertDialog.Builder(cart.this);
                reference = FirebaseDatabase.getInstance().getReference("Cart").child(number);
                Query getQuantity = reference.orderByChild("item_name").equalTo(list2.get(position));
                getQuantity.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Quantity = snapshot.child(list2.get(position)).child("quantity").getValue(String.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                caution.setMessage("Your order is: " + list2.get(position) + " " + Quantity + ".").setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reference = FirebaseDatabase.getInstance().getReference("Cart");
                                Query deleteItem = reference.child(number).orderByChild("item_name").equalTo(list2.get(position));
                                deleteItem.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        try {
                                            for (DataSnapshot delSnapshot : snapshot.getChildren()) {
                                                delSnapshot.getRef().removeValue();
                                            }
                                        }

                                        catch (Exception e){
                                            Toast.makeText(cart.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                String toRemove = cart_items.getItem(position);
                                cart_items.remove(toRemove);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = caution.create();
                alertDialog.setTitle("Confirmation");
                alertDialog.show();
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(cart.this, Priority_Page.class);
                order.putExtra("Number", number);
                order.putExtras(getIntent());
                startActivity(order);
                finish();
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
        Intent i = new Intent(cart.this, HomePage.class);
        i.putExtra("Mobile", num);
        i.putExtras(getIntent());
        startActivity(i);
        finish();
    }
}