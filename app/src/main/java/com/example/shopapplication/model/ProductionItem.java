package com.example.shopapplication.model;

public class ProductionItem {
    private int mId;
    private String mTitle;
    private String mUrl;

    //Constructor
    public ProductionItem() {
    }

    public ProductionItem(int id, String title, String url) {
        mId = id;
        mTitle = title;
        mUrl = url;
    }

    //Getter & Setter
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
