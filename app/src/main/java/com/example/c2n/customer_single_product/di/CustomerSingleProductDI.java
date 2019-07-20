package com.example.c2n.customer_single_product.di;

public class CustomerSingleProductDI {

    private static CustomerSingleProductComponent component;

    public static CustomerSingleProductComponent getCustomerSingleProductDI(){
        if(component==null){
            component = DaggerCustomerSingleProductComponent.builder().build();
        }
        return component;
    }
}
