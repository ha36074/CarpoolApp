package com.example.carpoolapp.VehicleClasses;

import java.util.ArrayList;

public class Segway extends Vehicle{
    private int range;
    private int weightCapacity;

    public Segway(String owner, String model, String vehicleID, int capacity, ArrayList<String> ridersUIDs, boolean open, String vehicleType, double basePrice, int range, int weightCapacity) {
        super(owner, model, vehicleID, capacity, ridersUIDs, open, vehicleType, basePrice);
        this.range = range;
        this.weightCapacity = weightCapacity;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    @Override
    public String toString() {
        return super.toString()+"/" + range +
                "/"  + weightCapacity;
    }
}
