package com.example.carpoolapp.VehicleClasses;

import java.util.ArrayList;

public class Helicopter extends Vehicle{
    private int maxAltitude;
    private int maxAirSpeed;

    public Helicopter(String owner, String model, String vehicleID, int capacity, ArrayList<String> ridersUIDs, boolean open, String vehicleType, double basePrice, int maxAltitude, int maxAirSpeed) {
        super(owner, model, vehicleID, capacity, ridersUIDs, open, vehicleType, basePrice);
        this.maxAltitude = maxAltitude;
        this.maxAirSpeed = maxAirSpeed;
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAirSpeed() {
        return maxAirSpeed;
    }

    public void setMaxAirSpeed(int maxAirSpeed) {
        this.maxAirSpeed = maxAirSpeed;
    }

    @Override
    public String toString() {
        return super.toString()+"/"  + maxAltitude +
                "/"  + maxAirSpeed;
    }
}
