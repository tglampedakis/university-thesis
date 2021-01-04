package com.example.androidchatbot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class WelcomeActivity extends AppCompatActivity {
    EditText unameIput, passInput;
    Button signInButton;
    TextView registerText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        unameIput = findViewById(R.id.unameInput);
        passInput = findViewById(R.id.passInput);
        signInButton = findViewById(R.id.signInButton);
        registerText = findViewById(R.id.registerText);
        progressBar = findViewById(R.id.progressBar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        //databaseReference.setValue("Hello friend");

        unameIput.setHint("Enter your e-mail...");
        passInput.setHint("Enter your password...");

        //register button
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        //log in button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(unameIput)) {
                    unameIput.setError("E-mail must not be empty!");
                }
                else if(isEmpty(passInput)){
                    passInput.setError("Password is required!");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(unameIput.getText().toString(), passInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(WelcomeActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                Intent travelActivity = new Intent(getApplicationContext(), TravelActivity.class);
                                startActivity(travelActivity);
                                finish();
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(WelcomeActivity.this, "Error! " + task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}