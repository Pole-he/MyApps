package com.nathan.myapps.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserInfoData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PoPoUserInfo.db";
    private static final int DATABASE_VERSION = 1;
    private String TAVLE_NAME = "popo_userloginfo";
    private String USER_ID = "user_id";
    private String USER_NAME = "user_name";
    private String USER_PIC = "user_pic";
    private String USER_ACCESS = "user_access";

    public UserInfoData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // String sql =
        // "CREATE TABLE popo_userloginfo (user_id Integer primary key, user_name VARCHAR(20) , user_pic VARCHAR(20) , user_access VARCHAR(20))";
        String sq = "CREATE TABLE " + TAVLE_NAME + " (" + USER_ID
                + " Integer primary key," + USER_NAME + " text," + USER_PIC + " text,"
                + USER_ACCESS + " text)";
        db.execSQL(sq);// 需要异常捕获

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        String sql = "drop table if exists " + TAVLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 添加数据
     */
    public long insert(String user_name, String user_pic, String user_access) {
        SQLiteDatabase db = getWritableDatabase();// 获取可写SQLiteDatabase对象
        // ContentValues类似map，存入的是键值对
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user_name);
        contentValues.put(USER_PIC, user_pic);
        contentValues.put(USER_ACCESS, user_access);
        return db.insert(TAVLE_NAME, null, contentValues);
    }

    /**
     * 删除记录
     * 
     * @param _id
     */
    public void delete(String _id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TAVLE_NAME, USER_ID+"=?", new String[] { _id });
        
    }

    public List<String> getUserInfo() {
        SQLiteDatabase db = getWritableDatabase();
        List<String> userInfo = new ArrayList<String>();
        Cursor cursor = db.query(TAVLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                userInfo.add(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)));
                userInfo.add(cursor.getString(cursor.getColumnIndexOrThrow(USER_PIC)));
                userInfo.add(cursor.getString(cursor.getColumnIndexOrThrow(USER_ACCESS)));
            }
            while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
        db.close();
        return userInfo;
    }

    /**
     * 更新记录的
     */
    public void update(String _id, String user_name, String user_pic, String user_access) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user_name);
        contentValues.put(USER_PIC, user_pic);
        contentValues.put(USER_ACCESS, user_access);
        db.update(TAVLE_NAME, contentValues, USER_ID+"=?", new String[] { _id });
    }
}
