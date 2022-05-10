package com.example.carpoolapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.UserClasses.User;
import com.example.carpoolapp.VehicleClasses.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private Button cancelRideBT; //establish this with layout button.
    private Vehicle myVehicle;
    private User myUser;
    private double mod;
    private double prices;

    private LinearLayout layout;
    private Button closeYourCar;
    private Button closeYourRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_vehicle_information);

        layout = findViewById(R.id.linearLayoutSpecificVehicle);

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
        TaskCompletionSource<String> getBasePrice = new TaskCompletionSource<>();
        firestore.collection("userInfo").whereEqualTo("authID", mAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println("Task result: "+task.getResult());
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        myUser = document.toObject(User.class);
                        System.out.println("In getting docs for mod.");
                    }
                    mod = Double.parseDouble(myUser.getPriceMultiplier()+"");
                    getBasePrice.setResult(null);
                }
                else {
                    Log.d("SpecificVehicle", "Error getting documents from db to make a user: ", task.getException());
                }
                if(getIntent().hasExtra("selected_vehicle")){
                    Vehicle mVehicle = (Vehicle) getIntent().getSerializableExtra("selected_vehicle");
                    System.out.println(mVehicle.toString());
                    Log.d(TAG, "onCreate: "+mVehicle.toString());

                    System.out.println(mod);
                    System.out.println(mVehicle.getBasePrice()+"");
                    prices = mod*Double.parseDouble(mVehicle.getBasePrice()+"");
                    System.out.println(prices);

                    owner.setText("Owner: "+mVehicle.getOwner());
                    capacity.setText(mVehicle.getRemainingCap()+" seats left");
                    price.setText(prices+"$");
                    model.setText("Model: "+mVehicle.getModel());
                    vehicleType.setText("Vehicle Type: "+mVehicle.getVehicleType());
                    myVehicle = mVehicle;

                    if(myUser.getOwnedVehicles().contains(mVehicle.getVehicleID())){
                        closeYourCar = new Button(getApplicationContext());
                        closeYourCar.setText("Close this Car");
                        layout.addView(closeYourCar);
                    }
                    if(myVehicle.getRidersUIDs().contains(mAuth.getUid())){
                        closeYourRide = new Button(getApplicationContext());
                        closeYourRide.setText("Cancel Ride");
                        layout.addView(closeYourRide);
                    }
                }
            }
        });
        bookRideBT = findViewById(R.id.bookBT);
        bookRideBT.setOnClickListener(this);
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

        firestore.collection("userInfo").document(mAuth.getUid())
                .update("money", myUser.getMoney()-prices);

        System.out.println(mAuth.getUid());
        myVehicle.addReservedUid(mAuth.getUid());
        System.out.println("The UIDS: "+myVehicle.getRidersUIDs());
        firestore.collection("Vehicles").document(myVehicle.getVehicleID())
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

    public void closeCar(){
        System.out.println("In close car");
        firestore.collection("Vehicles").document(myVehicle.getVehicleID())
                .update("open", false);
    }

    public void cancelRide(){
        System.out.println("In close ride");
        for(int i = myVehicle.getRidersUIDs().size(); i>=0; i--){
            if(myVehicle.getRidersUIDs().get(i).equals(mAuth.getUid())){
                myVehicle.getRidersUIDs().remove(i);
            }
        }
        firestore.collection("Vehicles").document(myVehicle.getVehicleID())
                .update("ridersUIDs", myVehicle.getRidersUIDs());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == bookRideBT.getId()) {
            bookRide();
        }
        if(i == closeYourCar.getId()){
            closeCar();
        }
        if(i == closeYourRide.getId()){
            cancelRide();
        }
    }
}