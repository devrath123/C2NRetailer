package com.example.c2n.retailerhome.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.c2n.core.mapper.OfferDataModelToOfferViewModelMapper;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.offer_cards_list.domain.GetOfferCardListUseCase;
import com.example.c2n.retailerhome.domain.DeleteExpireOfferCardsUseCase;
import com.example.c2n.retailerhome.domain.GetOfferCardsUseCase;
import com.example.c2n.retailerhome.domain.RetailerHomeUseCase;
import com.example.c2n.viewshops.domain.ViewShopsUseCase;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 20-06-2018.
 */

public class RetailerHomeFragmentPresenter {

    private static final String TAG = RetailerHomeFragmentPresenter.class.getSimpleName();
    private GetOfferCardListUseCase getOfferCardListUseCase;
    ViewShopsUseCase viewShopsUseCase;
    RetailerHomeUseCase retailerHomeUseCase;
    GetOfferCardsUseCase getOfferCardsUseCase;
    DeleteExpireOfferCardsUseCase deleteExpireOfferCardsUseCase;

    OfferDataModelToOfferViewModelMapper offerDataModelToOfferViewModelMapper;
    RetailerHomeFragmentView retailerHomeFragmentView;
    List<OfferDataModel> activeOffersList = new ArrayList<>();
    Context context;
    int count;
    List<OfferDataModel> expireOfferCards = new ArrayList<>();


    @Inject
    RetailerHomeFragmentPresenter(ViewShopsUseCase viewShopsUseCase, GetOfferCardsUseCase getOfferCardsUseCase, DeleteExpireOfferCardsUseCase deleteExpireOfferCardsUseCase, OfferDataModelToOfferViewModelMapper offerDataModelToOfferViewModelMapper, RetailerHomeUseCase retailerHomeUseCase, GetOfferCardListUseCase getOfferCardListUseCase) {
        this.viewShopsUseCase = viewShopsUseCase;
        this.getOfferCardsUseCase = getOfferCardsUseCase;
        this.deleteExpireOfferCardsUseCase = deleteExpireOfferCardsUseCase;
        this.offerDataModelToOfferViewModelMapper = offerDataModelToOfferViewModelMapper;
        this.retailerHomeUseCase = retailerHomeUseCase;
        this.getOfferCardListUseCase = getOfferCardListUseCase;
    }

    public void bind(RetailerHomeFragmentView retailerHomeFragmentView, Context context) {
        this.retailerHomeFragmentView = retailerHomeFragmentView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadShops() {
        viewShopsUseCase.execute(null, context)
                .subscribe(this::handleResponse, throwable -> handleError(throwable));
    }

    /*@SuppressLint("RxLeakedSubscription")
    public void loadTestimonials() {
        retailerHomeUseCase.execute(null, context)
                .subscribe(this::handleTestimonialsResponse, throwable -> handleTestimonialError(throwable));
    }
*/
    @SuppressLint("RxLeakedSubscription")
    public void loadOfferCards() {
        getOfferCardListUseCase.execute(null, context)
                .subscribe(this::handleOfferCardsResponse, throwable -> handleOfferCardsError(throwable));
    }

    boolean flag = false;

    private void handleOfferCardsResponse(List<OfferDataModel> offerDataModels) {
        if (offerDataModels != null && offerDataModels.size() != 0) {
//            if (!flag1) {
//                flag1 = true;
            retailerHomeFragmentView.setAllOffersList(offerDataModels);
            Log.d(TAG, "handleOfferCardsResponse: " + offerDataModels.size());
            activeOffersList = offerDataModelToOfferViewModelMapper.mapOffersListToActiveOffersList(offerDataModels);
            if (activeOffersList != null && activeOffersList.size() != 0) {
                retailerHomeFragmentView.showLoadingOffersProgress(false, null);
                retailerHomeFragmentView.showOfferCards(activeOffersList);
                retailerHomeFragmentView.loadRecentShops();
            } else {
                retailerHomeFragmentView.initImageSlider();
                retailerHomeFragmentView.loadRecentShops();
                retailerHomeFragmentView.showLoadingOffersProgress(false, null);
            }
            return;
//            }
        }

        retailerHomeFragmentView.showLoadingOffersProgress(false, null);
        if (!flag) {
            flag = true;
            retailerHomeFragmentView.initImageSlider();
        }
        retailerHomeFragmentView.loadRecentShops();
    }

    private void handleOfferCardsError(Throwable throwable) {
        Log.d(TAG, "handleOfferCardsError: " + throwable.getMessage());
        retailerHomeFragmentView.initImageSlider();
        retailerHomeFragmentView.loadRecentShops();

    }


    @SuppressLint("RxLeakedSubscription")
    public void expireOfferCards() {
//        deleteExpireOfferCardsUseCase.execute(expireOfferCards.get(count).getOfferID(), context)
//                .subscribe(this::handleExpireOfferCardsResponse, throwable -> handleExpireOfferCardsError(throwable));
    }

    public void setExpiredOffersList() {
        if (expireOfferCards.size() != 0) {
            expireOfferCards.clear();
        }
        expireOfferCards = retailerHomeFragmentView.getExpireOfferCardsList();
        count = expireOfferCards.size() - 1;
        Log.d(TAG, "setExpiredOffersList: count* : " + count);
    }

    private void handleExpireOfferCardsResponse(DocumentReference documentReference) {
        Log.d(TAG, "handleExpireOfferCardsResponse: success.");

        if (count > 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    --count;
                    Log.d(TAG, "run: remove : " + count);
                    expireOfferCards();
                }
            }, 200);
//            --count;
//            removeOffer();
        } else {
//            offerProductsView.showUpdateProductOffer(false);
//            offerProductsView.startApplyOffer();
            Log.d(TAG, "handleExpireOfferCardsResponse: deleted");
        }
    }

    private void handleExpireOfferCardsError(Throwable throwable) {
        Log.d(TAG, "handleExpireOfferCardsError: " + throwable.getMessage());
//        retailerHomeFragmentView.initImageSlider();
//        retailerHomeFragmentView.loadRecentShops();

    }

    /* private void handleTestimonialError(Throwable throwable) {
         Log.d("RetailerTestimonial", "handleErrors :" + throwable.getMessage());
     }

     private void handleTestimonialsResponse(List<RetailerDataModel> retailerDataModels) {
         Log.d("RetailerHomeFragment_", "" + retailerDataModels.size());
         if (retailerDataModels != null || retailerDataModels.size() != 0) {
             retailerHomeFragmentView.showRetailerTestimonialList(retailerDataModels);
             return;
         }
     }
 */
    private void handleResponse(List<ShopDataModel> shopDataModels) {
        if (shopDataModels == null || shopDataModels.size() == 0) {
            retailerHomeFragmentView.enableFreshRetailerView();
//            retailerHomeFragmentView.loadTestimonialRetailerList();
            return;
        } else {
            retailerHomeFragmentView.showRecentShopsList(shopDataModels);
            Log.d(TAG, "handleResponse: " + shopDataModels.size());
//            retailerHomeFragmentView.loadTestimonialRetailerList();
        }
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, "handleError: " + throwable.getMessage());
        if (throwable.getMessage().equals("Provided document path must not be null."))
            retailerHomeFragmentView.enableFreshRetailerView();

    }
}
