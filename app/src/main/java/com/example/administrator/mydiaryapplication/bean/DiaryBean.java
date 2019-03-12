package com.example.administrator.mydiaryapplication.bean;

/**
 * 创建实体类
 */
public class DiaryBean {
    private int _id;
    private String date;
    private String title;
    private String content;
    private String path;
    private String backgroundPath;

    /**
     * @param _id   id
     * @param date 日期
     * @param title 标题
     * @param content 正文
     * @param path 添加的图片路径
     * @param backgroundPath 背景图片路径
     */
    public DiaryBean(int _id, String date , String title , String content , String path, String backgroundPath ) {
        this._id = _id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.path = path;
        this.backgroundPath = backgroundPath;
    }
    public DiaryBean()
    {

    }

    //get() and set()方法
    public void set_id(int _id){
        this._id = _id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public int get_id() {
        return _id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPath() {
        return path;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }
}
