package com.springboks.takeawaymessenger.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Order {

private ArrayList<Product> products;
private int orderID;
private String name;
private LocalDate date;
private LocalTime selectedDeliveryTime;
private LocalTime actualDeliveryTime;
private boolean open;
private Customer customer;
private Courier courier;
private ArrayList<Message> customerMessages;
private ArrayList<Message> courierMessages;
}
