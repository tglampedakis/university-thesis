package com.example.androidchatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText firstNameInput, lastNameInput, usernameInput, emailInput, passwordInput;
    Button registerButton;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);

        firstNameInput.setHint("Enter your first name...");
        lastNameInput.setHint("Enter your last name...");
        usernameInput.setHint("Enter your username...");
        emailInput.setHint("Enter your e-mail address...");
        passwordInput.setHint("Enter your password...");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                if (checkData()) {
                    firebaseAuth.createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                User user = new User(firstNameInput.getText().toString(), lastNameInput.getText().toString(), usernameInput.getText().toString(), emailInput.getText().toString());
                                FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "User successfully created!", Toast.LENGTH_SHORT).show();
                                Intent back = new Intent(getApplicationContext(), WelcomeActivity.class);
                                startActivity(back);
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


    //Inputs validations
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean checkData() {
        boolean valid = true;
        if (isEmpty(firstNameInput)) {
            firstNameInput.setError("First name is required!");
            valid = false;
            progressBar.setVisibility(View.GONE);
        }

        if (isEmpty(lastNameInput)) {
            lastNameInput.setError("Last name is required!");
            valid = false;
            progressBar.setVisibility(View.GONE);
        }

        if (isEmpty(usernameInput)) {
            usernameInput.setError("A username is required!");
            valid = false;
            progressBar.setVisibility(View.GONE);
        }

        if (isEmail(emailInput) == false) {
            emailInput.setError("Enter valid e-mail!");
            valid = false;
            progressBar.setVisibility(View.GONE);
        }

        if (isEmpty(passwordInput)) {
            passwordInput.setError("Password is required!");
            valid = false;
            progressBar.setVisibility(View.GONE);
        }

        return valid;
    }
}