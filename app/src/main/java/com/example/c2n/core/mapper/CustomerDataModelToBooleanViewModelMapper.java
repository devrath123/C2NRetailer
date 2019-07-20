package com.example.c2n.core.mapper;

import com.example.c2n.core.model1.CustomerDataModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 25-07-2018.
 */

public class CustomerDataModelToBooleanViewModelMapper {
    @Inject
    CustomerDataModelToBooleanViewModelMapper() {

    }

    public boolean mapCustomerDataModelListToEmailBoolean(List<CustomerDataModel> customersList, String userEmail) {
        for (CustomerDataModel customer : customersList) {
            if (customer.getCustomerID().equals(userEmail)) {
                return true;
            }
        }
        return false;
    }
}
