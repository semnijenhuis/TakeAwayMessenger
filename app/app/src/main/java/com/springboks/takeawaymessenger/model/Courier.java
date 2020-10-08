package com.springboks.takeawaymessenger.model;

import java.util.ArrayList;

public class Courier extends User {

    private ArrayList<Order> orders;

    public Courier(int userId, String firstName, String lastName, String userName, String passWord, ArrayList<Order> orders) {
        super(userId, firstName, lastName, userName, passWord);
        this.orders = new ArrayList<Order>();
    }

    public Courier(int userId, String firstName, String lastName, String userName, String passWord) {
        super(userId, firstName, lastName, userName, passWord);
        this.orders = new ArrayList<Order>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
