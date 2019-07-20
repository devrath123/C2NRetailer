package com.example.c2n.customer_deal.presenter;

import com.example.c2n.core.models.CustomerDealDataModel;

import java.util.List;

public interface CustomerDealView {
    void showProgress(boolean b);

    void loadDeals(List<CustomerDealDataModel> customerDealDataModels);
}
