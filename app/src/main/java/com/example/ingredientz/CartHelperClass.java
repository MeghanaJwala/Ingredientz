package com.example.ingredientz;

public class CartHelperClass {

    String item_name;
    String brand_name;
    String price;
    String quantity;
    String rating;

    public CartHelperClass(String item_name, String brand_name, String price, String quantity, String rating) {
        this.item_name = item_name;
        this.brand_name = brand_name;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
    }

    public CartHelperClass() {
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
}
