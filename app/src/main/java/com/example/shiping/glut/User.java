package com.example.shiping.glut;

/**
 * Created by Admin on 1/24/2016.
 */
public class User {
    private String fullName;
    private String phoneNumber;
    private String gender;
    private String location;
    private BankAccount account;


    public User() {}
    public User(String fullName, String phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getLocations() {
        return location;
    }
}

class BankAccount {
    private long acctNumber;
}