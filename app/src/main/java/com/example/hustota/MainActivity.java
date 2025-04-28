// MainActivity.java
package com.example.hustota;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText massInput, volumeInput, densityInput;
    private Button calculateButton, switchButton;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Edge-to-edge support
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Top bar (Toolbar)
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // Apply system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI elements
        massInput = findViewById(R.id.input_mass);
        volumeInput = findViewById(R.id.input_volume);
        densityInput = findViewById(R.id.input_density);
        calculateButton = findViewById(R.id.button_calculate);
        switchButton = findViewById(R.id.button_switch);
        resultView = findViewById(R.id.text_result);

        calculateButton.setOnClickListener(v -> {
            String m = massInput.getText().toString().trim();
            String vStr = volumeInput.getText().toString().trim();
            String d = densityInput.getText().toString().trim();

            int emptyCount = 0;
            if (m.isEmpty()) emptyCount++;
            if (vStr.isEmpty()) emptyCount++;
            if (d.isEmpty()) emptyCount++;

            if (emptyCount != 1) {
                Toast.makeText(this, "Vyplňte právě dvě hodnoty!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double mass, volume, density;
                if (m.isEmpty()) {
                    // compute mass
                    volume = Double.parseDouble(vStr);
                    density = Double.parseDouble(d);
                    mass = density * volume;
                    massInput.setText(String.format("%.2f", mass));
                    resultView.setText("Vypočtená hmotnost: " + String.format("%.2f kg", mass));
                } else if (vStr.isEmpty()) {
                    // compute volume
                    mass = Double.parseDouble(m);
                    density = Double.parseDouble(d);
                    if (density == 0) {
                        Toast.makeText(this, "Hustota nesmí být nula.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    volume = mass / density;
                    volumeInput.setText(String.format("%.2f", volume));
                    resultView.setText("Vypočtený objem: " + String.format("%.2f m³", volume));
                } else {
                    // compute density
                    mass = Double.parseDouble(m);
                    volume = Double.parseDouble(vStr);
                    if (volume == 0) {
                        Toast.makeText(this, "Objem nesmí být nula.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    density = mass / volume;
                    densityInput.setText(String.format("%.2f", density));
                    resultView.setText("Vypočtená hustota: " + String.format("%.2f kg/m³", density));
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Neplatný formát čísel!", Toast.LENGTH_SHORT).show();
            }
        });

        // Switch to second activity
        switchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }
}