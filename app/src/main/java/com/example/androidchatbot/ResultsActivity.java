package com.example.androidchatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class ResultsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[], s2[], destinationTypeName, s3[];
    int images[];
    Toolbar toolbar;

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
                finish();
                startActivity(new Intent(this, TravelActivity.class));
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
        setContentView(R.layout.activity_results);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        //s1 = getResources().getStringArray(R.array.destination_names);
        //s2 = getResources().getStringArray(R.array.destination_description);

        //check what image and texts to use
        destinationTypeName = getIntent().getStringExtra("destinationType");

        //System.out.println(destinationType);

        if (destinationTypeName.equals("mountain")) {
            s1 = getResources().getStringArray(R.array.mountain_destinations);
            s2 = getResources().getStringArray(R.array.mountain_destinations_description);
            images = new int[] {R.drawable.faraggi_nedas, R.drawable.mainalo, R.drawable.nestos_rodoph, R.drawable.olumpos, R.drawable.parnwnas, R.drawable.tzoumerka, R.drawable.zagoroxwria, R.drawable.zhreia};
        }
        else if (destinationTypeName.equals("activities")) {
            s1 = getResources().getStringArray(R.array.activities);
            s2 = getResources().getStringArray(R.array.activities_description);
            images = new int[] {R.drawable.chania, R.drawable.kefalonia, R.drawable.leukada, R.drawable.mhlos, R.drawable.paros, R.drawable.rodos, R.drawable.samos, R.drawable.santorini, R.drawable.tinos, R.drawable.zakunthos};
        }
        else if (destinationTypeName.equals("nightlife")) {
            s1 = getResources().getStringArray(R.array.nightlife);
            s2 = getResources().getStringArray(R.array.nightlife_description);
            images = new int[] {R.drawable.chania, R.drawable.ios, R.drawable.kerkura, R.drawable.kos, R.drawable.leukada, R.drawable.mukonos, R.drawable.paros, R.drawable.sifnos, R.drawable.skiathos, R.drawable.zakunthos};
        }
        else if (destinationTypeName.equals("both_activities")) {
            s1 = getResources().getStringArray(R.array.both_activities);
            s2 = getResources().getStringArray(R.array.both_activities_description);
            images = new int[] {R.drawable.chania, R.drawable.ios, R.drawable.kefalonia, R.drawable.kerkura, R.drawable.kos, R.drawable.leukada, R.drawable.mhlos, R.drawable.mukonos, R.drawable.paros, R.drawable.rodos, R.drawable.samos, R.drawable.santorini, R.drawable.sifnos, R.drawable.skiathos, R.drawable.tinos, R.drawable.zakunthos};
        }
        else if (destinationTypeName.equals("sea_only")) {
            s1 = getResources().getStringArray(R.array.sea_destinations);
            s2 = getResources().getStringArray(R.array.sea_destinations_description);
            images = new int[] {R.drawable.amorgos, R.drawable.chania, R.drawable.ikaria, R.drawable.ios, R.drawable.ithaki, R.drawable.kalumnos, R.drawable.kefalonia, R.drawable.kerkura, R.drawable.kos, R.drawable.koufonisia, R.drawable.kuthnos, R.drawable.leukada, R.drawable.mhlos, R.drawable.mukonos, R.drawable.naxos, R.drawable.paros, R.drawable.rodos, R.drawable.samos, R.drawable.santorini, R.drawable.serifos, R.drawable.sifnos, R.drawable.skiathos, R.drawable.skopelos, R.drawable.suros, R.drawable.tinos, R.drawable.zakunthos};
        }
        else if (destinationTypeName.equals("both_sea_mountain")) {
            s1 = getResources().getStringArray(R.array.both_sea_mountain);
            s2 = getResources().getStringArray(R.array.both_sea_mountain_description);
            images = new int[] {R.drawable.kalamata, R.drawable.pilio, R.drawable.xalkidiki, R.drawable.zaxarw_pelop};
        }

        s3 = getResources().getStringArray(R.array.random_description);

        //System.out.println(destinationTypeName);
        //Recycler view things
        MyAdapter myAdapter = new MyAdapter(this, s1, s2, images, s3);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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