package com.oop.philhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    int currentStep = 1;
    LinearLayout layoutStep1, layoutStep2, layoutStep3;
    View step1Indicator, step2Indicator, step3Indicator;
    TextView tvStepLabel, tvBack;
    Button btnContinue, btnSaveProgress;
    Spinner spinnerCivilStatus, spinnerCitizenship, spinnerMemberType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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
        spinnerCivilStatus = findViewById(R.id.spinnerCivilStatus);
        spinnerCitizenship = findViewById(R.id.spinnerCitizenship);
        spinnerMemberType = findViewById(R.id.spinnerMemberType);

        // Setup spinners
        ArrayAdapter<String> civilStatusAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select Civil Status", "Single", "Married", "Widowed", "Separated"});
        civilStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCivilStatus.setAdapter(civilStatusAdapter);

        ArrayAdapter<String> citizenshipAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select Citizenship", "Filipino", "Dual Citizen", "Foreign National"});
        citizenshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCitizenship.setAdapter(citizenshipAdapter);

        ArrayAdapter<String> memberTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Employed Private", "Employed Government", "Self-Employed", "Voluntary"});
        memberTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMemberType.setAdapter(memberTypeAdapter);

        tvBack.setOnClickListener(v -> onBackPressed());

        btnContinue.setOnClickListener(v -> {
            if (currentStep < 3) {
                currentStep++;
                updateStep();
            } else {
                // Go to confirmation
                Intent intent = new Intent(RegistrationActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });

        btnSaveProgress.setOnClickListener(v ->
                Toast.makeText(this, "Progress saved!", Toast.LENGTH_SHORT).show());
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

        String[] labels = {"Step 1 of 3: Personal Details", "Step 2 of 3: Dependent Declaration", "Step 3 of 3: Member Type"};
        tvStepLabel.setText(labels[currentStep - 1]);

        btnContinue.setText(currentStep == 3 ? "Submit Application Form" : "Continue");
    }
}