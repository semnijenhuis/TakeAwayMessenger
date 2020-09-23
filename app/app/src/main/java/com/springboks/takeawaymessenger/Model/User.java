package com.springboks.takeawaymessenger.Model;

public abstract class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;

    public User (String firstName, String lastName, String userName, String passWord){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        //:TODO: password needs encryption!!!
        this.passWord = passWord;
    }
}
