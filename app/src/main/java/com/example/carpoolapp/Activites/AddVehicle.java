package com.example.carpoolapp.Activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.carpoolapp.R;
import com.example.carpoolapp.UserClasses.User;
import com.example.carpoolapp.VehicleClasses.Bicycle;
import com.example.carpoolapp.VehicleClasses.Car;
import com.example.carpoolapp.VehicleClasses.Helicopter;
import com.example.carpoolapp.VehicleClasses.Segway;
import com.example.carpoolapp.VehicleClasses.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddVehicle extends AppCompatActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE = 1;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private LinearLayout layout;
    private EditText owner;
    private EditText model;
    private EditText capacity;
    private EditText price;
    private EditText weightCapcityBikeAndSeg;
    private EditText bikeType;
    private EditText weightBike;
    private EditText rangeCarAndSeg;
    private EditText maxAltitudeHeli;
    private EditText maxSpeedHeli;
    private User myUser;
    private ImageView myImage;

    private Spinner vehicleTypeSpinner;
    private String selectedType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        layout = findViewById(R.id.linearLayoutCreateVehicle);
        vehicleTypeSpinner = findViewById(R.id.vehicleTypeSpinner);
        setupSpinner();

        myImage = findViewById(R.id.imageToUpload);
        myImage.setOnClickListener(this);
    }

    private void setupSpinner(){
        String [] vehicleTypes = {"Car", "Bike", "Helicopter", "Segway"};
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(AddVehicle.this,
                android.R.layout.simple_spinner_item, vehicleTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(langArrAdapter);

        vehicleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = parent.getItemAtPosition(position).toString();
                System.out.println(selectedType);
                addFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addFields(){
        sharedFields();
        if(selectedType.equals("Bike")){
            weightBike = new EditText(this);
            weightBike.setHint("Bicycle Weight");
            layout.addView(weightBike);

            weightCapcityBikeAndSeg = new EditText(this);
            weightCapcityBikeAndSeg.setHint("Weight Capacity");
            layout.addView(weightCapcityBikeAndSeg);

            bikeType = new EditText(this);
            bikeType.setHint("Bicycle Type");
            layout.addView(bikeType);
        }
        else if(selectedType.equals("Car")){
            rangeCarAndSeg = new EditText(this);
            rangeCarAndSeg.setHint("Range");
            layout.addView(rangeCarAndSeg);
        }
        else if(selectedType.equals("Helicopter")){
            maxSpeedHeli = new EditText(this);
            maxSpeedHeli.setHint("Max Speed");
            layout.addView(maxSpeedHeli);

            maxAltitudeHeli = new EditText(this);
            maxAltitudeHeli.setHint("Max Altitude");
            layout.addView(maxAltitudeHeli);
        }
        else{
            weightCapcityBikeAndSeg = new EditText(this);
            weightCapcityBikeAndSeg.setHint("Weight Capacity");
            layout.addView(weightCapcityBikeAndSeg);

            rangeCarAndSeg = new EditText(this);
            rangeCarAndSeg.setHint("Range");
            layout.addView(rangeCarAndSeg);
        }
    }

    public void sharedFields(){
        layout.removeAllViewsInLayout();

        owner = new EditText(this);
        owner.setHint("Owner");
        layout.addView(owner);

        model = new EditText(this);
        model.setHint("Model");
        layout.addView(model);

        capacity = new EditText(this);
        capacity.setHint("Capacity");
        layout.addView(capacity);

        price = new EditText(this);
        price.setHint("Base Price");
        layout.addView(price);
    }

    public void addV(View v){
        ArrayList<String> ar = new ArrayList<>();
        DocumentReference newRideRef = firestore.collection("Vehicles").document();
        String vehicleId = newRideRef.getId();
        FirebaseUser currUser = mAuth.getCurrentUser();

        Vehicle newVehicle = null;

        String o = owner.getText().toString();
        String m = model.getText().toString();
        int c = Integer.parseInt(capacity.getText().toString());
        double p = Double.parseDouble(price.getText().toString());

        if(selectedType.equals("Car")){
            System.out.println("In car");
            int range = Integer.parseInt(rangeCarAndSeg.getText().toString());
            newVehicle = new Car(o,m,vehicleId, c, ar,true ,"car", p, range);
        }
        else if (selectedType.equals("Bike")){
            int weight = Integer.parseInt(weightBike.getText().toString());
            int weightCap = Integer.parseInt(weightCapcityBikeAndSeg.getText().toString());
            String type = bikeType.getText().toString();
            newVehicle = new Bicycle(o, m, vehicleId, c, ar, true, "bicycle", p, type, weight,weightCap);
        }
        else if(selectedType.equals("Helicopter")){
            int speed = Integer.parseInt(maxSpeedHeli.getText().toString());
            int alt = Integer.parseInt(maxAltitudeHeli.getText().toString());
            newVehicle = new Helicopter(o,m,vehicleId,c,ar,true,"helicopter",p,alt,speed);
        }
        else{
            int r = Integer.parseInt(rangeCarAndSeg.getText().toString());
            int weight = Integer.parseInt(weightCapcityBikeAndSeg.getText().toString());
            newVehicle = new Segway(o,m,vehicleId,c,ar,true,"segway",p,r,weight);
        }

        newRideRef.set(newVehicle);
        System.out.println("AuthID "+mAuth.getUid().toString());
        TaskCompletionSource<String> updateOwnedVehicles = new TaskCompletionSource<>();
        firestore.collection("userInfo").whereEqualTo("authID", currUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println("Task result: "+task.getResult());
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("In the construction of user.");
                        myUser = document.toObject(User.class);
                        System.out.println(myUser.toString());
                    }
                    myUser.addOwnedVehicle(newRideRef.getId());
                    firestore.collection("userInfo").document(myUser.getUid())
                            .update("ownedVehicles", myUser.getOwnedVehicles());
                    updateOwnedVehicles.setResult(null);
                }
                else {
                    Log.d("AddVehicle", "Error getting documents from db to make a user: ", task.getException());
                }
            }
        });
    }

    public void backFromAddVehicle(View v){
        Intent intent = new Intent(this, VehicleInfo.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == myImage.getId()) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            myImage.setImageURI(selectedImage);
        }
    }
}