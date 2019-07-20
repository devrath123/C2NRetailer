package com.example.c2n.core.model1;

import java.util.List;

/**
 * Created by vipul.singhal on 07-09-2018.
 */

public class OfferedProductsDataModel {

    private OfferDataModel offerDataModel;
    private List<ProductDataModel> products;

    public OfferDataModel getOfferDataModel() {
        return offerDataModel;
    }

    public void setOfferDataModel(OfferDataModel offerDataModel) {
        this.offerDataModel = offerDataModel;
    }

    public List<ProductDataModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDataModel> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OfferedProductsDataModel{" +
                "offerDataModel=" + offerDataModel +
                ", products=" + products +
                '}';
    }
}
