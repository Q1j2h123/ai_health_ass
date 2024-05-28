package com.example.ai_health_ass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodDatabase.db";
    public static final String TABLE_BREAKFAST = "breakfast";
    public static final String TABLE_LUNCH = "lunch";
    public static final String TABLE_DINNER = "dinner";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建早餐表
        db.execSQL("CREATE TABLE " + TABLE_BREAKFAST + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_IMAGE + " INTEGER)");

        // 创建午餐表
        db.execSQL("CREATE TABLE " + TABLE_LUNCH + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_IMAGE + " INTEGER)");

        // 创建晚餐表
        db.execSQL("CREATE TABLE " + TABLE_DINNER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_IMAGE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除旧表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BREAKFAST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LUNCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DINNER);
        onCreate(db);
    }

    /**
     * 插入食物数据到指定表
     *
     * @param tableName 数据表名称
     * @param name      食物名称
     * @param quantity  食物数量
     * @param image     食物图片资源 ID
     * @return 插入操作是否成功
     */
    public boolean insertFood(String tableName, String name, int quantity, int image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_QUANTITY, quantity);
        contentValues.put(COLUMN_IMAGE, image);
        long result = db.insert(tableName, null, contentValues);
        return result != -1;
    }

    /**
     * 获取指定表中的所有食物数据
     *
     * @param tableName 数据表名称
     * @return 包含所有食物数据的 Cursor
     */
    public Cursor getFoodByTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName, null);
    }

    /**
     * 删除指定表中的特定食物
     *
     * @param tableName 数据表名称
     * @param name      要删除的食物名称
     */
    public void deleteFood(String tableName, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, COLUMN_NAME + " = ?", new String[]{name});
    }
}
