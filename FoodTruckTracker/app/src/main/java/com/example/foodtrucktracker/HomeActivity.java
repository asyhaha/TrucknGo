package com.example.foodtrucktracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    CardView cardReport, cardMap, cardAbout, cardAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ✅ Match status bar to mocha header color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT); // Make status bar transparent
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        // Bind cards
        cardReport = findViewById(R.id.cardReport);
        cardMap = findViewById(R.id.cardMap);
        cardAbout = findViewById(R.id.cardAbout);
        cardAdmin = findViewById(R.id.cardAdmin);

        // Report card click
        cardReport.setOnClickListener(view -> {
            animateCard(view);
            startActivity(new Intent(HomeActivity.this, ReportFormActivity.class));
        });

        // Map card click
        cardMap.setOnClickListener(view -> {
            animateCard(view);
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });

        // About card click
        cardAbout.setOnClickListener(view -> {
            animateCard(view);
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });

        /// Admin card click
        cardAdmin.setOnClickListener(view -> {
            animateCard(view);

            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(android.net.Uri.parse("https://asyhaha.github.io/TrucknGoAdmin/"));
            startActivity(browserIntent);
        });

    }

    // 🔁 Animation method for smooth press effect
    private void animateCard(View card) {
        card.animate()
                .scaleX(0.97f)
                .scaleY(0.97f)
                .setDuration(100)
                .withEndAction(() -> card.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start())
                .start();
    }
}
