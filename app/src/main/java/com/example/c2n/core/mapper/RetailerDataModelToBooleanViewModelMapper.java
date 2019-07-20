package com.example.c2n.core.mapper;

import com.example.c2n.core.model1.RetailerDataModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 25-07-2018.
 */

public class RetailerDataModelToBooleanViewModelMapper {
    @Inject
    RetailerDataModelToBooleanViewModelMapper() {

    }

    public boolean mapRetailerDataModelListToEmailBoolean(List<RetailerDataModel> retailersList, String retailerEmail) {
        for (RetailerDataModel retailer : retailersList) {
            if (retailer.getRetailerID().equals(retailerEmail)) {
                return true;
            }
        }
        return false;
    }
}
