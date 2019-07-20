package com.example.c2n.retailercategory.data;

import android.support.annotation.NonNull;
import android.util.Log;

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
 * Created by roshan.nimje on 18-06-2018.
 */

public class RetailerCategoryRepository {

    private static final String TAG = RetailerCategoryRepository.class.getSimpleName();

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();

    @Inject
    public RetailerCategoryRepository() {
    }

    public Observable<List<QueryDocumentSnapshot>> getCategories() {
        List<QueryDocumentSnapshot> queryDocumentSnapshotss = new ArrayList<>();
        Log.d(TAG, "getCategories: " + queryDocumentSnapshotss.size());
        return Observable.create(emitter ->
                database.collection("categories")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                    queryDocumentSnapshotss.add(queryDocumentSnapshot);
                                }
                                emitter.onNext(queryDocumentSnapshotss);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        queryDocumentSnapshots.add(document);
//                                        Log.d("QueryDocSnap(cat.)-", document.getId() + " => " + document.getData());
//                                    }
//                                    Log.d(TAG, "onComplete: Size : " + queryDocumentSnapshots.size());
//                                    emitter.onNext(queryDocumentSnapshots);
//                                } else {
//                                    emitter.onError(task.getException());
//                                    Log.e("Error(cat.)..- ", "Error getting documents.", task.getException());
//                                }
//
//                            }
//                        }));
    }

    public Observable<List<HashMap<String, Object>>> loadProducts(String[] param) {
        if (documentList.size() != 0)
            documentList.clear();
        return Observable.create(emitter ->
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
                                emitter.onError(e);
                            }
                        }));
    }
}
