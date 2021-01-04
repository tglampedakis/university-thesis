package com.example.androidchatbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TravelActivity extends AppCompatActivity {
    TextView textView, chatBotText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String firstNameOfUser, destinationType;
    Toolbar toolbar;
    Button resultsButton;
    RadioGroup radioGroup, radioGroup2;
    RadioButton mountainRadio, seaRadio, bothRadio, activitiesRadio, nightlifeRadio, both2Radio, noneRadio;

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, WelcomeActivity.class));

                break;
            case R.id.menuAbout:
                showMessage("About", "This Android application was built and designed by Theodore Glampedakis.\n\n" + "This application is about helping the respective users with picking the right destination, mainly for their summer vacation.\n\n" + "Bachelor's degree thesis in University of Piraeus, department of Informatics.");
                break;
            case R.id.menuHome:
                break;
            case R.id.menuContact:
                startActivity(new Intent(this, ContactActivity.class));
                break;
        }

        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        textView = findViewById(R.id.textView2);
        resultsButton = findViewById(R.id.resultsButton);
        radioGroup = findViewById(R.id.radioGroup);
        mountainRadio = findViewById(R.id.mountainRadio);
        seaRadio = findViewById(R.id.seaRadio);
        bothRadio = findViewById(R.id.bothRadio);
        radioGroup2 = findViewById(R.id.radioGroup2);
        activitiesRadio = findViewById(R.id.activitiesRadio);
        nightlifeRadio = findViewById(R.id.nightlifeRadio);
        both2Radio = findViewById(R.id.both2Radio);
        noneRadio = findViewById(R.id.noneRadio);
        chatBotText = findViewById(R.id.chatBotText);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.ic_baseline_home_24);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("firstName");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstNameOfUser = snapshot.getValue().toString();
                textView.setText("Welcome, " + firstNameOfUser + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //View the results for te user preferences
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mountainRadio.isChecked()) destinationType = "mountain";
                else if (seaRadio.isChecked()) {
                    if (activitiesRadio.isChecked()) destinationType = "activities";
                    else if (nightlifeRadio.isChecked()) destinationType = "nightlife";
                    else if (both2Radio.isChecked()) destinationType = "both_activities";
                    else destinationType = "sea_only";
                }
                else destinationType = "both_sea_mountain";

                //System.out.println(destinationType);
                Intent resultsIntent = new Intent(getApplicationContext(), ResultsActivity.class);
                resultsIntent.putExtra("destinationType", destinationType);
                startActivity(resultsIntent);
            }
        });



        //radios
        radioGroup2.setVisibility(View.GONE);

        seaRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radioGroup2.getVisibility() == View.GONE) radioGroup2.setVisibility(View.VISIBLE);
                else radioGroup2.setVisibility(View.GONE);
            }
        });


        //chat bot
        chatBotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatBotIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(chatBotIntent);
            }
        });

    }


    //dialog for message
    public void showMessage (String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(text);
        builder.show();
    }

}