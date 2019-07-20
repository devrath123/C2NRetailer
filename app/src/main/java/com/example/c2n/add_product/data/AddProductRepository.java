package com.example.c2n.add_product.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model1.ProductDataModel;
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
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 29-05-2018.
 */

public class AddProductRepository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();
    DocumentReference offer;
    DocumentReference product;

    private DocumentReference documentReference = database.collection("products").document();

    @Inject
    AddProductRepository() {

    }


    public Observable<List<QueryDocumentSnapshot>> getCategories() {
        return Observable.create(emitter ->
                database.collection("categories")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        documentList.add(document);
                                        Log.d("QueryDocSnap(cat.)-", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                } else {
                                    emitter.onError(task.getException());
                                    Log.e("Error(cat.)..- ", "Error getting documents.", task.getException());
                                }

                            }
                        }));
    }

    public Observable<String> addProduct(ProductDataModel productDataModel, String retailerID, String shopID) {
        if (productDataModel.getProductOffer() != null)
            productDataModel.getProductOffer().setOfferProducts(null);
        String productDocId = database.collection("products").document(retailerID).collection(shopID).document().getId();
        if (productDocId != null)
            productDataModel.setProductID(productDocId);
        return Observable.create(emitter ->
                database.collection("products").document(retailerID).collection(shopID).document(productDocId)
                        .set(productDataModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (productDataModel.getProductOffer() != null) {
                                    database.collection("offers").document(retailerID).collection("templates").document(productDataModel.getProductOffer().getOfferID())
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    List<HashMap<String, List<String>>> offerProducts = new ArrayList<>();

                                                    if (documentSnapshot.get("offerProducts") == null || ((List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts")).isEmpty()) {
                                                        offer = database.collection("offers").document(retailerID).collection("templates").document(productDataModel.getProductOffer().getOfferID());

                                                        HashMap<String, List<String>> shopsId = new HashMap<>();
                                                        List<String> productsId = new ArrayList<>();
                                                        productsId.add(productDocId);
                                                        shopsId.put(shopID, productsId);
//
                                                        offerProducts.add(shopsId);
                                                        Log.d("offer_products", offerProducts.toString());
//
                                                        offer.update("offerProducts", offerProducts).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("Product_offer_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
                                                                emitter.onComplete();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("Error_adding_poffer-", "...Error adding document-" + e.getMessage());
                                                                emitter.onError(e);
                                                            }
                                                        });
                                                    } else {
                                                        List<String> productsIdList = new ArrayList<>();
                                                        offerProducts = new ArrayList<>();
                                                        Boolean isOldShop = false;
                                                        List<HashMap<String, List<String>>> offerProductsList = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
                                                        for (int i = 0; i < offerProductsList.size(); i++) {
                                                            HashMap<String, List<String>> shopHashmap = (HashMap<String, List<String>>) offerProductsList.get(i);
                                                            Set<String> shopKeys = shopHashmap.keySet();
                                                            String shopKey = shopKeys.iterator().next();
                                                            if (shopKey.equals(shopID)) {
                                                                productsIdList = shopHashmap.get(shopID);
                                                                productsIdList.add(productDocId);
                                                                Log.d("productsIdList", productsIdList.toString());
                                                                shopHashmap.put(shopID, productsIdList);
                                                                isOldShop = true;
                                                            }
                                                            offerProducts.add(shopHashmap);

                                                            Log.d("offer_products", offerProducts.toString());
                                                        }
                                                        if (!isOldShop) {
                                                            HashMap<String, List<String>> shopsId = new HashMap<>();
                                                            List<String> productsId = new ArrayList<>();
                                                            productsId.add(productDocId);
                                                            shopsId.put(shopID, productsId);
                                                            offerProducts.add(shopsId);
                                                        }
                                                        offer = database.collection("offers").document(retailerID).collection("templates").document(productDataModel.getProductOffer().getOfferID());

                                                        offer.update("offerProducts", offerProducts).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("Product_offer_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
                                                                emitter.onComplete();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("Error_adding_poffer-", "...Error adding document-" + e.getMessage());
                                                                emitter.onError(e);
                                                            }
                                                        });

                                                        Log.d("list_offers", "not_empty-" + documentSnapshot.get("offerProducts").toString());

                                                    }
                                                    Log.d("Product_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
                                                    emitter.onNext(productDocId);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Error_geting_poffer-", "...Error-" + e.getMessage());
                                            emitter.onError(e);
                                        }
                                    });
                                }

                                Log.d("Product_ofer_added-", "...DocumentSnapshot added with ID: " + documentReference.getId());
                                emitter.onNext(productDocId);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Error_in_adding_prod.-", "...Error adding document-" + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<DocumentReference> updateProductDescription(ProductDataModel productDataModel, String retailerID, String shopID) {
        product = database.collection("products").document(retailerID).collection(shopID).document(productDataModel.getProductID());

        return Observable.create(emitter ->
                product.update("productDescription", productDataModel.getProductDescription())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("updateProductDescriptio", "success");
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("updateProductDescriptio", "" + e.getMessage());
                            }
                        }));
    }
}
