package com.example.ingredientz;

public class Store_Details_Variables {

    private String store_name;
    private String cost;
    private String items;
    private String distance;
    private String store_num;
    private String cust_num;

    public Store_Details_Variables(String store_name, String cost, String items, String distance, String store_num, String cust_num) {
        this.store_name = store_name;
        this.cost = cost;
        this.items = items;
        this.distance = distance;
        this.store_num = store_num;
        this.cust_num = cust_num;
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

    public String getNumber() {
        return store_num;
    }

    public void setNumber(String store_num) {
        this.store_num =store_num;
    }

    public String getCustNum() {
        return cust_num;
    }

    public void setCustNum(String cust_num) {
        this.cust_num =cust_num;
    }

}
