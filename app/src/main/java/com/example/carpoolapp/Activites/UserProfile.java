package com.example.carpoolapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.UserClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private TextView name;
    private TextView email;
    private TextView money;
    private TextView role;
    private User myUser;
    private FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.userNameTv);
        email = findViewById(R.id.emailTV);
        money = findViewById(R.id.userMoneyAmountTV);
        role = findViewById(R.id.roleTV);

        mAuth = FirebaseAuth.getInstance();

        setUp();
    }

    private void setUp(){
        FirebaseUser currUser = mAuth.getCurrentUser();
        TaskCompletionSource<String> getUserInProfile = new TaskCompletionSource<>();
        firestore.collection("userInfo").whereEqualTo("authID", currUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println("Task result: "+task.getResult());
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        myUser = document.toObject(User.class);
                        System.out.println("User: "+myUser.toString());
                    }
                    name.setText(myUser.getName().toString());
                    email.setText(myUser.getEmail().toString());
                    money.setText(String.valueOf(myUser.getMoney()));
                    role.setText(myUser.getUserType().toString());
                    getUserInProfile.setResult(null);
                }
                else {
                    Log.d("UserProfile", "Error getting documents from db to make a user: ", task.getException());
                }
            }
        });
    }

    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void toVehicleInfo(View v){
        Intent intent = new Intent(this, VehicleInfo.class);
        startActivity(intent);
    }
}