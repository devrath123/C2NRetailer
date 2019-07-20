package com.example.c2n.core.models;

import java.io.Serializable;

public class MasterProductDetailsDataModel implements Serializable {

    private MasterProductDataModel masterProductDataModel;
    private boolean whishlisted;

    public MasterProductDataModel getMasterProductDataModel() {
        return masterProductDataModel;
    }

    public void setMasterProductDataModel(MasterProductDataModel masterProductDataModel) {
        this.masterProductDataModel = masterProductDataModel;
    }

    public boolean isWhishlisted() {
        return whishlisted;
    }

    public void setMylisted(boolean whishlisted) {
        this.whishlisted = whishlisted;
    }


}
