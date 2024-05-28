package com.example.ai_health_ass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private TextView categoryText, recommendText, hotText;
    private ImageView mainImage, healthyDietImage;
    private Button learnMoreButton, startExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        searchEditText = findViewById(R.id.search_edit_text);
        categoryText = findViewById(R.id.category_text);
        recommendText = findViewById(R.id.recommend_text);
        hotText = findViewById(R.id.hot_text);
        mainImage = findViewById(R.id.main_image);

        learnMoreButton = findViewById(R.id.learn_more_button);
        startExerciseButton = findViewById(R.id.start_exercise_button);

        // 设置了解更多按钮点击事件
        learnMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DietManagementActivity.class);
                startActivity(intent);
            }
        });

        // 初始化 BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_discover) {
                    Toast.makeText(MainActivity.this, "发现", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_health) {
                    // 跳转到饮食页面
                    Intent intent = new Intent(MainActivity.this, DietActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_exercise) {
                    // 跳转到锻炼页面
                    Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_mine) {
                    Toast.makeText(MainActivity.this, "我的", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}
