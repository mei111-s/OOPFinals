package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_success);

        String pin = getIntent().getStringExtra("pin");
        String memberName = getIntent().getStringExtra("memberName");

        Button btnBackToDashboard = findViewById(R.id.btnBackToDashboard);
        btnBackToDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateSuccessActivity.this, DashboardActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}