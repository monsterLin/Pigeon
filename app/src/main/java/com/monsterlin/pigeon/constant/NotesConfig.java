package com.monsterlin.pigeon.constant;

/**
 * 记录程序所需要的常量
 * Created by Pluto  on 2016/5/6.
 */
public class NotesConfig {

    /*跳转编辑记事本的request code*/
    public static final int NOTE_REQUEST_CODE = 01;

    /**
     * 记事本表名 【notes】
     */
    public static final String TABLE_NAME_NOTES = "notes";

    /**
     * 记事本在数据库中的id  【_id】
     */
    public static final String COLUMN_NAME_ID = "_id";
    /**
     * 记事本的标题 【title】
     */
    public static final String COLUMN_NAME_NOTE_TITLE = "title";
    /**
     * 记事本内容
     */
    public static final String COLUMN_NAME_NOTE_CONTENT = "content";
    /**
     * 记事本更新时间
     */
    public static final String COLUMN_NAME_NOTE_DATE = "date";
    /**
     * 记事本分类
     */
    public static final String COLUMN_NAME_NOTE_SORT = "sort";
}
