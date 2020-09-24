package com.springboks.takeawaymessenger.Model;

import java.util.ArrayList;

public class Courier extends User {

    private ArrayList<Order> orders;

    public Courier(String firstName, String lastName, String userName, String passWord, ArrayList<Order> orders) {
        super(firstName, lastName, userName, passWord);
        this.orders = new ArrayList<Order>();
    }

    public Courier(String firstName, String lastName, String userName, String passWord) {
        super(firstName, lastName, userName, passWord);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
