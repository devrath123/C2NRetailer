package com.example.c2n.offer_cards_list.data;

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
 * Created by shivani.singh on 28-06-2018.
 */

public class OffersRepository {

    private final String TAG = OffersRepository.class.getSimpleName();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> queryDocumentSnapshots = new ArrayList<>();
    DocumentReference offer;
    OfferDataModel hashoffer = new OfferDataModel();
    List<HashMap<String, List<String>>> offerProducts = new ArrayList<>();
    private DocumentReference documentReference = firebaseFirestore.collection("retailers").document();


    @Inject

    public OffersRepository() {
    }

    public Observable<DocumentSnapshot> getOfferCards(String userDocumentId) {
        Log.d(TAG, "Retailer Email : " + userDocumentId);
        return Observable.create(emitter -> firebaseFirestore.collection("offers").document(userDocumentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        emitter.onNext(documentSnapshot);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                }));

    }


    //---------------------------------- delete offer card

//    public Observable<DocumentReference> deleteOfferCard(String userDocumentId, String offerDocumentId) {
//        return Observable.create(
//                emitter -> firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId)
//                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
//                        })
//
//        );
//    }
    //---------------------------------- delete offer card


    public Observable<Boolean> deleteOfferCard(String userDocumentId, String offerID) {

        firebaseFirestore.collection("shops").document(userDocumentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            if (shops.get(i).get("productsList") != null) {
                                List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shops.get(i).get("productsList");
                                for (int j = 0; j < products.size(); j++) {
                                    HashMap<String, Object> product = products.get(j);
                                    if (product.get("productOffer") != null) {
                                        HashMap<String, Object> offer = (HashMap<String, Object>) product.get("productOffer");
                                        if (offer.get("offerID").equals(offerID)) {
                                            product.put("productOffer", null);
                                        }
                                    }
                                }
                            }
                        }
                        firebaseFirestore.collection("shops").document(userDocumentId)
                                .update("retailerShops", shops)
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
                        Log.d(TAG, "activateOfferCard() shops Error : " + e.getMessage());
                    }
                });

        firebaseFirestore.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                            for (int i = 0; i < shops.size(); i++) {
                                if (shops.get(i).get("offerDataModel") != null) {
                                    HashMap<String, Object> offer = (HashMap<String, Object>) shops.get(i).get("offerDataModel");
                                    if (offer.get("offerID").equals(offerID)) {
                                        shops.get(i).put("offerDataModel", null);
                                    }
                                }
                            }
                            firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
                                    .update("shopDataModels", shops)
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("offers").document(userDocumentId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                for (int i = 0; i < offers.size(); i++) {
                                    HashMap<String, Object> offer = offers.get(i);
                                    if (offer.get("offerID").equals(offerID)) {
                                        offers.remove(offers.indexOf(offer));
                                    }
                                }
                                if (offers.size() == 0) {
                                    firebaseFirestore.collection("offers").document(userDocumentId)
                                            .update("offersList", null)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    emitter.onNext(true);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                } else {
                                    firebaseFirestore.collection("offers").document(userDocumentId)
                                            .update("offersList", offers)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
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
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "activateOfferCard Error : " + e.getMessage());
                            }
                        }));

//        return Observable.create(
//                emitter -> firebaseFirestore.collection("offers").document(userDocumentId)
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
////                                if (documentSnapshot.get("offerProducts") != null) {
//                                offerProducts = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                if (offerProducts != null && offerProducts.size() != 0) {
//
//
//                                    for (int i = 0; i < offerProducts.size() - 1; i++) {
//                                        HashMap<String, List<String>> shopProductsHashmap = offerProducts.get(i);
//                                        String shopEmail = shopProductsHashmap.keySet().iterator().next();
//                                        List<String> productIDs = shopProductsHashmap.get(shopEmail);
//                                        for (int j = 0; j < productIDs.size(); j++) {
//
//                                            String productId = productIDs.get(j);
//                                            firebaseFirestore.collection("products").document(userDocumentId).collection(shopEmail).document(productId)
//                                                    .update("productOffer", null)
//                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            Log.d("remove_", "" + shopEmail + "-" + productId);
//                                                        }
//                                                    })
////
//                                                    .addOnFailureListener(new OnFailureListener() {
//                                                        @Override
//                                                        public void onFailure(@NonNull Exception e) {
//                                                            Log.d("getOffer", "" + e.getMessage());
////                                                            emitter.onError(e);
//                                                        }
//                                                    });
//
//
//                                        }
//                                    }
//
//                                    //----------------------------------------
//
//
//                                    HashMap<String, List<String>> shopProductsHashmap = offerProducts.get(offerProducts.size() - 1);
//                                    String shopEmail = shopProductsHashmap.keySet().iterator().next();
//                                    List<String> productIDs = shopProductsHashmap.get(shopEmail);
//                                    for (int j = 0; j < productIDs.size() - 1; j++) {
//
//                                        String productId = productIDs.get(j);
//                                        firebaseFirestore.collection("products").document(userDocumentId).collection(shopEmail).document(productId)
//                                                .update("productOffer", null)
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//                                                        Log.d("remove_last_shop", "" + shopEmail + "-" + productId);
//                                                    }
//                                                })
////
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        Log.d("getOffer", "" + e.getMessage());
////                                                        emitter.onError(e);
//                                                    }
//                                                });
//
//
//                                    }
//
//
//                                    //----------------------------------------------
//
//
//                                    String productId = productIDs.get(productIDs.size() - 1);
//                                    firebaseFirestore.collection("products").document(userDocumentId).collection(shopEmail).document(productId)
//                                            .update("productOffer", null)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d("remove_last_shoppr", "" + shopEmail + "-" + productId);
////                                                        emitter.onNext(documentReference);
//
//
//                                                    firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId)
//                                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {
//                                                            emitter.onNext(documentReference);
//                                                            Log.d("remove", "offeracrcard");
//                                                        }
//                                                    })
//                                                            .addOnFailureListener(new OnFailureListener() {
//                                                                @Override
//                                                                public void onFailure(@NonNull Exception e) {
//                                                                    emitter.onError(e);
//                                                                    Log.d("remove_error", "offeracrcard");
//
//                                                                }
//                                                            });
//
//
//                                                }
//                                            })
////
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d("getOffer", "" + e.getMessage());
//                                                    emitter.onError(e);
//                                                }
//                                            });
//
//
//                                    //----------------------------------------
//
//
//                                    //--------------------------------------------------------------
//
//
//                                } else {
//                                    firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId)
//                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            emitter.onNext(documentReference);
//                                            Log.d("remove_empty", "offercard");
//                                        }
//                                    })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    emitter.onError(e);
//                                                    Log.d("remove_error_empty", "offercard");
//
//                                                }
//                                            });
//                                }
////                                }
//
////                                emitter.onNext(documentReference);
//                                Log.d("offers_get", "true");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                emitter.onError(e);
//                            }
//                        })
//
//        );
    }

//    public Observable<DocumentReference> activateOfferCard(String offerID, String userDocumentId) {
//        offer = firebaseFirestore.collection("offers").document(userDocumentId);
//
//        return Observable.create(emitter ->
//                offer.update("offerStatus", true)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("activeProductDescriptio", "success");
//
//                                offer.get()
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                                                if (documentSnapshot.get("offerProducts") != null && !((List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts")).isEmpty()) {
//                                                    offerProducts = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                                    for (int i = 0; i < offerProducts.size(); i++) {
//                                                        HashMap<String, List<String>> shopHashmap = offerProducts.get(i);
//                                                        String shopId = shopHashmap.keySet().iterator().next();
//                                                        List<String> productsId = shopHashmap.get(shopId);
//                                                        for (int j = 0; j < productsId.size(); j++) {
//                                                            firebaseFirestore.collection("products").document(userDocumentId).collection(shopId).document(productsId.get(j))
//                                                                    .update("productOfferStatus", true)
//                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                        @Override
//                                                                        public void onSuccess(Void aVoid) {
//                                                                            emitter.onComplete();
//                                                                        }
//                                                                    })
//                                                                    .addOnFailureListener(new OnFailureListener() {
//                                                                        @Override
//                                                                        public void onFailure(@NonNull Exception e) {
//                                                                            emitter.onError(e);
//                                                                        }
//                                                                    });
//
//                                                        }
//                                                    }
//
//                                                }
//                                                emitter.onNext(documentReference);
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                emitter.onError(e);
//                                            }
//                                        });
//
////                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("activeProductDescriptio", "" + e.getMessage());
//                                emitter.onError(e);
//                            }
//                        }));
//    }

    public Observable<Boolean> activateOfferCard(String offerID, String userDocumentId) {

        firebaseFirestore.collection("shops").document(userDocumentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            if (shops.get(i).get("productsList") != null) {
                                List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shops.get(i).get("productsList");
                                for (int j = 0; j < products.size(); j++) {
                                    HashMap<String, Object> product = products.get(j);
                                    if (product.get("productOffer") != null) {
                                        HashMap<String, Object> offer = (HashMap<String, Object>) product.get("productOffer");
                                        if (offer.get("offerID").equals(offerID)) {
                                            offer.put("offerStatus", true);
                                        }
                                    }
                                }
                            }
                        }
                        firebaseFirestore.collection("shops").document(userDocumentId)
                                .update("retailerShops", shops)
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
                        Log.d(TAG, "activateOfferCard() shops Error : " + e.getMessage());
                    }
                });

        firebaseFirestore.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                            for (int i = 0; i < shops.size(); i++) {
                                if (shops.get(i).get("offerDataModel") != null) {
                                    HashMap<String, Object> offer = (HashMap<String, Object>) shops.get(i).get("offerDataModel");
                                    if (offer.get("offerID").equals(offerID)) {
                                        offer.put("offerStatus", true);
                                    }
                                }
                            }
                            firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
                                    .update("shopDataModels", shops)
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("offers").document(userDocumentId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                for (int i = 0; i < offers.size(); i++) {
                                    if (offers.get(i).get("offerID").equals(offerID)) {
                                        offers.get(i).put("offerStatus", true);
                                        firebaseFirestore.collection("offers").document(userDocumentId)
                                                .update("offersList", offers)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        emitter.onNext(true);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "activateOfferCard Error : " + e.getMessage());
                                                    }
                                                });
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "activateOfferCard Error : " + e.getMessage());
                            }
                        }));

    }

    public Observable<Boolean> deactivateOfferCard(String userDocumentId, String offerID) {

        firebaseFirestore.collection("shops").document(userDocumentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                        for (int i = 0; i < shops.size(); i++) {
                            if (shops.get(i).get("productsList") != null) {
                                List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) shops.get(i).get("productsList");
                                for (int j = 0; j < products.size(); j++) {
                                    HashMap<String, Object> product = products.get(j);
                                    if (product.get("productOffer") != null) {
                                        HashMap<String, Object> offer = (HashMap<String, Object>) product.get("productOffer");
                                        if (offer.get("offerID").equals(offerID)) {
                                            offer.put("offerStatus", false);
                                        }
                                    }
                                }
                            }
                        }
                        firebaseFirestore.collection("shops").document(userDocumentId)
                                .update("retailerShops", shops)
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
                        Log.d(TAG, "activateOfferCard() shops Error : " + e.getMessage());
                    }
                });

        firebaseFirestore.collection("productoffers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            List<HashMap<String, Object>> shops = (List<HashMap<String, Object>>) queryDocumentSnapshot.get("shopDataModels");
                            for (int i = 0; i < shops.size(); i++) {
                                if (shops.get(i).get("offerDataModel") != null) {
                                    HashMap<String, Object> offer = (HashMap<String, Object>) shops.get(i).get("offerDataModel");
                                    if (offer.get("offerID").equals(offerID)) {
                                        offer.put("offerStatus", false);
                                    }
                                }
                            }
                            firebaseFirestore.collection("productoffers").document(queryDocumentSnapshot.getId())
                                    .update("shopDataModels", shops)
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        return Observable.create(emitter ->
                firebaseFirestore.collection("offers").document(userDocumentId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> offers = (List<HashMap<String, Object>>) documentSnapshot.get("offersList");
                                for (int i = 0; i < offers.size(); i++) {
                                    if (offers.get(i).get("offerID").equals(offerID)) {
                                        offers.get(i).put("offerStatus", false);
                                        firebaseFirestore.collection("offers").document(userDocumentId)
                                                .update("offersList", offers)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        emitter.onNext(true);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "deactivateOfferCard Error : " + e.getMessage());
                                                    }
                                                });
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "deactivateOfferCard Error : " + e.getMessage());
                            }
                        }));

    }

//    public Observable<DocumentReference> deactivateOfferCard(String userDocumentId, String offerDocumentId) {
//        offer = firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId);
//
//        return Observable.create(emitter ->
//                offer.update("offerStatus", false)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("deactiveProductDesc", "success");
//
//
//                                offer.get()
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                                                if (documentSnapshot.get("offerProducts") != null && !((List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts")).isEmpty()) {
//                                                    offerProducts = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
//                                                    for (int i = 0; i < offerProducts.size(); i++) {
//                                                        HashMap<String, List<String>> shopHashmap = offerProducts.get(i);
//                                                        String shopId = shopHashmap.keySet().iterator().next();
//                                                        List<String> productsId = shopHashmap.get(shopId);
//                                                        for (int j = 0; j < productsId.size(); j++) {
//                                                            firebaseFirestore.collection("products").document(userDocumentId).collection(shopId).document(productsId.get(j))
//                                                                    .update("productOfferStatus", false)
//                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                        @Override
//                                                                        public void onSuccess(Void aVoid) {
//                                                                            emitter.onComplete();
//                                                                        }
//                                                                    })
//                                                                    .addOnFailureListener(new OnFailureListener() {
//                                                                        @Override
//                                                                        public void onFailure(@NonNull Exception e) {
//                                                                            emitter.onError(e);
//                                                                        }
//                                                                    });
//                                                        }
//                                                    }
//
//                                                }
//                                                emitter.onNext(documentReference);
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                emitter.onError(e);
//                                            }
//                                        });
//
//
////                                emitter.onNext(documentReference);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("deactiveProductDesc", "" + e.getMessage());
//                            }
//                        }));
//    }
}
