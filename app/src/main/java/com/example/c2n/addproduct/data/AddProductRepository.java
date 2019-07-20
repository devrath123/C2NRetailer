package com.example.c2n.addproduct.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AddProductRepository {

    private static final String TAG = AddProductRepository.class.getSimpleName();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Inject
    public AddProductRepository() {
    }

    public Observable<String> addProductMaster(MasterProductDataModel masterProductDataModel) {
        String masterProductID = database.collection("masterproducts").document().getId();
        masterProductDataModel.setProductID(masterProductID);
        return Observable.create(emitter -> database.collection("masterproducts").document(masterProductID)
                .set(masterProductDataModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onNext(masterProductID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error : " + e.getMessage());
                    }
                }));
    }

    public Observable<String> addProductOffers(ProductDataModel productDataModel) {
        productDataModel.getShopDataModels().get(0).setProductsList(null);
        productDataModel.getShopDataModels().get(0).setProductMRP(productDataModel.getProductMRP());
        productDataModel.setProductMRP(0);
        return Observable.create(emitter -> database.collection("productoffers").document(productDataModel.getProductID())
                .set(productDataModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onNext(productDataModel.getProductID());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error : " + e.getMessage());
                    }
                }));
    }

    public Observable<Boolean> addProductOffersExisting(ProductDataModel productDataModel) {
        return Observable.create(emitter ->
                database.collection("productoffers").document(productDataModel.getProductID())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> shopDataModels = (List<HashMap<String, Object>>) documentSnapshot.get("shopDataModels");
                                if (shopDataModels != null) {
                                    Log.d(TAG, "shopDataModels Size : " + shopDataModels.size());
                                    boolean flag = false;
                                    for (int i = 0; i < shopDataModels.size(); i++) {
                                        if (productDataModel.getShopDataModels().get(0).getShopID().equals(shopDataModels.get(i).get("shopID"))) {
                                            flag = true;
                                        }
                                    }
                                    if (!flag) {
                                        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                                        if (productDataModel.getShopDataModels().get(0).getOfferDataModel() != null) {
                                            HashMap<String, Object> offerHashMap = new HashMap<>();
                                            offerHashMap.put("offerID", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferID());
                                            offerHashMap.put("offerDiscount", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferDiscount());
                                            offerHashMap.put("offerStartDate", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferStartDate());
                                            offerHashMap.put("offerEndDate", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferEndDate());
                                            offerHashMap.put("offerName", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferName());
                                            offerHashMap.put("sun", productDataModel.getShopDataModels().get(0).getOfferDataModel().isSun());
                                            offerHashMap.put("mon", productDataModel.getShopDataModels().get(0).getOfferDataModel().isMon());
                                            offerHashMap.put("tue", productDataModel.getShopDataModels().get(0).getOfferDataModel().isTue());
                                            offerHashMap.put("wed", productDataModel.getShopDataModels().get(0).getOfferDataModel().isWed());
                                            offerHashMap.put("thu", productDataModel.getShopDataModels().get(0).getOfferDataModel().isThu());
                                            offerHashMap.put("fri", productDataModel.getShopDataModels().get(0).getOfferDataModel().isFri());
                                            offerHashMap.put("sat", productDataModel.getShopDataModels().get(0).getOfferDataModel().isSat());
                                            offerHashMap.put("offerStatus", productDataModel.getShopDataModels().get(0).getOfferDataModel().isOfferStatus());
                                            offerHashMap.put("shopDataModels", null);
                                            stringObjectHashMap.put("offerDataModel", offerHashMap);
                                        } else {
                                            stringObjectHashMap.put("offerDataModel", null);
                                        }
                                        stringObjectHashMap.put("shopEmail", productDataModel.getShopDataModels().get(0).getShopEmail());
                                        stringObjectHashMap.put("productsList", null);
                                        stringObjectHashMap.put("retailerID", productDataModel.getShopDataModels().get(0).getRetailerID());
                                        stringObjectHashMap.put("shopAddress", productDataModel.getShopDataModels().get(0).getShopAddress());
                                        stringObjectHashMap.put("shopCellNo", productDataModel.getShopDataModels().get(0).getShopCellNo());
                                        stringObjectHashMap.put("shopID", productDataModel.getShopDataModels().get(0).getShopID());
                                        stringObjectHashMap.put("shopImageURL", productDataModel.getShopDataModels().get(0).getShopImageURL());
                                        stringObjectHashMap.put("shopLatitude", productDataModel.getShopDataModels().get(0).getShopLatitude());
                                        stringObjectHashMap.put("shopLongitude", productDataModel.getShopDataModels().get(0).getShopLongitude());
                                        stringObjectHashMap.put("shopName", productDataModel.getShopDataModels().get(0).getShopName());
                                        stringObjectHashMap.put("productMRP", productDataModel.getProductMRP());
                                        shopDataModels.add(stringObjectHashMap);
                                        database.collection("productoffers").document(productDataModel.getProductID())
                                                .update("shopDataModels", shopDataModels)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        emitter.onNext(false);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "Error : " + e.getMessage());
//                                                        emitter.onError(e);
                                                    }
                                                });
                                    } else {
                                        emitter.onNext(true);
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "addProductOffersExisting Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<Boolean> addProductToShops(ProductDataModel productDataModel, String retailerID) {
        return Observable.create(emitter ->
                database.collection("shops").document(retailerID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                                for (int i = 0; i < shops.size(); i++) {
                                    HashMap<String, Object> singleShop = shops.get(i);
//                                    Log.d(TAG, "addProductToShops Success : " + singleShop.get("shopID"));
                                    if (singleShop.get("shopID").equals(productDataModel.getShopDataModels().get(0).getShopID())) {
//                                        Log.d(TAG, "addProductToShops Success : " + singleShop.get("productsList"));
                                        if (singleShop.get("productsList") == null) {
                                            List<HashMap<String, Object>> productsList = new ArrayList<>();
                                            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                                            if (productDataModel.getProductOffer() != null) {
                                                HashMap<String, Object> offerHashMap = new HashMap<>();
                                                offerHashMap.put("offerID", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferID());
                                                offerHashMap.put("offerDiscount", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferDiscount());
                                                offerHashMap.put("offerStartDate", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferStartDate());
                                                offerHashMap.put("offerEndDate", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferEndDate());
                                                offerHashMap.put("offerName", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferName());
                                                offerHashMap.put("sun", productDataModel.getShopDataModels().get(0).getOfferDataModel().isSun());
                                                offerHashMap.put("mon", productDataModel.getShopDataModels().get(0).getOfferDataModel().isMon());
                                                offerHashMap.put("tue", productDataModel.getShopDataModels().get(0).getOfferDataModel().isTue());
                                                offerHashMap.put("wed", productDataModel.getShopDataModels().get(0).getOfferDataModel().isWed());
                                                offerHashMap.put("thu", productDataModel.getShopDataModels().get(0).getOfferDataModel().isThu());
                                                offerHashMap.put("fri", productDataModel.getShopDataModels().get(0).getOfferDataModel().isFri());
                                                offerHashMap.put("sat", productDataModel.getShopDataModels().get(0).getOfferDataModel().isSat());
                                                offerHashMap.put("offerStatus", productDataModel.getShopDataModels().get(0).getOfferDataModel().isOfferStatus());
                                                offerHashMap.put("shopDataModels", null);
                                                stringObjectHashMap.put("productOffer", offerHashMap);
                                            } else {
                                                stringObjectHashMap.put("productOffer", null);
                                            }
                                            stringObjectHashMap.put("productID", productDataModel.getProductID());
                                            stringObjectHashMap.put("productName", productDataModel.getProductName());
                                            stringObjectHashMap.put("productImageURL", productDataModel.getProductImageURL());
                                            stringObjectHashMap.put("productMRP", productDataModel.getProductMRP());
                                            stringObjectHashMap.put("productCategory", productDataModel.getProductCategory());
                                            stringObjectHashMap.put("productDescription", productDataModel.getProductDescription());
                                            stringObjectHashMap.put("productStockStatus", productDataModel.getProductStockStatus());
                                            stringObjectHashMap.put("productOfferStatus", productDataModel.getProductOfferStatus());
                                            stringObjectHashMap.put("shopDataModels", null);
                                            productsList.add(stringObjectHashMap);
                                            singleShop.put("productsList", productsList);
                                            database.collection("shops").document(retailerID)
                                                    .update("retailerShops", shops)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "addProductToShops : New productList created");
                                                            emitter.onNext(true);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d(TAG, "addProductToShops Error : " + e.getMessage());
                                                        }
                                                    });
                                        } else {
                                            List<HashMap<String, Object>> productList = (List<HashMap<String, Object>>) singleShop.get("productsList");
                                            boolean flag = false;
                                            for (int j = 0; j < productList.size(); j++) {
                                                HashMap<String, Object> singleProduct = productList.get(j);
                                                if (singleProduct.get("productID").equals(productDataModel.getProductID())) {
                                                    flag = true;
                                                }
                                            }
                                            if (!flag) {
                                                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                                                if (productDataModel.getShopDataModels().get(0).getOfferDataModel() != null) {
                                                    HashMap<String, Object> offerHashMap = new HashMap<>();
                                                    offerHashMap.put("offerID", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferID());
                                                    offerHashMap.put("offerDiscount", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferDiscount());
                                                    offerHashMap.put("offerStartDate", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferStartDate());
                                                    offerHashMap.put("offerEndDate", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferEndDate());
                                                    offerHashMap.put("offerName", productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferName());
                                                    offerHashMap.put("sun", productDataModel.getShopDataModels().get(0).getOfferDataModel().isSun());
                                                    offerHashMap.put("mon", productDataModel.getShopDataModels().get(0).getOfferDataModel().isMon());
                                                    offerHashMap.put("tue", productDataModel.getShopDataModels().get(0).getOfferDataModel().isTue());
                                                    offerHashMap.put("wed", productDataModel.getShopDataModels().get(0).getOfferDataModel().isWed());
                                                    offerHashMap.put("thu", productDataModel.getShopDataModels().get(0).getOfferDataModel().isThu());
                                                    offerHashMap.put("fri", productDataModel.getShopDataModels().get(0).getOfferDataModel().isFri());
                                                    offerHashMap.put("sat", productDataModel.getShopDataModels().get(0).getOfferDataModel().isSat());
                                                    offerHashMap.put("offerStatus", productDataModel.getShopDataModels().get(0).getOfferDataModel().isOfferStatus());
                                                    offerHashMap.put("shopDataModels", null);
                                                    stringObjectHashMap.put("productOffer", offerHashMap);
                                                } else {
                                                    stringObjectHashMap.put("productOffer", null);
                                                }
                                                stringObjectHashMap.put("productID", productDataModel.getProductID());
                                                stringObjectHashMap.put("productName", productDataModel.getProductName());
                                                stringObjectHashMap.put("productImageURL", productDataModel.getProductImageURL());
                                                stringObjectHashMap.put("productMRP", productDataModel.getProductMRP());
                                                stringObjectHashMap.put("productCategory", productDataModel.getProductCategory());
                                                stringObjectHashMap.put("productDescription", productDataModel.getProductDescription());
                                                stringObjectHashMap.put("productStockStatus", productDataModel.getProductStockStatus());
                                                stringObjectHashMap.put("productOfferStatus", productDataModel.getProductOfferStatus());
                                                stringObjectHashMap.put("shopDataModels", null);
                                                productList.add(stringObjectHashMap);
                                                database.collection("shops").document(retailerID)
                                                        .update("retailerShops", shops)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "addProductToShops : Product added in existing list");
                                                                emitter.onNext(true);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d(TAG, "addProductToShops Error : " + e.getMessage());
                                                            }
                                                        });
                                            } else {
                                                Log.d(TAG, "addProductToShops : Product already exist");
                                                emitter.onNext(false);
                                            }
                                        }
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "addProductToShops Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<Boolean> addProductToOffers(ProductDataModel productDataModel, String retailerID) {
        return Observable.create(emitter ->
                database.collection("offers").document(retailerID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
//                                Log.d(TAG, "addProductToOffers OfferID : " + productDataModel.toString());
                                for (int i = 0; i < offers.size(); i++) {
                                    if (offers.get(i).get("offerID").equals(productDataModel.getShopDataModels().get(0).getOfferDataModel().getOfferID())) {
                                        if (offers.get(i).get("shopDataModels") != null) {
                                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offers.get(i).get("shopDataModels");
                                            boolean shopFlag = false;
                                            for (int j = 0; j < shops.size(); j++) {
                                                if (shops.get(j).get("shopID").equals(productDataModel.getShopDataModels().get(0).getShopID())) {
                                                    shopFlag = true;
                                                    List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shops.get(i).get("productsList");
                                                    boolean productFlag = false;
                                                    for (int k = 0; k < products.size(); k++) {
                                                        if (products.get(k).get("productID").equals(productDataModel.getProductID())) {
                                                            productFlag = true;
                                                        }
                                                    }
                                                    if (!productFlag) {
                                                        HashMap<String, Object> product = new HashMap<>();
                                                        product.put("productID", productDataModel.getProductID());
                                                        product.put("productName", productDataModel.getProductName());
                                                        product.put("productImageURL", productDataModel.getProductImageURL());
                                                        product.put("productMRP", productDataModel.getProductMRP());
                                                        product.put("productCategory", productDataModel.getProductCategory());
                                                        product.put("productDescription", productDataModel.getProductDescription());
                                                        product.put("productStockStatus", productDataModel.getProductStockStatus());
                                                        product.put("productOfferStatus", productDataModel.getProductOfferStatus());
                                                        product.put("shopDataModels", null);
                                                        product.put("productOffer", null);
                                                        products.add(product);

                                                        database.collection("offers").document(retailerID)
                                                                .update("offersList", offers)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Log.d(TAG, "addProductToOffers : New product created");
                                                                        emitter.onNext(true);
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {

                                                                    }
                                                                });
                                                    } else {
                                                        emitter.onNext(true);
                                                    }
                                                }
                                            }
                                            if (!shopFlag) {
//                                            List<HashMap<String, Object>> shopList = new ArrayList<>();
                                                HashMap<String, Object> shop = new HashMap<>();
                                                shop.put("offerDataModel", null);
                                                shop.put("shopEmail", productDataModel.getShopDataModels().get(0).getShopEmail());
                                                shop.put("retailerID", productDataModel.getShopDataModels().get(0).getRetailerID());
                                                shop.put("shopAddress", productDataModel.getShopDataModels().get(0).getShopAddress());
                                                shop.put("shopCellNo", productDataModel.getShopDataModels().get(0).getShopCellNo());
                                                shop.put("shopID", productDataModel.getShopDataModels().get(0).getShopID());
                                                shop.put("shopImageURL", productDataModel.getShopDataModels().get(0).getShopImageURL());
                                                shop.put("shopLatitude", productDataModel.getShopDataModels().get(0).getShopLatitude());
                                                shop.put("shopLongitude", productDataModel.getShopDataModels().get(0).getShopLongitude());
                                                shop.put("shopName", productDataModel.getShopDataModels().get(0).getShopName());

                                                List<HashMap<String, Object>> products = new ArrayList<>();
                                                HashMap<String, Object> product = new HashMap<>();
                                                product.put("productID", productDataModel.getProductID());
                                                product.put("productName", productDataModel.getProductName());
                                                product.put("productImageURL", productDataModel.getProductImageURL());
                                                product.put("productMRP", productDataModel.getProductMRP());
                                                product.put("productCategory", productDataModel.getProductCategory());
                                                product.put("productDescription", productDataModel.getProductDescription());
                                                product.put("productStockStatus", productDataModel.getProductStockStatus());
                                                product.put("productOfferStatus", productDataModel.getProductOfferStatus());
                                                product.put("shopDataModels", null);
                                                product.put("productOffer", null);
                                                products.add(product);

                                                shop.put("productsList", products);
                                                shops.add(shop);

                                                database.collection("offers").document(retailerID)
                                                        .update("offersList", offers)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "addProductToOffers : New shop created");
                                                                emitter.onNext(true);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {

                                                            }
                                                        });
                                            } else {
                                                emitter.onNext(true);
                                            }
                                        } else {
                                            List<HashMap<String, Object>> shops = new ArrayList<>();
                                            HashMap<String, Object> shop = new HashMap<>();
                                            shop.put("offerDataModel", null);
                                            shop.put("shopEmail", productDataModel.getShopDataModels().get(0).getShopEmail());
                                            shop.put("retailerID", productDataModel.getShopDataModels().get(0).getRetailerID());
                                            shop.put("shopAddress", productDataModel.getShopDataModels().get(0).getShopAddress());
                                            shop.put("shopCellNo", productDataModel.getShopDataModels().get(0).getShopCellNo());
                                            shop.put("shopID", productDataModel.getShopDataModels().get(0).getShopID());
                                            shop.put("shopImageURL", productDataModel.getShopDataModels().get(0).getShopImageURL());
                                            shop.put("shopLatitude", productDataModel.getShopDataModels().get(0).getShopLatitude());
                                            shop.put("shopLongitude", productDataModel.getShopDataModels().get(0).getShopLongitude());
                                            shop.put("shopName", productDataModel.getShopDataModels().get(0).getShopName());

                                            List<HashMap<String, Object>> products = new ArrayList<>();
                                            HashMap<String, Object> product = new HashMap<>();
                                            product.put("productID", productDataModel.getProductID());
                                            product.put("productName", productDataModel.getProductName());
                                            product.put("productImageURL", productDataModel.getProductImageURL());
                                            product.put("productMRP", productDataModel.getProductMRP());
                                            product.put("productCategory", productDataModel.getProductCategory());
                                            product.put("productDescription", productDataModel.getProductDescription());
                                            product.put("productStockStatus", productDataModel.getProductStockStatus());
                                            product.put("productOfferStatus", productDataModel.getProductOfferStatus());
                                            product.put("shopDataModels", null);
                                            product.put("productOffer", null);
                                            products.add(product);

                                            shop.put("productsList", products);
                                            shops.add(shop);

                                            offers.get(i).put("shopDataModels", shops);
                                            database.collection("offers").document(retailerID)
                                                    .update("offersList", offers)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "addProductToOffers : New shop created");
                                                            emitter.onNext(true);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });
                                        }
                                    }
                                }
                            }
                        })
                        .

                                addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        emitter.onError(e);
                                    }
                                }));
    }
}
