package com.example.carpoolapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.carpoolapp.ForRecylerViews.AdapterForVehicleRecView;
import com.example.carpoolapp.ForRecylerViews.VehiclesViewHolder;
import com.example.carpoolapp.R;
import com.example.carpoolapp.VehicleClasses.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VehicleInfo extends AppCompatActivity implements VehiclesViewHolder.onVehicleListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;
    private Vehicle vehicleInfo;
    private ArrayList<Vehicle> vehiclesList;

    ArrayList myData;

    RecyclerView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        vehicleInfo = this.vehicleInfo;
        vehiclesList = new ArrayList<Vehicle>();
        rec = findViewById(R.id.vehicleRC);

        getData();
    }

    public void putinRec(){

        System.out.println(vehiclesList.toString());
        AdapterForVehicleRecView myAdapter = new AdapterForVehicleRecView(vehiclesList, this);
        rec.setAdapter(myAdapter);
        rec.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getData(){
        vehiclesList.clear();
        TaskCompletionSource<String> getAllRidesTask = new TaskCompletionSource<>();
        firestore.collection("Vehicles").whereEqualTo("open", true)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        vehiclesList.add(document.toObject(Vehicle.class));
                        System.out.println(document.toObject(Vehicle.class));
                        System.out.println(vehiclesList);
                    }
                    getAllRidesTask.setResult(null);
                }
                else {
                    Log.d("VehiclesInfoActivity", "Error getting documents from db: ", task.getException());
                }
            }
        });

        getAllRidesTask.getTask().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                System.out.println("VEHICLE INFO: " + vehiclesList.toString());

                putinRec();
            }
        });
    }

    public void toAddVehicle(View v){
        Intent intent = new Intent(this, AddVehicle.class);
        startActivity(intent);
    }

    public void backToUserProfie(View v){
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    @Override
    public void onVehicleClick(int position) {
        vehiclesList.get(position);
        Intent intent = new Intent(this, SpecificVehicleInformation.class);
        startActivity(intent);
    }
}