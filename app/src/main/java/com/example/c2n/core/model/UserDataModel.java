package com.example.c2n.core.model;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public class UserDataModel {

    private String userDocumentID;
    private String userType;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userMobileNo;
    private String userAddress;
    private String userProfilePicUrl;
    private String userDOB;
    private String userGender;
    private double userLatitude;
    private double userLongitude;


    public UserDataModel(String userDocumentID) {
        this.userDocumentID = userDocumentID;
    }

    public UserDataModel(String userDocumentID, String userMobileNo) {
        this.userDocumentID = userDocumentID;
        this.userMobileNo = userMobileNo;
    }

    public UserDataModel(String userType, String userName, String userDOB, String userMobileNo, String userEmail, String userGender, String userAddress, double userLatitude, double userLongitude, String userProfilePicUrl) {
        this.userType = userType;
        this.userName = userName;
        this.userDOB = userDOB;
        this.userMobileNo = userMobileNo;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userAddress = userAddress;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.userProfilePicUrl = userProfilePicUrl;
    }

    public UserDataModel(String userType, String userName, String userDOB, String userMobileNo, String userEmail, String userGender, String userAddress, String userProfilePicUrl) {
        this.userType = userType;
        this.userName = userName;
        this.userDOB = userDOB;
        this.userMobileNo = userMobileNo;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userAddress = userAddress;
        this.userProfilePicUrl = userProfilePicUrl;
    }

    public UserDataModel(String userType, String userName, String userEmail, String userPassword, String userMobileNo, String userAddress, String userProfilePicUrl, String userDOB, String userGender) {
        this.userType = userType;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userMobileNo = userMobileNo;
        this.userAddress = userAddress;
        this.userProfilePicUrl = userProfilePicUrl;
        this.userDOB = userDOB;
        this.userGender = userGender;
    }

    public UserDataModel(String userDocumentID, String userName, String userMobileNo, String userAddress, String userDOB, String userGender, String userProfilePicUrl, double userLatitude, double userLongitude) {
        this.userDocumentID = userDocumentID;
        this.userName = userName;
        this.userMobileNo = userMobileNo;
        this.userAddress = userAddress;
        this.userDOB = userDOB;
        this.userGender = userGender;
        this.userProfilePicUrl = userProfilePicUrl;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public void setUserProfilePicUrl(String userProfilePicUrl) {
        this.userProfilePicUrl = userProfilePicUrl;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserDocumentID() {
        return userDocumentID;
    }

    public void setUserDocumentID(String userDocumentID) {
        this.userDocumentID = userDocumentID;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLattitude) {
        this.userLatitude = userLattitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    @Override
    public String toString() {
        return "UserDataModel{" +
                "userType='" + userType + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userMobileNo='" + userMobileNo + '\'' +
                ", userProfilePicUrl='" + userProfilePicUrl + '\'' +
                ", userDOB='" + userDOB + '\'' +
                ", userGender='" + userGender + '\'' +
                '}';
    }
}
