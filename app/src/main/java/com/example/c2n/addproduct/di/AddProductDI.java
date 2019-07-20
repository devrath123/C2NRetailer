package com.example.c2n.addproduct.di;

public class AddProductDI {
    private static AddProductComponent addProductComponent;

    public static AddProductComponent getAddProductComponent() {
        if (addProductComponent == null) {
            addProductComponent = DaggerAddProductComponent.builder().build();
        }
        return addProductComponent;
    }
}
