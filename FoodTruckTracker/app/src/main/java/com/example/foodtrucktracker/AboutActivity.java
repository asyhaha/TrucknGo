package com.example.foodtrucktracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // ✅ GitHub clickable text
        TextView githubLink = findViewById(R.id.githubLink);
        githubLink.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://asyhaha.github.io/TrucknGo/")); // 🔁 Replace with your actual GitHub link
            startActivity(intent);
        });

        // ✅ Home button that goes back to homepage
        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // optional: finish AboutActivity
        });
    }
}
