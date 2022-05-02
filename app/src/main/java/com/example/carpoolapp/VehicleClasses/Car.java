package com.example.carpoolapp.VehicleClasses;

import java.util.ArrayList;

public class Car extends Vehicle{
    private int range;

    public Car(String owner, String model, String vehicleID, int capacity, ArrayList<String> ridersUIDs, boolean open, String vehicleType, double basePrice, int range) {
        super(owner, model, vehicleID, capacity, ridersUIDs, open, vehicleType, basePrice);
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return super.toString()+"/" + range;
    }
}
