package com.example.c2n.core.model1;

/**
 * Created by shivani.singh on 30-08-2018.
 */

public class OfferProductDetailsDataModel {
   private ProductDataModel productDataModel;
   private ShopDataModel shopDataModel;
   OfferDetailsDataModel offerDetailsDataModel;



    public ProductDataModel getProductDataModel() {
        return productDataModel;
    }

    public void setProductDataModel(ProductDataModel productDataModel) {
        this.productDataModel = productDataModel;
    }

    public ShopDataModel getShopDataModel() {
        return shopDataModel;
    }

    public void setShopDataModel(ShopDataModel shopDataModel) {
        this.shopDataModel = shopDataModel;
    }

    public OfferDetailsDataModel getOfferDetailsDataModel() {
        return offerDetailsDataModel;
    }

    public void setOfferDetailsDataModel(OfferDetailsDataModel offerDetailsDataModel) {
        this.offerDetailsDataModel = offerDetailsDataModel;
    }

    @Override
    public String toString() {
        return "OfferProductDetailsDataModel{" +
                "productDataModel=" + productDataModel +
                ", shopDataModel=" + shopDataModel +
                ", offerDetailsDataModel=" + offerDetailsDataModel +
                '}';
    }
}
