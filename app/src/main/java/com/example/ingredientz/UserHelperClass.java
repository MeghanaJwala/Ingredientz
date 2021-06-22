package com.example.ingredientz;

import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UserHelperClass {

    String name_signup, number_singup, email_signup, password_signup, gender;

    public UserHelperClass(String name_signup, String number_singup, String email_signup, String password_signup, String gender) {
        this.name_signup = name_signup;
        this.number_singup = number_singup;
        this.email_signup = email_signup;
        this.password_signup = password_signup;
        this.gender = gender;
    }

    public UserHelperClass() {
    }

    @Override
    public String toString() {
        return "UserHelperClass{" +
                "name_signup='" + name_signup + '\'' +
                ", number_singup='" + number_singup + '\'' +
                ", email_signup='" + email_signup + '\'' +
                ", password_signup='" + password_signup + '\'' +
                ", gender=" + gender +
                '}';
    }

    public String getName_signup() {
        return name_signup;
    }

    public void setName_signup(String name_signup) {
        this.name_signup = name_signup;
    }

    public String getNumber_singup() {
        return number_singup;
    }

    public void setNumber_singup(String number_singup) {
        this.number_singup = number_singup;
    }

    public String getEmail_signup() {
        return email_signup;
    }

    public void setEmail_signup(String email_signup) {
        this.email_signup = email_signup;
    }

    public String getPassword_signup() {
        return password_signup;
    }

    public void setPassword_signup(String password_signup) {
        this.password_signup = password_signup;
    }

    public String getGender() {
        return gender;
    }

    public void setRadio_group(String gender) {
        this.gender = gender;
    }
}