package com.example.c2n.customercategory.di;

import com.example.c2n.customercategory.presenter.CustomerCategoryFragment;

import dagger.Component;

/**
 * Created by shivani.singh on 17-08-2018.
 */

@Component(modules = CustomerCategoryModule.class)
public interface CustomerCategoryComponent {
    void inject(CustomerCategoryFragment customerCategoryFragment);
}
