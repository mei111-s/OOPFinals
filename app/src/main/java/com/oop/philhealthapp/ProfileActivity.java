package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TextView tvProfileName, tvProfileEmail, tvProfileLocation;
    TextView tvDOB, tvCitizenship, tvPhone, tvAddress, tvCivilStatus;
    Button btnUpdateMembership;
    String pin, memberName;
    String BASE_URL = "http://10.0.2.2/philhealth/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pin = getIntent().getStringExtra("pin");
        memberName = getIntent().getStringExtra("memberName");

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileLocation = findViewById(R.id.tvProfileLocation);
        tvDOB = findViewById(R.id.tvDOB);
        tvCitizenship = findViewById(R.id.tvCitizenship);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvCivilStatus = findViewById(R.id.tvCivilStatus);
        btnUpdateMembership = findViewById(R.id.btnUpdateMembership);

        if (memberName != null) tvProfileName.setText(memberName);

        fetchMemberData();

        btnUpdateMembership.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateMembershipActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            startActivity(intent);
        });

        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
            intent.putExtra("pin", pin);
            intent.putExtra("memberName", memberName);
            startActivity(intent);
        });
    }

    void fetchMemberData() {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL + "get_member.php");
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

                JSONObject json = new JSONObject(response.toString());
                if (json.getBoolean("success")) {
                    JSONObject member = json.getJSONObject("member");

                    runOnUiThread(() -> {
                        try {
                            tvProfileName.setText(member.getString("MemberName"));
                            tvProfileEmail.setText(member.getString("EmailAddress"));
                            tvProfileLocation.setText(member.getString("PlaceOfBirth"));
                            tvDOB.setText(member.getString("DateOfBirth"));
                            tvCitizenship.setText(member.getString("Citizenship"));
                            tvPhone.setText(member.getString("MobileNum"));
                            tvAddress.setText(member.getString("PermanentAddress"));
                            tvCivilStatus.setText(member.getString("CivilStatus"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}