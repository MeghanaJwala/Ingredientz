package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class item_details extends AppCompatActivity {

    TextView brand_name, item_name, price, quantity, rating;
    EditText quantity_customer;
    Button add_to_cart;
    int count = 0;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    String num;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        num = i.getStringExtra("Mobile Number");

        brand_name = findViewById(R.id.brand_name);
        item_name = findViewById(R.id.item_name);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        rating = findViewById(R.id.rating);
        add_to_cart = findViewById(R.id.add_to_cart);
        quantity_customer = findViewById(R.id.quantity_customer);

        quantity_customer.setText("1");

        Intent intent3 = getIntent();
        String num = intent3.getStringExtra("Mobile Number");

        Intent intent = getIntent();
        String s1 = intent.getStringExtra("Item_Name");
        String s2 = intent.getStringExtra("Brand");
        String s3 = intent.getStringExtra("Quantity");
        String s4 = intent.getStringExtra("Rating");
        String s5 = intent.getStringExtra("Price");

        brand_name.setText(s2);
        item_name.setText(s1);
        price.setText("Min " + s5);
        quantity.setText(s3);
        rating.setText("Rating:" + " " + s4);

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(isNetworkAvailable(getApplicationContext())) {
                        CartHelperClass helperClass = new CartHelperClass(s1, s2, s5, quantity_customer.getText().toString() + "x" + s3, s4);
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("Cart");
                        assert num != null;
                        assert s1 != null;
                        reference.child(num).child(s1).setValue(helperClass);
                        Toast.makeText(item_details.this, s1 + " added to your cart", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(item_details.this, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                catch (Exception e) {
                    Toast.makeText(item_details.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent2 = getIntent();
        count = intent2.getIntExtra("Count", count);
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
        Intent i = new Intent(this, HomePage.class);
        i.putExtra("Mobile", num);
        i.putExtras(getIntent());
        startActivity(i);
        finish();
    }
}