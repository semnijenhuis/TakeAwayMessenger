package com.springboks.takeawaymessenger.model;

import java.util.ArrayList;

public class Customer extends User {

    private String address;
    private ArrayList<Order> orders;

    public Customer(int userId, String firstName, String lastName, String userName, String passWord, String address, ArrayList<Order> orders) {
        super( userId, firstName, lastName, userName, passWord);
        this.address = address;
        this.orders = new ArrayList<Order>();
    }

    public Customer(int userId, String firstName, String lastName, String userName, String passWord, String address) {
        super(userId, firstName, lastName, userName, passWord);
        this.address = address;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
