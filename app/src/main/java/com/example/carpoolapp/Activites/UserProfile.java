package com.example.carpoolapp.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.carpoolapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView name;
    private TextView email;
    private TextView money;
    private TextView role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.userNameTv);
        email = findViewById(R.id.emailTV);
        money = findViewById(R.id.userMoneyAmountTV);
        role = findViewById(R.id.roleTV);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void toVehicleInfo(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, VehicleInfo.class);
        startActivity(intent);
    }
}