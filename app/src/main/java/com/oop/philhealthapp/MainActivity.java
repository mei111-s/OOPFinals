package com.oop.philhealthapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    EditText etPin, etPassword;
    Button btnLogin;
    TextView tvForgotPassword, tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPin = findViewById(R.id.etPin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnLogin.setOnClickListener(v -> {
            String pin = etPin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (pin.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your PIN and password", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}