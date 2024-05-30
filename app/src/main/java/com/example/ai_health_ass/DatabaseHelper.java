package com.example.ai_health_ass;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodDatabase.db";
    public static final String TABLE_USER = "user";
    public static final String TABLE_BREAKFAST = "breakfast";
    public static final String TABLE_LUNCH = "lunch";
    public static final String TABLE_DINNER = "dinner";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    private static final String PREF_NAME = "LoginPref";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final String LOGGED_IN_USERNAME = "LoggedInUsername";
    private SharedPreferences sharedPreferences;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 创建用户表
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT)");

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
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

    /**
     * 插入新用户数据
     *
     * @param username 用户名
     * @param password 密码
     * @return 插入操作是否成功
     */
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, contentValues);
        return result != -1;
    }
    /**
     * 设置登录状态
     */
    public void setLoggedIn(boolean loggedIn, String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.putString(LOGGED_IN_USERNAME, username);
        editor.apply();
    }
    /**
     * 验证用户登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 验证是否成功
     */
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
    /**
     * 检查用户是否已登录
     */
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    /**
     * 获取已登录用户名
     */
    public String getLoggedInUsername() {
        return sharedPreferences.getString(LOGGED_IN_USERNAME, null);
    }

    /**
     * 注销用户
     */ public void logout() {
        setLoggedIn(false, null);
    }
}
