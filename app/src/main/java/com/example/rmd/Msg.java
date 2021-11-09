package com.example.rmd;

import android.app.Activity;

public class Msg {
    private int id;
    private int imageResourceID;
    private String title;
    private String content;
    private String date;
    private String key;

    //initialize the Msg type
    public Msg(int id, int imageResourceID, String title, String content, String date, String key) {
        this.id = id;
        this.imageResourceID = imageResourceID;
        this.title = title;
        this.content = content;
        this.date = date;
        this.key = key;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getImageResourceID() {
        return imageResourceID;
    }
    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public String getDate() {return date;}
    public String getKey() {return key;}
    public void setContent(String content) {
        this.content = content;
    }
}
