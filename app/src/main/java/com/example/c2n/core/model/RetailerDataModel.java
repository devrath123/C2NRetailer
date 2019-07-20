package com.example.c2n.core.model;

/**
 * Created by shivani.singh on 21-06-2018.
 */

public class RetailerDataModel {
    private String userPhotoUrl;
    private String userName;
    private String userSuccessStory;

    public RetailerDataModel(String userPhotoUrl, String userName, String userSuccessStory) {
        this.userPhotoUrl = userPhotoUrl;
        this.userName = userName;
        this.userSuccessStory = userSuccessStory;
    }

    /*public RetailerDataModel(String userPhotoUrl, String userSuccessStory) {
        this.userPhotoUrl = userPhotoUrl;
        this.userSuccessStory = userSuccessStory;
    }*/

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSuccessStory() {
        return userSuccessStory;
    }

    public void setUserSuccessStory(String userSuccessStory) {
        this.userSuccessStory = userSuccessStory;
    }
}
