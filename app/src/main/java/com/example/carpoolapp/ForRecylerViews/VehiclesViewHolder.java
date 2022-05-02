package com.example.carpoolapp.ForRecylerViews;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolapp.R;

public class VehiclesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected TextView name;
    protected TextView vehicle;
    protected TextView price;
    protected TextView seats;

    onVehicleListener onVehicleListener;

    public VehiclesViewHolder(@NonNull View itemView, onVehicleListener onVehicleListener) {
        super(itemView);

        vehicle = itemView.findViewById(R.id.vehicleTvRC);
        price = itemView.findViewById(R.id.priceTVRC);
        seats = itemView.findViewById(R.id.seatsTvRC);
        this.onVehicleListener = onVehicleListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onVehicleListener.onVehicleClick(getAdapterPosition());
    }

    public interface onVehicleListener{
        void onVehicleClick(int position);
    }
}
