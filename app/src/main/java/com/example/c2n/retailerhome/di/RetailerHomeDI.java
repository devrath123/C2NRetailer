package com.example.c2n.retailerhome.di;

/**
 * Created by shivani.singh on 21-06-2018.
 */

public class RetailerHomeDI {
    private static RetailerHomeComponent component;

    public static RetailerHomeComponent getRetailerHomeComponent(){
        if (component == null){
            component = DaggerRetailerHomeComponent.builder().build();
        }
        return component;
    }

}
