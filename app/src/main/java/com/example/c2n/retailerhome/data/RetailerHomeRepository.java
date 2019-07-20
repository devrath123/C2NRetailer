package com.example.c2n.retailerhome.data;

import android.support.annotation.NonNull;
import android.util.Log;

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
 * Created by shivani.singh on 21-06-2018.
 */

public class RetailerHomeRepository {

//    FirebaseFirestore database = FirebaseFirestore.getInstance();
//    List<QueryDocumentSnapshot> documentSnapshots = new ArrayList<>();

    private final String TAG = "RetailerHomeRepository";

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> queryDocumentSnapshots = new ArrayList<>();
    private List<QueryDocumentSnapshot> documentList = new ArrayList<>();
    List<HashMap<String, List<String>>> offerProducts = new ArrayList<>();
    private DocumentReference documentReference = firebaseFirestore.collection("retailers").document();


    @Inject
    public RetailerHomeRepository() {

    }

//    public Observable<List<QueryDocumentSnapshot>> getRetailerList() {
//        return Observable.create(emitter ->
//                database.collection("testimonials")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//
//                                        documentSnapshots.add(documentSnapshot);
//                                        Log.d("RetailerHomeRepository ", documentSnapshot.getId() + "--------" + documentSnapshot.getData());
//                                    }
//                                    emitter.onNext(documentSnapshots);
//                                } else {
//                                    emitter.onError(task.getException());
//                                    Log.d("RetailerHomeRepository", "Error getting documents.", task.getException());
//                                }
//                            }
//                        }));
//    }

    public Observable<List<QueryDocumentSnapshot>> getRetailerList() {
        return Observable.create(
                emitter -> firebaseFirestore.collection("testimonials")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        queryDocumentSnapshots.add(documentSnapshot);
                                        Log.d("QueryDocumentSnapshot..", documentSnapshot.getId() + " => " + documentSnapshot.getData().toString());
                                    }
                                    emitter.onNext(queryDocumentSnapshots);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("loadPreferences", "" + e.getMessage());
                            }
                        }));
    }

    public Observable<DocumentSnapshot> getOfferCards(String userDocumentId) {
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
                })

        );

    }


    public Observable<DocumentReference> deleteExpireOfferCards(String userDocumentId, String offerDocumentId) {
        return Observable.create(
                emitter -> firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                if (documentSnapshot.get("offerProducts") != null) {
                                offerProducts = (List<HashMap<String, List<String>>>) documentSnapshot.get("offerProducts");
                                if (offerProducts != null && offerProducts.size() != 0) {


                                    for (int i = 0; i < offerProducts.size() - 1; i++) {
                                        HashMap<String, List<String>> shopProductsHashmap = offerProducts.get(i);
                                        String shopEmail = shopProductsHashmap.keySet().iterator().next();
                                        List<String> productIDs = shopProductsHashmap.get(shopEmail);
                                        for (int j = 0; j < productIDs.size(); j++) {

                                            String productId = productIDs.get(j);
                                            firebaseFirestore.collection("products").document(userDocumentId).collection(shopEmail).document(productId)
                                                    .update("productOffer", null)
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
                                        firebaseFirestore.collection("products").document(userDocumentId).collection(shopEmail).document(productId)
                                                .update("productOffer", null)
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
                                    firebaseFirestore.collection("products").document(userDocumentId).collection(shopEmail).document(productId)
                                            .update("productOffer", null)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("remove_last_shoppr", "" + shopEmail + "-" + productId);
//                                                        emitter.onNext(documentReference);


                                                    firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId)
                                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            emitter.onNext(documentReference);
                                                            Log.d("remove", "offeracrcard");
                                                        }
                                                    })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    emitter.onError(e);
                                                                    Log.d("remove_error", "offeracrcard");

                                                                }
                                                            });


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

//                                    else
//                                    {
//                                        firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId)
//                                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                emitter.onNext(documentReference);
//                                                Log.d("remove", "offeracrcard");
//                                            }
//                                        })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        emitter.onError(e);
//                                                        Log.d("remove_error", "offeracrcard");
//
//                                                    }
//                                                });
//                                    }

                                    //----------------------------------------


                                    //--------------------------------------------------------------


                                } else {
                                    firebaseFirestore.collection("offers").document(userDocumentId).collection("templates").document(offerDocumentId)
                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            emitter.onNext(documentReference);
                                            Log.d("remove_empty", "offercard");
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    emitter.onError(e);
                                                    Log.d("remove_error_empty", "offercard");

                                                }
                                            });
                                }
//                                }

//                                emitter.onNext(documentReference);
                                Log.d("offers_get", "true");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        })

        );
    }
}
