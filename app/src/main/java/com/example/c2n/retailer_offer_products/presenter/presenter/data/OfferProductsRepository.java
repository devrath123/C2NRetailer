package com.example.c2n.retailer_offer_products.presenter.presenter.data;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 04-07-2018.
 */

public class OfferProductsRepository {

    private static final String TAG = "OfferProductsRepository";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference product;
    private DocumentReference documentReference;
    QueryDocumentSnapshot queryDocumentSnapshot;
    private List<QueryDocumentSnapshot> documentList = new ArrayList<>();
    List<QueryDocumentSnapshot> queryDocumentSnapshots = new ArrayList<>();


    //    --------------------------------------------------
    DocumentReference offer;
//    --------------------------------------------------

    @Inject
    public OfferProductsRepository() {

    }


//    public Observable<DocumentReference> updateProducts(List<ProductDataModel>productDataModels){
//
//    }

//    public Observable<DocumentReference> updateProducts(List<ProductDataModel> productDataModels) {
//        documentReferences = new List[productDataModels.size()];
//        for (int i = 0; i < productDataModels.size(); i++) {
//            ProductDataModel productDataModel = productDataModels.get(i);
//            product = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
//            return Observable.create(emitter ->
//                    product.update("", productDataModel.getProductDescription())
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    emitter.onNext(documentReference);
//                                    Log.d("updateProductOffer", "success");
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("updateProductOffer", "fail " + e.getMessage());
//                                }
//                            }));
//        }
//        return null;
//    }

//    public Observable<DocumentReference> updateProducts(Object[] objects) {
//        Log.d("OfferProductsReposit__u", (String) objects[1] + " - " + (String) objects[2]);
//        List<ProductDataModel> productDataModels = (List<ProductDataModel>) objects[0];
//        for (int i = 0; i < productDataModels.size() - 1; i++) {
//            ProductDataModel productDataModel = productDataModels.get(i);
//            Log.d("OfferProductsRepositor_", "" + productDataModel.toString());
////            documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
//            documentReference = firebaseFirestore.collection("products").document((String) objects[1]).collection((String) objects[2]).document(productDataModel.getProductID());
////            Observable.create(emitter ->
//
////            Map<String, Object> updateMap = new HashMap();
////            updateMap.put("productOffer", productDataModel);
//
//            Map<String, Object> productOffer = new HashMap<>();
//            productOffer.put("offerID", productDataModel.getProductOffer().getOfferID());
//            productOffer.put("offerDiscount", productDataModel.getProductOffer().getOfferDiscount());
//            productOffer.put("mon", productDataModel.getProductOffer().isMon());
//            productOffer.put("tue", productDataModel.getProductOffer().isTue());
//            productOffer.put("wed", productDataModel.getProductOffer().isWed());
//            productOffer.put("thu", productDataModel.getProductOffer().isThu());
//            productOffer.put("fri", productDataModel.getProductOffer().isFri());
//            productOffer.put("sat", productDataModel.getProductOffer().isSat());
//            productOffer.put("sun", productDataModel.getProductOffer().isSun());
//            productOffer.put("offerStartDate", productDataModel.getProductOffer().getOfferStartDate());
//            productOffer.put("offerEndDate", productDataModel.getProductOffer().getOfferEndDate());
//            productOffer.put("offerName", productDataModel.getProductOffer().getOfferName());
//            productOffer.put("offerStatus", productDataModel.getProductOffer().isOfferStatus());
//            productOffer.put("offerProducts", null);
////            updateMap.put("productOffer", productDataModel);
//
////            documentReference.update("productOffer", productDataModel.getProductOffer())
//            documentReference.update("productOffer", productOffer)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
////                                    emitter.onNext(documentReference);
//                            Log.d("updateProductOffer", "success" + productDataModel.getProductID());
////----------------------------------------------------------------------------------------------------------------------
//                            firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID())
//                                    .get()
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            List<HashMap<String, List<String>>> productOffers = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                            Log.d("offerProducts___****", "" + productOffers.toString());
//                                            boolean flag = false;
//                                            int position = 0;
//                                            for (int i = 0; i < productOffers.size(); i++) {
//                                                HashMap<String, List<String>> shop = productOffers.get(i);
////                                                Log.d("offerProducts__", productDataModel.getProductOffer().getOfferID() + "--" + shop.toString());
////                                                Log.d("offerProducts___" + i, "" + shop.containsKey((String) objects[2]));
//                                                if (shop.containsKey((String) objects[2])) {
//                                                    flag = true;
//                                                    position = i;
////                                                    Log.d("offerProducts___" + i, "" + flag);
//                                                }
//                                            }
//                                            if (flag) {
//                                                Log.d("offerProducts___***$", "in if");
//                                                HashMap<String, List<String>> shop = productOffers.get(position);
//                                                List<String> productIDs = shop.get((String) objects[2]);
//                                                if (!productIDs.contains(productDataModel.getProductID())) {
//                                                    productIDs.add(productDataModel.getProductID());
//                                                    shop.put((String) objects[2], productIDs);
////                                                        Log.d("List_offerProduct___***", "" + productIDs.toString());
////                                                        Log.d("offerProducts___**", "" + shop.toString());
//                                                }
//                                                productOffers.remove(productOffers.get(position));
//                                                productOffers.add(shop);
////                                                    Log.d("offerProducts___***", "" + shop.toString());
//                                            } else {
//                                                Log.d("offerProducts___***$", "in else");
//                                                HashMap<String, List<String>> shop = new HashMap<>();
//                                                shop.put((String) objects[2], Collections.singletonList(productDataModel.getProductID()));
////                                                Log.d("offerProducts___*", "" + shop.toString());
//                                                productOffers.add(shop);
//                                            }
//                                            Log.d("offerProducts___***#", "" + productOffers.toString());
//                                            offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID());
//                                            offer.update("offerProducts", productOffers).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
////                                                    emitter.onComplete();
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d("Error_adding_offer-", "...Error adding document-" + e.getMessage());
////                                                    emitter.onError(e);
//                                                }
//                                            });
//                                        }
//                                    });
////----------------------------------------------------------------------------------------------------------------------
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("updateProductOffer", "fail " + e.getMessage());
//                        }
//                    });
//        }
//
//        ProductDataModel productDataModel = productDataModels.get(productDataModels.size() - 1);
////        Log.d("OfferProductsRepository", "" + productDataModel.getProductId());
////        Log.d("OfferProductsRepositor_", "" + productDataModel.getShopEmail() + " - " + productDataModel.getProductCategory() + "-" + productDataModel.getProductId());
////        documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
//        documentReference = firebaseFirestore.collection("products").document((String) objects[1]).collection((String) objects[2]).document(productDataModel.getProductID());
////        Map<String, Object> updateMap = new HashMap();
////        updateMap.put("productOffer", productDataModel);
//
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
//
//        return Observable.create(emitter ->
//                documentReference.update("productOffer", productOffer)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//
//                                Log.d("updateProductOffer", "success");
//
////                                ------------------------------------------------------------------------------------------------------
//                                new java.util.Timer().schedule(
//                                        new java.util.TimerTask() {
//                                            @Override
//                                            public void run() {
//                                                // your code here
//
//                                                firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID())
//                                                        .get()
//                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                                            @Override
//                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                                List<HashMap<String, List<String>>> productOffers =
//                                                                        (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                                                Log.d("offerProducts___****", "" + productOffers.toString());
//                                                                boolean flag = false;
//                                                                int position = 0;
//                                                                for (int i = 0; i < productOffers.size(); i++) {
//                                                                    HashMap<String, List<String>> shop = productOffers.get(i);
////                                                    Log.d("offerProducts__", productDataModel.getProductOffer().getOfferID() + "--" + shop.toString());
////                                                    Log.d("offerProducts___" + i, "" + shop.containsKey((String) objects[2]));
//                                                                    if (shop.containsKey((String) objects[2])) {
//                                                                        flag = true;
//                                                                        position = i;
////                                                        Log.d("offerProducts___" + i, "" + flag);
//                                                                    }
//                                                                }
//                                                                if (flag) {
//                                                                    Log.d("offerProducts___***$", "in if");
//                                                                    HashMap<String, List<String>> shop = productOffers.get(position);
//                                                                    List<String> productIDs = shop.get((String) objects[2]);
//                                                                    if (!productIDs.contains(productDataModel.getProductID())) {
//                                                                        productIDs.add(productDataModel.getProductID());
//                                                                        shop.put((String) objects[2], productIDs);
////                                                        Log.d("List_offerProduct___***", "" + productIDs.toString());
////                                                        Log.d("offerProducts___**", "" + shop.toString());
//                                                                    }
//                                                                    productOffers.remove(productOffers.get(position));
//                                                                    productOffers.add(shop);
////                                                    Log.d("offerProducts___***", "" + shop.toString());
//                                                                } else {
//                                                                    Log.d("offerProducts___***$", "in else");
//                                                                    HashMap<String, List<String>> shop = new HashMap<>();
//                                                                    shop.put((String) objects[2], Collections.singletonList(productDataModel.getProductID()));
////                                                    Log.d("offerProducts___*", "" + shop.toString());
//                                                                    productOffers.add(shop);
//                                                                }
//                                                                Log.d("offerProducts___***#", "" + productOffers.toString());
//                                                                offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID());
//                                                                offer.update("offerProducts", productOffers).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                    @Override
//                                                                    public void onSuccess(Void aVoid) {
//                                                                        emitter.onComplete();
//                                                                    }
//                                                                }).addOnFailureListener(new OnFailureListener() {
//                                                                    @Override
//                                                                    public void onFailure(@NonNull Exception e) {
//                                                                        Log.d("Error_adding_offer-", "...Error adding document-" + e.getMessage());
//                                                                        emitter.onError(e);
//                                                                    }
//                                                                });
////                                                    offer.update("offerProducts", offerProducts).addOnSuccessListener(new OnSuccessListener<Void>() {
////                                                        @Override
////                                                        public void onSuccess(Void aVoid) {
////                                                            Log.d("Product_offer_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//////                                                        emitter.onComplete();
////                                                        }
////                                                    }).addOnFailureListener(new OnFailureListener() {
////                                                        @Override
////                                                        public void onFailure(@NonNull Exception e) {
////                                                            Log.w("Error_adding_poffer-", "...Error adding document-" + e.getMessage());
//////                                                        emitter.onError(e);
////                                                        }
////                                                    });
////                                                } else {
////                                                    List<String> productsIdList = new ArrayList<>();
////                                                    if (productsIdList.size() != 0) {
////                                                        productsIdList.clear();
////                                                    }
//////                                                    offerProducts = new ArrayList<>();
//////                                                    if (offerProducts.size() != 0) {
//////                                                        offerProducts.clear();
//////                                                    }
////                                                    Boolean isOldShop = false;
////                                                    List<HashMap<String, List<String>>> offerProductsList = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//////                                                if (offerProducts.size() != 0) {
//////                                                    offerProducts.clear();
//////                                                }
////                                                    for (int i = 0; i < offerProductsList.size(); i++) {
////                                                        HashMap<String, List<String>> shopHashmap = (HashMap<String, List<String>>) offerProductsList.get(i);
//////                                                    if (shopHashmap.size() != 0) {
//////                                                        shopHashmap.clear();
//////                                                    }
////                                                        Set<String> shopKeys = shopHashmap.keySet();
////                                                        String shopKey = shopKeys.iterator().next();
////                                                        if (shopKey.equals((String) objects[2])) {
////                                                            productsIdList = shopHashmap.get((String) objects[2]);
////                                                            if (!productsIdList.contains(productDataModel.getProductID()))
////                                                                productsIdList.add(productDataModel.getProductID());
////                                                            Log.d("productsIdList", productsIdList.toString());
////                                                            shopHashmap.put((String) objects[2], productsIdList);
////                                                            isOldShop = true;
////                                                        }
////                                                        offerProducts.add(shopHashmap);
////
////                                                        Log.d("offer_products", offerProducts.toString());
////                                                    }
////                                                    if (!isOldShop) {
////                                                        HashMap<String, List<String>> shopsId = new HashMap<>();
////                                                        if (shopsId.size() != 0) {
////                                                            shopsId.clear();
////                                                        }
////                                                        List<String> productsId = new ArrayList<>();
////                                                        if (productsId.size() != 0) {
////                                                            productsId.clear();
////                                                        }
////                                                        productsId.add(productDataModel.getProductID());
////                                                        shopsId.put((String) objects[2], productsId);
////                                                        offerProducts.add(shopsId);
////                                                    }
////                                                    offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID());
////
////                                                    offer.update("offerProducts", offerProducts).addOnSuccessListener(new OnSuccessListener<Void>() {
////                                                        @Override
////                                                        public void onSuccess(Void aVoid) {
////                                                            Log.d("Product_offer_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//////                                                        emitter.onComplete();
////                                                        }
////                                                    }).addOnFailureListener(new OnFailureListener() {
////                                                        @Override
////                                                        public void onFailure(@NonNull Exception e) {
////                                                            Log.w("Error_adding_poffer-", "...Error adding document-" + e.getMessage());
//////                                                        emitter.onError(e);
////                                                        }
////                                                    });
////
////                                                    Log.d("list_offers", "not_empty-" + documentSnapshot.get("offerProducts").toString());
////
////                                                }
////                                                Log.d("Product_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
//////                                            emitter.onNext(productDocId);
////                                            }
////                                        }).addOnFailureListener(new OnFailureListener() {
////                                    @Override
////                                    public void onFailure(@NonNull Exception e) {
////                                        Log.w("Error_geting_poffer-", "...Error-" + e.getMessage());
//////                                    emitter.onError(e);
//                                                            }
//                                                        });
//                                            }
//                                        },
//                                        1000
//                                );
////                                ------------------------------------------------------------------------------------------------------
//                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("updateProductOffer", "fail " + e.getMessage());
//                            }
//                        }));
//    }

    public Observable<DocumentReference> updateProducts(Object[] objects) {
        Log.d("OfferProductsReposit__u", (String) objects[1] + " - " + (String) objects[2]);
//        List<ProductDataModel> productDataModels = (List<ProductDataModel>) objects[0];
//        for (int i = 0; i < productDataModels.size() - 1; i++) {
//            ProductDataModel productDataModel = productDataModels.get(i);
//            Log.d("OfferProductsRepositor_", "" + productDataModel.toString());
////            documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
//            documentReference = firebaseFirestore.collection("products").document((String) objects[1]).collection((String) objects[2]).document(productDataModel.getProductID());
////            Observable.create(emitter ->
//
////            Map<String, Object> updateMap = new HashMap();
////            updateMap.put("productOffer", productDataModel);
//
//            Map<String, Object> productOffer = new HashMap<>();
//            productOffer.put("offerID", productDataModel.getProductOffer().getOfferID());
//            productOffer.put("offerDiscount", productDataModel.getProductOffer().getOfferDiscount());
//            productOffer.put("mon", productDataModel.getProductOffer().isMon());
//            productOffer.put("tue", productDataModel.getProductOffer().isTue());
//            productOffer.put("wed", productDataModel.getProductOffer().isWed());
//            productOffer.put("thu", productDataModel.getProductOffer().isThu());
//            productOffer.put("fri", productDataModel.getProductOffer().isFri());
//            productOffer.put("sat", productDataModel.getProductOffer().isSat());
//            productOffer.put("sun", productDataModel.getProductOffer().isSun());
//            productOffer.put("offerStartDate", productDataModel.getProductOffer().getOfferStartDate());
//            productOffer.put("offerEndDate", productDataModel.getProductOffer().getOfferEndDate());
//            productOffer.put("offerName", productDataModel.getProductOffer().getOfferName());
//            productOffer.put("offerStatus", productDataModel.getProductOffer().isOfferStatus());
//            productOffer.put("offerProducts", null);
////            updateMap.put("productOffer", productDataModel);
//
////            documentReference.update("productOffer", productDataModel.getProductOffer())
//            documentReference.update("productOffer", productOffer)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
////                                    emitter.onNext(documentReference);
//                            Log.d("updateProductOffer", "success" + productDataModel.getProductID());
////----------------------------------------------------------------------------------------------------------------------
//                            firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID())
//                                    .get()
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            List<HashMap<String, List<String>>> productOffers = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                            Log.d("offerProducts___****", "" + productOffers.toString());
//                                            boolean flag = false;
//                                            int position = 0;
//                                            for (int i = 0; i < productOffers.size(); i++) {
//                                                HashMap<String, List<String>> shop = productOffers.get(i);
////                                                Log.d("offerProducts__", productDataModel.getProductOffer().getOfferID() + "--" + shop.toString());
////                                                Log.d("offerProducts___" + i, "" + shop.containsKey((String) objects[2]));
//                                                if (shop.containsKey((String) objects[2])) {
//                                                    flag = true;
//                                                    position = i;
////                                                    Log.d("offerProducts___" + i, "" + flag);
//                                                }
//                                            }
//                                            if (flag) {
//                                                Log.d("offerProducts___***$", "in if");
//                                                HashMap<String, List<String>> shop = productOffers.get(position);
//                                                List<String> productIDs = shop.get((String) objects[2]);
//                                                if (!productIDs.contains(productDataModel.getProductID())) {
//                                                    productIDs.add(productDataModel.getProductID());
//                                                    shop.put((String) objects[2], productIDs);
////                                                        Log.d("List_offerProduct___***", "" + productIDs.toString());
////                                                        Log.d("offerProducts___**", "" + shop.toString());
//                                                }
//                                                productOffers.remove(productOffers.get(position));
//                                                productOffers.add(shop);
////                                                    Log.d("offerProducts___***", "" + shop.toString());
//                                            } else {
//                                                Log.d("offerProducts___***$", "in else");
//                                                HashMap<String, List<String>> shop = new HashMap<>();
//                                                shop.put((String) objects[2], Collections.singletonList(productDataModel.getProductID()));
////                                                Log.d("offerProducts___*", "" + shop.toString());
//                                                productOffers.add(shop);
//                                            }
//                                            Log.d("offerProducts___***#", "" + productOffers.toString());
//                                            offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID());
//                                            offer.update("offerProducts", productOffers).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
////                                                    emitter.onComplete();
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d("Error_adding_offer-", "...Error adding document-" + e.getMessage());
////                                                    emitter.onError(e);
//                                                }
//                                            });
//                                        }
//                                    });
////----------------------------------------------------------------------------------------------------------------------
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("updateProductOffer", "fail " + e.getMessage());
//                        }
//                    });
//        }

        ProductDataModel productDataModel = (ProductDataModel) objects[0];
//        Log.d("OfferProductsRepository", "" + productDataModel.getProductId());
//        Log.d("OfferProductsRepositor_", "" + productDataModel.getShopEmail() + " - " + productDataModel.getProductCategory() + "-" + productDataModel.getProductId());
//        documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
        documentReference = firebaseFirestore.collection("productoffers").document();
//        Map<String, Object> updateMap = new HashMap();
//        updateMap.put("productOffer", productDataModel);

        Map<String, Object> productOffer = null;
        if (productDataModel.getProductOffer() != null) {
            productOffer = new HashMap<>();
            productOffer.put("offerID", productDataModel.getProductOffer().getOfferID());
            productOffer.put("offerDiscount", productDataModel.getProductOffer().getOfferDiscount());
            productOffer.put("mon", productDataModel.getProductOffer().isMon());
            productOffer.put("tue", productDataModel.getProductOffer().isTue());
            productOffer.put("wed", productDataModel.getProductOffer().isWed());
            productOffer.put("thu", productDataModel.getProductOffer().isThu());
            productOffer.put("fri", productDataModel.getProductOffer().isFri());
            productOffer.put("sat", productDataModel.getProductOffer().isSat());
            productOffer.put("sun", productDataModel.getProductOffer().isSun());
            productOffer.put("offerStartDate", productDataModel.getProductOffer().getOfferStartDate());
            productOffer.put("offerEndDate", productDataModel.getProductOffer().getOfferEndDate());
            productOffer.put("offerName", productDataModel.getProductOffer().getOfferName());
            productOffer.put("offerStatus", productDataModel.getProductOffer().isOfferStatus());
            productOffer.put("offerProducts", null);
        }

        Map<String, Object> finalProductOffer = productOffer;
        return Observable.create(emitter ->
                documentReference.update("productOffer", finalProductOffer)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Log.d("updateProductOffer", "success");

//                                -----------------------------------------------------------------------------------------------------

//                                firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID())
//                                        .get()
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                List<HashMap<String, List<String>>> productOffers =
//                                                        (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                                if (productOffers != null && productOffers.size() != 0) {
//                                                    for (int i = 0; i < productOffers.size(); i++) {
//                                                        HashMap<String, List<String>> shopIDs = productOffers.get(i);
//                                                        Set<String> shops = shopIDs.keySet();
//                                                        for (String s : shops) {
//                                                            if (!s.equals((String) objects[2])) {
//                                                                List<String> productIDs = shopIDs.get(s);
//                                                                for (int j = 0; j < productIDs.size(); j++) {
//                                                                    if (productIDs.get(j).equals(productDataModel.getProductID())) {
////                                                                        productOffers.get(i).get(s).remove(productIDs.indexOf(productDataModel.getProductID()));
//                                                                        productOffers.get(i).get(s).remove(productDataModel.getProductID());
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                    offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID());
//                                                    offer.update("offerProducts", productOffers).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
////                                                        emitter.onNext();
//                                                        }
//                                                    }).addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Log.d("Error_adding_offer-", "...Error adding document-" + e.getMessage());
////                                                        emitter.onError(e);
//                                                        }
//                                                    });
//                                                }
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//
//                                            }
//                                        });

//                                -----------------------------------------------------------------------------------------------------
                                if (productDataModel.getProductOffer() != null) {
                                    firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID())
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    List<HashMap<String, List<String>>> productOffers =
                                                            (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
                                                    if (productOffers != null && productOffers.size() != 0) {
                                                        Log.d("offerProducts___****", "" + productOffers.toString());
                                                        boolean flag = false;
                                                        int position = 0;
                                                        for (int i = 0; i < productOffers.size(); i++) {
                                                            HashMap<String, List<String>> shop = productOffers.get(i);
//                                                    Log.d("offerProducts__", productDataModel.getProductOffer().getOfferID() + "--" + shop.toString());
//                                                    Log.d("offerProducts___" + i, "" + shop.containsKey((String) objects[2]));
                                                            if (shop.containsKey((String) objects[2])) {
                                                                flag = true;
                                                                position = i;
//                                                        Log.d("offerProducts___" + i, "" + flag);
                                                            }
                                                        }
                                                        if (flag) {
                                                            Log.d("offerProducts___***$", "in if");
                                                            HashMap<String, List<String>> shop = productOffers.get(position);
                                                            List<String> productIDs = shop.get((String) objects[2]);
                                                            if (!productIDs.contains(productDataModel.getProductID())) {
                                                                productIDs.add(productDataModel.getProductID());
                                                                shop.put((String) objects[2], productIDs);
//                                                        Log.d("List_offerProduct___***", "" + productIDs.toString());
//                                                        Log.d("offerProducts___**", "" + shop.toString());
                                                            }
                                                            productOffers.remove(productOffers.get(position));
                                                            productOffers.add(shop);
//                                                    Log.d("offerProducts___***", "" + shop.toString());
                                                        } else {
                                                            Log.d("offerProducts___***$", "in else");
                                                            HashMap<String, List<String>> shop = new HashMap<>();
                                                            shop.put((String) objects[2], Collections.singletonList(productDataModel.getProductID()));
//                                                    Log.d("offerProducts___*", "" + shop.toString());
                                                            productOffers.add(shop);
                                                        }
                                                        Log.d("offerProducts___***#", "" + productOffers.toString());
                                                        offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID());
                                                        offer.update("offerProducts", productOffers).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
//                                                        emitter.onNext();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d("Error_adding_offer-", "...Error adding document-" + e.getMessage());
//                                                        emitter.onError(e);
                                                            }
                                                        });
                                                    } else {
                                                        productOffers = new ArrayList<>();
                                                        HashMap<String, List<String>> shop = new HashMap<>();
                                                        shop.put((String) objects[2], Collections.singletonList(productDataModel.getProductID()));
//                                                    Log.d("offerProducts___*", "" + shop.toString());
                                                        productOffers.add(shop);
                                                        offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(productDataModel.getProductOffer().getOfferID());
                                                        offer.update("offerProducts", productOffers).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
//                                                        emitter.onNext();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d("Error_adding_offer-", "...Error adding document-" + e.getMessage());
//                                                        emitter.onError(e);
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                }
//                                ------------------------------------------------------------------------------------------------------
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("updateProductOffer", "fail " + e.getMessage());
                            }
                        }));
    }

    public Observable<List<QueryDocumentSnapshot>> removeOffer(Object[] objects) {
        if (queryDocumentSnapshots.size() != 0) {
            queryDocumentSnapshots.clear();
        }
        return Observable.create(
                emitter -> firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                        queryDocumentSnapshots.add(queryDocumentSnapshot);
                                    }
//----------------------------------------------------------------------------------------------------------
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        List<HashMap<String, List<String>>> productOffers =
                                                (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
                                        if (productOffers != null && productOffers.size() != 0) {
                                            for (int i = 0; i < productOffers.size(); i++) {
                                                HashMap<String, List<String>> shopIDs = productOffers.get(i);
                                                Set<String> shops = shopIDs.keySet();
                                                if (shops.size() != 0) {
                                                    for (String s : shops) {
                                                        List<String> productIDs = shopIDs.get(s);
                                                        ProductDataModel productDataModel = (ProductDataModel) objects[0];
                                                        if (productIDs.contains(productDataModel.getProductID())) {
                                                            productOffers.get(i).get(s).remove(productDataModel.getProductID());
                                                        }
                                                        if (productIDs.size() == 0) {
                                                            productOffers.remove(i);
                                                        }
//                                                        for (String productID : productIDs) {
//                                                            if ((productDataModel.getProductID().equals(productID))) {
//                                                                productOffers.get(i).get(s).remove(productDataModel.getProductID());
//                                                            }
//                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        offer = firebaseFirestore.collection("offers").document((String) objects[1]).collection("templates").document(documentSnapshot.get("offerID").toString());
                                        offer.update("offerProducts", productOffers)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
                                    }
//----------------------------------------------------------------------------------------------------------
                                    emitter.onNext(queryDocumentSnapshots);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        })
        );
    }


//    public Observable<DocumentReference> removeProductsOffer(List<ProductDataModel> productDataModels) {
//        Log.d("OfferProductsReposit__r", productDataModels.size() + " - " + productDataModels.toString());
//        for (int i = 0; i < productDataModels.size() - 1; i++) {
//            ProductDataModel productDataModel = productDataModels.get(i);
//            Log.d("OfferProductsRepository", "" + productDataModel.getProductId());
////            documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
//            documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
////            Observable.create(emitter ->
//            documentReference.update("productOfferID", productDataModel.getProductOfferID())
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
////                                    emitter.onNext(documentReference);
//                            Log.d("updateProductOffer", "success");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("updateProductOffer", "fail " + e.getMessage());
//                        }
//                    });
//        }
//
//        ProductDataModel productDataModel = productDataModels.get(productDataModels.size() - 1);
//        Log.d("OfferProductsRepository", "" + productDataModel.getProductId());
//        Log.d("OfferProductsRepositor_", "" + productDataModel.getShopEmail() + " - " + productDataModel.getProductCategory() + "-" + productDataModel.getProductId());
////        documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
//        documentReference = firebaseFirestore.collection("inventory").document(productDataModel.getShopEmail()).collection(productDataModel.getProductCategory()).document(productDataModel.getProductId());
//        return Observable.create(emitter ->
//                documentReference.update("productOfferID", productDataModel.getProductOfferID())
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                emitter.onNext(documentReference);
//                                Log.d("updateProductOffer", "success");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("updateProductOffer", "fail " + e.getMessage());
//                            }
//                        }));
//    }

//    public Observable<DocumentReference> updateProducts(ProductDataModel productDataModels) {
//        Log.d("OfferProductsRepositor_", "" + productDataModels.getShopEmail() + " - " + productDataModels.getProductCategory() + " - " + productDataModels.getProductId());
//        documentReference = firebaseFirestore.collection("inventory").document(productDataModels.getShopEmail()).collection(productDataModels.getProductCategory()).document(productDataModels.getProductId());
//        product = firebaseFirestore.collection("inventory").document(productDataModels.getShopEmail()).collection(productDataModels.getProductCategory()).document(productDataModels.getProductId());
//        return Observable.create(emitter ->
//                product.update("productOfferID", productDataModels.getProductDescription())
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                emitter.onNext(documentReference);
//                                Log.d("updateProductOffer", "success");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("updateProductOffer", "fail " + e.getMessage());
//                            }
//                        }));
//    }

    public Observable<List<QueryDocumentSnapshot>> loadProducts(String[] selectedShopnCategory) {
        if (documentList.size() != 0)
            documentList.clear();
        return Observable.create(emitter ->
                firebaseFirestore.collection("inventory").document(selectedShopnCategory[0]).collection(selectedShopnCategory[1])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        documentList.add(document);
                                        Log.d("QueryDocSnap(products)-", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                } else {
                                    emitter.onError(task.getException());
                                    Log.e("Error(products)..- ", "Error getting documents.", task.getException());
                                }

                            }
                        }));
    }

    public Observable<DocumentSnapshot> getDiscount(String[] params) {
        Log.d("getDiscount", "in getDiscount");
        documentReference = firebaseFirestore.collection("users").document(params[0]).collection("offers").document(params[1]);
        return Observable.create(emitter ->
                documentReference.collection("users").document(params[0]).collection("offers").document(params[1])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Log.d("getDiscount", "in onComplete getDiscount");
                                DocumentSnapshot documentSnapshot = task.getResult();
                                emitter.onNext(documentSnapshot);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("getDicsount", "" + e.getMessage());
                                emitter.onError(e);
                            }
                        }));

    }

    public Observable<Boolean> applyOffers(Object[] objects, String retailerID) {

//        firebaseFirestore.collection("productoffers")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<String> seletedProductIDs = (List<String>) objects[0];
//                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
//                            for (int i = 0; i < seletedProductIDs.size(); i++) {
//                                if (seletedProductIDs.get(i).equals(queryDocumentSnapshot.getId())) {
//                                    List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
//                                    for (int j = 0; j < shops.size(); j++) {
//                                        HashMap<String, Object> shop = shops.get(j);
//                                        if (shop.get("shopID").equals(objects[1])) {
//                                            OfferDataModel offer = (OfferDataModel) objects[2];
//                                            offer.setShopDataModels(null);
//
//                                            HashMap<String, Object> offerHashMap = new HashMap<>();
//                                            offerHashMap.put("offerID", offer.getOfferID());
//                                            offerHashMap.put("offerDiscount", offer.getOfferDiscount());
//                                            offerHashMap.put("offerStartDate", offer.getOfferStartDate());
//                                            offerHashMap.put("offerEndDate", offer.getOfferEndDate());
//                                            offerHashMap.put("offerName", offer.getOfferName());
//                                            offerHashMap.put("sun", offer.isSun());
//                                            offerHashMap.put("mon", offer.isMon());
//                                            offerHashMap.put("tue", offer.isTue());
//                                            offerHashMap.put("wed", offer.isWed());
//                                            offerHashMap.put("thu", offer.isThu());
//                                            offerHashMap.put("fri", offer.isFri());
//                                            offerHashMap.put("sat", offer.isSat());
//                                            offerHashMap.put("offerStatus", offer.isOfferStatus());
//                                            offerHashMap.put("shopDataModels", null);
//                                            shop.put("offerDataModel", objects);
//                                        }
//                                    }
//                                    Log.d(TAG, "queryDocumentSnapshot documentID : " + queryDocumentSnapshot.getId());
//                                    firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
//                                            .update("shopDataModels", shops)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d(TAG, "applyOffers productOffers : Success");
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d(TAG, "applyOffers Error : " + e.getMessage());
//                                                }
//                                            });
//                                }
//                            }
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "applyOffers Error : " + e.getMessage());
//                    }
//                });

        List<String> selectedProductIDs = (List<String>) objects[0];
        for (int i = 0; i < selectedProductIDs.size(); i++) {
            int finalI = i;
            firebaseFirestore.collection("productoffers").document(selectedProductIDs.get(i))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("shopDataModels");
                            for (int j = 0; j < shops.size(); j++) {
                                HashMap<String, Object> shop = shops.get(j);
                                if (shop.get("shopID").equals(objects[1])) {
                                    OfferDataModel offer = (OfferDataModel) objects[2];
                                    offer.setShopDataModels(null);

                                    HashMap<String, Object> offerHashMap = new HashMap<>();
                                    offerHashMap.put("offerID", offer.getOfferID());
                                    offerHashMap.put("offerDiscount", offer.getOfferDiscount());
                                    offerHashMap.put("offerStartDate", offer.getOfferStartDate());
                                    offerHashMap.put("offerEndDate", offer.getOfferEndDate());
                                    offerHashMap.put("offerName", offer.getOfferName());
                                    offerHashMap.put("sun", offer.isSun());
                                    offerHashMap.put("mon", offer.isMon());
                                    offerHashMap.put("tue", offer.isTue());
                                    offerHashMap.put("wed", offer.isWed());
                                    offerHashMap.put("thu", offer.isThu());
                                    offerHashMap.put("fri", offer.isFri());
                                    offerHashMap.put("sat", offer.isSat());
                                    offerHashMap.put("offerStatus", offer.isOfferStatus());
                                    offerHashMap.put("shopDataModels", null);
                                    shop.put("offerDataModel", offerHashMap);
                                }
                            }
                            firebaseFirestore.collection("productoffers").document(selectedProductIDs.get(finalI))
                                    .update("shopDataModels", shops)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "applyOffers productoffers Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "applyOffers productoffers Error : " + e.getMessage());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

        firebaseFirestore.collection("offers").document(retailerID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                        for (int i = 0; i < offers.size(); i++) {
                            HashMap<String, Object> offer = offers.get(i);
                            if (offer.get("shopDataModels") != null) {
                                List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                                for (int j = 0; j < shops.size(); j++) {
                                    HashMap<String, Object> shop = shops.get(j);
                                    if (shop.get("productsList") != null) {
                                        List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                        List<String> deseletedProductIDs = (List<String>) objects[0];

                                        for (int k = 0; k < products.size(); k++) {
                                            HashMap<String, Object> product = products.get(k);
                                            for (int l = 0; l < deseletedProductIDs.size(); l++) {
                                                if (product.get("productID").equals(deseletedProductIDs.get(l))) {
                                                    products.remove(products.indexOf(product));
                                                }
                                            }
                                        }
                                        if (products.size() == 0) {
                                            shop.put("productsList", null);
                                            shops.remove(shops.indexOf(shop));
                                        }
                                    }
                                }
                                if (shops.size() == 0) {
                                    offer.put("shopDataModels", null);
                                }
                            }
                        }
                        firebaseFirestore.collection("offers").document(retailerID)
                                .update("offersList", offers)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        OfferDataModel offerDataModel = (OfferDataModel) objects[2];

                                        firebaseFirestore.collection("offers").document(retailerID)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                                        for (int i = 0; i < offers.size(); i++) {
                                                            HashMap<String, Object> offer = offers.get(i);
                                                            if (offer.get("offerID").equals(offerDataModel.getOfferID())) {
                                                                if (offer.get("shopDataModels") != null) {
                                                                    List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                                                                    boolean flag = false;
                                                                    for (int j = 0; j < shops.size(); j++) {
                                                                        HashMap<String, Object> shop = shops.get(j);
                                                                        if (shop.get("shopID").equals(objects[1])) {
                                                                            flag = true;
                                                                            List<ProductDataModel> selectedProducts = (List<ProductDataModel>) objects[5];
                                                                            List<HashMap<String, Object>> products = new ArrayList<>();
                                                                            for (int k = 0; k < selectedProducts.size(); k++) {
                                                                                ProductDataModel productDataModel = selectedProducts.get(k);
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

                                                                            List<HashMap<String, Object>> productsHashMap = (List<HashMap<String, Object>>) shop.get("productsList");
                                                                            productsHashMap.addAll(products);
                                                                        }
                                                                    }
                                                                    if (!flag) {

                                                                        ShopDataModel shopDataModel = (ShopDataModel) objects[4];

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

                                                                        List<ProductDataModel> productDataModels = (List<ProductDataModel>) objects[5];
                                                                        List<HashMap<String, Object>> products = new ArrayList<>();
                                                                        for (int k = 0; k < productDataModels.size(); k++) {
                                                                            ProductDataModel productDataModel = productDataModels.get(k);
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
                                                                        shop.put("productsList", products);
                                                                        shops.add(shop);
                                                                    }
                                                                } else {

                                                                    ShopDataModel shopDataModel = (ShopDataModel) objects[4];

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

                                                                    List<ProductDataModel> productDataModels = (List<ProductDataModel>) objects[5];
                                                                    List<HashMap<String, Object>> products = new ArrayList<>();
                                                                    for (int k = 0; k < productDataModels.size(); k++) {
                                                                        ProductDataModel productDataModel = productDataModels.get(k);
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

                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {

                                                                    }
                                                                });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "removeOffers Error : " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "removeOffers Error : " + e.getMessage());
                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("shops").document(retailerID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                                for (int i = 0; i < shops.size(); i++) {
                                    HashMap<String, Object> shop = shops.get(i);
                                    if (shop.get("shopID").equals(objects[1])) {
                                        if (shop.get("productsList") != null) {
                                            List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                            for (int j = 0; j < products.size(); j++) {
                                                HashMap<String, Object> product = products.get(j);
                                                List<String> seletedProductIDs = (List<String>) objects[0];
                                                for (int k = 0; k < seletedProductIDs.size(); k++) {
                                                    if (seletedProductIDs.get(k).equals(product.get("productID"))) {
                                                        OfferDataModel offer = (OfferDataModel) objects[2];
                                                        offer.setShopDataModels(null);

                                                        HashMap<String, Object> offerHashMap = new HashMap<>();
                                                        offerHashMap.put("offerID", offer.getOfferID());
                                                        offerHashMap.put("offerDiscount", offer.getOfferDiscount());
                                                        offerHashMap.put("offerStartDate", offer.getOfferStartDate());
                                                        offerHashMap.put("offerEndDate", offer.getOfferEndDate());
                                                        offerHashMap.put("offerName", offer.getOfferName());
                                                        offerHashMap.put("sun", offer.isSun());
                                                        offerHashMap.put("mon", offer.isMon());
                                                        offerHashMap.put("tue", offer.isTue());
                                                        offerHashMap.put("wed", offer.isWed());
                                                        offerHashMap.put("thu", offer.isThu());
                                                        offerHashMap.put("fri", offer.isFri());
                                                        offerHashMap.put("sat", offer.isSat());
                                                        offerHashMap.put("offerStatus", offer.isOfferStatus());
                                                        offerHashMap.put("shopDataModels", null);

                                                        product.put("productOffer", offerHashMap);
                                                    }
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
                                                emitter.onNext(true);
                                                Log.d(TAG, "applyOffers shops : Success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "applyOffers Error : " + e.getMessage());
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "applyOffers Error : " + e.getMessage());
                            }
                        }));
    }

    public Observable<Boolean> removeOffers(Object[] objects, String retailerID) {

        List<String> deselectedProductIDs = (List<String>) objects[3];
        for (int i = 0; i < deselectedProductIDs.size(); i++) {
            int finalI = i;
            firebaseFirestore.collection("productoffers").document(deselectedProductIDs.get(i))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("shopDataModels");
                            for (int j = 0; j < shops.size(); j++) {
                                if (shops.get(j).get("shopID").equals(objects[1])) {
                                    shops.get(j).put("offerDataModel", null);
                                }
                            }
                            firebaseFirestore.collection("productoffers").document(deselectedProductIDs.get(finalI))
                                    .update("shopDataModels", shops)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "applyOffers productoffers Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "applyOffers productoffers Error : " + e.getMessage());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

        firebaseFirestore.collection("offers").document(retailerID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                        for (int i = 0; i < offers.size(); i++) {
                            HashMap<String, Object> offer = offers.get(i);
                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                            for (int j = 0; j < shops.size(); j++) {
                                HashMap<String, Object> shop = shops.get(j);
                                List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                List<String> deseletedProductIDs = (List<String>) objects[3];

                                for (int k = 0; k < products.size(); k++) {
                                    HashMap<String, Object> product = products.get(k);
                                    for (int l = 0; l < deseletedProductIDs.size(); l++) {
                                        if (product.get("productID").equals(deseletedProductIDs.get(l))) {
                                            products.remove(products.indexOf(product));
                                        }
                                    }
                                }
                                if (products.size() == 0) {
                                    shop.put("productsList", null);
                                    shops.remove(shops.indexOf(shop));
                                }
                            }
                            if (shops.size() == 0) {
                                offer.put("shopDataModels", null);
                            }
                        }
                        firebaseFirestore.collection("offers").document(retailerID)
                                .update("offersList", offers)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "removeOffers Error : " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "removeOffers Error : " + e.getMessage());
                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("shops").document(retailerID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                                for (int i = 0; i < shops.size(); i++) {
                                    HashMap<String, Object> shop = shops.get(i);
                                    if (shop.get("shopID").equals(objects[1])) {
                                        if (shop.get("productsList") != null) {
                                            List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                            for (int j = 0; j < products.size(); j++) {
                                                HashMap<String, Object> product = products.get(j);
                                                List<String> deseletedProductIDs = (List<String>) objects[3];
                                                for (int k = 0; k < deseletedProductIDs.size(); k++) {
                                                    if (deseletedProductIDs.get(k).equals(product.get("productID"))) {
                                                        product.put("productOffer", null);
                                                    }
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
                                                emitter.onNext(true);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                emitter.onError(e);
                                                Log.d(TAG, "removeOffers Error : " + e.getMessage());
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                                Log.d(TAG, "removeOffers Error : " + e.getMessage());
                            }
                        }));
    }
}
