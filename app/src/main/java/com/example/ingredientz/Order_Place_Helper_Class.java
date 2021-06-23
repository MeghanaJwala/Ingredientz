package com.example.ingredientz;

import android.content.Intent;

import java.util.Random;

public class Order_Place_Helper_Class {

    private String number;
    private String store;
    private String accepted;
    private String address;
    private String mode;

    public Order_Place_Helper_Class(String  number, String store, String accepted, String address, String mode) {
        this.number = number;
        this.store = store;
        this.accepted = accepted;
        this.address = address;
        this.mode = mode;
    }

    public Order_Place_Helper_Class() {
    }

    @Override
    public String toString() {
        return "Order_Place_Helper_Class{" +
                "Accepted='" + accepted + '\'' +
                ", Number='" + number + '\'' +
                ", Store='" + store + '\'' +
                ", Address='" + address + '\'' +
                ", Mode='" + mode + '\'' +
                '}';
    }

    public String getNumber() {
        return  number;
    }

    public void setNumber(String  number) {
        this. number =  number;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}