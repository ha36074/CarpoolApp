package com.example.carpoolapp.UserClasses;

import java.util.ArrayList;

public class Student extends User{
    private String graduatingYear;
    //private ArrayList<String> parentsUIDs;

    public Student(String uid, String email, String name, String userType, double priceMultiplier, ArrayList<String> ownedVehicles, double money, String aID, String graduatingYear) {
        super(uid, email, name, userType, priceMultiplier, ownedVehicles, money, aID);
        this.graduatingYear = graduatingYear;
        //this.parentsUIDs = parentsUIDs;
    }

    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    //public ArrayList<String> getParentsUIDs() {
        //return parentsUIDs;
    //}

    //public void setParentsUIDs(ArrayList<String> parentsUIDs) {
        //this.parentsUIDs = parentsUIDs;
    //}

    @Override
    public String toString() {
        return super.toString()+"Student{" +
                "graduatingYear='" + graduatingYear + '\'' +
                '}';
    }
}
