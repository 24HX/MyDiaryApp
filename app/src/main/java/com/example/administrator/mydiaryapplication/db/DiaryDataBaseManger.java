package com.example.administrator.mydiaryapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.mydiaryapplication.bean.DiaryBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作工具类
 */
public class DiaryDataBaseManger {
    //数据库名称
    private static String DB_NAME = "Diary.db";
    //数据库版本
    private static int DB_VERSION = 7;
    private SQLiteDatabase db;
    private DiaryDataBaseHelper dbHelper;
    private  static  DiaryDataBaseManger instance =null;

    /**
     * 构造函数
     * @param context
     */
    public DiaryDataBaseManger(Context context) {
        dbHelper = new DiaryDataBaseHelper(context,DB_NAME,null,DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取本类对象实例
     * @param context
     * @return
     */
    public static final DiaryDataBaseManger getInstance(Context context)
    {
        if (instance == null)
        {
            if(context == null)
            {
                throw new RuntimeException("上下文为空");
            }
            instance = new DiaryDataBaseManger(context);
        }
        return instance;
    }

    /**
     * 关闭数据库
     */
    public void close()
    {
        db.close();
        dbHelper.close();
    }

    /**
     * 获取表中所有记录
     * @return
     */
    public List<DiaryBean> getDiaryBeanList() {
        List<DiaryBean> diaryBeans = new ArrayList<>();
        List<DiaryBean> mDiaryBeanList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Diary",null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                DiaryBean diary = new DiaryBean();
                diary.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
                diary.setDate(cursor.getString(cursor.getColumnIndex("date")));
                diary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                diary.setContent(cursor.getString(cursor.getColumnIndex("content")));
                diary.setPath(cursor.getString(cursor.getColumnIndex("path")));
                diary.setBackgroundPath(cursor.getString(cursor.getColumnIndex("backgroundPath")));
                String tag = "主键id";
                Log.d(tag,"id"+diary.get_id());
                Log.d(tag,"名字："+diary.getTitle());
                mDiaryBeanList.add(diary);
            }while (cursor.moveToNext());
        }
        cursor.close();
        //排序
        for (int i = mDiaryBeanList.size() -1; i>=0; i--)
        {
            diaryBeans.add(mDiaryBeanList.get(i));
        }
        mDiaryBeanList = diaryBeans;
        return mDiaryBeanList;
    }

    /**
     * 添加一条日记
     * @param diary
     * @return
     */
    public long saveDiary(DiaryBean diary) {
        ContentValues values = new ContentValues();
        values.put("date", diary.getDate() );
        values.put("title", diary.getTitle());
        values.put("content", diary.getContent());
        values.put("path", diary.getPath());
        values.put("backgroundPath", diary.getBackgroundPath());
        long insert = db.insert("Diary", null, values);
        db.close();
        values.clear();
        return insert;
    }

    /**
     * 删除一条日记
     * @param _id
     * @return
     */
    public long deleteDiary(int _id)
    {
        db = dbHelper.getWritableDatabase();
        long deleteDiary = db.delete("Diary","_id = ?",new String[]{String.valueOf(_id)});
        return deleteDiary;
    }


    /**
     * 更新一条日记
     * @param diary
     * @return
     */
    public long updateDiary(DiaryBean diary,int id)
    {
        ContentValues values = new ContentValues();
        values.put("title", diary.getTitle());
        values.put("content", diary.getContent());
        values.put("path", diary.getPath());
        values.put("backgroundPath", diary.getBackgroundPath());
        long update = db.update("Diary", values,"_id = ?",new String[]{String.valueOf(id)});
        String tag = "+++旧id+++";
        Log.d(tag,"++++"+id);
        Log.d(tag,"++++++传奇霸业++++"+diary.getTitle());
        values.clear();
        return update;
    }
}
