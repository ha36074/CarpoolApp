package com.example.carpoolapp.VehicleClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {
    private String owner;
    private String model;
    private String vehicleID;
    private int capacity;
    private ArrayList<String> ridersUIDs;
    private boolean open;
    private String vehicleType;
    private double basePrice;

    public Vehicle(String owner, String model, String vehicleID, int capacity, ArrayList<String> ridersUIDs, boolean open, String vehicleType, double basePrice) {
        this.owner = owner;
        this.model = model;
        this.vehicleID = vehicleID;
        this.capacity = capacity;
        this.ridersUIDs = ridersUIDs;
        this.open = open;
        this.vehicleType = vehicleType;
        this.basePrice = basePrice;
    }

    public Vehicle() {
        this.owner = owner;
        this.model = model;
        this.vehicleID = vehicleID;
        this.capacity = capacity;
        this.ridersUIDs = ridersUIDs;
        this.open = open;
        this.vehicleType = vehicleType;
        this.basePrice = basePrice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<String> getRidersUIDs() {
        return ridersUIDs;
    }

    public void setRidersUIDs(ArrayList<String> ridersUIDs) {
        this.ridersUIDs = ridersUIDs;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return owner + "/" +model + "/" + vehicleID + "/" + capacity +
                "/" + ridersUIDs +
                "/" + open +
                "/"  + vehicleType + '\'' +
                "/"  + basePrice;
    }
}
