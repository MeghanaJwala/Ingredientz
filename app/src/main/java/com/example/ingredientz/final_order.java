package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class final_order extends AppCompatActivity {

    Button confirm_order;
    TextView store_name, total_price, total_items;

    String num, store;
    String total_cost, number_of_items, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        confirm_order = findViewById(R.id.confirm_order);
        store_name = findViewById(R.id.store_name);
        total_price = findViewById(R.id.total_price);
        total_items = findViewById(R.id.total_items);
        store = "";

        Intent finalorder = getIntent();
        onNewIntent(finalorder);
        store = finalorder.getStringExtra("Store");
        Toast.makeText(final_order.this, finalorder.getStringExtra("Store"), Toast.LENGTH_SHORT).show();
        num = finalorder.getStringExtra("Mobile");
        total_cost = finalorder.getStringExtra("Price");
        number_of_items = finalorder.getStringExtra("Items");
        count = finalorder.getStringExtra("Count");

        store_name.setText(store);
        total_price.setText(total_cost);
        total_items.setText(number_of_items + "/" + count);

        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        Intent i = new Intent(final_order.this, Store_Selector.class);
        i.putExtra("Number", num);
        i.putExtras(getIntent());
        startActivity(i);
        finish();
    }
}