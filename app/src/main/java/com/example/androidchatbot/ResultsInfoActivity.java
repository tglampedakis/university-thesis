package com.example.androidchatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsInfoActivity extends AppCompatActivity {

    ImageView mainImageView;
    TextView titleText, descText;
    String data1, data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_info);

        mainImageView = findViewById(R.id.mainImageView);
        titleText = findViewById(R.id.titleText);
        descText = findViewById(R.id.descrText);

        getData();
        setData();
    }


    private void getData() {
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")) {
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage", 1);
        }
        else Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();

    }

    private void setData() {
        titleText.setText(data1);
        descText.setText(data2);
        mainImageView.setImageResource(myImage);
    }
}