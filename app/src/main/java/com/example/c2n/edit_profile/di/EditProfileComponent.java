package com.example.c2n.edit_profile.di;

import com.example.c2n.edit_profile.presenter.EditProfileFragment;

import dagger.Component;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

@Component(modules = EditProfileModule.class)
public interface EditProfileComponent {
    void inject(EditProfileFragment editProfileFragment);
}
