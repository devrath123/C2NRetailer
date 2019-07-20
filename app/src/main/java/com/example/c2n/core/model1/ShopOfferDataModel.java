package com.example.c2n.core.model1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vipul.singhal on 29-07-2018.
 */

public class ShopOfferDataModel implements Serializable {
    List<HashMap<String, List<String>>> productsId;

//    public ShopOfferDataModel(HashMap<String, List<String>> productsId) {
//        this.productsId = productsId;
//    }

    public ShopOfferDataModel() {
        productsId = new ArrayList<>();
    }

    public List<HashMap<String, List<String>>> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<HashMap<String, List<String>>> productsId) {
        this.productsId = productsId;
    }

    @Override
    public String toString() {
        return "ShopOfferDataModel{" +
                "productsId=" + productsId +
                '}';
    }
}
