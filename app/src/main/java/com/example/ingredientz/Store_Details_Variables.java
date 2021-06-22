package com.example.ingredientz;

public class Store_Details_Variables {

    private String store_name;
    private String cost;
    private String items;
    private String distance;

    public Store_Details_Variables(String store_name, String cost, String items, String distance) {
        this.store_name = store_name;
        this.cost = cost;
        this.items = items;
        this.distance = distance;
    }

    public Store_Details_Variables() {
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
