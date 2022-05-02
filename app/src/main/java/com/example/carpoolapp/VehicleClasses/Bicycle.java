package com.example.carpoolapp.VehicleClasses;

import java.util.ArrayList;

public class Bicycle extends Vehicle{
    private String bicycleType;
    private int weight;
    private int weightCapacity;

    public Bicycle(String owner, String model, String vehicleID, int capacity, ArrayList<String> ridersUIDs, boolean open, String vehicleType, double basePrice, String bicycleType, int weight, int weightCapacity) {
        super(owner, model, vehicleID, capacity, ridersUIDs, open, vehicleType, basePrice);
        this.bicycleType = bicycleType;
        this.weight = weight;
        this.weightCapacity = weightCapacity;
    }

    public String getBicycleType() {
        return bicycleType;
    }

    public void setBicycleType(String bicycleType) {
        this.bicycleType = bicycleType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    @Override
    public String toString() {
        return super.toString()+"/"  + bicycleType + "/"  + weight +"/"  + weightCapacity;
    }
}
