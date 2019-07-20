package com.example.c2n.view_product.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.view_product.domain.GetAppliedOfferCardUseCase;
import com.example.c2n.view_product.domain.GetProductDocumentIdUseCase;
import com.example.c2n.view_product.domain.RemoveProductOfferCardUseCase;
import com.example.c2n.view_product.domain.ViewUpdateProductUseCase;
import com.example.c2n.view_product.presenter.view.ViewUpdateProductView;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 24-06-2018.
 */

public class ViewUpdateProductPresenter {

    private final String TAG = "ViewUpdateProductPresen";
    ViewUpdateProductUseCase viewUpdateProductUseCase;
    GetAppliedOfferCardUseCase getAppliedOfferCardUseCase;
    RemoveProductOfferCardUseCase removeProductOfferCardUseCase;
    GetProductDocumentIdUseCase getProductDocumentIdUseCase;
    ViewUpdateProductView viewUpdateProductView;
    Context context;

    @Inject
    ViewUpdateProductPresenter(ViewUpdateProductUseCase viewUpdateProductUseCase, GetAppliedOfferCardUseCase getAppliedOfferCardUseCase, RemoveProductOfferCardUseCase removeProductOfferCardUseCase, GetProductDocumentIdUseCase getProductDocumentIdUseCase) {
        this.viewUpdateProductUseCase = viewUpdateProductUseCase;
        this.getAppliedOfferCardUseCase = getAppliedOfferCardUseCase;
        this.removeProductOfferCardUseCase = removeProductOfferCardUseCase;
        this.getProductDocumentIdUseCase = getProductDocumentIdUseCase;
    }

    public void bind(ViewUpdateProductView viewUpdateProductView, Context context) {
        this.viewUpdateProductView = viewUpdateProductView;
        this.context = context;
    }

    public void validateProductDetails() {
        if (!TextUtils.isEmpty(viewUpdateProductView.getProductName())) {
            if (!TextUtils.isEmpty(String.valueOf(viewUpdateProductView.getProductMrp())))
                viewUpdateProductView.uploadImage();
            else
                viewUpdateProductView.setProductNameError();
        } else {
            viewUpdateProductView.setProductMrpError();
        }
    }

//    @SuppressLint("RxLeakedSubscription")
//    public void getProductDocumentId(ProductDataModel productDataModel) {
//        Log.d("product_data_model", productDataModel.toString());
//
//        getProductDocumentIdUseCase.execute(new ProductDataModel(viewUpdateProductView.getShopEmail(), viewUpdateProductView.getProductCategory(), productDataModel.getProductName(), "", "", ""), context)
//                .doOnSubscribe(disposable -> viewUpdateProductView.showUpdateProductProgress(true))
//                .subscribe(this::handleDocumentIdResponse, throwable -> handleDocumentIdError(throwable));
//    }
//
//    private void handleDocumentIdResponse(String documentId) {
//        viewUpdateProductView.showUpdateProductProgress(false);
//        viewUpdateProductView.getDocumentIdSuccess(documentId);
//    }
//
//    private void handleDocumentIdError(Throwable throwable) {
//        viewUpdateProductView.showUpdateProductProgress(false);
//        viewUpdateProductView.getDocumentIdSuccess(null);
//        Log.d("ViewProductPresenter", "" + throwable.getMessage());
//    }

    @SuppressLint("RxLeakedSubscription")
    public void updateProductDetails() {
        ProductDataModel productDataModel = new ProductDataModel();
        productDataModel.setProductID(viewUpdateProductView.getProductDocumentId());
        productDataModel.setProductName(viewUpdateProductView.getProductName());
        productDataModel.setProductDescription(viewUpdateProductView.getProductDescription());
        productDataModel.setProductImageURL(viewUpdateProductView.getProductImageURL());
        productDataModel.setProductCategory(viewUpdateProductView.getProductCategory());
        productDataModel.setProductMRP(viewUpdateProductView.getProductMrp());
//        productDataModel.setProductOfferStatus(viewUpdateProductView.getProductOfferStatus());
        productDataModel.setProductOffer(viewUpdateProductView.getProductOffer());
        viewUpdateProductUseCase.execute(new Object[]{productDataModel, viewUpdateProductView.getShopID()}, context)
                .doOnSubscribe(disposable ->
                {
                    viewUpdateProductView.setProductDataModel(productDataModel);
                    viewUpdateProductView.showUpdateProductProgress(true);
                })
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleResponse(Boolean aBoolean) {
        viewUpdateProductView.showUpdateProductProgress(false);
        viewUpdateProductView.isProductUpdationSuccess(true);
    }

    private void handleError(Throwable throwable) {
        viewUpdateProductView.showUpdateProductProgress(false);
        viewUpdateProductView.isProductUpdationSuccess(true);
        Log.d("ViewProductPresenter", "" + throwable.getMessage());
    }


    @SuppressLint("RxLeakedSubscription")
    public void getAppliedOffer() {
        getAppliedOfferCardUseCase.execute(viewUpdateProductView.getProductOfferId(), context)
                .doOnSubscribe(disposable -> viewUpdateProductView.showLoadingOfferProgress(true))
                .subscribe(this::handleOfferResponse, throwable -> handleOfferError(throwable));
    }

    private void handleOfferResponse(OfferDataModel offerDataModel) {
        viewUpdateProductView.showLoadingOfferProgress(false);
        viewUpdateProductView.isLoadOfferSuccess(offerDataModel);
    }


    private void handleOfferError(Throwable throwable) {
        viewUpdateProductView.showLoadingOfferProgress(false);
        Log.d("ViewProductPres_offer", "" + throwable.getMessage());
    }


    @SuppressLint("RxLeakedSubscription")
    public void removeOfferCard() {
//        Log.d(TAG, "removeOfferCard ProductID : " + viewUpdateProductView.getProductDocumentId() + ", OfferID : " + viewUpdateProductView.getProductOffer().getOfferID() + ", ShopID : " + viewUpdateProductView.getShopID());
        removeProductOfferCardUseCase.execute(new Object[]{viewUpdateProductView.getProductDocumentId(), viewUpdateProductView.getShopID(), viewUpdateProductView.getProductOffer().getOfferID()}, context)
                .doOnSubscribe(disposable -> viewUpdateProductView.showLoadingOfferProgress(true))
                .subscribe(this::handleRemoveOfferResponse, throwable -> handleOfferError(throwable));
    }

    private void handleRemoveOfferResponse(Boolean aBoolean) {
        viewUpdateProductView.showLoadingOfferProgress(false);
        viewUpdateProductView.removeOfferSuccess();
//        viewUpdateProductView.isLoadOfferSuccess(offerDataModel);
    }

}
