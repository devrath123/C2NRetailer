package com.example.c2n.customer_single_shop_products.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.core.models.ProductDetailsQueryDocumentSnapshotDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerSingleShopProductsRepository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Inject
    public CustomerSingleShopProductsRepository() {

    }

    public Observable<List<HashMap<String, Object>>> getAllProducts(String[] param) {
        List<ProductDetailsQueryDocumentSnapshotDataModel> productDetailsQueryDocumentSnapshotDataModels = new ArrayList<>();
        return Observable.create(emitter ->
                database.collection("shops").document(param[0])
                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
//                                    ProductDetailsQueryDocumentSnapshotDataModel productDetailsQueryDocumentSnapshotDataModel = new ProductDetailsQueryDocumentSnapshotDataModel();
//                                    productDetailsQueryDocumentSnapshotDataModel.setShopID(param[1]);
//                                    productDetailsQueryDocumentSnapshotDataModel.setQueryDocumentSnapshot(queryDocumentSnapshot);
//                                    productDetailsQueryDocumentSnapshotDataModels.add(productDetailsQueryDocumentSnapshotDataModel);
//                                }
//                                emitter.onNext(productDetailsQueryDocumentSnapshotDataModels);
//                            }
//                        })
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> products = new ArrayList<>();
                                List<HashMap<String, Object>> shopsListHashmap = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                                for (HashMap<String, Object> hashmap : shopsListHashmap) {
                                    if (hashmap.get("shopID").equals(param[1])) {
                                        Log.d("QueryDocSnap(pros)- ", hashmap.get("shopID") + "--> " + hashmap.get("productsList"));
                                        if (products.size() != 0) {
                                            products.clear();
                                        }
                                        products.addAll((List<HashMap<String, Object>>) hashmap.get("productsList"));
//                                        emitter.onNext((List<HashMap<String, Object>>) hashmap.get("productsList"));
                                    }
                                }
                                emitter.onNext(products);
//                                emitter.onError(new Exception("Shop Not Exists"));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(cat.)..- ", "getAllShopIDs Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }
}
