package com.example.c2n.add_product.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.add_product.domain.AddProductUseCase;
import com.example.c2n.add_product.domain.UpdateProductDescriptionUseCase;
import com.example.c2n.add_product.presenter.view.AddProductResultView;
import com.example.c2n.core.models.ProductDataModel;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 13-06-2018.
 */

public class AddProductPresenter {

    AddProductUseCase addProductUseCase;
    AddProductResultView addProductResultView;
    UpdateProductDescriptionUseCase updateProductDescriptionUseCase;
    Context context;

    @Inject
    AddProductPresenter(AddProductUseCase addProductUseCase, UpdateProductDescriptionUseCase updateProductDescriptionUseCase) {
        this.addProductUseCase = addProductUseCase;
        this.updateProductDescriptionUseCase = updateProductDescriptionUseCase;
    }

    public void bind(AddProductResultView addProductResultView, Context context) {
        this.addProductResultView = addProductResultView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void addProduct(String shopEmail) {
        ProductDataModel productDataModel = new ProductDataModel();
        productDataModel.setProductCategory(addProductResultView.getProductCategory());
        productDataModel.setProductName(addProductResultView.getProductName());
        productDataModel.setProductMRP(addProductResultView.getProductMRP());
        productDataModel.setProductImageURL(addProductResultView.getProductPhotoUrl());
        productDataModel.setProductStockStatus(true);
        productDataModel.setProductOfferStatus(false);
        if (addProductResultView.getProductOffer() != null)
            productDataModel.setProductOffer(addProductResultView.getProductOffer());
        addProductUseCase.execute(new Object[]{productDataModel, shopEmail}, context)
                .doOnSubscribe(disposable -> addProductResultView.showAddProductProgress(true, "Adding Product"))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateProductDescription() {
        ProductDataModel productDataModel = new ProductDataModel();
        productDataModel.setProductDescription(addProductResultView.getProductDescription());
        productDataModel.setProductID(addProductResultView.getProductID());
        productDataModel.setProductCategory(addProductResultView.getProductCategory());
        updateProductDescriptionUseCase.execute(
                new Object[]{productDataModel, addProductResultView.getShopEmail()}, context)
                .doOnSubscribe(disposable -> addProductResultView.showAddProductProgress(true, "Adding Description"))
                .subscribe(this::handleUpdateProductResponse, throwable -> handleError(throwable));
    }

    private void handleUpdateProductResponse(DocumentReference documentReference) {
        addProductResultView.setProductDocumentID(documentReference.getId());
        Log.d("Document_reference:..", "............  " + documentReference + documentReference.getId());
        addProductResultView.isAddProductSuccess(true);
        addProductResultView.updateProductDescriptionSuccess();
    }


    public void handleResponse(String productDocumentId) {
        addProductResultView.setProductDocumentID(productDocumentId);
        Log.d("Document_reference:..", "............  " + productDocumentId);
//        if (addProductResultView.getProductOffer() != null)
//            addProductOffer();
//        else
            addProductResultView.isAddProductSuccess(true);
    }

    private void addProductOffer() {

    }

    public void handleError(Throwable e) {
        Log.d("error...", "..." + e.getMessage());
        addProductResultView.isAddProductSuccess(false);
    }

}
