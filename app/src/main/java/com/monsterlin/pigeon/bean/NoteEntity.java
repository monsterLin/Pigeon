package com.monsterlin.pigeon.bean;

/**
 * Created by Pluto  on 2016/5/6.
 */
public class NoteEntity{

    /*因为id为自增长，所以不需要动态设置，只需要get方法即可*/
    private int id;/*id*/

    /*标题*/
    private String noteTitle;

    /*内容*/
    private String noteContent;

    /*时间*/
    private String createDate;

    /*分类*/
    private int sort;

    public NoteEntity() {
    }

    public NoteEntity(int id, String noteTitle, String noteContent, String createDate, int sort) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createDate = createDate;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", createDate=" + createDate +
                ", sort=" + sort +
                '}';
    }
}
