package com.example.c2n.retailercategory.di;

/**
 * Created by roshan.nimje on 18-06-2018.
 */

public class RetailerCategoryDI {
    private static RetailerCategoryComponent retailerCategoryComponent;

    public static RetailerCategoryComponent getRetailerCategoryComponent() {
        if (retailerCategoryComponent == null) {
            retailerCategoryComponent = DaggerRetailerCategoryComponent.builder().build();
        }
        return retailerCategoryComponent;
    }
}
