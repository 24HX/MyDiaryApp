package com.example.administrator.mydiaryapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDataBaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_DIARY = "create table Diary("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "date text, "
            + "title text, "
            + "content text,"
            + "path text,"+
            "backgroundPath text)";

    /**构造函数
     * @param context 上下文
     * @param name 数据库名
     * @param factory 游标工厂
     * @param version 版本
     */
    public DiaryDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 数据库创建
     * @param db 数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
    }

    /**更新数据库操作
     * @param db 数据库
     * @param oldVersion 旧版本
     * @param newVersion 新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Diary");
        onCreate(db);
    }
}
