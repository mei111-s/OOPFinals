package com.oop.philhealthapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ViewMembershipActivity extends AppCompatActivity {

    TextView tvPinDisplay, tvNameDisplay, tvKonsulta, tvMemberName, tvMotherName,
            tvDOB, tvPlaceOfBirth, tvSex, tvCivilStatus, tvCitizenship,
            tvMobile, tvEmail, tvAddress, tvIncome, tvProfession;
    String pin, memberName;
    String BASE_URL = "http://10.0.2.2/philhealth/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_membership);

        pin = getIntent().getStringExtra("pin");
        memberName = getIntent().getStringExtra("memberName");

        tvPinDisplay = findViewById(R.id.tvPinDisplay);
        tvNameDisplay = findViewById(R.id.tvNameDisplay);
        tvKonsulta = findViewById(R.id.tvKonsulta);
        tvMemberName = findViewById(R.id.tvMemberName);
        tvMotherName = findViewById(R.id.tvMotherName);
        tvDOB = findViewById(R.id.tvDOB);
        tvPlaceOfBirth = findViewById(R.id.tvPlaceOfBirth);
        tvSex = findViewById(R.id.tvSex);
        tvCivilStatus = findViewById(R.id.tvCivilStatus);
        tvCitizenship = findViewById(R.id.tvCitizenship);
        tvMobile = findViewById(R.id.tvMobile);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        tvIncome = findViewById(R.id.tvIncome);
        tvProfession = findViewById(R.id.tvProfession);

        tvPinDisplay.setText(pin);
        tvNameDisplay.setText(memberName);

        findViewById(R.id.tvBack).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.btnDownload).setOnClickListener(v ->
                Toast.makeText(this, "Download feature coming soon!", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnPrint).setOnClickListener(v ->
                Toast.makeText(this, "Print feature coming soon!", Toast.LENGTH_SHORT).show());

        fetchMemberData();
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
                    JSONObject m = json.getJSONObject("member");
                    runOnUiThread(() -> {
                        try {
                            tvKonsulta.setText(m.getString("KonSultaProvider"));
                            tvMemberName.setText(m.getString("MemberName"));
                            tvMotherName.setText(m.getString("MotherMaidenName"));
                            tvDOB.setText(m.getString("DateOfBirth"));
                            tvPlaceOfBirth.setText(m.getString("PlaceOfBirth"));
                            tvSex.setText(m.getString("Sex"));
                            tvCivilStatus.setText(m.getString("CivilStatus"));
                            tvCitizenship.setText(m.getString("Citizenship"));
                            tvMobile.setText(m.getString("MobileNum"));
                            tvEmail.setText(m.getString("EmailAddress"));
                            tvAddress.setText(m.getString("PermanentAddress"));
                            tvIncome.setText(m.getString("MonthlyIncome"));
                            tvProfession.setText(m.getString("Profession"));
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