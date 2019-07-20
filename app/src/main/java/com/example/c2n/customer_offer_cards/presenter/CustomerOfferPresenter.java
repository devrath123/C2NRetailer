package com.example.c2n.customer_offer_cards.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.core.mapper.ListOfferDataModelToListAppliedOfferViewMapper;
import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferDetailsDataModel;
import com.example.c2n.core.model1.OfferProductDataModel;
import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.customer_offer_cards.domain.GetOfferedProductsUseCase;
import com.example.c2n.customer_offer_cards.domain.GetOffersCardUseCase;
import com.example.c2n.customer_offer_cards.domain.GetRetailersIDUseCase;
import com.example.c2n.edit_profile.domain.EditProfileGetCustomerUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class CustomerOfferPresenter {

    private ListOfferDataModelToListAppliedOfferViewMapper listOfferDataModelToListAppliedOfferViewMapper;

    CustomerOfferView customerOfferView;
    Context context;
    GetRetailersIDUseCase getRetailersIDUseCase;
    GetOffersCardUseCase getOffersCardUseCase;
    EditProfileGetCustomerUseCase editProfileGetCustomerUseCase;
    GetOfferedProductsUseCase getOfferedProductsUseCase;
    List<OfferDetailsDataModel> offerDetailsDataModels = new ArrayList<>();
    List<HashMap<OfferDataModel, List<OfferProductDataModel>>> offeredProductsHashmapList = new ArrayList<>();
    List<HashMap<OfferDataModel, List<ProductDataModel>>> offeredProductsList = new ArrayList<>();
    List<ProductDataModel> productDataModelList = new ArrayList<>();

    List<String> retailerIDs = new ArrayList<>();
    int count;
    int offerCount;
    int productCount = 0;

    @Inject
    public CustomerOfferPresenter(GetRetailersIDUseCase getRetailersIDUseCase, GetOffersCardUseCase getOffersCardUseCase, EditProfileGetCustomerUseCase editProfileGetCustomerUseCase, GetOfferedProductsUseCase getOfferedProductsUseCase, ListOfferDataModelToListAppliedOfferViewMapper listOfferDataModelToListAppliedOfferViewMapper) {
        this.getRetailersIDUseCase = getRetailersIDUseCase;
        this.getOffersCardUseCase = getOffersCardUseCase;
        this.editProfileGetCustomerUseCase = editProfileGetCustomerUseCase;
        this.getOfferedProductsUseCase = getOfferedProductsUseCase;
        this.listOfferDataModelToListAppliedOfferViewMapper = listOfferDataModelToListAppliedOfferViewMapper;
    }

    public void bind(CustomerOfferView customerOfferView, Context context) {
        this.customerOfferView = customerOfferView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getRetailersCount() {
//        if (this.offeredProductsList.size() != 0) {
//            this.offeredProductsList.clear();
//        }
        getRetailersIDUseCase.execute(null, context)
                .subscribe(this::handleRetailersResponse, throwable -> handleError(throwable));
    }

    private void handleError(Throwable throwable) {
        Log.d("customerOfferPresenter", throwable.getMessage());
    }

    private void handleRetailersResponse(List<String> retailerIds) {
        if (this.retailerIDs.size() != 0)
            this.retailerIDs.clear();
        this.retailerIDs = retailerIds;
        Log.d("shops_retailer_ids", retailerIds.toString());
        if (this.retailerIDs.size() != 0) {
            count = this.retailerIDs.size() - 1;
            loadOffers();
        }
    }

    @SuppressLint("RxLeakedSubscription")
    private void loadOffers() {
        getOffersCardUseCase.execute(retailerIDs.get(count), context)
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    private void handleResponse(List<OfferDetailsDataModel> offerDetailsDataModels) {
//        customerOfferView.showProgress(false);
        this.offerDetailsDataModels.addAll(offerDetailsDataModels);
        if (count > 0) {
            --count;
//            customerOfferView.loadOffers(offerDataModels);
            loadOffers();
            Log.d("customerofferPresenter", "" + offerDetailsDataModels.size() + "--" + count);
        } else {
//            customerOfferView.loadOffers(listOfferDataModelToListAppliedOfferViewMapper.mapOfferModelToShopModel(this.offerDetailsDataModels));
            offeredProductsHashmapList = listOfferDataModelToListAppliedOfferViewMapper.mapOfferModelToShopModel(this.offerDetailsDataModels);
            if (this.offeredProductsHashmapList.size() != 0) {
                offerCount = this.offeredProductsHashmapList.size() - 1;
                productCount = this.offeredProductsHashmapList.get(offerCount).get(this.offeredProductsHashmapList.get(offerCount).keySet().iterator().next()).size() - 1;
                loadProducts();
            }
        }
    }

    @SuppressLint("RxLeakedSubscription")
    private void loadProducts() {
        getOfferedProductsUseCase.execute(this.offeredProductsHashmapList.get(offerCount)
                .get(this.offeredProductsHashmapList.get(offerCount).keySet().iterator().next()).get(productCount), context)
//                .map(productDataModelList -> new ArrayList(productDataModelList))
                .subscribe(this::handleProductsResponse, throwable -> handleError(throwable));
    }

    //    private void handleProductsResponse(final List<ProductDataModel> productDataModelList) {
//        OfferDataModel keyModel = this.offeredProductsHashmapList.get(offerCount).keySet().iterator().next();
//
//        Log.d("hashmap_offer", "" + keyModel + " --> " + productDataModelList.toString());
//        HashMap<OfferDataModel, List<ProductDataModel>> productsHashmap = new HashMap<>();
//        productsHashmap.put(keyModel, productDataModelList);
//        this.offeredProductsList.add(productsHashmap);
//        if (offerCount > 0) {
//            --offerCount;
//            loadProducts();
//            Log.d("customerofferProsPrsntr", "" + offeredProductsHashmapList.size() + "--" + offerCount);
//        } else {
//            Log.d("offered_products_list", this.offeredProductsList.toString());
//            customerOfferView.loadOffers(this.offeredProductsList);
//            // call further to display the offered products in Offers and Discounts.
//        }
//    }
    private void handleProductsResponse(ProductDataModel productDataModel) {
        productDataModelList.add(productDataModel);
        if (offerCount > 0 || productCount > 0) {

            if (productCount > 0) {
                --productCount;
                Log.d("product_count", "Count : " + productCount);
                loadProducts();
            } else {
                OfferDataModel keyModel = this.offeredProductsHashmapList.get(offerCount).keySet().iterator().next();

                Log.d("hashmap_offer", "" + keyModel + " --> " + productDataModelList.toString());
                HashMap<OfferDataModel, List<ProductDataModel>> productsHashmap = new HashMap<>();
                productsHashmap.put(keyModel, productDataModelList);
                this.offeredProductsList.add(productsHashmap);
//                if (productDataModelList.size() != 0)
                productDataModelList = new ArrayList<>();
                --offerCount;
                productCount = this.offeredProductsHashmapList.get(offerCount).get(this.offeredProductsHashmapList.get(offerCount).keySet().iterator().next()).size() - 1;
                loadProducts();
                Log.d("customerofferProsPrsntr", "" + offeredProductsHashmapList.size() + "--" + offerCount);
            }


        } else {
            if (offerCount == 0) {
                OfferDataModel keyModel = this.offeredProductsHashmapList.get(offerCount).keySet().iterator().next();

                Log.d("hashmap_offer", "" + keyModel + " --> " + productDataModelList.toString());
                HashMap<OfferDataModel, List<ProductDataModel>> productsHashmap = new HashMap<>();
                productsHashmap.put(keyModel, productDataModelList);
                this.offeredProductsList.add(productsHashmap);
            }
            Log.d("offered_products_list", this.offeredProductsList.toString());
            customerOfferView.loadOffers(this.offeredProductsList);
            // call further to display the offered products in Offers and Discounts.
        }
    }
}