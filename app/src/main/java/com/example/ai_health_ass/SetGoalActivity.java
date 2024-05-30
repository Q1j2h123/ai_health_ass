package com.example.ai_health_ass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetGoalActivity extends AppCompatActivity {

    private NumberPicker goalDurationPicker;
    private Spinner goalActivitySpinner;
    private Button saveGoalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        goalDurationPicker = findViewById(R.id.goal_duration_picker);
        goalDurationPicker.setMinValue(1);
        goalDurationPicker.setMaxValue(120);
        goalDurationPicker.setValue(30);
        goalDurationPicker.setWrapSelectorWheel(true);

        goalActivitySpinner = findViewById(R.id.goal_activity_spinner);
        saveGoalButton = findViewById(R.id.save_goal_button);

        saveGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });
    }

    private void saveGoal() {
        int duration = goalDurationPicker.getValue();
        String activity = goalActivitySpinner.getSelectedItem().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("duration", String.valueOf(duration));
        resultIntent.putExtra("activity", activity);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}