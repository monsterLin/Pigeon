package com.monsterlin.pigeon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.monsterlin.pigeon.constant.NotesConfig;


/**
 * Created by Pluto  on 2016/5/9.
 */
public class NotesDB extends SQLiteOpenHelper {
    /**
     * 初始化,建表
     */
    public NotesDB(Context context) {
        super(context, NotesConfig.TABLE_NAME_NOTES, null, 1);
    }

    /**
     * 创建数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ NotesConfig.TABLE_NAME_NOTES+"(" +
                NotesConfig.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotesConfig.COLUMN_NAME_NOTE_TITLE +" TEXT NOT NULL DEFAULT \"\"," +
                NotesConfig.COLUMN_NAME_NOTE_CONTENT+" TEXT NOT NULL DEFAULT \"\"," +
                NotesConfig.COLUMN_NAME_NOTE_SORT+" INTEGER NOT NULL DEFAULT \"\"," +
                NotesConfig.COLUMN_NAME_NOTE_DATE+" TEXT NOT NULL DEFAULT \"\"" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
