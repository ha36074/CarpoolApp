package com.example.carpoolapp.UserClasses;

import java.util.ArrayList;

public class Alumni extends User{
    private String graduatingYear;
    //private ArrayList<String> parentsUIDs;

    public Alumni(String uid, String email, String name, String userType, double priceMultiplier, ArrayList<String> ownedVehicles, double money, String graduatingYear) {
        super(uid, email, name, userType, priceMultiplier, ownedVehicles, money);
        this.graduatingYear = graduatingYear;
        //this.parentsUIDs = parentsUIDs;
    }

    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }


    //public void setParentsUIDs(ArrayList<String> parentsUIDs) {
        //this.parentsUIDs = parentsUIDs;
    //}

    @Override
    public String toString() {
        return super.toString()+"Alumni{" +
                "graduatingYear='" + graduatingYear + '\'' +
                '}';
    }
}
