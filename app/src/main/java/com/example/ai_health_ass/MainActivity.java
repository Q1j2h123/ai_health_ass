package com.example.ai_health_ass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Button learnMoreButton;
    private Button start_exercise_button;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper=new DatabaseHelper(this);

        // 初始化视图
        learnMoreButton = findViewById(R.id.learn_more_button);
        start_exercise_button = findViewById(R.id.start_exercise_button);

        // 设置了解更多按钮的点击事件
        learnMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到饮食管理页面
                Intent intent = new Intent(MainActivity.this, DietManagementActivity.class);
                startActivity(intent);
            }
        });
        start_exercise_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到饮食管理页面
                Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
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
                    // 发现页面跳转
                    // 这里可以实现跳转到发现页面的逻辑
                    return true;
                } else if (itemId == R.id.nav_health) {
                    // 健康页面跳转
                    Intent intent = new Intent(MainActivity.this, weight_health_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_diet) {
                    // 跳转到饮食管理页面
                    Intent intent = new Intent(MainActivity.this, DietManagementActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_exercise) {
                    // 锻炼页面跳转
                    Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_mine) {
                    // 我的页面跳转
                    if (databaseHelper.isLoggedIn()) {
                        // User is logged in, navigate to MineActivity
                        Intent intent = new Intent(MainActivity.this, MineActivity.class);
                        startActivity(intent);
                    } else {
                        // User is not logged in, navigate to LoginActivity
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
