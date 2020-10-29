package com.springboks.takeawaymessenger.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

private ArrayList<Integer> productIds;
private int orderID;
private String name;
private String date;
private String selectedDeliveryTime;
private String actualDeliveryTime;
private boolean open;
private int customerId;
private int courierId;
private String imageFile;
private ArrayList<Message> customerMessages;
private ArrayList<Message> courierMessages;

    public Order( int orderID, String name, String date, String selectedDeliveryTime, String actualDeliveryTime, boolean open, int customerId, int courierId, ArrayList<Integer> productIds) {
        this.productIds = productIds;
        this.orderID = orderID;
        this.name = name;
        this.date = date;
        this.selectedDeliveryTime = selectedDeliveryTime;
        this.actualDeliveryTime = actualDeliveryTime;
        this.open = open;
        this.customerId = customerId;
        this.courierId = courierId;
        imageFile = "subway.png";
    }

    public Order(int orderID, String restaurantName, String date, String selectedDeliveryTime, String actualDeliveryTime, boolean open, int courierId, ArrayList<Integer> productIds) {
        this.productIds = productIds;
        this.orderID = orderID;
        this.name = restaurantName;
        this.date = date;
        this.selectedDeliveryTime = selectedDeliveryTime;
        this.actualDeliveryTime = actualDeliveryTime;
        this.open = open;
        this.courierId = courierId;
        this.imageFile = imageFile;
        imageFile = "subway.png";
    }

    public int getOrderID() {
        return orderID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    public String getImageFile() {
        return imageFile;
    }

    public ArrayList<Integer> getProductIds() {
        return productIds;
    }


}
