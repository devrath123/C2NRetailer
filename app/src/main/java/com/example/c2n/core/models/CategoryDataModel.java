package com.example.c2n.core.models;

/**
 * Created by shivani.singh on 20-07-2018.
 */

public class CategoryDataModel {
    private String categoryName;
    private String categoryImageURL;
    private boolean isSelected = false;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "CategoryDataModel{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryImageURL='" + categoryImageURL + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
