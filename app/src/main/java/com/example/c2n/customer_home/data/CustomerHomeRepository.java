package com.example.c2n.customer_home.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model1.ProductDetailsQueryDocumentSnapshotDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
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

/**
 * Created by shivani.singh on 16-08-2018.
 */

public class CustomerHomeRepository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Inject
    public CustomerHomeRepository() {
    }

    public Observable<DocumentSnapshot> getMylist(String userID) {
        return Observable.create(emitter ->
                database.collection("mylist").document(userID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                documentSnapshot.get("mylist");
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

    public Observable<QuerySnapshot> getAllMasterProducts() {
        return Observable.create(emitter ->
                database.collection("masterproducts")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                emitter.onNext(queryDocumentSnapshots);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(cat.)..- ", "Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<Boolean> addToMylist(String userID, MasterProductDataModel masterProductDataModel) {
        return Observable.create(emitter ->
                database.collection("mylist").document(userID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                HashMap<String, Object> stringListHashMap = new HashMap<>();
//                                if (documentSnapshot.get("mylist") != null) {
//                                    List<String> strings = (List<String>) documentSnapshot.get("mylist");
//                                    strings.add(productID);
//                                    stringListHashMap.put("mylist", strings);
//                                } else {
//                                    List<String> strings = new ArrayList<>();
//                                    strings.add(productID);
//                                    stringListHashMap.put("mylist", strings);
//                                }

                                List<HashMap<String, Object>> products;
                                if (documentSnapshot.get("mylist") != null) {
                                    products = (List<HashMap<String, Object>>) documentSnapshot.get("mylist");
                                    HashMap<String, Object> product = new HashMap<>();
                                    product.put("productID", masterProductDataModel.getProductID());
                                    product.put("productDescription", masterProductDataModel.getProductDescription());
                                    product.put("productName", masterProductDataModel.getProductName());
                                    product.put("productCategory", masterProductDataModel.getProductCategory());
                                    product.put("productImageURL", masterProductDataModel.getProductImageURL());
                                    products.add(product);

                                } else {
                                    products = new ArrayList<>();
                                    HashMap<String, Object> product = new HashMap<>();
                                    product.put("productID", masterProductDataModel.getProductID());
                                    product.put("productDescription", masterProductDataModel.getProductDescription());
                                    product.put("productName", masterProductDataModel.getProductName());
                                    product.put("productCategory", masterProductDataModel.getProductCategory());
                                    product.put("productImageURL", masterProductDataModel.getProductImageURL());
                                    products.add(product);
                                }

                                HashMap<String, Object> mylist = new HashMap<>();
                                mylist.put("mylist", products);

                                database.collection("mylist").document(userID)
                                        .set(mylist)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                emitter.onNext(true);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                emitter.onError(e);
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }));
    }

    public Observable<Boolean> removeFromMylist(String userID, String productID) {
        return Observable.create(emitter ->
                database.collection("mylist").document(userID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                List<HashMap<String, Object>> products;
                                if (documentSnapshot.get("mylist") != null) {
                                    products = (List<HashMap<String, Object>>) documentSnapshot.get("mylist");
                                    for (int i = 0; i < products.size(); i++) {
                                        HashMap<String, Object> product = products.get(i);
                                        if (product.get("productID").equals(productID)) {
                                            products.remove(i);
                                        }
                                    }

//                                }
//                                HashMap<String, Object> stringListHashMap = new HashMap<>();
//                                if (documentSnapshot.get("mylist") != null) {
//                                    List<String> strings = (List<String>) documentSnapshot.get("mylist");
//                                    strings.remove(strings.indexOf(productID));
//                                    stringListHashMap.put("mylist", strings);

                                    HashMap<String, Object> mylist = new HashMap<>();
                                    mylist.put("mylist", products);

                                    database.collection("mylist").document(userID)
                                            .set(mylist)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    emitter.onNext(true);
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
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }));
    }

    public Observable<List<String>> getAllUserIDs() {
        List<String> userIDs = new ArrayList<>();
        return Observable.create(emitter ->
                database.collection("shops")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                    userIDs.add(queryDocumentSnapshot.getId());
                                }
                                emitter.onNext(userIDs);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(cat.)..- ", "Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<HashMap<String, List<String>>> getAllShopIDs(String userIDs) {
        List<String> shopIDs = new ArrayList<>();
        HashMap<String, List<String>> stringListHashMap = new HashMap<>();
        return Observable.create(emitter ->
                database.collection("shops").document(userIDs).collection("shops")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                    shopIDs.add(queryDocumentSnapshot.getId());
                                }
                                stringListHashMap.put(userIDs, shopIDs);
                                emitter.onNext(stringListHashMap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(cat.)..- ", "getAllShopIDs Error : " + e.getMessage());
                            }
                        }));
    }

    public Observable<List<HashMap<String, Object>>> getAllProducts(String[] param) {
        List<ProductDetailsQueryDocumentSnapshotDataModel> productDetailsQueryDocumentSnapshotDataModels = new ArrayList<>();
        return Observable.create(emitter ->
//                database.collection("products").document(param[0]).collection(param[1])
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                Log.d("QWERTYUIOPAQ", "Count - " + queryDocumentSnapshots.size());
//                                if (queryDocumentSnapshots.size() != 0) {
//                                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
//                                        Log.d("QWERTYPOIUYTRE", queryDocumentSnapshot.toString());
//                                        ProductDetailsQueryDocumentSnapshotDataModel productDetailsQueryDocumentSnapshotDataModel = new ProductDetailsQueryDocumentSnapshotDataModel();
//                                        productDetailsQueryDocumentSnapshotDataModel.setShopID(param[0]);
//                                        productDetailsQueryDocumentSnapshotDataModel.setQueryDocumentSnapshot(queryDocumentSnapshot);
//                                        productDetailsQueryDocumentSnapshotDataModels.add(productDetailsQueryDocumentSnapshotDataModel);
//                                    }
//                                }
//                                emitter.onNext(productDetailsQueryDocumentSnapshotDataModels);
//                            }
//                        })
                database.collection("shops").document(param[0])
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                List<HashMap<String, Object>> shopsListHashmap = (List<HashMap<String, Object>>) documentSnapshot.get("retailerShops");
                                for (HashMap<String, Object> hashmap : shopsListHashmap) {
                                    if (hashmap.get("shopID").equals(param[1])) {
                                        Log.d("QueryDocSnap(pros)- ", hashmap.get("shopID") + "--> " + hashmap.get("productsList"));

                                        emitter.onNext((List<HashMap<String, Object>>) hashmap.get("productsList"));
                                    }
                                }
//                                emitter.onError(new Exception("Shop Not Exists"));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(cat.)..- ", "Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }
}