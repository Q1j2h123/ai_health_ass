package com.example.ai_health_ass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class weight_health_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_health);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> handleNavigationItemSelected(item));
    }

    private boolean handleNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent = null;
        if (itemId == R.id.nav_discover) {
            intent = new Intent(weight_health_Activity.this, MainActivity.class);
        } else if (itemId == R.id.nav_exercise) {
            intent = new Intent(weight_health_Activity.this, WorkoutActivity.class);
        } else if (itemId == R.id.nav_diet) {
            intent = new Intent(weight_health_Activity.this, DietManagementActivity.class);
        } else if (itemId == R.id.nav_mine) {
            intent = new Intent(weight_health_Activity.this, MainActivity.class);
        } else if (itemId == R.id.nav_health) {
            // 当前页面，无需跳转
            return true;
        }

        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return false;
    }
}
