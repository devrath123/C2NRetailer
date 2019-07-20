package com.example.c2n.addproduct.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.addproduct.domain.AddProducOffersExistingUseCase;
import com.example.c2n.addproduct.domain.AddProducOffersUseCase;
import com.example.c2n.addproduct.domain.AddProducToOffersUseCase;
import com.example.c2n.addproduct.domain.AddProducToShopsUseCase;
import com.example.c2n.addproduct.domain.AddProductToMasterProductsUseCase;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.offer_cards_list.domain.GetOfferCardListUseCase;

import java.util.List;

import javax.inject.Inject;

public class AddProductPricePresenter {
    private final String TAG = "AddProductPricePresente";
    private Context context;
    private AddProductPriceView addProductPriceView;
    private String productID;

    private GetOfferCardListUseCase getOfferCardListUseCase;
    private AddProductToMasterProductsUseCase addProductToMasterProductsUseCase;
    private AddProducOffersUseCase addProducOffersUseCase;
    private AddProducOffersExistingUseCase addProducOffersExistingUseCase;
    private AddProducToShopsUseCase addProducToShopsUseCase;
    private AddProducToOffersUseCase addProducToOffersUseCase;

    @Inject
    public AddProductPricePresenter(GetOfferCardListUseCase getOfferCardListUseCase, AddProductToMasterProductsUseCase addProductToMasterProductsUseCase, AddProducOffersUseCase addProducOffersUseCase, AddProducOffersExistingUseCase addProducOffersExistingUseCase, AddProducToShopsUseCase addProducToShopsUseCase, AddProducToOffersUseCase addProducToOffersUseCase) {
        this.getOfferCardListUseCase = getOfferCardListUseCase;
        this.addProductToMasterProductsUseCase = addProductToMasterProductsUseCase;
        this.addProducOffersUseCase = addProducOffersUseCase;
        this.addProducOffersExistingUseCase = addProducOffersExistingUseCase;
        this.addProducToShopsUseCase = addProducToShopsUseCase;
        this.addProducToOffersUseCase = addProducToOffersUseCase;
    }

    public void bind(Context context, AddProductPriceView addProductPriceView) {
        this.context = context;
        this.addProductPriceView = addProductPriceView;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getAllOffers() {
        getOfferCardListUseCase.execute(null, context)
                .doOnSubscribe(disposable ->
                        addProductPriceView.showLoadingOffersProgress(true, "Loading Offer Cards...")
                )
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleResponse(List<OfferDataModel> offerDataModels) {
        Log.d(TAG, "OfferDataModels Size : " + offerDataModels.size());
        addProductPriceView.showLoadingOffersProgress(false, null);
        addProductPriceView.showAllOffers(offerDataModels);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addProductMaster() {
        addProductToMasterProductsUseCase.execute(addProductPriceView.getMasterProduct(), context)
                .doOnSubscribe(disposable -> addProductPriceView.showLoadingOffersProgress(true, "Adding Product..."))
                .subscribe(documentReference -> handleAddProductMasterResponse(documentReference), throwable -> handleError(throwable));
    }

    private void handleAddProductMasterResponse(String documentID) {
        productID = documentID;
        addProductPriceView.showLoadingOffersProgress(false, null);
        Log.d(TAG, "handleAddProductMasterResponse Success : " + documentID);
        addProductOffers(documentID);
    }

    @SuppressLint("RxLeakedSubscription")
    public void addProductOffers(String productID) {
        this.productID = productID;
        ProductDataModel productDataModel = addProductPriceView.getProductDataModel();
        productDataModel.setProductID(productID);
        addProducOffersUseCase.execute(productDataModel, context)
                .doOnSubscribe(disposable -> addProductPriceView.showLoadingOffersProgress(true, "Adding Product..."))
                .subscribe(documentReference -> handleAddProductOffers(documentReference), throwable -> handleError(throwable));
    }

    private void handleAddProductOffers(String documentReference) {
        addProductPriceView.showLoadingOffersProgress(false, null);
        Log.d(TAG, "handleAddProductOffers Success : " + documentReference);
        addProductPriceView.hanndleAddProductOffers();
        addProductToShops();
    }

    @SuppressLint("RxLeakedSubscription")
    public void addProductOffersExisting(String productID) {
        this.productID = productID;
        ProductDataModel productDataModel = addProductPriceView.getProductDataModel();
        productDataModel.setProductID(productID);
        addProducOffersExistingUseCase.execute(productDataModel, context)
                .doOnSubscribe(disposable -> addProductPriceView.showLoadingOffersProgress(true, "Adding Product..."))
                .subscribe(documentReference -> handleAddProductOffersExisting(documentReference), throwable -> handleOfferExistingError(throwable));
    }

    private void handleOfferExistingError(Throwable throwable) {
        Log.d(TAG, "handleOfferExistingError Error : " + throwable.getMessage());
        addProductPriceView.showLoadingOffersProgress(false, null);
    }

    private void handleAddProductOffersExisting(Boolean aBoolean) {
        addProductPriceView.showLoadingOffersProgress(false, null);
        Log.d(TAG, "handleAddProductOffersExisting Success : " + aBoolean);
        addProductPriceView.handleAppProductExisting(aBoolean);
        addProductToShops();
    }

    @SuppressLint("RxLeakedSubscription")
    public void addProductToShops() {
        ProductDataModel productDataModel = addProductPriceView.getProductDataModel();
        productDataModel.setProductID(productID);
        addProducToShopsUseCase.execute(productDataModel, context)
                .doOnSubscribe(disposable -> addProductPriceView.showLoadingOffersProgress(true, "Adding Product..."))
                .subscribe(documentReference -> handleAddProductToShopsResponse(documentReference), throwable -> handleToShopsError(throwable));
    }

    private void handleToShopsError(Throwable throwable) {
        Log.d(TAG, "handleToShopsError Error : " + throwable.getMessage());
    }

    private void handleAddProductToShopsResponse(Boolean status) {
        addProductPriceView.showLoadingOffersProgress(false, null);
        addProductToOffers();
    }

    @SuppressLint("RxLeakedSubscription")
    public void addProductToOffers() {
        ProductDataModel productDataModel = addProductPriceView.getProductDataModel();
        productDataModel.setProductID(productID);
        if (productDataModel.getShopDataModels().get(0).getOfferDataModel() != null) {
            addProducToOffersUseCase.execute(productDataModel, context)
                    .doOnSubscribe(disposable -> addProductPriceView.showLoadingOffersProgress(true, "Adding Product..."))
                    .subscribe(documentReference -> handleAddProductToOffersResponse(documentReference), throwable -> handleToOffersError(throwable));
        }
    }

    private void handleToOffersError(Throwable throwable) {
        Log.d(TAG, "handleToOffersError Error : " + throwable.getMessage());
    }

    private void handleAddProductToOffersResponse(Boolean documentReference) {
        addProductPriceView.showLoadingOffersProgress(false, null);
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, "handleError Error : " + throwable.getMessage());
        addProductPriceView.showLoadingOffersProgress(false, null);
    }
}
