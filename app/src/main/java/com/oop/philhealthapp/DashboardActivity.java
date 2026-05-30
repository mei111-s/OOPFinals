package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnNewMembership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnNewMembership = findViewById(R.id.btnNewMembership);

        btnNewMembership.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}