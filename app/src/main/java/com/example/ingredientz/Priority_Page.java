package com.example.ingredientz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Priority_Page extends AppCompatActivity {

    Button priority_button, confirmation;
    RadioGroup radio_priority;
    String num, selectedRadioButtonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_page);

        priority_button = findViewById(R.id.priority_button);

        confirmation = findViewById(R.id.confirmation);

        radio_priority = findViewById(R.id.radio_priority);

        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation.setClickable(false);
                int radioSelectedId = radio_priority.getCheckedRadioButtonId();

                if(radioSelectedId != -1){
                    RadioButton selectedRadioButton = (RadioButton) findViewById(radioSelectedId);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                    Log.d("choice: ", selectedRadioButtonText);
                }
            }
        });

        Intent i = getIntent();
        num = i.getStringExtra("Number");

        priority_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(Priority_Page.this, Store_Selector_Final.class);
                order.putExtra("Number", num);
                order.putExtra("Choice", selectedRadioButtonText);
                order.putExtras(getIntent());
                startActivity(order);
                finish();
            }
        });
    }
}