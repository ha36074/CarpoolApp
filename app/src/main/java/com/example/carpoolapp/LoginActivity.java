package com.example.carpoolapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText userEmail;
    private EditText userPassword;
    private Spinner selectType;
    private String selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        userEmail = findViewById(R.id.emailAdressET);
        userPassword = findViewById(R.id.passwordET);
        selectType = findViewById(R.id.spinner);
    }

    private void setUpSpinner(){
        String [] options = {"Staff", "Parent", "Student"};
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, options);
        String s = "";
        selectType.setAdapter(langArrAdapter);
        selectType.setOnItemClickListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void signIn(View v){
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("SIGN IN", "Successfully signed in");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else{
                    Log.w("SIGN IN", "signInWithEmail:failure", task.getException());
                    updateUI(null);

                }
            }
        });
    }

    public void signUp(View v){
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("SIGN UP", "Successfully signed up the user");

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    public void updateUI(FirebaseUser currUser){
        if (currUser != null){
            Intent intent = new Intent(this, UserProfile.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currUser = mAuth.getCurrentUser();
        if(currUser != null){
            Intent intent = new Intent(this, UserProfile.class);
            startActivity(intent);
        }
    }
}