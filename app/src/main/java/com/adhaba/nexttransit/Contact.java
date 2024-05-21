package com.adhaba.nexttransit;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact extends AppCompatActivity {
    private EditText subjectEditText;
    private EditText messageEditText;
    private Button sendButton;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("messages");

        // Initialize views
        subjectEditText = findViewById(R.id.subjectEditText);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_settings);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), About.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_settings:
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }
    private void sendMessage() {
        // Get subject and message text
        String subject = subjectEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        // Check if subject and message are not empty
        if (!subject.isEmpty() && !message.isEmpty()) {
            // Push data to Firebase
            DatabaseReference newMessageRef = databaseReference.push();
            newMessageRef.child("subject").setValue(subject);
            newMessageRef.child("message").setValue(message);

            // Clear EditText fields
            subjectEditText.setText("");
            messageEditText.setText("");

            // Notify user that message has been sent (optional)
            // You can use Toast or Snackbar to show a message to the user
        } else {
            // Notify user that subject or message is empty (optional)
            // You can use Toast or Snackbar to show a message to the user
        }
    }
}