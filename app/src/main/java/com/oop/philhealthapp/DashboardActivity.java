package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnNewMembership, btnUpdateMembership, btnViewMembership;
    Button btnOnlinePayment, btnViewMDR;
    TextView tvWelcome;
    String pin, memberName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnNewMembership = findViewById(R.id.btnNewMembership);
        btnUpdateMembership = findViewById(R.id.btnUpdateMembership);
        btnViewMembership = findViewById(R.id.btnViewMembership);
        btnOnlinePayment = findViewById(R.id.btnOnlinePayment);
        btnViewMDR = findViewById(R.id.btnViewMDR);

        pin = getIntent().getStringExtra("pin");
        memberName = getIntent().getStringExtra("memberName");

        if (memberName != null) {
            tvWelcome.setText("Mabuhay " + memberName + "!");
        }

        // New Membership Registration
        btnNewMembership.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, RegistrationActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            startActivity(intent);
        });

        // Update Membership
        btnUpdateMembership.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, UpdateMembershipActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            startActivity(intent);
        });

        // View Membership Record
        btnViewMembership.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ViewMembershipActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            startActivity(intent);
        });

        // Online Payment placeholder
        btnOnlinePayment.setOnClickListener(v ->
                Toast.makeText(this, "Online Payment coming soon!", Toast.LENGTH_SHORT).show());

        // View or Print MDR
        btnViewMDR.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ViewMembershipActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            startActivity(intent);
        });

        // Bottom nav
        findViewById(R.id.navProfile).setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            startActivity(intent);
        });
    }
}