package com.example.c2n.addshop.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.models.ShopDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public class AddshopRepository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    List<HashMap<String, Object>> shopsList = new ArrayList<>();
//    CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("my-collection");
//    GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);

    @Inject
    AddshopRepository() {

    }

//    public Observable<DocumentReference> addShop(ShopDataModel shopDataModel) {
//        documentReference = database.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail());
//        return Observable.create(emitter ->
////                database.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail())
//                database.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail())
////                        .add(shopDataModel)
//                        .set(shopDataModel)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                emitter.onError(e);
//                            }
//                        }));
////                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
////                            @Override
////                            public void onSuccess(DocumentReference documentReference) {
////                                Log.d("AddshopRepository", "...DocumentSnapshot added with ID: " + documentReference.getId());
////                                emitter.onNext(documentReference);
////                            }
////                        })
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Log.w("AddshopRepository", "...Error adding shop" + e);
////                                emitter.onError(e);
////                            }
////                        }));
//    }


    public Observable<DocumentReference> addShop(ShopDataModel shopDataModel) {
        documentReference = database.collection("shops").document(shopDataModel.getRetailerID());
        return Observable.create(emitter ->
                database.collection("shops").document(shopDataModel.getRetailerID())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().get("retailerShops") == null) {
                                        Log.d("shops--> ", "empty");
//                                        shopsList.add(shopDataModel);
                                        HashMap<String, Object> shopHashmap = new HashMap<>();

                                        shopHashmap.put("shopID", System.currentTimeMillis() + shopDataModel.getShopEmail());
                                        shopHashmap.put("retailerID", shopDataModel.getRetailerID());
                                        shopHashmap.put("shopName", shopDataModel.getShopName());
                                        shopHashmap.put("shopAddress", shopDataModel.getShopAddress());
                                        shopHashmap.put("shopCellNo", shopDataModel.getShopCellNo());
                                        shopHashmap.put("shopEmail", shopDataModel.getShopEmail());
                                        shopHashmap.put("shopImageURL", shopDataModel.getShopImageURL());
                                        shopHashmap.put("shopLatitude", shopDataModel.getShopLatitude());
                                        shopHashmap.put("shopLongitude", shopDataModel.getShopLongitude());
                                        shopHashmap.put("productsList", null);
//                                        OfferDataModel f = shopDataModel.getOfferDataModel();
                                        shopHashmap.put("offerDataModel", null);

                                        List<HashMap<String, Object>> shopDataModelList = new ArrayList<>();
                                        shopDataModelList.add(shopHashmap);
                                        HashMap<String, List<HashMap<String, Object>>> shopsListHashmap = new HashMap();
                                        shopsListHashmap.put("retailerShops", shopDataModelList);
                                        database.collection("shops").document(shopDataModel.getRetailerID())
                                                .set(shopsListHashmap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("shop_added:--> ", shopsList.toString());
                                                        emitter.onNext(documentReference);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        emitter.onError(e);
                                                    }
                                                });
                                    } else {
                                        shopsList = (List<HashMap<String, Object>>) task.getResult().get("retailerShops");
                                        HashMap<String, Object> shopHashmap = new HashMap<>();
                                        shopHashmap.put("shopID", System.currentTimeMillis() + shopDataModel.getShopEmail());
                                        shopHashmap.put("retailerID", shopDataModel.getRetailerID());
                                        shopHashmap.put("shopName", shopDataModel.getShopName());
                                        shopHashmap.put("shopAddress", shopDataModel.getShopAddress());
                                        shopHashmap.put("shopCellNo", shopDataModel.getShopCellNo());
                                        shopHashmap.put("shopEmail", shopDataModel.getShopEmail());
                                        shopHashmap.put("shopImageURL", shopDataModel.getShopImageURL());
                                        shopHashmap.put("shopLatitude", shopDataModel.getShopLatitude());
                                        shopHashmap.put("shopLongitude", shopDataModel.getShopLongitude());
                                        shopHashmap.put("productsList", null);
//                                        OfferDataModel f = shopDataModel.getOfferDataModel();
                                        shopHashmap.put("offerDataModel", null);
                                        shopsList.add(shopHashmap);
//                                        List<HashMap<String,Object>> shopDataModelList = new ArrayList<>();
//                                        shopDataModelList.addAll(shopsList);
                                        HashMap<String, List<HashMap<String, Object>>> shopsListHashmap = new HashMap();
                                        shopsListHashmap.put("retailerShops", shopsList);
                                        database.collection("shops").document(shopDataModel.getRetailerID())
                                                .set(shopsListHashmap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("shop_added:--> ", shopsList.toString());
                                                        emitter.onNext(documentReference);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        emitter.onError(e);
                                                    }
                                                });
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        }));

    }

}
