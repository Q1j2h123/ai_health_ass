package com.example.ai_health_ass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SET_GOAL = 1;

    private ListView workoutHistoryList;
    private List<String> workoutHistory;
    private ArrayAdapter<String> adapter;
    private TextView todayWorkoutText;
    private TextView todayWorkoutDetails;
    private Button startWorkoutButton;
    private Button setGoalButton;
    private Button textGuideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutHistoryList = findViewById(R.id.workout_history_list);
        todayWorkoutText = findViewById(R.id.today_workout_text);
        todayWorkoutDetails = findViewById(R.id.today_workout_details);
        startWorkoutButton = findViewById(R.id.start_workout_button);
        setGoalButton = findViewById(R.id.set_goal_button);
        textGuideButton = findViewById(R.id.text_guide_button);

        workoutHistory = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workoutHistory);
        workoutHistoryList.setAdapter(adapter);

        startWorkoutButton.setOnClickListener(v -> startWorkout());

        setGoalButton.setOnClickListener(v -> {
            Intent intent = new Intent(WorkoutActivity.this, SetGoalActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SET_GOAL);
        });

        textGuideButton.setOnClickListener(v -> showTextGuide());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> handleNavigationItemSelected(item));
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Intent intent = null;
        if (itemId == R.id.nav_discover) {
            intent = new Intent(WorkoutActivity.this, MainActivity.class);
        } else if (itemId == R.id.nav_exercise) {
            // 当前页面，无需跳转
            return true;
        } else if (itemId == R.id.nav_diet) {
            intent = new Intent(WorkoutActivity.this, DietManagementActivity.class);
        } else if (itemId == R.id.nav_mine) {
            intent = new Intent(WorkoutActivity.this, MainActivity.class);
        } else if (itemId == R.id.nav_health) {
            intent = new Intent(WorkoutActivity.this, weight_health_Activity.class);
        }

        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return false;
    }

    private void startWorkout() {
        String workout = todayWorkoutDetails.getText().toString();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        workoutHistory.add(workout + " - " + timestamp);
        adapter.notifyDataSetChanged();
    }

    private void showTextGuide() {
        String workoutDetails = todayWorkoutDetails.getText().toString();
        String guideContent = "每日锻炼计划: " + workoutDetails + "\n\n注意事项:\n1. 适当热身\n2. 充分补水\n3. 注意姿势正确\n4. 不要勉强自己";

        new AlertDialog.Builder(this)
                .setTitle("文字指南")
                .setMessage(guideContent)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SET_GOAL && resultCode == RESULT_OK && data != null) {
            String duration = data.getStringExtra("duration");
            String activity = data.getStringExtra("activity");
            todayWorkoutDetails.setText(duration + "分钟" + activity);
        }
    }
}
