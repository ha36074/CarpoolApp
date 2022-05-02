package com.example.carpoolapp.UserClasses;

import java.util.ArrayList;

public class Staff extends User{
    private String inSchoolTitle;

    public Staff(String uid, String email, String name, String userType, double priceMultiplier, ArrayList<String> ownedVehicles, double money, String inSchoolTitle) {
        super(uid, email, name, userType, priceMultiplier, ownedVehicles, money);
        this.inSchoolTitle = inSchoolTitle;
    }

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }

    @Override
    public String toString() {
        return super.toString()+"Staff{" +
                "inSchoolTitle='" + inSchoolTitle + '\'' +
                '}';
    }
}
