package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnNewMembership;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnNewMembership = findViewById(R.id.btnNewMembership);

        // Get member name passed from login
        String memberName = getIntent().getStringExtra("memberName");
        if (memberName != null) {
            tvWelcome.setText("Mabuhay " + memberName + "!");
        }

        btnNewMembership.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}