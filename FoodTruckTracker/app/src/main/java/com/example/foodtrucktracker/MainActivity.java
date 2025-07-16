package com.example.foodtrucktracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.Timestamp;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // ✅ Report Button (opens ReportFormActivity)
        Button btnReport = findViewById(R.id.btnReport);
        btnReport.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportFormActivity.class);
            startActivity(intent);
        });

// ✅ Home button
        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // optional: close map activity so back button doesn't reopen it
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Center map on KL
        LatLng kl = new LatLng(3.139, 101.686);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kl, 12f));

        // Load food trucks from Firebase
        db.collection("foodtrucks").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    String name = doc.getString("name");
                    String truckType = doc.getString("truckType");
                    String reporter = doc.getString("reportedBy");
                    Timestamp timestamp = doc.getTimestamp("reportedAt");

                    Double lat = doc.getDouble("latitude");
                    Double lng = doc.getDouble("longitude");

                    if (name != null && lat != null && lng != null) {
                        LatLng position = new LatLng(lat, lng);
                        String snippet = "Truck Type: " + truckType + "\n"
                                + "Reported by: " + reporter + "\n"
                                + "Reported at: " + (timestamp != null ? timestamp.toDate().toString() : "Unknown");

                        mMap.addMarker(new MarkerOptions()
                                .position(position)
                                .title(name)
                                .snippet(snippet));
                    }
                }
            } else {
                task.getException().printStackTrace();
            }
        });
    }
}
