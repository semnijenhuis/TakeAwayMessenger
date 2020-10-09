package com.springboks.takeawaymessenger.model;

public class Product {

    private String description;
    private double price;
    private int productId;

    public Product(String description, double price, int productId) {
        this.description = description;
        this.price = price;
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
