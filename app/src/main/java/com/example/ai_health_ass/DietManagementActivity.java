package com.example.ai_health_ass;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class DietManagementActivity extends AppCompatActivity {

    // 声明视图和变量
    private LinearLayout breakfastList;
    private LinearLayout lunchList;
    private LinearLayout dinnerList;
    private HashMap<String, Integer> foodCountMap;
    private DatabaseHelper databaseHelper;
    private ImageView breakfastImage;
    private ImageView lunchImage;
    private ImageView dinnerImage;
    private TextView breakfastText;
    private TextView lunchText;
    private TextView dinnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_management);

        // 初始化视图
        breakfastList = findViewById(R.id.breakfast_list);
        lunchList = findViewById(R.id.lunch_list);
        dinnerList = findViewById(R.id.dinner_list);

        breakfastImage = findViewById(R.id.breakfast_image);
        lunchImage = findViewById(R.id.lunch_image);
        dinnerImage = findViewById(R.id.dinner_image);

        breakfastText = findViewById(R.id.breakfast_text);
        lunchText = findViewById(R.id.lunch_text);
        dinnerText = findViewById(R.id.dinner_text);

        // 初始化 HashMap 和数据库助手
        foodCountMap = new HashMap<>();
        databaseHelper = new DatabaseHelper(this);

        // 添加示例数据到数据库
        addSampleData();

        // 加载推荐菜谱
        loadRecommendedRecipes();

        // 设置按钮点击事件
        Button addBreakfastButton = findViewById(R.id.add_breakfast_button);
        Button addLunchButton = findViewById(R.id.add_lunch_button);
        Button addDinnerButton = findViewById(R.id.add_dinner_button);

        // 点击添加早餐按钮时调用的方法
        addBreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToCategory(breakfastList, DatabaseHelper.TABLE_BREAKFAST);
            }
        });

        // 点击添加午餐按钮时调用的方法
        addLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToCategory(lunchList, DatabaseHelper.TABLE_LUNCH);
            }
        });

        // 点击添加晚餐按钮时调用的方法
        addDinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToCategory(dinnerList, DatabaseHelper.TABLE_DINNER);
            }
        });
    }

    /**
     * 添加示例数据到数据库
     */
    private void addSampleData() {
        databaseHelper.insertFood(DatabaseHelper.TABLE_BREAKFAST, "鸡蛋", 1, R.drawable.egg);
        databaseHelper.insertFood(DatabaseHelper.TABLE_BREAKFAST, "牛奶", 1, R.drawable.milk);
        databaseHelper.insertFood(DatabaseHelper.TABLE_LUNCH, "鸡肉", 1, R.drawable.chicken);
        databaseHelper.insertFood(DatabaseHelper.TABLE_LUNCH, "米饭", 1, R.drawable.rice);
        databaseHelper.insertFood(DatabaseHelper.TABLE_DINNER, "鱼", 1, R.drawable.fish);
        databaseHelper.insertFood(DatabaseHelper.TABLE_DINNER, "蔬菜", 1, R.drawable.vegetables);
    }

    /**
     * 加载推荐菜谱
     */
    private void loadRecommendedRecipes() {
        loadRecommendedRecipe(DatabaseHelper.TABLE_BREAKFAST, breakfastImage, breakfastText);
        loadRecommendedRecipe(DatabaseHelper.TABLE_LUNCH, lunchImage, lunchText);
        loadRecommendedRecipe(DatabaseHelper.TABLE_DINNER, dinnerImage, dinnerText);
    }

    /**
     * 加载推荐的食谱项
     *
     * @param tableName 数据表名称
     * @param imageView 显示食物图片的 ImageView
     * @param textView  显示推荐文字的 TextView
     */
    private void loadRecommendedRecipe(String tableName, ImageView imageView, TextView textView) {
        Cursor cursor = databaseHelper.getFoodByTable(tableName);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int imageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE);

            // 检查列索引是否有效
            if (nameIndex >= 0 && imageIndex >= 0) {
                String foodName = cursor.getString(nameIndex);
                int foodImage = cursor.getInt(imageIndex);
                textView.setText("推荐: " + foodName);
                imageView.setImageResource(foodImage);
            } else {
                Toast.makeText(this, "列索引无效", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 添加食物到指定类别
     *
     * @param categoryList 食物列表的 LinearLayout
     * @param tableName    数据表名称
     */
    private void addFoodToCategory(LinearLayout categoryList, String tableName) {
        Cursor cursor = databaseHelper.getFoodByTable(tableName);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int imageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE);

            // 检查列索引是否有效
            if (nameIndex >= 0 && imageIndex >= 0) {
                String foodName = cursor.getString(nameIndex);
                int foodImage = cursor.getInt(imageIndex);
                int foodCount = foodCountMap.getOrDefault(foodName, 0);

                if (foodCount < 2) {
                    foodCountMap.put(foodName, foodCount + 1);
                    addFoodItemToList(categoryList, foodName, foodCount + 1, foodImage);

                    // 删除已添加的食物并加载下一个推荐食物
                    databaseHelper.deleteFood(tableName, foodName);
                    loadRecommendedRecipe(tableName, getImageViewByTableName(tableName), getTextViewByTableName(tableName));
                } else {
                    Toast.makeText(this, foodName + " 已经添加到最大数量", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "列索引无效", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 添加食物项到列表
     *
     * @param categoryList 食物列表的 LinearLayout
     * @param foodName     食物名称
     * @param quantity     食物数量
     * @param foodImage    食物图片资源 ID
     */
    private void addFoodItemToList(LinearLayout categoryList, String foodName, int quantity, int foodImage) {
        View foodItemView = getLayoutInflater().inflate(R.layout.food_item, null);

        ImageView foodImageView = foodItemView.findViewById(R.id.food_image);
        TextView foodNameTextView = foodItemView.findViewById(R.id.food_name);
        TextView foodQuantityTextView = foodItemView.findViewById(R.id.food_quantity);

        foodImageView.setImageResource(foodImage);
        foodNameTextView.setText(foodName);
        foodQuantityTextView.setText("数量: " + quantity);

        categoryList.addView(foodItemView);
    }

    /**
     * 根据表名获取对应的 ImageView
     *
     * @param tableName 数据表名称
     * @return 对应的 ImageView
     */
    private ImageView getImageViewByTableName(String tableName) {
        switch (tableName) {
            case DatabaseHelper.TABLE_BREAKFAST:
                return breakfastImage;
            case DatabaseHelper.TABLE_LUNCH:
                return lunchImage;
            case DatabaseHelper.TABLE_DINNER:
                return dinnerImage;
            default:
                return null;
        }
    }

    /**
     * 根据表名获取对应的 TextView
     *
     * @param tableName 数据表名称
     * @return 对应的 TextView
     */
    private TextView getTextViewByTableName(String tableName) {
        switch (tableName) {
            case DatabaseHelper.TABLE_BREAKFAST:
                return breakfastText;
            case DatabaseHelper.TABLE_LUNCH:
                return lunchText;
            case DatabaseHelper.TABLE_DINNER:
                return dinnerText;
            default:
                return null;
        }
    }
}
