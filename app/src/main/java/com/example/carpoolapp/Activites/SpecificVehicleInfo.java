package com.example.carpoolapp.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.VehicleClasses.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SpecificVehicleInfo extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private static final String TAG = "SpecificVehicleInfo";
    private TextView owner;
    private TextView capacity;
    private TextView price;
    private TextView model;
    private TextView vehicleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_vehicle_information);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        owner = findViewById(R.id.ownerTV);
        capacity = findViewById(R.id.seatsLeftTV);
        price = findViewById(R.id.priceTV);
        model = findViewById(R.id.modelTV);
        vehicleType = findViewById(R.id.vehicleTypeTV);
        setUp();
    }

    private void setUp(){
        if(getIntent().hasExtra("selected_vehicle")){
            Vehicle mVehicle = (Vehicle) getIntent().getSerializableExtra("selected_vehicle");
            System.out.println(mVehicle.toString());
            Log.d(TAG, "onCreate: "+mVehicle.toString());

            owner.setText("Owner: "+mVehicle.getOwner());
            capacity.setText(mVehicle.getCapacity()+" seats left");
            //Add the modifier
            price.setText(mVehicle.getBasePrice()+"$");
            model.setText("Model: "+mVehicle.getModel());
            vehicleType.setText("Vehicle Type: "+mVehicle.getVehicleType());
        }
    }
}