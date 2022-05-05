package com.example.carpoolapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.UserClasses.User;
import com.example.carpoolapp.VehicleClasses.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SpecificVehicleInfo extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;

    private static final String TAG = "SpecificVehicleInfo";
    private TextView owner;
    private TextView capacity;
    private TextView price;
    private TextView model;
    private TextView vehicleType;
    private Button bookRideBT;
    private Button cancelRideBT;
    private Vehicle myVehicle;

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
            myVehicle = mVehicle;
        }
        bookRideBT = findViewById(R.id.bookBT);
        bookRideBT.setOnClickListener(this);
        cancelRideBT = findViewById(R.id.cancelSeatBT);
        cancelRideBT.setOnClickListener(this);
    }

    public void backToVehicleInfo(View view){
        Intent mIntent = new Intent(this, VehicleInfo.class);
        startActivity(mIntent);
    }

    public void bookRide(){
        System.out.println("Vehicle: "+myVehicle.toString());
        System.out.println("Remaining Capacity: "+myVehicle.getRemainingCap());
        if(myVehicle.getRemainingCap() == 1){
            firestore.collection("Vehicles").document(myVehicle.getVehicleID())
                    .update("open", false);
        }

        firestore.collection("Vehicles").document(myVehicle.getVehicleID())
                .update("remainingCap", myVehicle.getRemainingCap()-1);

        //returning null value when entering it into the arraylist
        System.out.println(mAuth.getUid());
        myVehicle.addReservedUid(mAuth.getUid());
        System.out.println(myVehicle.getRidersUIDs());
        firestore.collection("Vehicle").document(myVehicle.getVehicleID())
                .update("ridersUIDs", myVehicle.getRidersUIDs())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent mIntent = new Intent(getApplicationContext(), VehicleInfo.class);
                        startActivity(mIntent);
                        finish();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == bookRideBT.getId()) {
            bookRide();
        }
        if(i == cancelRideBT.getId()){
            //needed thing here
        }
    }
}