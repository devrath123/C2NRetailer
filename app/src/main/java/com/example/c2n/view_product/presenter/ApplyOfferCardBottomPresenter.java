package com.example.c2n.view_product.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.offer_cards_list.domain.GetOfferCardListUseCase;
import com.example.c2n.retailerhome.domain.GetOfferCardsUseCase;
import com.example.c2n.view_product.domain.GetProductDocumentIdUseCase;
import com.example.c2n.view_product.domain.UpdateProductOfferCardUseCase;
import com.example.c2n.view_product.presenter.view.ApplyOfferCardBottomFragmentView;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 09-07-2018.
 */

public class ApplyOfferCardBottomPresenter {
    ApplyOfferCardBottomFragmentView applyOfferCardBottomFragmentView;
    GetOfferCardsUseCase getOfferCardsUseCase;
    GetOfferCardListUseCase getOfferCardListUseCase;
    UpdateProductOfferCardUseCase updateProductOfferCardUseCase;
    GetProductDocumentIdUseCase getProductDocumentIdUseCase;
    Context context;

    @Inject
    ApplyOfferCardBottomPresenter(GetOfferCardsUseCase getOfferCardsUseCase, UpdateProductOfferCardUseCase updateProductOfferCardUseCase, GetProductDocumentIdUseCase getProductDocumentIdUseCase, GetOfferCardListUseCase getOfferCardListUseCase) {
        this.getOfferCardsUseCase = getOfferCardsUseCase;
        this.updateProductOfferCardUseCase = updateProductOfferCardUseCase;
        this.getProductDocumentIdUseCase = getProductDocumentIdUseCase;
        this.getOfferCardListUseCase = getOfferCardListUseCase;
    }

    public void bind(ApplyOfferCardBottomFragmentView applyOfferCardBottomFragmentView, Context context) {
        this.applyOfferCardBottomFragmentView = applyOfferCardBottomFragmentView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadOfferCards() {
        getOfferCardListUseCase.execute(null, context)
                .doOnSubscribe(disposable -> applyOfferCardBottomFragmentView.showProgress(true))
                .subscribe(this::handleOfferCardsResponse, throwable -> handleOfferCardsError(throwable));
    }

    private void handleOfferCardsResponse(List<OfferDataModel> offerListDataModels) {
        Log.d("AddProductPricePresent_", "" + offerListDataModels.size());
        applyOfferCardBottomFragmentView.showProgress(false);
        applyOfferCardBottomFragmentView.showOfferCards(offerListDataModels);
    }

    private void handleOfferCardsError(Throwable throwable) {
        applyOfferCardBottomFragmentView.showProgress(false);
        Log.d("AddProductPricePresent_", "" + throwable.getMessage());
    }

//    @SuppressLint("RxLeakedSubscription")
//    public void getProductDocumentId(ProductDataModel productDataModel) {
//        Log.d("product_data_model", productDataModel.toString());
//        getProductDocumentIdUseCase.execute(new ProductDataModel(applyOfferCardBottomFragmentView.getShopEmail(), applyOfferCardBottomFragmentView.getProductCategory(), productDataModel.getProductName(), "", "", ""), context)
//                .doOnSubscribe(disposable -> applyOfferCardBottomFragmentView.showUpdateProductProgress(true))
//                .subscribe(this::handleDocumentIdResponse, throwable -> handleDocumentIdError(throwable));
//    }
//
//    private void handleDocumentIdResponse(String documentId) {
//        Log.d("apply_offer_id", documentId + "");
//        applyOfferCardBottomFragmentView.showUpdateProductProgress(false);
//        applyOfferCardBottomFragmentView.getDocumentIdSuccess(documentId);
//    }
//
//    private void handleDocumentIdError(Throwable throwable) {
//        applyOfferCardBottomFragmentView.showUpdateProductProgress(false);
//        applyOfferCardBottomFragmentView.getDocumentIdSuccess(null);
//        Log.d("ViewProductPresenter", "" + throwable.getMessage());
//    }

    @SuppressLint("RxLeakedSubscription")
    public void updateProductOffer() {
        ProductDataModel productDataModel = new ProductDataModel();
        productDataModel.setProductOffer(applyOfferCardBottomFragmentView.getProductOffer());
        productDataModel.setProductID(applyOfferCardBottomFragmentView.getProductDocumentId());
        productDataModel.setProductCategory(applyOfferCardBottomFragmentView.getProductCategory());
        updateProductOfferCardUseCase.execute(new Object[]{applyOfferCardBottomFragmentView.getProductDataModel(), applyOfferCardBottomFragmentView.getShopDataModel(), applyOfferCardBottomFragmentView.getOfferListDataModel()}, context)
                .doOnSubscribe(disposable -> applyOfferCardBottomFragmentView.showUpdateProductProgress(true))
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleResponse(Boolean documentReference) {
        applyOfferCardBottomFragmentView.showUpdateProductProgress(false);
        applyOfferCardBottomFragmentView.isProductUpdationSuccess(true);
        Log.d("apply_offer", "success");
    }

    private void handleError(Throwable throwable) {
        applyOfferCardBottomFragmentView.showUpdateProductProgress(false);
        applyOfferCardBottomFragmentView.isProductUpdationSuccess(false);
        Log.d("ViewProductPresenter", "" + throwable.getMessage());
    }
}
