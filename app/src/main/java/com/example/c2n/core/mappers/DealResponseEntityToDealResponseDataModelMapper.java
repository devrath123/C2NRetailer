package com.example.c2n.core.mappers;

import com.example.c2n.core.models.DealResponseDataModel;
import com.example.c2n.core.models.DealResponseEntity;

import javax.inject.Inject;

public class DealResponseEntityToDealResponseDataModelMapper {

    @Inject
    DealResponseEntityToDealResponseDataModelMapper() {
    }

    public DealResponseDataModel mapEntityToData(DealResponseEntity dealResponseEntity) {
        DealResponseDataModel dealResponseDataModel = new DealResponseDataModel();
        dealResponseDataModel.setStatus(dealResponseEntity.getStatus());
        return dealResponseDataModel;
    }
}


