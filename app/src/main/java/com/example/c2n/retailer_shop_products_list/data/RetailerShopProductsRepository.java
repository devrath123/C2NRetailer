package com.example.c2n.retailer_shop_products_list.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class RetailerShopProductsRepository {
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();
    private DocumentReference documentReference = database.collection("products").document();

    @Inject
    RetailerShopProductsRepository() {
    }

    public Observable<List<QueryDocumentSnapshot>> loadProducts(String[] selectedShopnCategory) {
        if (documentList.size() != 0)
            documentList.clear();
        return Observable.create(emitter ->
                database.collection("inventory").document(selectedShopnCategory[0]).collection(selectedShopnCategory[1])
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


    public Observable<DocumentReference> updateProductUnavailable(String retailerEmail, String shopEmail, String productId) {

        return Observable.create(emitter ->
                database.collection("products").document(retailerEmail).collection(shopEmail).document(productId)
                        .update("productStockStatus", false)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("product_unavailable", "success");
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("error_product_unav", "" + e.getMessage());
                                emitter.onError(e);
                            }
                        })
        );
    }

    public Observable<DocumentReference> updateProductAvailable(String retailerEmail, String shopEmail, String productId) {

        return Observable.create(emitter ->
                database.collection("products").document(retailerEmail).collection(shopEmail).document(productId)
                        .update("productStockStatus", true)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("product_available", "success");
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("error_product_av", "" + e.getMessage());
                                emitter.onError(e);
                            }
                        })
        );
    }

}


