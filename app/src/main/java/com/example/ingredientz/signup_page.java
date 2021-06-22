package com.example.ingredientz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class signup_page extends AppCompatActivity {

    Button signup_signup_button;
    EditText name_signup, number_signup,  email_signup, password_signup, passconfirm_signup;
    RadioGroup radio_group;
    String selectedRadioButtonText;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        signup_signup_button = findViewById(R.id.signup_signup_button);
        name_signup = findViewById(R.id.name_signup);
        number_signup = findViewById(R.id.number_signup);
        email_signup = findViewById(R.id.email_signup);
        password_signup = findViewById(R.id.password_signup);
        passconfirm_signup = findViewById(R.id.passconfirm_signup);
        radio_group = findViewById(R.id.radio_group);

        signup_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                int radioSelectedId = radio_group.getCheckedRadioButtonId();

                if(radioSelectedId != -1){
                    RadioButton selectedRadioButton = (RadioButton) findViewById(radioSelectedId);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                    Log.d("m/f: ", selectedRadioButtonText);
                }

                String name = name_signup.getText().toString();
                String number = number_signup.getText().toString();
                String email = email_signup.getText().toString();
                String password = password_signup.getText().toString();
                String passConfirm= passconfirm_signup.getText().toString();

                if(name.equals("")) {
                    Toast.makeText(signup_page.this, "Required: Name", Toast.LENGTH_SHORT).show();
                }

                else{
                    if(number.equals("") || number.length()!=10){
                        Toast.makeText(signup_page.this, "Required: Valid Phone Number", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if(password.equals("") || passConfirm.equals("")){
                            Toast.makeText(signup_page.this, "Password Field Empty", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            if(password.equals(passConfirm)) {

                                try {
                                    UserHelperClass helperClass = new UserHelperClass(name, number, email, password, selectedRadioButtonText);
                                    reference.child(number).setValue(helperClass);
                                    Toast.makeText(signup_page.this, "Sign-up Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent2 = new Intent(signup_page.this, MainActivity.class);
                                    startActivity(intent2);
                                }
                                catch (Exception e){
                                    Toast.makeText(signup_page.this, "Sign-up Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else {
                                Toast.makeText(signup_page.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
}