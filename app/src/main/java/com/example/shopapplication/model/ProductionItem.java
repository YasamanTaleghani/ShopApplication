package com.example.shopapplication.model;

import com.example.shopapplication.retrofitModel.ImagesItem;

import java.util.List;

public class ProductionItem {
    private int mId;
    private String mTitle;
    private String mDescription;
    private List<Image> mImages;

    //Constructor
    public ProductionItem() {
    }

    public ProductionItem(int id, String title, String description, List<Image> images) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mImages = images;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }
}
