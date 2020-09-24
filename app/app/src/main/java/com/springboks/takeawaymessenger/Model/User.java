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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
