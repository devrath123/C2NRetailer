package com.example.c2n.make_offer_card.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.models.OfferDataModel;
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
 * Created by vipul.singhal on 02-07-2018.
 */

public class MakeOfferCardRepository {

    private final String TAG = "MakeOfferCardRepository";

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DocumentReference offer;
    List<HashMap<String, Object>> offerList = new ArrayList<>();
    private DocumentReference documentReference = database.collection("retailers").document();
    List<HashMap<String, Object>> offerProducts = new ArrayList<>();
    List<HashMap<String, Object>> updatedOffersListHashmap;


    @Inject
    MakeOfferCardRepository() {

    }


    public Observable<DocumentReference> addOfferCard(OfferDataModel offerDataModel, String retailerEmail) {

        Log.d("offer_card_", offerDataModel.toString());
        DocumentReference documentReference = database.collection("offers").document(retailerEmail);
        return Observable.create(emitter ->
                database.collection("offers").document(retailerEmail)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().get("offersList") == null) {
                                        Log.d("offers ", "" + offerDataModel.toString());
                                        HashMap<String, Object> offerHashmap = new HashMap<>();
                                        offerHashmap.put("offerID", System.currentTimeMillis() + offerDataModel.getOfferName());
                                        offerHashmap.put("offerName", offerDataModel.getOfferName());
                                        offerHashmap.put("offerDiscount", offerDataModel.getOfferDiscount());
                                        offerHashmap.put("offerStartDate", offerDataModel.getOfferStartDate());
                                        offerHashmap.put("offerEndDate", offerDataModel.getOfferEndDate());
                                        offerHashmap.put("sun", offerDataModel.isSun());
                                        offerHashmap.put("mon", offerDataModel.isMon());
                                        offerHashmap.put("tue", offerDataModel.isTue());
                                        offerHashmap.put("wed", offerDataModel.isWed());
                                        offerHashmap.put("thu", offerDataModel.isThu());
                                        offerHashmap.put("fri", offerDataModel.isFri());
                                        offerHashmap.put("sat", offerDataModel.isSat());
                                        offerHashmap.put("offerStatus", offerDataModel.isOfferStatus());
                                        offerHashmap.put("shopDataModels", null);
//                                        offerList.add(offerDataModel);
                                        List<HashMap<String, Object>> offersList = new ArrayList<>();
                                        offersList.add(offerHashmap);
                                        HashMap<String, List<HashMap<String, Object>>> offersListHashmap = new HashMap();
                                        offersListHashmap.put("offersList", offersList);
                                        database.collection("offers").document(retailerEmail)
                                                .set(offersListHashmap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("offersList", offerList.toString());
                                                        emitter.onNext(documentReference);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("offersFailure", e.getMessage());
                                                        emitter.onError(e);
                                                    }
                                                });
                                    } else {
                                        offerList = (List<HashMap<String, Object>>) task.getResult().get("offersList");
                                        HashMap<String, Object> offerHashmap = new HashMap<>();
                                        offerHashmap.put("offerID", System.currentTimeMillis() + offerDataModel.getOfferName());
                                        offerHashmap.put("offerName", offerDataModel.getOfferName());
                                        offerHashmap.put("offerDiscount", offerDataModel.getOfferDiscount());
                                        offerHashmap.put("offerStartDate", offerDataModel.getOfferStartDate());
                                        offerHashmap.put("offerEndDate", offerDataModel.getOfferEndDate());
                                        offerHashmap.put("sun", offerDataModel.isSun());
                                        offerHashmap.put("mon", offerDataModel.isMon());
                                        offerHashmap.put("tue", offerDataModel.isTue());
                                        offerHashmap.put("wed", offerDataModel.isWed());
                                        offerHashmap.put("thu", offerDataModel.isThu());
                                        offerHashmap.put("fri", offerDataModel.isFri());
                                        offerHashmap.put("sat", offerDataModel.isSat());
                                        offerHashmap.put("offerStatus", offerDataModel.isOfferStatus());
                                        offerHashmap.put("shopDataModels", null);
                                        offerList.add(offerHashmap);
                                        HashMap<String, List<HashMap<String, Object>>> offersListHashmap = new HashMap();
                                        offersListHashmap.put("offersList", offerList);
                                        database.collection("offers")
                                                .document(retailerEmail)
                                                .set(offersListHashmap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("OfferListDataModel", offerList.toString());
                                                        emitter.onNext(documentReference);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        emitter.onError(e);
                                                        Log.e("onFailure", e.getMessage());
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
                                Log.e("onFailure", e.getMessage());
                            }
                        }));
    }


    public Observable<Boolean> updateOfferCard(OfferDataModel offerDataModel, String userDocumentID) {

        Log.d(TAG, "updateOfferCard OfferDatModel :" + offerDataModel.toString());

        database.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                            for (int i = 0; i < shops.size(); i++) {
                                HashMap<String, Object> shop = shops.get(i);
                                if (shop.get("offerDataModel") != null) {
                                    HashMap<String, Object> offer = (HashMap<String, Object>) shops.get(i).get("offerDataModel");
                                    if (offer.get("offerID").equals(offerDataModel.getOfferID())) {

                                        HashMap<String, Object> updatedOffersList = new HashMap<>();
                                        updatedOffersList.put("offerID", offerDataModel.getOfferID());
                                        updatedOffersList.put("offerName", offerDataModel.getOfferName());
                                        updatedOffersList.put("offerDiscount", offerDataModel.getOfferDiscount());
                                        updatedOffersList.put("offerStartDate", offerDataModel.getOfferStartDate());
                                        updatedOffersList.put("offerEndDate", offerDataModel.getOfferEndDate());
                                        updatedOffersList.put("sun", offerDataModel.isSun());
                                        updatedOffersList.put("mon", offerDataModel.isMon());
                                        updatedOffersList.put("tue", offerDataModel.isTue());
                                        updatedOffersList.put("wed", offerDataModel.isWed());
                                        updatedOffersList.put("thu", offerDataModel.isThu());
                                        updatedOffersList.put("fri", offerDataModel.isFri());
                                        updatedOffersList.put("sat", offerDataModel.isSat());
                                        updatedOffersList.put("offerStatus", offerDataModel.isOfferStatus());
                                        updatedOffersList.put("shopDataModels", null);

                                        shop.put("offerDataModel", updatedOffersList);
                                    }
                                }
                            }
                            database.collection("productoffers").document(queryDocumentSnapshot.getId())
                                    .update("shopDataModels", shops)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "updateOfferCard productoffers : Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("onFailure", e.getMessage());
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("onFailure", e.getMessage());
                    }
                });

        database.collection("shops")
                .document(userDocumentID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            HashMap<String, Object> shop = shops.get(i);
                            if (shop.get("productsList") != null) {
                                List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shop.get("productsList");
                                for (int j = 0; j < products.size(); j++) {
                                    HashMap<String, Object> product = products.get(j);
                                    if (product.get("productOffer") != null) {
                                        HashMap<String, Object> offer = (HashMap<String, Object>) product.get("productOffer");
                                        if (offer.get("offerID").equals(offerDataModel.getOfferID())) {

                                            HashMap<String, Object> updatedOffersList = new HashMap<>();
                                            updatedOffersList.put("offerID", offerDataModel.getOfferID());
                                            updatedOffersList.put("offerName", offerDataModel.getOfferName());
                                            updatedOffersList.put("offerDiscount", offerDataModel.getOfferDiscount());
                                            updatedOffersList.put("offerStartDate", offerDataModel.getOfferStartDate());
                                            updatedOffersList.put("offerEndDate", offerDataModel.getOfferEndDate());
                                            updatedOffersList.put("sun", offerDataModel.isSun());
                                            updatedOffersList.put("mon", offerDataModel.isMon());
                                            updatedOffersList.put("tue", offerDataModel.isTue());
                                            updatedOffersList.put("wed", offerDataModel.isWed());
                                            updatedOffersList.put("thu", offerDataModel.isThu());
                                            updatedOffersList.put("fri", offerDataModel.isFri());
                                            updatedOffersList.put("sat", offerDataModel.isSat());
                                            updatedOffersList.put("offerStatus", offerDataModel.isOfferStatus());
                                            updatedOffersList.put("shopDataModels", null);

                                            product.put("productOffer", updatedOffersList);
                                        }
                                    }
                                }
                            }
                        }
                        database.collection("shops").document(userDocumentID)
                                .update("retailerShops", shops)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "updateOfferCard shops : Success");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "updateOfferCard shops : Error : " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "updateOfferCard shops : Error : " + e.getMessage());
                    }
                });

        return Observable.create(emitter ->
                database.collection("offers").document(userDocumentID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                for (int i = 0; i < offers.size(); i++) {
                                    HashMap<String, Object> offer = offers.get(i);
                                    if (offer.get("offerID").equals(offerDataModel.getOfferID())) {
                                        offer.put("offerID", offerDataModel.getOfferID());
                                        offer.put("offerName", offerDataModel.getOfferName());
                                        offer.put("offerDiscount", offerDataModel.getOfferDiscount());
                                        offer.put("offerStartDate", offerDataModel.getOfferStartDate());
                                        offer.put("offerEndDate", offerDataModel.getOfferEndDate());
                                        offer.put("sun", offerDataModel.isSun());
                                        offer.put("mon", offerDataModel.isMon());
                                        offer.put("tue", offerDataModel.isTue());
                                        offer.put("wed", offerDataModel.isWed());
                                        offer.put("thu", offerDataModel.isThu());
                                        offer.put("fri", offerDataModel.isFri());
                                        offer.put("sat", offerDataModel.isSat());
                                        offer.put("offerStatus", offerDataModel.isOfferStatus());
                                    }
                                }
                                database.collection("offers").document(userDocumentID)
                                        .update("offersList", offers)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "updateOfferCard offers : Success");
                                                emitter.onNext(true);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "updateOfferCard Error : " + e.getMessage());
                                            }
                                        });
                            }
                        })
//                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    offerProducts = (List<HashMap<String, Object>>) task.getResult().get("offersList");
//                                    updatedOffersListHashmap = new ArrayList<>();
//                                    updatedOffersListHashmap.addAll(offerProducts);
//                                    for (HashMap<String, Object> offerProductList : offerProducts) {
//                                        if (offerProductList.get("offerID").equals(offerDataModel.getOfferID())) {
//                                            updatedOffersListHashmap.remove(offerProductList);
//                                            HashMap<String, Object> updatedOffersList = new HashMap<>();
//                                            updatedOffersList.put("offerID", offerDataModel.getOfferID());
//                                            updatedOffersList.put("offerName", offerDataModel.getOfferName());
//                                            updatedOffersList.put("offerDiscount", offerDataModel.getOfferDiscount());
//                                            updatedOffersList.put("offerStartDate", offerDataModel.getOfferStartDate());
//                                            updatedOffersList.put("offerEndDate", offerDataModel.getOfferEndDate());
//                                            updatedOffersList.put("sun", offerDataModel.isSun());
//                                            updatedOffersList.put("mon", offerDataModel.isMon());
//                                            updatedOffersList.put("tue", offerDataModel.isTue());
//                                            updatedOffersList.put("wed", offerDataModel.isWed());
//                                            updatedOffersList.put("thu", offerDataModel.isThu());
//                                            updatedOffersList.put("fri", offerDataModel.isFri());
//                                            updatedOffersList.put("sat", offerDataModel.isSat());
//                                            updatedOffersList.put("offerStatus", offerDataModel.isOfferStatus());
//                                            updatedOffersList.put("shopDataModels", offerDataModel.getShopDataModels());
//                                            updatedOffersListHashmap.add(updatedOffersList);
//                                        }
//                                    }
//                                    database.collection("offers").document(userDocumentID)
//                                            .update("offersList", updatedOffersListHashmap)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d("updatedoffersList", updatedOffersListHashmap + "");
//                                                    emitter.onNext(documentReference);
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d("onFailure", e.getMessage());
//                                                    emitter.onError(e);
//                                                }
//                                            });
//                                }
//                            }
//                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "updateOfferCard Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }
}






 /*   public Observable<DocumentReference> updateOfferCard(OfferDataModel offerDataModel, String userDocumentID) {
        offer = database.collection("offers").document(userDocumentID);

        offer.update("offerName", offerDataModel.getOfferName());
        offer.update("offerDiscount", offerDataModel.getOfferDiscount());
        offer.update("offerStartDate", offerDataModel.getOfferStartDate());
        offer.update("offerEndDate", offerDataModel.getOfferEndDate());
        offer.update("sun", offerDataModel.isSun());
        offer.update("mon", offerDataModel.isMon());
        offer.update("tue", offerDataModel.isTue());
        offer.update("wed", offerDataModel.isWed());
        offer.update("thu", offerDataModel.isThu());
        offer.update("fri", offerDataModel.isFri());
        return Observable.create(emitter ->
                offer.update("sat", offerDataModel.isSat())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                Map<String, Object> productOffer = new HashMap<>();
                                productOffer.put("offerID", offerDataModel.getOfferID());
                                productOffer.put("offerDiscount", offerDataModel.getOfferDiscount());
                                productOffer.put("mon", offerDataModel.isMon());
                                productOffer.put("tue", offerDataModel.isTue());
                                productOffer.put("wed", offerDataModel.isWed());
                                productOffer.put("thu", offerDataModel.isThu());
                                productOffer.put("fri", offerDataModel.isFri());
                                productOffer.put("sat", offerDataModel.isSat());
                                productOffer.put("sun", offerDataModel.isSun());
                                productOffer.put("offerStartDate", offerDataModel.getOfferStartDate());
                                productOffer.put("offerEndDate", offerDataModel.getOfferEndDate());
                                productOffer.put("offerName", offerDataModel.getOfferName());
                                productOffer.put("offerStatus", offerDataModel.isOfferStatus());
                                productOffer.put("offerProducts", null);


                                database.collection("offers").document(userDocumentID).collection("templates").document(offerDataModel.getOfferID())
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override

                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                if (documentSnapshot.get("offerProducts") != null) {
                                                offerProducts = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
                                                if (offerProducts != null && offerProducts.size() != 0) {


                                                    for (int i = 0; i < offerProducts.size(); i++) {
                                                        HashMap<String, List<String>> shopProductsHashmap = offerProducts.get(i);
                                                        String shopEmail = shopProductsHashmap.keySet().iterator().next();
                                                        List<String> productIDs = shopProductsHashmap.get(shopEmail);
                                                        for (int j = 0; j < productIDs.size(); j++) {

                                                            String productId = productIDs.get(j);
                                                            database.collection("products").document(userDocumentID).collection(shopEmail).document(productId)
                                                                    .update("productOffer", productOffer)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Log.d("remove_", "" + shopEmail + "-" + productId);
                                                                        }
                                                                    })
//
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Log.d("getOffer", "" + e.getMessage());
//                                                            emitter.onError(e);
                                                                        }
                                                                    });


                                                        }
                                                    }

                                                    //----------------------------------------


                                                    HashMap<String, List<String>> shopProductsHashmap = offerProducts.get(offerProducts.size() - 1);
                                                    String shopEmail = shopProductsHashmap.keySet().iterator().next();
                                                    List<String> productIDs = shopProductsHashmap.get(shopEmail);
                                                    for (int j = 0; j < productIDs.size() - 1; j++) {

                                                        String productId = productIDs.get(j);
                                                        database.collection("products").document(userDocumentID).collection(shopEmail).document(productId)
                                                                .update("productOffer", productOffer)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Log.d("remove_last_shop", "" + shopEmail + "-" + productId);
                                                                    }
                                                                })
//
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.d("getOffer", "" + e.getMessage());
//                                                        emitter.onError(e);
                                                                    }
                                                                });


                                                    }


                                                    //----------------------------------------------


                                                    String productId = productIDs.get(productIDs.size() - 1);
                                                    database.collection("products").document(userDocumentID).collection(shopEmail).document(productId)
                                                            .update("productOffer", productOffer)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d("remove_last_shoppr", "" + shopEmail + "-" + productId);
                                                                    emitter.onNext(documentReference);

//
//                                                                    database.collection("offers").document(userDocumentID).collection("templates").document(offerDataModel.getOfferID())
//                                                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                        @Override
//                                                                        public void onSuccess(Void aVoid) {
//                                                                            emitter.onNext(documentReference);
//                                                                            Log.d("remove", "offeracrcard");
//                                                                        }
//                                                                    })
//                                                                            .addOnFailureListener(new OnFailureListener() {
//                                                                                @Override
//                                                                                public void onFailure(@NonNull Exception e) {
//                                                                                    emitter.onError(e);
//                                                                                    Log.d("remove_error", "offeracrcard");
//
//                                                                                }
//                                                                            });


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


                                                    //----------------------------------------


                                                    //--------------------------------------------------------------


                                                } else {
//                                                    database.collection("offers").document(userDocumentID).collection("templates").document(offerDataModel.getOfferID())
//                                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            emitter.onNext(documentReference);
//                                                            Log.d("remove_empty", "offercard");
//                                                        }
//                                                    })
//                                                            .addOnFailureListener(new OnFailureListener() {
//                                                                @Override
//                                                                public void onFailure(@NonNull Exception e) {
//                                                                    emitter.onError(e);
//                                                                    Log.d("remove_error_empty", "offercard");
//
//                                                                }
//                                                            });
                                                }
//                                }

//                                                emitter.onNext(documentReference);
                                                Log.d("offers_get", "true");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                emitter.onError(e);
                                            }
                                        });


                                Log.d("updateProductDescriptio", "success");
//                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("updateProductDescriptio", "" + e.getMessage());
                            }
                        }));
    }
}*/
