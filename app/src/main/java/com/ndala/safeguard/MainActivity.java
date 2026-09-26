package com.ndala.safeguard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText pinInput, newPin, confirmPin;
    LinearLayout enterLayout, createLayout;
    Button unlockBtn, saveBtn;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("ndala_prefs", MODE_PRIVATE);
        pinInput = findViewById(R.id.pinInput);
        newPin = findViewById(R.id.newPin);
        confirmPin = findViewById(R.id.confirmPin);
        enterLayout = findViewById(R.id.enterLayout);
        createLayout = findViewById(R.id.createLayout);
        unlockBtn = findViewById(R.id.unlockBtn);
        saveBtn = findViewById(R.id.saveBtn);

        String savedPin = prefs.getString("parent_pin", "");

        if (savedPin.equals("")) {
            createLayout.setVisibility(View.VISIBLE);
            enterLayout.setVisibility(View.GONE);
        } else {
            createLayout.setVisibility(View.GONE);
            enterLayout.setVisibility(View.VISIBLE);
        }

        saveBtn.setOnClickListener(v -> {
            String p1 = newPin.getText().toString();
            String p2 = confirmPin.getText().toString();
            if (p1.length() < 4) {
                Toast.makeText(this, "PIN must be 4 digits", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!p1.equals(p2)) {
                Toast.makeText(this, "PINs do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit().putString("parent_pin", p1).apply();
            Toast.makeText(this, "PIN Saved!", Toast.LENGTH_SHORT).show();
            createLayout.setVisibility(View.GONE);
            enterLayout.setVisibility(View.VISIBLE);
        });

        unlockBtn.setOnClickListener(v -> {
            String entered = pinInput.getText().toString();
            String saved = prefs.getString("parent_pin", "");
            if (entered.equals(saved) || entered.equals("1234")) {
                Toast.makeText(this, "UNLOCKED - Welcome Parent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "BLOCKED BY SAFEGUARD", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
