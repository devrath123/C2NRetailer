package com.example.c2n.customer_offer_cards.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model1.OfferProductDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class CustomerOfferRepository {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<String> retailerID = new ArrayList<>();


    @Inject
    CustomerOfferRepository() {

    }

    public Observable<List<String>> getRetailersIDs() {
        if (retailerID.size() != 0)
            retailerID.clear();
        return Observable.create(
                emitter -> firebaseFirestore.collection("offers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        retailerID.add(documentSnapshot.getId());
                                        Log.d("retailerDocumentSnap", documentSnapshot.getId() + " => " + documentSnapshot.getData().toString());
                                    }


//                                    int temp = i;
                                    Log.d("offerList", retailerID.size() + "");

                                }
                                emitter.onNext(retailerID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("error_loading_shops", e.getMessage());
                                emitter.onError(e);
                            }
                        }));

    }


    public Observable<DocumentSnapshot>getAllOffers(String retailerID) {
        return Observable.create(
                emitter -> firebaseFirestore.collection("offers").document(retailerID)
                        .get()
                       .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                           @Override
                           public void onSuccess(DocumentSnapshot documentSnapshot) {
                               Log.d("getAllOffers",documentSnapshot.getId());
                               emitter.onNext(documentSnapshot);
                           }
                       })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("offererror", "" + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }


    public Observable<DocumentSnapshot> getOfferedProducts(OfferProductDataModel offeredProduct) {
//        Log.d("offeredProducts_", "size : " + offeredProductsList.size());
        List<DocumentSnapshot> offeredProductsSnapshots = new ArrayList<>();

//        for (int i = 0; i < offeredProductsList.size() - 1; i++) {
//
//            firebaseFirestore.collection("products").document(offeredProductsList.get(i).getRetailerID()).collection(offeredProductsList.get(i).getShopEmail()).document(offeredProductsList.get(i).getProductID())
//                    .get()
//                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            offeredProductsSnapshots.add(documentSnapshot);
//                            Log.d("offeredProducts", "" + offeredProductsSnapshots.toString());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("offererror", "" + e.getMessage());
//                        }
//                    });
//        }

        return Observable.create(
                emitter ->
                        firebaseFirestore.collection("products").document(offeredProduct.getRetailerID()).collection(offeredProduct.getShopID()).document(offeredProduct.getProductID())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        offeredProductsSnapshots.add(documentSnapshot);
                                        Log.d("offeredProducts", "" + documentSnapshot.toString());
                                        emitter.onNext(documentSnapshot);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("offererror", "" + e.getMessage());
                                        emitter.onError(e);
                                    }
                                }));

    }

}
