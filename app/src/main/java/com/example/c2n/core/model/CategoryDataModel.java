package com.example.c2n.core.model;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

public class CategoryDataModel {

    private String category;
    private String picURL;
    private boolean isSelected = false;

    public CategoryDataModel(String category, String picURL) {
        this.category = category;
        this.picURL = picURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
