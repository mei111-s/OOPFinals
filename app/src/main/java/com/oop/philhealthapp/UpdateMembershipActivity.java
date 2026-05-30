package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONObject;

public class UpdateMembershipActivity extends AppCompatActivity {

    int currentStep = 1;
    LinearLayout layoutStep1, layoutStep2, layoutStep3;
    View step1Indicator, step2Indicator, step3Indicator;
    TextView tvStepLabel, tvBack;
    Button btnContinue, btnSaveProgress, btnMale, btnFemale;
    Spinner spinnerCivilStatus, spinnerCitizenship, spinnerMemberType;
    EditText etKonsulta, etMemberName, etMotherName, etSpouseName, etDOB,
            etPlaceOfBirth, etPermanentAddress, etMailingAddress,
            etMobile, etEmail, etIncome, etProfession, etProfessionID;
    String pin, memberName, selectedSex = "";
    String BASE_URL = "http://10.0.2.2/philhealth/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_membership);

        pin = getIntent().getStringExtra("pin");
        memberName = getIntent().getStringExtra("memberName");

        layoutStep1 = findViewById(R.id.layoutStep1);
        layoutStep2 = findViewById(R.id.layoutStep2);
        layoutStep3 = findViewById(R.id.layoutStep3);
        step1Indicator = findViewById(R.id.step1Indicator);
        step2Indicator = findViewById(R.id.step2Indicator);
        step3Indicator = findViewById(R.id.step3Indicator);
        tvStepLabel = findViewById(R.id.tvStepLabel);
        tvBack = findViewById(R.id.tvBack);
        btnContinue = findViewById(R.id.btnContinue);
        btnSaveProgress = findViewById(R.id.btnSaveProgress);
        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);
        spinnerCivilStatus = findViewById(R.id.spinnerCivilStatus);
        spinnerCitizenship = findViewById(R.id.spinnerCitizenship);
        spinnerMemberType = findViewById(R.id.spinnerMemberType);
        etKonsulta = findViewById(R.id.etKonsulta);
        etMemberName = findViewById(R.id.etMemberName);
        etMotherName = findViewById(R.id.etMotherName);
        etSpouseName = findViewById(R.id.etSpouseName);
        etDOB = findViewById(R.id.etDOB);
        etPlaceOfBirth = findViewById(R.id.etPlaceOfBirth);
        etPermanentAddress = findViewById(R.id.etPermanentAddress);
        etMailingAddress = findViewById(R.id.etMailingAddress);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etIncome = findViewById(R.id.etIncome);
        etProfession = findViewById(R.id.etProfession);
        etProfessionID = findViewById(R.id.etProfessionID);

        // Spinners
        ArrayAdapter<String> civilAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Single", "Married", "Widowed", "Separated", "Legally Separated"});
        civilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCivilStatus.setAdapter(civilAdapter);

        ArrayAdapter<String> citizenAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Filipino", "Dual Citizen", "Foreign National"});
        citizenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCitizenship.setAdapter(citizenAdapter);

        ArrayAdapter<String> memberTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Employed Private", "Employed Government", "Self-Earning Individual", "Sole Proprietor", "Professional Practitioner"});
        memberTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMemberType.setAdapter(memberTypeAdapter);

        // Sex buttons
        btnFemale.setOnClickListener(v -> {
            selectedSex = "Female";
            btnFemale.setBackgroundTintList(getColorStateList(R.color.philhealth_green));
            btnFemale.setTextColor(getResources().getColor(R.color.white, null));
            btnMale.setBackgroundTintList(getColorStateList(R.color.light_gray));
            btnMale.setTextColor(getResources().getColor(R.color.black, null));
        });

        btnMale.setOnClickListener(v -> {
            selectedSex = "Male";
            btnMale.setBackgroundTintList(getColorStateList(R.color.philhealth_green));
            btnMale.setTextColor(getResources().getColor(R.color.white, null));
            btnFemale.setBackgroundTintList(getColorStateList(R.color.light_gray));
            btnFemale.setTextColor(getResources().getColor(R.color.black, null));
        });

        tvBack.setOnClickListener(v -> onBackPressed());

        // Pre-fill data from database
        fetchAndPrefill();

        btnContinue.setOnClickListener(v -> {
            if (currentStep < 3) {
                currentStep++;
                updateStep();
            } else {
                submitUpdate();
            }
        });

        btnSaveProgress.setOnClickListener(v ->
                Toast.makeText(this, "Progress saved!", Toast.LENGTH_SHORT).show());
    }

    void fetchAndPrefill() {
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
                            etKonsulta.setText(m.getString("KonSultaProvider"));
                            etMemberName.setText(m.getString("MemberName"));
                            etMotherName.setText(m.getString("MotherMaidenName"));
                            etSpouseName.setText(m.getString("SpouseName"));
                            etDOB.setText(m.getString("DateOfBirth"));
                            etPlaceOfBirth.setText(m.getString("PlaceOfBirth"));
                            etPermanentAddress.setText(m.getString("PermanentAddress"));
                            etMailingAddress.setText(m.getString("MailingAddress"));
                            etMobile.setText(m.getString("MobileNum"));
                            etEmail.setText(m.getString("EmailAddress"));
                            etIncome.setText(m.getString("MonthlyIncome"));
                            etProfession.setText(m.getString("Profession"));
                            etProfessionID.setText(m.getString("ProfessionID"));
                            selectedSex = m.getString("Sex");
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

    void submitUpdate() {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL + "update_member.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String postData = "pin=" + URLEncoder.encode(pin, "UTF-8")
                        + "&konsulta=" + URLEncoder.encode(etKonsulta.getText().toString(), "UTF-8")
                        + "&memberName=" + URLEncoder.encode(etMemberName.getText().toString(), "UTF-8")
                        + "&motherName=" + URLEncoder.encode(etMotherName.getText().toString(), "UTF-8")
                        + "&spouseName=" + URLEncoder.encode(etSpouseName.getText().toString(), "UTF-8")
                        + "&dob=" + URLEncoder.encode(etDOB.getText().toString(), "UTF-8")
                        + "&placeOfBirth=" + URLEncoder.encode(etPlaceOfBirth.getText().toString(), "UTF-8")
                        + "&sex=" + URLEncoder.encode(selectedSex, "UTF-8")
                        + "&civilStatus=" + URLEncoder.encode(spinnerCivilStatus.getSelectedItem().toString(), "UTF-8")
                        + "&citizenship=" + URLEncoder.encode(spinnerCitizenship.getSelectedItem().toString(), "UTF-8")
                        + "&permanentAddress=" + URLEncoder.encode(etPermanentAddress.getText().toString(), "UTF-8")
                        + "&mailingAddress=" + URLEncoder.encode(etMailingAddress.getText().toString(), "UTF-8")
                        + "&mobileNum=" + URLEncoder.encode(etMobile.getText().toString(), "UTF-8")
                        + "&email=" + URLEncoder.encode(etEmail.getText().toString(), "UTF-8")
                        + "&monthlyIncome=" + URLEncoder.encode(etIncome.getText().toString(), "UTF-8")
                        + "&profession=" + URLEncoder.encode(etProfession.getText().toString(), "UTF-8")
                        + "&professionID=" + URLEncoder.encode(etProfessionID.getText().toString(), "UTF-8");

                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) response.append(scanner.nextLine());
                scanner.close();

                JSONObject json = new JSONObject(response.toString());
                runOnUiThread(() -> {
                    if (json.optBoolean("success")) {
                        Intent intent = new Intent(UpdateMembershipActivity.this, UpdateSuccessActivity.class);
                        intent.putExtra("pin", pin);
                        intent.putExtra("memberName", memberName);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Update failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    void updateStep() {
        layoutStep1.setVisibility(currentStep == 1 ? View.VISIBLE : View.GONE);
        layoutStep2.setVisibility(currentStep == 2 ? View.VISIBLE : View.GONE);
        layoutStep3.setVisibility(currentStep == 3 ? View.VISIBLE : View.GONE);

        int green = getResources().getColor(R.color.philhealth_green, null);
        int gray = getResources().getColor(R.color.light_gray, null);
        step1Indicator.setBackgroundColor(currentStep >= 1 ? green : gray);
        step2Indicator.setBackgroundColor(currentStep >= 2 ? green : gray);
        step3Indicator.setBackgroundColor(currentStep >= 3 ? green : gray);

        String[] labels = {"Page 1 of 3: Personal Details", "Page 2 of 3: Declaration of Dependents", "Page 3 of 3: Member Type"};
        tvStepLabel.setText(labels[currentStep - 1]);
        btnContinue.setText(currentStep == 3 ? "Update Form" : "Continue");
    }
}