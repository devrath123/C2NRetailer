package com.example.c2n.edit_profile.presenter;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

public interface EditProfileView {

//    void checkProfileFields();

    void getGEOLocation();

    void updateProfile();

    void showEditProfileProgress(Boolean bool);

    void changeContactNo();

    void isEditProfileSuccess(Boolean success);

    Boolean isDOBEmpty();

    Boolean isMobileNoEmpty();

    Boolean isAddressEmpty();

    void pickProfileImage();

    String getUserName();

    String getDOB();

    String getMobileNo();

    String getEmail();

//    String getGender();

    String getAddress();

    String getUserDocumentID();

    double getLatitude();

    double getLongitude();

    String getProfilePicURL();

    void getUserDetails();

//    void setUserType(String userType);

    String getUserType();

    void setUserName(String userName);

    void setDOB(String userDOB);

    void setMobileNo(String userMobileNo);

    void setEmail(String userEmail);

//    void setGender(String userGender);

    void setAddress(String userAddress);

    void setLatitude(double userLatitude);

    void setLongitude(double userLongitude);

    void setProfilePicURL(String userProfilePicURL);

    void pickDate();

    void openPreferences();

}
