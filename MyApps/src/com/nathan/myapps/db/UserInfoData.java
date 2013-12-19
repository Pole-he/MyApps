package com.nathan.myapps.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserInfoData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PoPoUserInfo.db";
    private static final int DATABASE_VERSION = 1;
    private String TAVLE_NAME = "popo_userloginfo";

    public UserInfoData(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE popo_userloginfo (user_id Integer primary key, user_name VARCHAR(20) , user_pic VARCHAR(20) , user_access VARCHAR(20))";

        db.execSQL(sql);// 需要异常捕获

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        String sql = "drop table " + TAVLE_NAME;
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
        contentValues.put("user_name", user_name);
        contentValues.put("user_pic", user_pic);
        contentValues.put("user_access", user_access);
        return db.insert(TAVLE_NAME, null, contentValues);
    }

    /**
     * 删除记录
     * 
     * @param _id
     */
    public void delete(String _id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TAVLE_NAME, "_id=?", new String[] { _id });
    }

    /**
     * 更新记录的，跟插入的很像
     */
    public void update(String _id, String tname, int tage, String ttel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tname", tname);
        contentValues.put("tage", tage);
        contentValues.put("ttel", ttel);
        db.update(TAVLE_NAME, contentValues, "_id=?", new String[] { _id });
    }
}
