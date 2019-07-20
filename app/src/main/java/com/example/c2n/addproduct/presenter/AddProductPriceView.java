package com.example.c2n.addproduct.presenter;

import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;

import java.util.List;

public interface AddProductPriceView {
    void showAllOffers(List<OfferDataModel> offerDataModelList);

    void showLoadingOffersProgress(Boolean progress, String msg);

    MasterProductDataModel getMasterProduct();

    ProductDataModel getProductDataModel();

    void hanndleAddProductOffers();

    void handleAppProductExisting(Boolean aBoolean);
}
