package com.example.c2n.retailerhome.presenter;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ShopDataModel;

import java.util.List;

/**
 * Created by vipul.singhal on 20-06-2018.
 */

public interface RetailerHomeFragmentView {
//    void loadTestimonialRetailerList();

    void loadRecentShops();

    void enableFreshRetailerView();

    void showRecentShopsList(List<ShopDataModel> shops);

//    void showRetailerTestimonialList(List<RetailerDataModel> retailerDataModels);

    void showOfferCards(List<OfferDataModel> offerDataModels);

    void initImageSlider();

    void setAllOffersList(List<OfferDataModel> offerDataModels);

    List<OfferDataModel> getExpireOfferCardsList();

    void showLoadingOffersProgress(Boolean progress, String msg);
}