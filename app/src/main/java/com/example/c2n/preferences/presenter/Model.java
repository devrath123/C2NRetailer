package com.example.c2n.preferences.presenter;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

class Model {
    private String text;
    private boolean isSelected = false;

    public Model(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
