package com.example.ai_health_ass;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private TextView categoryText, recommendText, hotText;
    private ImageView mainImage, healthyDietImage;
    private Button learnMoreButton, startExerciseButton, detailsButton;

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
     //   healthyDietImage = findViewById(R.id.healthy_diet_image);
        learnMoreButton = findViewById(R.id.learn_more_button);
        startExerciseButton = findViewById(R.id.start_exercise_button);
     //   detailsButton = findViewById(R.id.details_button);

        // 设置按钮点击事件
        learnMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "了解更多", Toast.LENGTH_SHORT).show();
            }
        });

        startExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "开始锻炼", Toast.LENGTH_SHORT).show();
            }
        });

//        detailsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "详细", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
