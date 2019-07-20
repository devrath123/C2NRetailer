package com.example.c2n.core.model;

import android.util.Log;

import com.example.c2n.core.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 14-05-2018.
 */

public class UserDataModelListToEmailViewModelMapper {

    @Inject
    UserDataModelListToEmailViewModelMapper() {

    }

    public boolean mapUserDataModelListToEmailBoolean(List<UserDataModel> usersList, String userEmail) {
        for (UserDataModel user : usersList) {
            Log.d("boolean_check", user.getUserEmail() + "-" + userEmail);
            if (user.getUserEmail().equals(userEmail)) {
                BaseActivity.currentUser = user;
                return true;
            }
        }
        return false;
    }
}
