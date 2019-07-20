package com.example.c2n.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class ProductDataModelToViewModelMapper {

    List<ProductDataModel> productsList = new ArrayList<>();

    @Inject
    ProductDataModelToViewModelMapper() {

    }

    public List<ProductDataModel> mapProductsToViewModelMapper(List<ProductDataModel> products, String productsCategory) {
        for (ProductDataModel product : products) {
            if (product.getProductCategory().equals(productsCategory)) {
                productsList.add(product);
            }
        }
        return productsList;
    }
}
