package com.example.c2n.core.model;

/**
 * Created by vipul.singhal on 17-05-2018.
 */

public class NavigationDataModel {
    private boolean isSelected;
    private String name;
    private int drawableId;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
