package com.example.c2n.core.database;

import com.example.c2n.core.models.ProductDetailsDataModel;

import java.util.ArrayList;
import java.util.List;

public class CartProducts {
    private static CartProducts cartProducts = new CartProducts();
    private List<ProductDetailsDataModel> productDetailsDataModelList = new ArrayList<>();

    private CartProducts() {

    }

    public static CartProducts getCartProducts() {
        return cartProducts;
    }

    public void addProduct(ProductDetailsDataModel productDetailsDataModel) {
        if (!productDetailsDataModelList.contains(productDetailsDataModel))
            productDetailsDataModelList.add(productDetailsDataModel);
    }

    public void remove(ProductDetailsDataModel productDetailsDataModel) {
        if (productDetailsDataModelList.contains(productDetailsDataModel)) {
            int index = productDetailsDataModelList.indexOf(productDetailsDataModel);
            productDetailsDataModelList.remove(index);
        }
    }

    public List<ProductDetailsDataModel> getAllProducts() {
        return productDetailsDataModelList;
    }
}
