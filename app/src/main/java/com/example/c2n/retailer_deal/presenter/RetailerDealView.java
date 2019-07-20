package com.example.c2n.retailer_deal.presenter;

import com.example.c2n.core.models.RetailerDealDataModel;
import com.example.c2n.core.models.ShopDealDataModel;

import java.util.List;

public interface RetailerDealView {

    //    public void loadDeals(HashMap<String, RetailerDealDataModel> stringRetailerDealDataModelHashMap);
    public void loadDeals(List<RetailerDealDataModel> retailerDealDataModels);

    void updateRetailerAccepted();

    void showProgress(boolean status);

    void declinedDeal(ShopDealDataModel shopDealDataModel);

    void updateRetailerDeclined();
}
