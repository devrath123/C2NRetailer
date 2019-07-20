package com.example.c2n.core.model;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 14-05-2018.
 */

public class UserDataModelListToUsernameViewModelMapper {

    @Inject
    UserDataModelListToUsernameViewModelMapper() {

    }

    public boolean mapUserDataModelListToUserEmailBoolean(List<UserDataModel> usersList, String userEmail) {
        for (UserDataModel user : usersList) {
            if (user.getUserEmail().equals(userEmail)) {
                return true;
            }
        }
        return false;
    }

}
