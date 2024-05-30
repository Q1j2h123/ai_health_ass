package com.example.ai_health_ass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MineActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        // 初始化 BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_discover) {
                    // 发现页面跳转
                    Intent intent = new Intent(MineActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_health) {
                    // 健康页面跳转
                    Intent intent = new Intent(MineActivity.this, weight_health_Activity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_diet) {
                    // 跳转到饮食管理页面
                    Intent intent = new Intent(MineActivity.this, DietManagementActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_exercise) {
                    // 锻炼页面跳转
                    Intent intent = new Intent(MineActivity.this, WorkoutActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_mine) {
                    // 当前页面
                    return true;
                }
                return false;
            }
        });
        // 初始化视图
        usernameTextView = findViewById(R.id.username_text_view);

        // 初始化数据库助手
        databaseHelper = new DatabaseHelper(this);

        // 获取当前登录的用户名
        String loggedInUsername = databaseHelper.getLoggedInUsername();

        // 设置用户名
        if (loggedInUsername != null) {
            usernameTextView.setText(loggedInUsername);
        } else {
            usernameTextView.setText("未登录");
        }
    }
}
