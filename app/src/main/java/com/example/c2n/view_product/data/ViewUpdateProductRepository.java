package com.example.c2n.view_product.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 24-06-2018.
 */

public class ViewUpdateProductRepository {

    private final String TAG = "ViewUpdateProductReposi";

    DocumentReference reference;
    DocumentReference offer;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = firebaseFirestore.collection("users").document();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();


    @Inject
    public ViewUpdateProductRepository() {
    }

//    public Observable<List<QueryDocumentSnapshot>> getProducts(ProductDataModel productDataModel) {
//        Log.d("update_offer_pro.", productDataModel.toString());
//
//        return Observable.create(emitter ->
//                firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        documentList.add(document);
//                                        Log.d("QueryDocSnap(products)-", document.getId() + " => " + document.getData());
//                                    }
//                                    emitter.onNext(documentList);
//                                } else {
//                                    emitter.onError(task.getException());
//                                    Log.e("Error(products)..- ", "Error getting documents.", task.getException());
//                                }
//
//                            }
//                        }));
//    }

    public Observable<Boolean> updateProductDetails(String retailerEmail, ProductDataModel productDataModel, String shopID) {
        firebaseFirestore.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            if (productDataModel.getProductID().equals(queryDocumentSnapshot.getId())) {
                                if (queryDocumentSnapshot.get("shopDataModels") != null) {
                                    List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                                    for (int i = 0; i < shops.size(); i++) {
                                        HashMap<String, Object> shop = shops.get(i);
                                        if (shop.get("shopID").equals(shopID)) {
                                            shop.put("productMRP", productDataModel.getProductMRP());
                                        }
                                    }
                                    firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
                                            .update("shopDataModels", shops)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "updateProductOffer productoffers Success");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "updateProductOffer productoffers Error : " + e.getMessage());
                                                }
                                            });
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "updateProductOffer productoffers Error : " + e.getMessage());
                    }
                });

        firebaseFirestore.collection("shops").document(retailerEmail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            HashMap<String, Object> shop = shops.get(i);
                            if (shop.get("shopID").equals(shopID)) {
                                if (shop.get("productsList") != null) {
                                    List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                    for (int j = 0; j < products.size(); j++) {
                                        HashMap<String, Object> product = products.get(j);
                                        if (product.get("productID").equals(productDataModel.getProductID())) {
                                            product.put("productMRP", productDataModel.getProductMRP());
                                        }
                                    }
                                }
                            }
                        }
                        firebaseFirestore.collection("shops").document(retailerEmail)
                                .update("retailerShops", shops)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "updateProductOffer Shops Success");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "updateProductOffer Shops Error : " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "updateProductOffer Shops Error : " + e.getMessage());
                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("offers").document(retailerEmail)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (productDataModel.getProductOffer() != null) {
                                    List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                    Log.d(TAG, "updateProductOffer_ Offers List : " + offers.size());
                                    for (int i = 0; i < offers.size(); i++) {
                                        HashMap<String, Object> offer = offers.get(i);
                                        if (offer.get("offerID").equals(productDataModel.getProductOffer().getOfferID())) {
                                            Log.d(TAG, "updateProductOffer_ OfferID  : " + offer.get("offerID") + "  - " + productDataModel.getProductOffer().getOfferID());
                                            if (offer.get("shopDataModels") != null || ((List<HashMap<String, Object>>) offer.get("shopDataModels") != null)) {
                                                List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                                                for (int j = 0; j < shops.size(); j++) {
                                                    HashMap<String, Object> shop = shops.get(j);
                                                    if (shop.get("shopID").equals(shopID)) {
                                                        if (shop.get("productsList") != null) {
                                                            List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                                            for (int k = 0; k < products.size(); k++) {
                                                                HashMap<String, Object> product = products.get(k);
                                                                if (product.get("productID").equals(productDataModel.getProductID())) {
                                                                    product.put("productMRP", productDataModel.getProductMRP());
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        firebaseFirestore.collection("offers").document(retailerEmail)
                                                .update("offersList", offers)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "updateProductOffer offers Success");
                                                        emitter.onNext(true);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "updateProductOffer offers Error : " + e.getMessage());
                                                        emitter.onError(e);
                                                    }
                                                });
                                    }
                                } else {
                                    emitter.onNext(true);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.d(TAG, "updateProductOffer offers Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));

//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
//                                Log.d(TAG, "updateProductOffer_ Offers List : " + offers.size());
//                                for (int i = 0; i < offers.size(); i++) {
//                                    HashMap<String, Object> offer = offers.get(i);
//                                    if (offer.get("offerID").equals(productDataModel.getProductOffer().getOfferID())) {
//                                        Log.d(TAG, "updateProductOffer_ OfferID  : " + offer.get("offerID") + "  - " + productDataModel.getProductOffer().getOfferID());
//                                        if (offer.get("shopDataModels") != null || ((List<HashMap<String, Object>>) offer.get("shopDataModels") != null)) {
//                                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
//                                            for (int j = 0; j < shops.size(); j++) {
//                                                HashMap<String, Object> shop = shops.get(j);
//                                                if (shop.get("shopID").equals(shopID)) {
//                                                    if (shop.get("productsList") != null) {
//                                                        List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
//                                                        for (int k = 0; k < products.size(); k++) {
//                                                            HashMap<String, Object> product = products.get(k);
//                                                            if (product.get("productID").equals(productDataModel.getProductID())) {
//                                                                product.put("productMRP", productDataModel.getProductMRP());
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                    firebaseFirestore.collection("offers").document(retailerEmail)
//                                            .update("offersList", offers)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d(TAG, "updateProductOffer offers Success");
//                                                    emitter.onNext(true);
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d(TAG, "updateProductOffer offers Error : " + e.getMessage());
//                                                    emitter.onError(e);
//                                                }
//                                            });
//                                }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d(TAG, "updateProductOffer offers Error : " + e.getMessage());
//                                emitter.onError(e);
//                            }
//                        }));

//        return Observable.create(
//                emitter -> {
//                    reference = firebaseFirestore.collection("products").document(retailerEmail).collection(shopEmail).document(productDataModel.getProductID());
//                    reference.update("productName", productDataModel.getProductName());
//                    reference.update("productMRP", productDataModel.getProductMRP());
//                    reference.update("productImageURL", productDataModel.getProductImageURL());
//                    reference.update("productDescription", productDataModel.getProductDescription());
////        reference.update("productOfferStatus", productDataModel.getProductOfferStatus());
//                    reference.update("productCategory", productDataModel.getProductCategory())
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    emitter.onNext(documentReference);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    emitter.onError(e);
//                                }
//                            });
//                });
    }

    public Observable<DocumentSnapshot> getOfferCard(String userDocumentId, String offerDocumentId) {
        return Observable.create(
                emitter -> {
                    firebaseFirestore.collection("offers").document(userDocumentId)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Log.d("getOffer", "success");
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    Log.d("getOffer", "success " + documentSnapshot.get("userMobileNo"));

//                                    if (documentSnapshot.exists()) {
                                    emitter.onNext(documentSnapshot);
//                                    } else {
//
//                                    }
                                }
                            })
//
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("getOffer", "" + e.getMessage());
                                    emitter.onError(e);
                                }
                            });
                }
        );
    }

//    public Observable<DocumentReference> updateProductOffer(ProductDataModel productDataModel, String retailerEmail, String shopEmail) {
//        reference = firebaseFirestore.collection("products").document(retailerEmail).collection(shopEmail).document(productDataModel.getProductID());
//
//        Map<String, Object> productOffer = new HashMap<>();
//        productOffer.put("offerID", productDataModel.getProductOffer().getOfferID());
//        productOffer.put("offerDiscount", productDataModel.getProductOffer().getOfferDiscount());
//        productOffer.put("mon", productDataModel.getProductOffer().isMon());
//        productOffer.put("tue", productDataModel.getProductOffer().isTue());
//        productOffer.put("wed", productDataModel.getProductOffer().isWed());
//        productOffer.put("thu", productDataModel.getProductOffer().isThu());
//        productOffer.put("fri", productDataModel.getProductOffer().isFri());
//        productOffer.put("sat", productDataModel.getProductOffer().isSat());
//        productOffer.put("sun", productDataModel.getProductOffer().isSun());
//        productOffer.put("offerStartDate", productDataModel.getProductOffer().getOfferStartDate());
//        productOffer.put("offerEndDate", productDataModel.getProductOffer().getOfferEndDate());
//        productOffer.put("offerName", productDataModel.getProductOffer().getOfferName());
//        productOffer.put("offerStatus", productDataModel.getProductOffer().isOfferStatus());
//        productOffer.put("offerProducts", null);
//        reference.update("productOfferStatus", productDataModel.getProductOffer().isOfferStatus());
//        return Observable.create(emitter ->
//                reference.update("productOffer", productOffer)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//
//
//                                firebaseFirestore.collection("offers").document(retailerEmail).collection("templates").document(productDataModel.getProductOffer().getOfferID())
//                                        .get()
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                List<HashMap<String, List<String>>> offerProducts = new ArrayList<>();
//
//                                                if (documentSnapshot.get("offerProducts") == null || ((List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts")).isEmpty()) {
//                                                    offer = firebaseFirestore.collection("offers").document(retailerEmail).collection("templates").document(productDataModel.getProductOffer().getOfferID());
//
//                                                    HashMap<String, List<String>> shopsId = new HashMap<>();
//                                                    List<String> productsId = new ArrayList<>();
//                                                    productsId.add(productDataModel.getProductID());
//                                                    shopsId.put(shopEmail, productsId);
////
//                                                    offerProducts.add(shopsId);
//                                                    Log.d("offer_products", offerProducts.toString());
////
//                                                    offer.update("offerProducts", offerProducts).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            Log.d("Product_offer_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//                                                            emitter.onComplete();
//                                                        }
//                                                    }).addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Log.w("Error_adding_poffer-", "...Error adding document-" + e.getMessage());
//                                                            emitter.onError(e);
//                                                        }
//                                                    });
//                                                } else {
//                                                    List<String> productsIdList = new ArrayList<>();
//                                                    offerProducts = new ArrayList<>();
//                                                    Boolean isOldShop = false;
//                                                    List<HashMap<String, List<String>>> offerProductsList = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                                    for (int i = 0; i < offerProductsList.size(); i++) {
//                                                        HashMap<String, List<String>> shopHashmap = (HashMap<String, List<String>>) offerProductsList.get(i);
//                                                        Set<String> shopKeys = shopHashmap.keySet();
//                                                        String shopKey = shopKeys.iterator().next();
//                                                        if (shopKey.equals(shopEmail)) {
//                                                            productsIdList = shopHashmap.get(shopEmail);
//                                                            if (!productsIdList.contains(productDataModel.getProductID()))
//                                                                productsIdList.add(productDataModel.getProductID());
//                                                            Log.d("productsIdList", productsIdList.toString());
//                                                            shopHashmap.put(shopEmail, productsIdList);
//                                                            isOldShop = true;
//                                                        }
//                                                        offerProducts.add(shopHashmap);
//
//                                                        Log.d("offer_products", offerProducts.toString());
//                                                    }
//                                                    if (!isOldShop) {
//                                                        HashMap<String, List<String>> shopsId = new HashMap<>();
//                                                        List<String> productsId = new ArrayList<>();
//                                                        productsId.add(productDataModel.getProductID());
//                                                        shopsId.put(shopEmail, productsId);
//                                                        offerProducts.add(shopsId);
//                                                    }
//                                                    offer = firebaseFirestore.collection("offers").document(retailerEmail).collection("templates").document(productDataModel.getProductOffer().getOfferID());
//
//                                                    offer.update("offerProducts", offerProducts).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            Log.d("Product_offer_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//                                                            emitter.onComplete();
//                                                        }
//                                                    }).addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Log.w("Error_adding_poffer-", "...Error adding document-" + e.getMessage());
//                                                            emitter.onError(e);
//                                                        }
//                                                    });
//
//                                                    Log.d("list_offers", "not_empty-" + documentSnapshot.get("offerProducts").toString());
//
//                                                }
//                                                Log.d("Product_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//                                                emitter.onNext(documentReference);
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w("Error_geting_poffer-", "...Error-" + e.getMessage());
//                                        emitter.onError(e);
//                                    }
//                                });
//
//
////                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("updateShopDetails", "" + e.getMessage());
//                                emitter.onError(e);
//                            }
//                        }));
//    }

    public Observable<Boolean> updateProductOffer(ProductDataModel productDataModel, ShopDataModel shopDataModel, OfferDataModel offerDataModel, String retailerID) {
        firebaseFirestore.collection("shops").document(retailerID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            HashMap<String, Object> shop = shops.get(i);
                            if (shop.get("shopID").equals(shopDataModel.getShopID())) {
                                if (shop.get("productsList") != null) {
                                    List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                    for (int j = 0; j < products.size(); j++) {
                                        HashMap<String, Object> product = products.get(j);
                                        if (product.get("productID").equals(productDataModel.getProductID())) {

                                            HashMap<String, Object> offer = new HashMap<>();
                                            offer.put("offerID", offerDataModel.getOfferID());
                                            offer.put("offerDiscount", offerDataModel.getOfferDiscount());
                                            offer.put("offerStartDate", offerDataModel.getOfferStartDate());
                                            offer.put("offerEndDate", offerDataModel.getOfferEndDate());
                                            offer.put("offerName", offerDataModel.getOfferName());
                                            offer.put("sun", offerDataModel.isSun());
                                            offer.put("mon", offerDataModel.isMon());
                                            offer.put("tue", offerDataModel.isTue());
                                            offer.put("wed", offerDataModel.isWed());
                                            offer.put("thu", offerDataModel.isThu());
                                            offer.put("fri", offerDataModel.isFri());
                                            offer.put("sat", offerDataModel.isSat());
                                            offer.put("offerStatus", offerDataModel.isOfferStatus());
                                            offer.put("shopDataModels", null);

                                            product.put("productOffer", offer);
                                        }
                                    }
                                } else {
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

                                    HashMap<String, Object> offer = new HashMap<>();
                                    offer.put("offerID", offerDataModel.getOfferID());
                                    offer.put("offerDiscount", offerDataModel.getOfferDiscount());
                                    offer.put("offerStartDate", offerDataModel.getOfferStartDate());
                                    offer.put("offerEndDate", offerDataModel.getOfferEndDate());
                                    offer.put("offerName", offerDataModel.getOfferName());
                                    offer.put("sun", offerDataModel.isSun());
                                    offer.put("mon", offerDataModel.isMon());
                                    offer.put("tue", offerDataModel.isTue());
                                    offer.put("wed", offerDataModel.isWed());
                                    offer.put("thu", offerDataModel.isThu());
                                    offer.put("fri", offerDataModel.isFri());
                                    offer.put("sat", offerDataModel.isSat());
                                    offer.put("offerStatus", offerDataModel.isOfferStatus());
                                    offer.put("shopDataModels", null);

                                    product.put("productOffer", offer);
                                    products.add(product);
                                    shop.put("productsList", products);
                                }
                            }
                        }
                        firebaseFirestore.collection("shops").document(retailerID)
                                .update("retailerShops", shops)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "updateProductOffer Shops Success");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "updateProductOffer Shops Error : " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "updateProductOffer Shops Error : " + e.getMessage());
                    }
                });

        firebaseFirestore.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            if (productDataModel.getProductID().equals(queryDocumentSnapshot.getId())) {
                                if (queryDocumentSnapshot.get("shopDataModels") != null) {
                                    List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                                    for (int i = 0; i < shops.size(); i++) {
                                        HashMap<String, Object> shop = shops.get(i);
                                        if (shop.get("shopID").equals(shopDataModel.getShopID())) {

                                            HashMap<String, Object> offer = new HashMap<>();
                                            offer.put("offerID", offerDataModel.getOfferID());
                                            offer.put("offerDiscount", offerDataModel.getOfferDiscount());
                                            offer.put("offerStartDate", offerDataModel.getOfferStartDate());
                                            offer.put("offerEndDate", offerDataModel.getOfferEndDate());
                                            offer.put("offerName", offerDataModel.getOfferName());
                                            offer.put("sun", offerDataModel.isSun());
                                            offer.put("mon", offerDataModel.isMon());
                                            offer.put("tue", offerDataModel.isTue());
                                            offer.put("wed", offerDataModel.isWed());
                                            offer.put("thu", offerDataModel.isThu());
                                            offer.put("fri", offerDataModel.isFri());
                                            offer.put("sat", offerDataModel.isSat());
                                            offer.put("offerStatus", offerDataModel.isOfferStatus());
                                            offer.put("shopDataModels", null);

                                            shop.put("offerDataModel", offer);
                                        }
//                                    if (shop.get("offeDataModel") != null) {
//                                        HashMap<String, Object> offer = (HashMap<String, Object>) shop.get("offeDataModel");
//                                        if (offer.get("offerID").equals(productDataModel.getProductOffer().getOfferID())) {
//                                            shop.put("offeDataModel", null);
//                                        }
//                                    }
                                    }
                                    firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
                                            .update("shopDataModels", shops)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "updateProductOffer productoffers Success");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "updateProductOffer productoffers Error : " + e.getMessage());
                                                }
                                            });
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "updateProductOffer productoffers Error : " + e.getMessage());
                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("offers").document(retailerID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                Log.d(TAG, "updateProductOffer_ Offers List : " + offers.size());
                                for (int i = 0; i < offers.size(); i++) {
                                    HashMap<String, Object> offer = offers.get(i);
                                    if (offer.get("offerID").equals(offerDataModel.getOfferID())) {
                                        Log.d(TAG, "updateProductOffer_ OfferID  : " + offer.get("offerID") + "  - " + offerDataModel.getOfferID());
                                        if (offer.get("shopDataModels") != null || ((List<HashMap<String, Object>>) offer.get("shopDataModels") != null)) {
                                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                                            for (int j = 0; j < shops.size(); j++) {
                                                HashMap<String, Object> shop = shops.get(j);
                                                if (shop.get("shopID").equals(shopDataModel.getShopID())) {
                                                    if (shop.get("productsList") != null) {
                                                        List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                                        boolean flag = false;
                                                        for (int k = 0; k < products.size(); k++) {
                                                            HashMap<String, Object> product = products.get(k);
                                                            if (product.get("productID").equals(productDataModel.getProductID())) {
                                                                flag = true;
                                                            }
                                                        }
                                                        if (!flag) {
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
                                                        }
                                                    } else {
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
                                                    }
                                                }
                                            }
                                        } else {
                                            List<HashMap<String, Object>> shops = new ArrayList<>();
                                            HashMap<String, Object> shop = new HashMap<>();
                                            shop.put("offerDataModel", null);
                                            shop.put("shopEmail", shopDataModel.getShopEmail());
                                            shop.put("retailerID", shopDataModel.getRetailerID());
                                            shop.put("shopAddress", shopDataModel.getShopAddress());
                                            shop.put("shopCellNo", shopDataModel.getShopCellNo());
                                            shop.put("shopID", shopDataModel.getShopID());
                                            shop.put("shopImageURL", shopDataModel.getShopImageURL());
                                            shop.put("shopLatitude", shopDataModel.getShopLatitude());
                                            shop.put("shopLongitude", shopDataModel.getShopLongitude());
                                            shop.put("shopName", shopDataModel.getShopName());

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

                                            offer.put("shopDataModels", shops);
                                        }
                                    }
                                }
                                firebaseFirestore.collection("offers").document(retailerID)
                                        .update("offersList", offers)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "updateProductOffer offers Success");
                                                emitter.onNext(true);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "updateProductOffer offers Error : " + e.getMessage());
                                                emitter.onError(e);
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "updateProductOffer offers Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }


//    public Observable<DocumentReference> removeProductOffer(String productId, String
//            retailerEmail, String shopEmail, String appliedOfferId) {
//        reference = firebaseFirestore.collection("products").document(retailerEmail).collection(shopEmail).document(productId);
//
//        return Observable.create(emitter ->
//                reference.update("productOffer", null)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//
//
//                                firebaseFirestore.collection("offers").document(retailerEmail).collection("templates").document(appliedOfferId)
//                                        .get()
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                List<HashMap<String, List<String>>> offerProducts = new ArrayList<>();
//
//                                                if (documentSnapshot.get("offerProducts") == null || ((List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts")).isEmpty()) {
//
//                                                } else {
//                                                    List<String> productsIdList = new ArrayList<>();
//                                                    offerProducts = new ArrayList<>();
//                                                    Boolean isOldShop = false;
//                                                    List<HashMap<String, List<String>>> offerProductsList = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                                    for (int i = 0; i < offerProductsList.size(); i++) {
//                                                        HashMap<String, List<String>> shopHashmap = (HashMap<String, List<String>>) offerProductsList.get(i);
//                                                        Set<String> shopKeys = shopHashmap.keySet();
////                                                        String shopKey = shopKeys.iterator().next();
//                                                        if (shopKeys.contains(shopEmail)) {
//                                                            productsIdList = shopHashmap.get(shopEmail);
//                                                            productsIdList.remove(productId);
////                                                            productsIdList.add(productDataModel.getProductID());
//                                                            Log.d("productsIdList", productsIdList.toString());
//                                                            shopHashmap.put(shopEmail, productsIdList);
//                                                            isOldShop = true;
//                                                        }
//                                                        offerProducts.add(shopHashmap);
//                                                        if (productsIdList.size() == 0) {
//                                                            offerProducts.remove(shopHashmap);
//                                                        }
//
//                                                        Log.d("offer_products", offerProducts.toString());
//                                                    }
////                                                    if (!isOldShop) {
////                                                        HashMap<String, List<String>> shopsId = new HashMap<>();
////                                                        List<String> productsId = new ArrayList<>();
////                                                        productsId.add(productDataModel.getProductID());
////                                                        shopsId.put(shopEmail, productsId);
////                                                        offerProducts.add(shopsId);
////                                                    }
//                                                    offer = firebaseFirestore.collection("offers").document(retailerEmail).collection("templates").document(appliedOfferId);
//
//                                                    offer.update("offerProducts", offerProducts).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            Log.d("Product_offer_removed-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//                                                            emitter.onComplete();
//                                                        }
//                                                    }).addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Log.w("Error_adding_poffer-", "...Error adding document-" + e.getMessage());
//                                                            emitter.onError(e);
//                                                        }
//                                                    });
//
//                                                    Log.d("list_offers", "not_empty-" + documentSnapshot.get("offerProducts").toString());
//
//                                                }
//                                                Log.d("Product_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//                                                emitter.onNext(documentReference);
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w("Error_geting_poffer-", "...Error-" + e.getMessage());
//                                        emitter.onError(e);
//                                    }
//                                });
//
//
////                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("updateShopDetails", "" + e.getMessage());
//                                emitter.onError(e);
//                            }
//                        }));
//    }

    public Observable<Boolean> removeProductOffer(String productId, String retailerID, String shopID, String offerID) {
        firebaseFirestore.collection("shops").document(retailerID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            HashMap<String, Object> shop = shops.get(i);
                            if (shop.get("shopID").equals(shopID)) {
                                if (shop.get("productsList") != null) {
                                    List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                    for (int j = 0; j < products.size(); j++) {
                                        HashMap<String, Object> product = products.get(j);
                                        if (product.get("productID").equals(productId)) {
                                            product.put("productOffer", null);
                                        }
                                    }
                                }
                            }
                        }
                        firebaseFirestore.collection("shops").document(retailerID)
                                .update("retailerShops", shops)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "removeProductOffer shops Success");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "removeProductOffer shops Error : " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "removeProductOffer shops Error : " + e.getMessage());
                    }
                });

        firebaseFirestore.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            if (productId.equals(queryDocumentSnapshot.getId())) {
                                List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                                for (int i = 0; i < shops.size(); i++) {
                                    HashMap<String, Object> shop = shops.get(i);
                                    if (shop.get("shopID").equals(shopID)) {
                                        shop.put("offerDataModel", null);
                                    }
//                                    if (shop.get("offeDataModel") != null) {
//                                        HashMap<String, Object> offer = (HashMap<String, Object>) shop.get("offeDataModel");
//                                        if (offer.get("offerID").equals(productDataModel.getProductOffer().getOfferID())) {
//                                            shop.put("offeDataModel", null);
//                                        }
//                                    }
                                }
                                firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
                                        .update("shopDataModels", shops)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "removeProductOffer productoffers Success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "removeProductOffer productoffers Error : " + e.getMessage());

                                            }
                                        });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "removeProductOffer productoffers Error : " + e.getMessage());
                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("offers").document(retailerID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                for (int i = 0; i < offers.size(); i++) {
                                    HashMap<String, Object> offer = offers.get(i);
                                    if (offer.get("offerID").equals(offerID)) {
                                        if (offer.get("shopDataModels") != null) {
                                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                                            for (int j = 0; j < shops.size(); j++) {
                                                HashMap<String, Object> shop = shops.get(j);
                                                if (shop.get("shopID").equals(shopID)) {
                                                    if (shop.get("productsList") != null) {
                                                        List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                                        for (int k = 0; k < products.size(); k++) {
                                                            HashMap<String, Object> product = products.get(k);
                                                            if (product.get("productID").equals(productId)) {
                                                                products.remove(products.indexOf(product));

                                                            }
                                                        }
                                                        if (products.size() == 0) {
                                                            shop.put("productsList", null);
                                                        }
                                                    }
                                                    if (shop.get("productsList") == null) {
                                                        shops.remove(shops.indexOf(shop));
                                                    }
                                                }
                                            }
                                            if (shops.size() == 0) {
                                                offer.put("shopDataModels", null);
                                            }
                                        }
                                    }
                                }
                                firebaseFirestore.collection("offers").document(retailerID)
                                        .update("offersList", offers)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "removeProductOffer offers Success");
                                                emitter.onNext(true);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "removeProductOffer offers Error : " + e.getMessage());
                                                emitter.onError(e);
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "removeProductOffer offers Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }
}
