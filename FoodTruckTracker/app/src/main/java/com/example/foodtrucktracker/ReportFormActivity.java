package com.example.foodtrucktracker;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportFormActivity extends AppCompatActivity {

    EditText nameInput, typeInput, descInput, latInput, lngInput, reporterInput;
    Button submitBtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form); // ✅ Layout with header + back button

        // 🔗 Bind views
        nameInput = findViewById(R.id.truckName);
        typeInput = findViewById(R.id.truckType);
        descInput = findViewById(R.id.truckDesc);
        latInput = findViewById(R.id.truckLat);
        lngInput = findViewById(R.id.truckLng);
        reporterInput = findViewById(R.id.reportedBy);
        submitBtn = findViewById(R.id.submitBtn);


        db = FirebaseFirestore.getInstance();
        // ✅ Submit Button logic
        submitBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String type = typeInput.getText().toString().trim();
            String desc = descInput.getText().toString().trim();
            String latStr = latInput.getText().toString().trim();
            String lngStr = lngInput.getText().toString().trim();
            String reportedBy = reporterInput.getText().toString().trim();

            // 🚫 Validation
            if (name.isEmpty() || type.isEmpty() || desc.isEmpty()
                    || latStr.isEmpty() || lngStr.isEmpty() || reportedBy.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🌍 Parse coordinates
            double lat, lng;
            try {
                lat = Double.parseDouble(latStr);
                lng = Double.parseDouble(lngStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Latitude/Longitude must be valid numbers!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔥 Build data
            Map<String, Object> report = new HashMap<>();
            report.put("name", name);
            report.put("truckType", type);
            report.put("description", desc);
            report.put("latitude", lat);
            report.put("longitude", lng);
            report.put("reportedBy", reportedBy);
            report.put("reportedAt", new Timestamp(new Date()));

            // 📤 Upload to Firestore
            db.collection("foodtrucks").add(report)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(this, "✅ Report submitted!", Toast.LENGTH_SHORT).show();
                        finish(); // Close form after submission
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "❌ Failed to submit: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }
}
