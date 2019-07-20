package com.example.c2n.customer_single_product.data;

import android.support.annotation.NonNull;

import com.example.c2n.core.models.ProductDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerSingleProductRepository {
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Inject
    CustomerSingleProductRepository() {

    }

    public Observable<DocumentSnapshot> getProductOffers(String productID) {
        return Observable.create(emitter ->
                database.collection("productoffers").document(productID)
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
}
