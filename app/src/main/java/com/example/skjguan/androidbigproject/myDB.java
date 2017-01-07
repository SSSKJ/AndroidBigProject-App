package com.example.skjguan.androidbigproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jeyo on 2016/11/21.
 */

public class myDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "my_sqlite_db.db";
    private static final String TABLE_NAME = "todolist";

    public myDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF not EXISTS " + TABLE_NAME + " (_id INTEGER PRIMARY KEY,title TEXT,content TEXT, createTime TEXT, deadline TEXT, remindingTime INTEGER, importanceLevel TEXT, isLike INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void deleteByTitle(String str) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "title='" + str + "'", null);
        db.close();
    }

    public void insert(String title, String content, String createTime, String deadline, int remindingTime, String importanceLevel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        cv.put("createTime", createTime);
        cv.put("deadline", deadline);
        cv.put("remindingTime", remindingTime);
        cv.put("importanceLevel", importanceLevel);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public Cursor getTable() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_NAME, new String[] { "_id", "title", "content", "createTime", "deadline", "remindingTime", "importanceLevel" }, null, null, null, null, null);
    }

    public void updateByName(String title, String content, String createTime, String deadline, int remindingTime, String importanceLevel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("content", content);
        cv.put("createTime", createTime);
        cv.put("deadline", deadline);
        cv.put("remindingTime", remindingTime);
        cv.put("importanceLevel", importanceLevel);
        db.update(TABLE_NAME, cv, "title='" + title + "'", null);
        db.close();
    }


}
