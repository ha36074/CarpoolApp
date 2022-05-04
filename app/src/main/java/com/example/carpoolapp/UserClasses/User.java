package com.example.carpoolapp.UserClasses;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class User {
    private String uid;
    private String email;
    private String name;
    private String userType;
    private double priceMultiplier;
    private ArrayList<String> ownedVehicles;
    private double money;

    public User(String uid, String email, String name, String userType, double priceMultiplier, ArrayList<String> ownedVehicles, double m) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.userType = userType;
        this.priceMultiplier = priceMultiplier;
        this.ownedVehicles = ownedVehicles;
        this.money = m;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", userType='" + userType + '\'' +
                ", priceMultiplier=" + priceMultiplier +
                ", ownedVehicles=" + ownedVehicles +", money=" + money +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }


    public ArrayList<String> getOwnedVehicles() {
        return ownedVehicles;
    }

    public void setOwnedVehicles(ArrayList<String> ownedVehicles) {
        this.ownedVehicles = ownedVehicles;
    }

    public void addOwnedVehicle(String newRideRef) {
        this.ownedVehicles.add(newRideRef);
    }
}
