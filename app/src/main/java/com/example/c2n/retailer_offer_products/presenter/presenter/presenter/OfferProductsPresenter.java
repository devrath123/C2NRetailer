package com.example.c2n.retailer_offer_products.presenter.presenter.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.c2n.core.model.CategoryDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.retailer_offer_products.presenter.presenter.domain.GetDiscountUseCase;
import com.example.c2n.retailer_offer_products.presenter.presenter.domain.OfferProductsApplyOfferUseCase;
import com.example.c2n.retailer_offer_products.presenter.presenter.domain.OfferProductsRemoveOfferUseCase;
import com.example.c2n.retailer_offer_products.presenter.presenter.domain.OfferProductsUseCase;
import com.example.c2n.retailer_offer_products.presenter.presenter.domain.RemoveOfferProductsUseCase;
import com.example.c2n.retailer_offer_products.presenter.presenter.domain.TodyasOfferProductsUseCase;
import com.example.c2n.retailer_shop_products_list.domain.RetailerShopProductsUseCase;
import com.example.c2n.retailercategory.domain.RetailerCategoryUseCase;
import com.example.c2n.viewshops.domain.ViewShopsUseCase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by roshan.nimje on 29-06-2018.
 */

public class OfferProductsPresenter {

    private OfferProductsApplyOfferUseCase offerProductsApplyOfferUseCase;
    private OfferProductsRemoveOfferUseCase offerProductsRemoveOfferUseCase;
    private RetailerCategoryUseCase retailerCategoryUseCase;
    private ViewShopsUseCase viewShopsUseCase;
    private RetailerCategoryUseCase getRetailerCategoryUseCase;
    private RetailerShopProductsUseCase retailerShopProductsUseCase;
    private OfferProductsUseCase offerProductsUseCase;
    private TodyasOfferProductsUseCase todyasOfferProductsUseCase;
    private OfferProductsView offerProductsView;
    private GetDiscountUseCase getDiscountUseCase;
    private RemoveOfferProductsUseCase removeOfferProductsUseCase;
    private Context context;

    private List<ProductDataModel> productDataModels = new ArrayList<>();
    private List<ProductDataModel> newProductDataModels = new ArrayList<>();

    ConnectableObservable<List<ProductDataModel>> productsObservable;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    OfferProductsPresenter(RetailerCategoryUseCase getRetailerCategoryUseCase, RemoveOfferProductsUseCase removeOfferProductsUseCase, RetailerCategoryUseCase retailerCategoryUseCase, ViewShopsUseCase viewShopsUseCase, RetailerShopProductsUseCase retailerShopProductsUseCase, OfferProductsUseCase offerProductsUseCase, TodyasOfferProductsUseCase todyasOfferProductsUseCase, GetDiscountUseCase getDiscountUseCase, OfferProductsApplyOfferUseCase offerProductsApplyOfferUseCase, OfferProductsRemoveOfferUseCase offerProductsRemoveOfferUseCase) {
        this.getRetailerCategoryUseCase = getRetailerCategoryUseCase;
        this.retailerCategoryUseCase = retailerCategoryUseCase;
        this.viewShopsUseCase = viewShopsUseCase;
        this.removeOfferProductsUseCase = removeOfferProductsUseCase;
        this.retailerShopProductsUseCase = retailerShopProductsUseCase;
        this.offerProductsUseCase = offerProductsUseCase;
        this.todyasOfferProductsUseCase = todyasOfferProductsUseCase;
        this.getDiscountUseCase = getDiscountUseCase;
        this.offerProductsApplyOfferUseCase = offerProductsApplyOfferUseCase;
        this.offerProductsRemoveOfferUseCase = offerProductsRemoveOfferUseCase;
    }

//    public void getProducts(String shopName, String categorySelected) {
//        offerProductsView.shopProductsList(productDataModels);
//        productsObservable = getProductsList(shopName, categorySelected).replay();
//        disposable.add(
//                productsObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<List<ProductDataModel>>() {
//
//                            @Override
//                            public void onNext(List<ProductDataModel> tickets) {
//                                offerProductsView.showProgress(false);
//
//                                // Refreshing list
//                                productDataModels.clear();
//                                productDataModels.addAll(tickets);
//
//                                offerProductsView.datahanged(productDataModels);
//                                offerProductsView.notifyDataSetChnaged();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
////                                showError(e);
//                                Log.d("OfferProductsPresenter_", "add1 " + e.getMessage());
//
//                            }
//
//                            @Override
//                            public void onComplete() {
////                                ticketsView.fetchDetails(airlineTicketsDataModels);
//                                offerProductsView.showProgress(false);
//                            }
//                        }));
//        disposable.add(
//                productsObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .flatMap(new Function<List<ProductDataModel>, ObservableSource<ProductDataModel>>() {
//                            @Override
//                            public ObservableSource<ProductDataModel> apply(List<ProductDataModel> productDataModels) throws Exception {
////                                Log.d("TicketsListPresenter", "1st " + airlineTicketsDataModels.size());
//                                return Observable.fromIterable(productDataModels);
//                            }
//                        })
//                        .flatMap(new Function<ProductDataModel, ObservableSource<ProductDataModel>>() {
//                            @Override
//                            public ObservableSource<ProductDataModel> apply(ProductDataModel productDataModel) throws Exception {
////                                Log.d("TicketsListPresenter", "2nd " + airlineTicketsDataModels.size());
//                                if (!productDataModel.getProductOfferID().equals("")) {
//                                    Log.d("OfferProductsPresenter_", "add2 if" + " " + productDataModel.getProductOfferID());
//                                    return getDiscount(productDataModel);
//                                } else {
//                                    Log.d("OfferProductsPresenter_", "add2 else");
//                                    return Observable.just(productDataModel);
//                                }
//                            }
//                        })
//                        .subscribeWith(new DisposableObserver<ProductDataModel>() {
//
//                            @Override
//                            public void onNext(ProductDataModel productDataModel) {
//                                offerProductsView.showProgress(false);
//
//                                int position = productDataModels.indexOf(productDataModel);
//                                if (position == -1) {
//                                    return;
//                                }
//                                productDataModels.set(position, productDataModel);
//                                offerProductsView.datahanged(productDataModels);
//                                offerProductsView.notifyDataSetChnaged();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.d("OfferProductsPresenter_", "add2 exception : " + e.getMessage());
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                offerProductsView.showProgress(false);
//                            }
//                        }));
//        productsObservable.connect();
//    }

//    private Observable<List<ProductDataModel>> getProductsList(String shopName, String categorySelected) {
//        return retailerShopProductsUseCase.execute(new String[]{shopName, categorySelected}, context)
//                .doOnSubscribe(disposable -> {
//                    offerProductsView.showProgress(true);
//                });
////                .subscribe(this::handleResponseProducts, throwable -> handleError(throwable));
//    }

//    private Observable<ProductDataModel> getDiscount(final ProductDataModel productDataModell) {
//        String userDocumentId = offerProductsView.getUserID();
//        String offerDocumentId = productDataModell.getProductOfferID();
//        return getDiscountUseCase.execute(new String[]{userDocumentId, offerDocumentId}, context)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Function<ProductDataModel, ProductDataModel>() {
//                    @Override
//                    public ProductDataModel apply(ProductDataModel productDataModel) throws Exception {
//                        Log.d("OfferProductsPresenter_", "discount " + productDataModel.getProductDiscount());
//                        productDataModell.setProductDiscount(productDataModel.getProductDiscount());
//                        return productDataModel;
//                    }
//                });
//    }

    public void bind(OfferProductsView offerProductsView, Context context) {
        this.offerProductsView = offerProductsView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getCategoryList() {
        retailerCategoryUseCase.execute(null, context)
                .doOnSubscribe(disposable -> offerProductsView.showProgress(true))
                .subscribe(this::handleResponseCategory1, throwable -> handleError(throwable));
    }

    private void handleResponseCategory1(List<ProductDataModel> productDataModels) {

    }

    private void handleResponseCategory(List<CategoryDataModel> categoryDataModels) {
        Log.d("OfferProductsPresenter_", "" + categoryDataModels.size());
//        offerProductsView.showShopCategory(categoryDataModels);
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadShops() {
        viewShopsUseCase.execute(offerProductsView.getUserID(), context)
                .doOnSubscribe(disposable -> offerProductsView.showProgress(true))
                .subscribe(this::handleResponseShopp, throwable -> handleError(throwable));
    }

    private void handleResponseShopp(List<ShopDataModel> shopDataModels) {
        Log.d("OfferProductsPresenter_", "" + shopDataModels.size());
        offerProductsView.showProgress(false);
        offerProductsView.showShopList(shopDataModels);
    }


    private void handleResponseShop(List<ShopDataModel> shopDataModels) {
        offerProductsView.showProgress(false);
        offerProductsView.showShopList(shopDataModels);
//        offerProductsView.getShopCategory();
        Log.d("OfferProductsPresenter_", "" + shopDataModels.size());
    }

    @SuppressLint("RxLeakedSubscription")
    public void loadProducts(String userID, String shopID) {
        getRetailerCategoryUseCase.execute(new String[]{userID, shopID}, context)
                .doOnSubscribe(disposable -> {
                    offerProductsView.showProgress(true);
                })
                .subscribe(this::handleResponseProducts, throwable -> handleError(throwable));
    }

    private void handleResponseProducts(List<ProductDataModel> productDataModels) {
        offerProductsView.showProgress(false);
        Log.d("OfferProductsPresenter_", "" + productDataModels.size());
        if (productDataModels.size() != 0)
            offerProductsView.shopCategoryList(productDataModels);
        else
            offerProductsView.setNoProductBackground();
    }

    //    @SuppressLint("RxLeakedSubscription")
//    public void loadTodaysProducts(String shopName, String categorySelected, String offerID) {
//
//        todyasOfferProductsUseCase.execute(new String[]{shopName, categorySelected, offerID}, context)
//                .doOnSubscribe(disposable -> {
//                    offerProductsView.showProgress(true);
//                })
//                .subscribe(this::handleResponseProducts, throwable -> handleError(throwable));
//    }
    int count1, count2;

    public void setProductList() {
        if (productDataModels.size() != 0) {
            productDataModels.clear();
        }
        productDataModels = offerProductsView.getProductList();
        count1 = offerProductsView.getProductList().size() - 1;
        Log.d("OfferProductsPresenter_", "count* : " + count1);
    }

    public void setNewProductList() {
        if (newProductDataModels.size() != 0) {
            newProductDataModels.clear();
        }
        newProductDataModels = offerProductsView.getProductList();
        count2 = newProductDataModels.size() - 1;
        Log.d("OfferProductsPresenter_", "count# : " + count2);
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateOffers() {
        Log.d("OfferProductsPresenter_", "" + "in update offer");
        offerProductsUseCase.execute(new Object[]{newProductDataModels.get(count2), offerProductsView.getUserID(), offerProductsView.getShopID()}, context)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposable -> {
//                    offerProductsView.showUpdateProductOffer(true);
                })
                .subscribe(this::handleResponseUpdateProduct, throwable -> handleError(throwable));
    }

    private void handleResponseUpdateProduct(DocumentReference documentReference) {
//        Log.d("");
        if (count2 > 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    --count2;
                    Log.d("OfferProductsPresenter_", "update : " + count2);
                    updateOffers();
                }
            }, 500);

        } else {
//            offerProductsView.showUpdateProductOffer(false);
//            offerProductsView.startOfferCardActivity();
        }
    }

    @SuppressLint("RxLeakedSubscription")
    public void removeOffer() {
        removeOfferProductsUseCase.execute(new Object[]{productDataModels.get(count1), offerProductsView.getUserID(), offerProductsView.getShopID()}, context)
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposable1 -> {
//                    offerProductsView.showUpdateProductOffer(true);
                })
                .subscribe(this::hamdleRemoveOfferResponse, throwable -> handleError(throwable));
    }

    private void hamdleRemoveOfferResponse(List<QueryDocumentSnapshot> queryDocumentSnapshots) {
        if (count1 > 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    --count1;
                    Log.d("OfferProductsPresenter_", "remove : " + count1);
                    removeOffer();
                }
            }, 500);
//            --count;
//            removeOffer();
        } else {
//            offerProductsView.showUpdateProductOffer(false);
            offerProductsView.startApplyOffer();
        }
    }

    @SuppressLint("RxLeakedSubscription")
    public void applyOffers() {
        Object[] objects = offerProductsView.getProductIDs();
        List<String> selectedProductIDs = (List<String>) objects[0];
        if (selectedProductIDs.size() != 0) {
            offerProductsApplyOfferUseCase.execute(offerProductsView.getProductIDs(), context)
                    .doOnSubscribe(disposable1 -> offerProductsView.showUpdateProductOffer(true))
                    .subscribe(this::handleApplyOfferResponse, throwable -> handleError(throwable));
        } else {
            handleApplyOfferResponse(true);
        }

    }

    private void handleApplyOfferResponse(Boolean flag) {
        offerProductsView.showUpdateProductOffer(false);
        offerProductsView.startOfferCardActivity();
    }

    @SuppressLint("RxLeakedSubscription")
    public void removeOffers() {
        Object[] objects = offerProductsView.getProductIDs();
        List<String> deselectedProductIDs = (List<String>) objects[3];
        if (deselectedProductIDs.size() != 0) {
            offerProductsRemoveOfferUseCase.execute(offerProductsView.getProductIDs(), context)
                    .doOnSubscribe(disposable1 -> offerProductsView.showUpdateProductOffer(true))
                    .subscribe(this::handleRemoveOfferResponse, throwable -> handleError(throwable));
        } else {
            applyOffers();
        }
    }

    private void handleRemoveOfferResponse(Boolean aBoolean) {
//        Object[] objects = offerProductsView.getProductIDs();
//        List<String> selectedProductIDs = (List<String>) objects[0];
//        if (selectedProductIDs.size() != 0) {
        offerProductsView.showUpdateProductOffer(false);
        applyOffers();
//        }
    }

    private void handleError(Throwable throwable) {
        Log.d("OfferProductsPresenter_", "" + throwable.getMessage());
    }

}
