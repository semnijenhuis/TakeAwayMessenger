package com.springboks.takeawaymessenger.Model;

public class Customer extends User {

    private String address;

    public Customer(String firstName, String lastName, String userName, String passWord, String address) {
        super(firstName, lastName, userName, passWord);
        this.address = address;
    }
}
