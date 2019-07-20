package com.example.c2n.customercategory.di;

/**
 * Created by shivani.singh on 17-08-2018.
 */

public class CustomerCategoryDI {
    private static CustomerCategoryComponent categoryComponent;

    public  static CustomerCategoryComponent getCategoryComponent(){
        if (categoryComponent == null){
            categoryComponent = DaggerCustomerCategoryComponent.builder().build();
        }
       return categoryComponent;
    }

}
