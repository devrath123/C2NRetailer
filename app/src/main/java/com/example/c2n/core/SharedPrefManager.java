package com.example.c2n.core;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Prime on 24-01-2017.
 */

public class SharedPrefManager {

    public static final String MY_EMP_PREFS = "MySharedPref";

    private static Context mContext;

    private static boolean _skippedEditProfile;

    private static String _userEmail, _userDocumentID,_retailerDocumentID, _userProfilePic, _userMobileNo, _userAddress, _userType, _userFullName;

    public static void Init(Context context) {
        mContext = context;
    }

    public static void LoadFromPref() {
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 0);
        // Note here the 2nd parameter 0 is the default parameter for private access,
        //Operating mode. Use 0 or MODE_PRIVATE for the default operation,
        _skippedEditProfile = settings.getBoolean("skipped_edit_profile", false);
        _userEmail = settings.getString("user_email", "");
        _userDocumentID = settings.getString("user_document_id", "");
        _userProfilePic = settings.getString("user_profile_pic", "");
        _userMobileNo = settings.getString("user_mobile_no", "");
        _userAddress = settings.getString("user_address", "");
        _userType = settings.getString("user_type", "");
        _userFullName = settings.getString("user_name", "");
        _retailerDocumentID = settings.getString("retailer_document_id", "");


//        _username = settings.getString("user_name", "");
//        _email = settings.getString("email", "");
//        _passwod = settings.getString("password", "");
//        _name = settings.getString("name", "");
//        _dob = settings.getString("dob", "");
//        _gender = settings.getString("gender", "");
//        _mobile_no = settings.getString("mobile_no", "");
//        _profile_pic_url = settings.getString("profile_pic_url", "");
    }


    public static void StoreToPref() {
        // get the existing preference file
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 0);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("skipped_edit_profile", _skippedEditProfile);
        editor.putString("user_email", _userEmail);
        editor.putString("user_document_id", _userDocumentID);
        editor.putString("user_profile_pic", _userProfilePic);
        editor.putString("user_mobile_no", _userMobileNo);
        editor.putString("user_address", _userAddress);
        editor.putString("user_type", _userType);
        editor.putString("user_name", _userFullName);
        editor.putString("retailer_document_id",_retailerDocumentID);

//        editor.putString("user_name", _username);
//        editor.putString("email", _email);
//        editor.putString("password", _passwod);
//        editor.putString("name", _name);
//        editor.putString("dob", _dob);
//        editor.putString("gender", _gender);
//        editor.putString("mobile_no", _mobile_no);
//        editor.putString("profile_pic_url", _profile_pic_url);

        //final step to commit (save)the changes in to the shared pref
        editor.commit();
    }

    public static void DeleteAllEntriesFromPref() {
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 0);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
//
//    public static String get_userid() {
//        return _userid;
//    }
//
//    public static void set_userid(String _userid) {
//        SharedPrefManager._userid = _userid;
//    }
//
//    public static String get_username() {
//        return _username;
//    }
//
//    public static void set_username(String _username) {
//        SharedPrefManager._username = _username;
//    }
//
//    public static String get_email() {
//        return _email;
//    }
//
//    public static void set_email(String _email) {
//        SharedPrefManager._email = _email;
//    }
//
//    public static String get_passwod() {
//        return _passwod;
//    }
//
//    public static void set_passwod(String _passwod) {
//        SharedPrefManager._passwod = _passwod;
//    }
//
//    public static String get_name() {
//        return _name;
//    }
//
//    public static void set_name(String _name) {
//        SharedPrefManager._name = _name;
//    }
//
//    public static String get_dob() {
//        return _dob;
//    }
//
//    public static void set_dob(String _dob) {
//        SharedPrefManager._dob = _dob;
//    }
//
//    public static String get_gender() {
//        return _gender;
//    }
//
//    public static void set_gender(String _gender) {
//        SharedPrefManager._gender = _gender;
//    }
//
//    public static String get_mobile_no() {
//        return _mobile_no;
//    }
//
//    public static void set_mobile_no(String _mobile_no) {
//        SharedPrefManager._mobile_no = _mobile_no;
//    }
//
//    public static String get_profile_pic_url() {
//        return _profile_pic_url;
//    }
//
//    public static void set_profile_pic_url(String _profile_pic_url) {
//        SharedPrefManager._profile_pic_url = _profile_pic_url;
//    }


    public static boolean is_skippedEditProfile() {
        return _skippedEditProfile;
    }

    public static void set_skippedEditProfile(boolean _skippedEditProfile) {
        SharedPrefManager._skippedEditProfile = _skippedEditProfile;
    }

    public static String get_userEmail() {
        return _userEmail;
    }

    public static void set_userEmail(String _userEmail) {
        SharedPrefManager._userEmail = _userEmail;
    }

    public static String get_userDocumentID() {
        return _userDocumentID;

    }

    public static void set_userDocumentID(String _userDocumentID) {
        SharedPrefManager._userDocumentID = _userDocumentID;

//        SharedPrefManager._userDocumentID = "test@gmail.com";
    }

    public static String get_retailerDocumentID() {
        return _retailerDocumentID;
    }

    public static void set_retailerDocumentID(String _retailerDocumentID) {
        SharedPrefManager._retailerDocumentID = _retailerDocumentID;
    }

    public static String get_userProfilePic() {
        return _userProfilePic;
    }

    public static void set_userProfilePic(String _userProfilePic) {
        SharedPrefManager._userProfilePic = _userProfilePic;
    }

    public static String get_userMobileNo() {
        return _userMobileNo;
    }

    public static void set_userMobileNo(String _userMobileNo) {
        SharedPrefManager._userMobileNo = _userMobileNo;
    }

    public static String get_userAddress() {
        return _userAddress;
    }

    public static void set_userAddress(String _userAddress) {
        SharedPrefManager._userAddress = _userAddress;
    }

    public static void set_userType(String _userType) {
        SharedPrefManager._userType = _userType;
    }

    public static String get_userType() {
        return _userType;
    }

    public static String get_userFullName() {
        return _userFullName;
    }

    public static void set_userFullName(String _userFullName) {
        SharedPrefManager._userFullName = _userFullName;
    }
}
