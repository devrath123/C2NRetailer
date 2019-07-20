package com.example.c2n.edit_profile.di;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

public class EditProfileDI {
    private static EditProfileComponent component;

    public static EditProfileComponent getUserEditProfileComponent() {
        if (component == null) {
            component = DaggerEditProfileComponent.builder().build();
        }
        return component;
    }
}
