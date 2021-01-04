package com.example.androidchatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity {

    EditText subjectText, messageText;
    Button sendMailMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        subjectText = findViewById(R.id.subjectText);
        messageText = findViewById(R.id.messageText);
        sendMailMessageButton = findViewById(R.id.sendMailMessageButton);


        sendMailMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto: teoglabes1@hotmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, subjectText.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, messageText.getText().toString());
                startActivity(intent);
            }
        });
    }
}