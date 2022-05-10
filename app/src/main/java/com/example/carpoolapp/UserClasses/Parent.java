package com.example.carpoolapp.UserClasses;

import java.util.ArrayList;

public class Parent extends User{
    //private ArrayList<String> childrensUIDs;
    private String pts;

    public Parent(String uid, String email, String name, String userType, double priceMultiplier, ArrayList<String> ownedVehicles, double money, String aID, String pts) {
        super(uid, email, name, userType, priceMultiplier, ownedVehicles, money, aID);
        //this.childrensUIDs = childrensUIDs;
        this.pts = pts;
    }


    //public ArrayList<String> getChildrensUIDs() {
        //return childrensUIDs;
    //}

    //public void setChildrensUIDs(ArrayList<String> childrensUIDs) {
        //this.childrensUIDs = childrensUIDs;
    //}

    public String getPts() {
        return pts;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

    @Override
    public String toString() {
        return "Parent{" +
                ", pts='" + pts + '\'' +
                '}';
    }
}
