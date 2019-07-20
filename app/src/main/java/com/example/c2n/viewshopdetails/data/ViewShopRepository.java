package com.example.c2n.viewshopdetails.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.models.ShopDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
 * Created by roshan.nimje on 24-05-2018.
 */

public class ViewShopRepository {

    public static final String TAG = ViewShopRepository.class.getSimpleName();

    DocumentReference reference;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<ShopDataModel> shopsList = new ArrayList<>();
    List<HashMap<String, Object>> shopsListHashmap = new ArrayList<>();
    HashMap<String, Object> updatedShopHashmap;
    List<HashMap<String, Object>> updatedShopsListHashmap;
    List<HashMap<String, Object>> offersListHashmap = new ArrayList<>();
    HashMap<String, Object> updatedOfferHashmap;
    List<HashMap<String, Object>> updatedOffersListHashmap;


    private DocumentReference documentReference;// = firebaseFirestore.collection("users").document();

    @Inject
    public ViewShopRepository() {
    }

//    public Observable<DocumentReference> updateShopDetails(ShopDataModel shopDataModel) {
//        documentReference = firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID());
//        reference = firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID());
////        reference.update("retailerID", shopDataModel.getRetailerID());
////        reference.update("shopID", shopDataModel.getShopEmail());
////        reference.update("shopAddress", shopDataModel.getShopAddress());
////        reference.update("shopCellNo", shopDataModel.getShopCellNo());
////        reference.update("shopLongitude", shopDataModel.getShopLongitude());
////        reference.update("shopLatitude", shopDataModel.getShopLatitude());
////        reference.update("shopName", shopDataModel.getShopName());
//
//        Log.d("updateShopDetails", "" + shopDataModel.getShopImageURL());
//
//        return Observable.create(emitter ->
//                reference.update("shopImageURL", shopDataModel.getShopImageURL())
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("updateShopDetails", "" + e.getMessage());
//                            }
//                        }));
//    }


    public Observable<Boolean> updateShopDetails(ShopDataModel shopDataModel, String retailerID) {

        firebaseFirestore.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            if (queryDocumentSnapshot.get("shopDataModels") != null) {
                                List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                                for (int i = 0; i < shops.size(); i++) {
                                    HashMap<String, Object> shop = shops.get(i);
                                    if (shop.get("shopID").equals(shopDataModel.getShopID())) {
                                        shop.put("retailerID", shopDataModel.getRetailerID());
                                        shop.put("shopName", shopDataModel.getShopName());
                                        shop.put("shopAddress", shopDataModel.getShopAddress());
                                        shop.put("shopCellNo", shopDataModel.getShopCellNo());
                                        shop.put("shopEmail", shopDataModel.getShopEmail());
                                        shop.put("shopImageURL", shopDataModel.getShopImageURL());
                                        shop.put("shopLatitude", shopDataModel.getShopLatitude());
                                        shop.put("shopLongitude", shopDataModel.getShopLongitude());
                                    }
                                }
                                firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
                                        .update("shopDataModels", shops)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "updateShopDetails productoffers Success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "updateShopDetails productoffers Error : " + e.getMessage());
                                            }
                                        });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "updateShopDetails productoffers Error : " + e.getMessage());
                    }
                });

        firebaseFirestore.collection("shops").document(retailerID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap> shops = (List<HashMap>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            HashMap<String, Object> shop = shops.get(i);
                            if (shop.get("shopID").equals(shopDataModel.getShopID())) {
                                shop.put("retailerID", shopDataModel.getRetailerID());
                                shop.put("shopName", shopDataModel.getShopName());
                                shop.put("shopAddress", shopDataModel.getShopAddress());
                                shop.put("shopCellNo", shopDataModel.getShopCellNo());
                                shop.put("shopEmail", shopDataModel.getShopEmail());
                                shop.put("shopImageURL", shopDataModel.getShopImageURL());
                                shop.put("shopLatitude", shopDataModel.getShopLatitude());
                                shop.put("shopLongitude", shopDataModel.getShopLongitude());
                            }
                        }
                        firebaseFirestore.collection("shops").document(retailerID)
                                .update("retailerShops", shops)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "updateShopDetails shops Success");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "updateShopDetails shops Error : " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "updateShopDetails shops Error : " + e.getMessage());
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
                                    if (offer.get("shopDataModels") != null) {
                                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) offer.get("shopDataModels");
                                        for (int j = 0; j < shops.size(); j++) {
                                            HashMap<String, Object> shop = shops.get(j);
                                            if (shop.get("shopID").equals(shopDataModel.getShopID())) {
                                                shop.put("retailerID", shopDataModel.getRetailerID());
                                                shop.put("shopName", shopDataModel.getShopName());
                                                shop.put("shopAddress", shopDataModel.getShopAddress());
                                                shop.put("shopCellNo", shopDataModel.getShopCellNo());
                                                shop.put("shopEmail", shopDataModel.getShopEmail());
                                                shop.put("shopImageURL", shopDataModel.getShopImageURL());
                                                shop.put("shopLatitude", shopDataModel.getShopLatitude());
                                                shop.put("shopLongitude", shopDataModel.getShopLongitude());
                                            }
                                        }
                                    }
                                }
                                firebaseFirestore.collection("offers").document(retailerID)
                                        .update("offersList", offers)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "updateShopDetails offers Success");
                                                emitter.onNext(true);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "updateShopDetails offers Error : " + e.getMessage());
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "updateShopDetails offers Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));

//        documentReference = firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID());
//        return Observable.create(emitter ->
//                firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    shopsListHashmap = (List<HashMap<String, Object>>) task.getResult().get("retailerShops");
//                                    updatedShopsListHashmap = new ArrayList<>();
//                                    updatedShopsListHashmap.addAll(shopsListHashmap);
//                                    for (HashMap<String, Object> shopHashmap : shopsListHashmap) {
//                                        if (shopHashmap.get("shopID").equals(shopDataModel.getShopID())) {
//                                            updatedShopsListHashmap.remove(shopHashmap);
//                                            updatedShopHashmap = new HashMap<>();
////                                            Log.d("timestamp-", ServerValue.TIMESTAMP.get("timestamp"));
//                                            updatedShopHashmap.put("shopID", shopDataModel.getShopID());
//                                            updatedShopHashmap.put("retailerID", shopDataModel.getRetailerID());
//                                            updatedShopHashmap.put("shopName", shopDataModel.getShopName());
//                                            updatedShopHashmap.put("shopAddress", shopDataModel.getShopAddress());
//                                            updatedShopHashmap.put("shopCellNo", shopDataModel.getShopCellNo());
//                                            updatedShopHashmap.put("shopEmail", shopDataModel.getShopEmail());
//                                            updatedShopHashmap.put("shopImageURL", shopDataModel.getShopImageURL());
//                                            updatedShopHashmap.put("shopLatitude", shopDataModel.getShopLatitude());
//                                            updatedShopHashmap.put("shopLongitude", shopDataModel.getShopLongitude());
//                                            updatedShopHashmap.put("productsList", shopDataModel.getProductsList());
////                                        OfferDataModel f = shopDataModel.getOfferDataModel();
//                                            updatedShopHashmap.put("öfferDataModel", null);
//                                            updatedShopsListHashmap.add(updatedShopHashmap);
//                                        }
//                                    }
////                                    ShopListDataModel shopListDataModel = new ShopListDataModel();
////                                    shopListDataModel.setRetailerShops(shopsList);
//                                    firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID())
//                                            .update("retailerShops", updatedShopsListHashmap)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d("shops_updated:--> ", shopsList.toString());
//
//                                                    firebaseFirestore.collection("productoffers")
//                                                            .get()
//                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                                @Override
//                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                                    if (task.isSuccessful()) {
//
//                                                                        List<DocumentSnapshot> queryDocumentSnapshotList = (List<DocumentSnapshot>) task.getResult().getDocuments();
//                                                                        for (int i = 0; i < queryDocumentSnapshotList.size() - 1; i++) {
////                                                                            queryDocumentSnapshots.add(queryDocumentSnapshot);
//                                                                            List<HashMap<String, Object>> shopsHashmapList = (List<HashMap<String, Object>>) queryDocumentSnapshotList.get(i).get("shopDataModels");
//                                                                            List<HashMap<String, Object>> listShopsHashmapList = (List<HashMap<String, Object>>) queryDocumentSnapshotList.get(i).get("shopDataModels");
//
//                                                                            for (HashMap<String, Object> shopHashmap : listShopsHashmapList) {
//                                                                                if (shopHashmap.get("shopID").equals(shopDataModel.getShopID())) {
//                                                                                    shopsHashmapList.remove(shopHashmap);
//                                                                                }
//                                                                            }
//                                                                            shopsHashmapList.add(updatedShopHashmap);
//
//                                                                            firebaseFirestore.collection("productoffers").document(queryDocumentSnapshotList.get(i).getId())
//                                                                                    .update("shopDataModels", shopsHashmapList);
//
//                                                                        }
//
//
//                                                                        List<HashMap<String, Object>> shopsHashmapList = (List<HashMap<String, Object>>) queryDocumentSnapshotList.get(queryDocumentSnapshotList.size() - 1).get("shopDataModels");
//                                                                        List<HashMap<String, Object>> listShopsHashmapList = (List<HashMap<String, Object>>) queryDocumentSnapshotList.get(queryDocumentSnapshotList.size() - 1).get("shopDataModels");
//
//                                                                        for (HashMap<String, Object> shopHashmap : listShopsHashmapList) {
//                                                                            if (shopHashmap.get("shopID").equals(shopDataModel.getShopID())) {
//                                                                                shopsHashmapList.remove(shopHashmap);
//                                                                            }
//                                                                        }
//                                                                        shopsHashmapList.add(updatedShopHashmap);
//
//                                                                        firebaseFirestore.collection("productoffers").document(queryDocumentSnapshotList.get(queryDocumentSnapshotList.size() - 1).getId())
//                                                                                .update("shopDataModels", shopsHashmapList)
//                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                                    @Override
//                                                                                    public void onSuccess(Void aVoid) {
//
//                                                                                        firebaseFirestore.collection("offers").document(shopDataModel.getRetailerID())
//                                                                                                .get()
//                                                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                                                                    @Override
//                                                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                                                                        if (task.isSuccessful()) {
//                                                                                                            offersListHashmap = (List<HashMap<String, Object>>) task.getResult().get("offersList");
//                                                                                                            updatedOffersListHashmap = new ArrayList<>();
//                                                                                                            updatedOffersListHashmap.addAll(offersListHashmap);
//                                                                                                            for (HashMap<String, Object> offerHashmap : offersListHashmap) {
//                                                                                                                shopsListHashmap = (List<HashMap<String, Object>>) offerHashmap.get("shopDataModels");
//                                                                                                                updatedShopsListHashmap = new ArrayList<>();
//                                                                                                                updatedShopsListHashmap.addAll(shopsListHashmap);
//                                                                                                                for (HashMap<String, Object> shopHashmap : shopsListHashmap) {
//                                                                                                                    if (shopHashmap.get("shopID").equals(shopDataModel.getShopID())) {
//                                                                                                                        updatedShopsListHashmap.remove(shopHashmap);
//                                                                                                                        updatedShopHashmap = new HashMap<>();
////                                            Log.d("timestamp-", ServerValue.TIMESTAMP.get("timestamp"));
//                                                                                                                        updatedShopHashmap.put("shopID", shopDataModel.getShopID());
//                                                                                                                        updatedShopHashmap.put("retailerID", shopDataModel.getRetailerID());
//                                                                                                                        updatedShopHashmap.put("shopName", shopDataModel.getShopName());
//                                                                                                                        updatedShopHashmap.put("shopAddress", shopDataModel.getShopAddress());
//                                                                                                                        updatedShopHashmap.put("shopCellNo", shopDataModel.getShopCellNo());
//                                                                                                                        updatedShopHashmap.put("shopEmail", shopDataModel.getShopEmail());
//                                                                                                                        updatedShopHashmap.put("shopImageURL", shopDataModel.getShopImageURL());
//                                                                                                                        updatedShopHashmap.put("shopLatitude", shopDataModel.getShopLatitude());
//                                                                                                                        updatedShopHashmap.put("shopLongitude", shopDataModel.getShopLongitude());
//                                                                                                                        updatedShopHashmap.put("productsList", shopHashmap.get("productsList"));
////                                        OfferDataModel f = shopDataModel.getOfferDataModel();
//                                                                                                                        updatedShopHashmap.put("öfferDataModel", null);
//                                                                                                                        updatedShopsListHashmap.add(updatedShopHashmap);
//                                                                                                                    }
//                                                                                                                }
//                                                                                                                updatedOffersListHashmap.remove(offerHashmap);
//                                                                                                                updatedOfferHashmap = new HashMap<>();
//                                                                                                                updatedOfferHashmap.put("offerID", offerHashmap.get("offerID"));
//                                                                                                                updatedOfferHashmap.put("offerDiscount", offerHashmap.get("offerDiscount"));
//                                                                                                                updatedOfferHashmap.put("offerStartDate", offerHashmap.get("offerStartDate"));
//                                                                                                                updatedOfferHashmap.put("offerEndDate", offerHashmap.get("offerEndDate"));
//                                                                                                                updatedOfferHashmap.put("offerName", offerHashmap.get("offerName"));
//                                                                                                                updatedOfferHashmap.put("sun", offerHashmap.get("sun"));
//                                                                                                                updatedOfferHashmap.put("mon", offerHashmap.get("mon"));
//                                                                                                                updatedOfferHashmap.put("tue", offerHashmap.get("tue"));
//                                                                                                                updatedOfferHashmap.put("wed", offerHashmap.get("wed"));
//                                                                                                                updatedOfferHashmap.put("thu", offerHashmap.get("thu"));
//                                                                                                                updatedOfferHashmap.put("fri", offerHashmap.get("fri"));
//                                                                                                                updatedOfferHashmap.put("sat", offerHashmap.get("sat"));
//                                                                                                                updatedOfferHashmap.put("offerStatus", offerHashmap.get("offerStatus"));
//                                                                                                                updatedOfferHashmap.put("shopDataModels", updatedShopsListHashmap);
//                                                                                                                updatedOffersListHashmap.add(updatedOfferHashmap);
//
//
//                                                                                                            }
//
//                                                                                                            firebaseFirestore.collection("offers").document(shopDataModel.getRetailerID())
//                                                                                                                    .update("offersList", updatedOffersListHashmap)
//                                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                                                                        @Override
//                                                                                                                        public void onSuccess(Void aVoid) {
//                                                                                                                            Log.d("updated_offerslist", updatedOffersListHashmap.toString());
//                                                                                                                            emitter.onNext(documentReference);
//
//                                                                                                                        }
//                                                                                                                    })
//                                                                                                                    .addOnFailureListener(new OnFailureListener() {
//                                                                                                                        @Override
//                                                                                                                        public void onFailure(@NonNull Exception e) {
//                                                                                                                            emitter.onError(e);
//                                                                                                                        }
//                                                                                                                    });
//
//                                                                                                        }
//                                                                                                    }
//                                                                                                })
//                                                                                                .addOnFailureListener(new OnFailureListener() {
//                                                                                                    @Override
//                                                                                                    public void onFailure(@NonNull Exception e) {
//                                                                                                        emitter.onError(e);
//                                                                                                    }
//                                                                                                });
//                                                                                    }
//                                                                                })
//                                                                                .addOnFailureListener(new OnFailureListener() {
//                                                                                    @Override
//                                                                                    public void onFailure(@NonNull Exception e) {
//                                                                                        emitter.onError(e);
//                                                                                    }
//                                                                                });
//
//
//                                                                    }
////                                                                    emitter.onNext(documentReference);
//                                                                }
//                                                            })
//                                                            .addOnFailureListener(new OnFailureListener() {
//                                                                @Override
//                                                                public void onFailure(@NonNull Exception e) {
//                                                                    emitter.onError(e);
//                                                                }
//                                                            });
//
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    emitter.onError(e);
//                                                }
//                                            });
//                                }
//                            }
//
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                emitter.onError(e);
//                            }
//                        }));

    }


    public Observable<DocumentReference> deleteShop(ShopDataModel shopDataModel) {
        documentReference = firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail());
        return Observable.create(emitter ->
//                database.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail())
                firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail())
//                        .add(shopDataModel)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        }));
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Log.d("AddshopRepository", "...DocumentSnapshot added with ID: " + documentReference.getId());
//                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("AddshopRepository", "...Error adding shop" + e);
//                                emitter.onError(e);
//                            }
//                        }));
    }

    public Observable<DocumentReference> addShop(ShopDataModel shopDataModel) {
        documentReference = firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail());
        return Observable.create(emitter ->
//                database.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail())
                firebaseFirestore.collection("shops").document(shopDataModel.getRetailerID()).collection("shops").document(shopDataModel.getShopEmail())
//                        .add(shopDataModel)
                        .set(shopDataModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        }));
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Log.d("AddshopRepository", "...DocumentSnapshot added with ID: " + documentReference.getId());
//                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("AddshopRepository", "...Error adding shop" + e);
//                                emitter.onError(e);
//                            }
//                        }));
    }

//    public Observable<DocumentSnapshot> getShopDetails(ShopDataModel shopDataModel) {
//        return Observable.create(
//                emitter -> {
//                    firebaseFirestore.collection("shops").document(shopDataModel.getShopDocumentID())
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    DocumentSnapshot documentSnapshot = task.getResult();
//                                    emitter.onNext(documentSnapshot);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//
//                                }
//                            });
//                }
//        );
//    }
}

