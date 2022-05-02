package com.example.carpoolapp.ForRecylerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.VehicleClasses.Vehicle;


import java.util.ArrayList;

public class AdapterForVehicleRecView extends RecyclerView.Adapter<VehiclesViewHolder>{

    ArrayList<Vehicle> mData;
    private VehiclesViewHolder.onVehicleListener mOnVehicleListener;

    public AdapterForVehicleRecView(ArrayList<Vehicle> data, VehiclesViewHolder.onVehicleListener onVList){
        mData = data;
        this.mOnVehicleListener = onVList;
    }

    @NonNull
    @Override
    public VehiclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_view, parent, false);

        VehiclesViewHolder holder = new VehiclesViewHolder(myView, mOnVehicleListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesViewHolder holder, int position) {
        System.out.println("In adapter "+mData.get(position).toString());
        holder.seats.setText(mData.get(position).getCapacity()+" seats left");
        holder.vehicle.setText(mData.get(position).getVehicleType());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

