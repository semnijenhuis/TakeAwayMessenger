package com.springboks.takeawaymessenger.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderAdmin {

    public static List<Order> orderList;

    static {
        orderList = new ArrayList<Order>();
    }

    public OrderAdmin() { orderList = new ArrayList<Order>();}

    public List getOrderList() {
        if (orderList != null) {
            return orderList;
        }
        System.out.println("null order list");
        return null;
    }

    public void setOrderList() {

        Product product = new Product("Big Mac", 4.00, 2);
        Product product1 = new Product("Small fries", 2.25, 2);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);

        Customer customer = new Customer("Stephen", "Nedd", "stephennedd", "password", "Saxionweg 1");
        Courier courier = new Courier("Sem", "Nijenhuis", "semnijenhuis", "password");

        Order order = new Order(products, 1, customer.getFirstName(), LocalDate.now(), LocalTime.now().plusHours(1), LocalTime.now().plusHours(1), true, customer, courier);
        Order order1 = new Order(products, 2,  customer.getFirstName(), LocalDate.now(), LocalTime.now().plusHours(1), LocalTime.now().plusHours(1), true, customer, courier);
        orderList.add(order);
        orderList.add(order1);

    }

}
