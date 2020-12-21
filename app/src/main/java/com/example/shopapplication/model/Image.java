package com.example.shopapplication.model;

public class Image {

    private int mId;
    private String src;

    //Constructor
    public Image(int id, String src) {
        mId = id;
        this.src = src;
    }

    public Image() {
    }

    //Getter & Setter
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
