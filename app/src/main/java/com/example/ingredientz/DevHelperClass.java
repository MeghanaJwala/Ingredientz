package com.example.ingredientz;

import android.content.Intent;

import java.util.Random;

public class DevHelperClass {

    private String item_name;
    private String brand_name;
    private String price;
    private String quantity;
    private String rating;
    private String UID;

    public DevHelperClass(String item_name, String brand_name, String price, String quantity, String rating, String UID) {
        this.item_name = item_name;
        this.brand_name = brand_name;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
        this.UID = UID;
    }

    public DevHelperClass() {
    }

    @Override
    public String toString() {
        return "DevHelperClass{" +
                "item_name='" + item_name + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", rating='" + rating + '\'' +
                ", UID='" + UID + '\'' +
                '}';
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}



