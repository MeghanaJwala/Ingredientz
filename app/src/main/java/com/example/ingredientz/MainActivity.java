package com.example.ingredientz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button login_button, signup_button;
    EditText loginid, password;
    String numberFromDB, nameFromDB;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button=findViewById(R.id.login_button);
        signup_button=findViewById(R.id.signup_button);
        loginid=findViewById(R.id.loginid);
        password=findViewById(R.id.password);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = loginid.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(id.equals("")){
                    Toast.makeText(MainActivity.this, "Username Field cannot be Empty.", Toast.LENGTH_SHORT).show();
                    loginid.requestFocus();
                }

                else if(pass.equals("")){
                    Toast.makeText(MainActivity.this, "Password field cannot be Empty.", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }

                else{
                    isUser();
                }
            }
        });
    }

    private void isUser(){
        final String id = loginid.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("number_singup").equalTo(id);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String passwordFromDB = snapshot.child(id).child("password_signup").getValue(String.class);
                    String emailFromDB = snapshot.child(id).child("email_signup").getValue(String.class);
                    String genderFromDB = snapshot.child(id).child("gender").getValue(String.class);
                    nameFromDB = snapshot.child(id).child("name_signup").getValue(String.class);
                    numberFromDB = snapshot.child(id).child("number_singup").getValue(String.class);

                    if(id.equals("8019234091")){
                        String devPass = snapshot.child(id).child("password_signup").getValue(String.class);

                        assert devPass != null;
                        if(pass.equals("om201199")){
                            Intent devintent = new Intent(MainActivity.this, Developer.class);
                            Toast.makeText(MainActivity.this, "Welcome Dev", Toast.LENGTH_SHORT).show();
                            startActivity(devintent);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Wrong Password for dev.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(id.equals("8179971915")){
                        String devPass = snapshot.child(id).child("password_signup").getValue(String.class);

                        assert devPass != null;
                        if(pass.equals("3")){
                            Intent store_intent = new Intent(MainActivity.this, Store_Login.class);
                            Toast.makeText(MainActivity.this, "Accept/Reject Orders", Toast.LENGTH_SHORT).show();
                            store_intent.putExtra("Number", id);
                            startActivity(store_intent);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Wrong Password for store login.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(passwordFromDB.equals(pass)){

                        Intent intent3 = new Intent(MainActivity.this, HomePage.class);
                        intent3.putExtra("Name", nameFromDB);
                        intent3.putExtra("Gender", genderFromDB);
                        intent3.putExtra("Email", emailFromDB);
                        intent3.putExtra("Mobile Number", numberFromDB);

                        Toast.makeText(MainActivity.this, "Welcome " + nameFromDB, Toast.LENGTH_SHORT).show();

                        startActivity(intent3);
                        finish();
                    }

                    else{
                        Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(MainActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickSignupPage(View view) {
        Intent intent = new Intent(this, signup_page.class);
        startActivity(intent);
    }


}
