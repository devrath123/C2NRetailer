package com.example.c2n.master_list.presenter;

import com.example.c2n.core.models.MasterProductDataModel;

import java.util.List;

public interface MasterListView {
    void loadAllProducts(List<MasterProductDataModel> masterProducts);

    void showProgress(boolean flag);
}
