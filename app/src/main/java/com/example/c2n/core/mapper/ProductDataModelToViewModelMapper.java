package com.example.c2n.core.mapper;


import com.example.c2n.core.model1.ProductDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 20-07-2018.
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
