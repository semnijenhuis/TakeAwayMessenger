package com.springboks.takeawaymessenger.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

private ArrayList<Product> products;
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

    public Order( int orderID, String name, String date, String selectedDeliveryTime, String actualDeliveryTime, boolean open, int customerId, int courierId) {
        products = new ArrayList<>();
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }

    public String getSelectedDeliveryTime() {
        return selectedDeliveryTime;
    }

//    public void setSelectedDeliveryTime(LocalTime selectedDeliveryTime) {
//        this.selectedDeliveryTime = selectedDeliveryTime;
//    }

    public String getActualDeliveryTime() {
        return actualDeliveryTime;
    }

//    public void setActualDeliveryTime(LocalTime actualDeliveryTime) {
//        this.actualDeliveryTime = actualDeliveryTime;
//    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getCustomerId() {
        return customerId;
    }

//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
    public int getCourierId() {
        return courierId;
    }
//
//    public void setCourier(Courier courier) {
//        this.courier = courier;
//    }

    public ArrayList<Message> getCustomerMessages() {
        return customerMessages;
    }

    public void setCustomerMessages(ArrayList<Message> customerMessages) {
        this.customerMessages = customerMessages;
    }

    public ArrayList<Message> getCourierMessages() {
        return courierMessages;
    }

    public void setCourierMessages(ArrayList<Message> courierMessages) {
        this.courierMessages = courierMessages;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
}
