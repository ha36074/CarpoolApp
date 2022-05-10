package com.example.carpoolapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.carpoolapp.R;
import com.example.carpoolapp.UserClasses.User;
import com.example.carpoolapp.UserClasses.Alumni;
import com.example.carpoolapp.UserClasses.Parent;
import com.example.carpoolapp.UserClasses.Staff;
import com.example.carpoolapp.UserClasses.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText userEmail;
    private EditText userPassword;
    private EditText gYoT;
    private EditText name;
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
        gYoT = findViewById(R.id.gYoTTV);
        name = findViewById(R.id.nameET);
        setUpSpinner();
    }

    private void setUpSpinner(){
        String [] options = {"Staff", "Parent", "Student", "Alumni"};
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, options);
        String s = "";
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectType.setAdapter(langArrAdapter);
        selectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = parent.getItemAtPosition(position).toString();
                if(selectedOption.equals("Student") || selectedOption.equals("Alumni")){
                    gYoT.setHint("Graduation year");
                }
                else{
                    gYoT.setHint("Preferred school name");
                }
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
        String got = gYoT.getText().toString();
        String n = name.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("SIGN UP", "Successfully signed up the user");

                    ArrayList<String> ar = new ArrayList<>();

                    DocumentReference newUserRef = firestore.collection("userInfo").document();
                    String userID = newUserRef.getId();

                    User myUser = null;

                    if(selectedOption.equals("Parent")){
                        myUser = new Parent(userID, email, n, selectedOption, 1.25, ar, 100 , mAuth.getUid().toString(),got);
                    }
                    else if(selectedOption.equals("Alumni")){
                        myUser = new Alumni(userID, email, n, selectedOption, 1.25, ar, 100, mAuth.getUid().toString(), got);
                    }
                    else if(selectedOption.equals("Student")){
                        myUser = new Student(userID, email, n, selectedOption, 1, ar, 100, mAuth.getUid().toString(), got);
                    }
                    else if(selectedOption.equals("Staff")){
                        myUser = new Staff(userID, email, n, selectedOption, 1.25, ar, 100, mAuth.getUid().toString(), got);
                    }
                    newUserRef.set(myUser);
                    Intent mIntent = new Intent(getApplicationContext(), UserProfile.class);
                    startActivity(mIntent);
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

    public void click(View v) {
        System.out.println("selectedOption: " + selectedOption);
    }
}