package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText etPin, etPassword;
    Button btnLogin;
    String BASE_URL = "http://10.0.2.2/philhealth/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPin = findViewById(R.id.etPin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String pin = etPin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (pin.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your PIN and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Run network request on background thread
            new Thread(() -> {
                try {
                    URL url = new URL(BASE_URL + "login.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    String postData = "pin=" + pin;
                    OutputStream os = conn.getOutputStream();
                    os.write(postData.getBytes());
                    os.flush();
                    os.close();

                    Scanner scanner = new Scanner(conn.getInputStream());
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) response.append(scanner.nextLine());
                    scanner.close();

                    String result = response.toString();

                    runOnUiThread(() -> {
                        if (result.contains("\"success\":true")) {
                            // Extract member name from JSON
                            String memberName = result.split("\"memberName\":\"")[1].split("\"")[0];
                            String memberPin = result.split("\"pin\":\"")[1].split("\"")[0];

                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            intent.putExtra("memberName", memberName);
                            intent.putExtra("pin", memberPin);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Invalid PIN. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Connection error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }).start();
        });
    }
}