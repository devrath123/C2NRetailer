package com.example.c2n.viewshops.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class ViewShopsRepository {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> queryDocumentSnapshots = new ArrayList<>();

    @Inject
    ViewShopsRepository() {

    }

//    public Observable<List<QueryDocumentSnapshot>> loadShops() {
//        return io.reactivex.Observable.create(
//                emitter -> firebaseFirestore.collection("shops")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                                        queryDocumentSnapshots.add(documentSnapshot);
//                                        Log.d("QueryDocumentSnapshot..", documentSnapshot.getId() + " => " + documentSnapshot.getData().toString());
//                                    }
//                                    emitter.onNext(queryDocumentSnapshots);
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("loadPreferences", "" + e.getMessage());
//                            }
//                        }));
//    }

//    public Observable<List<QueryDocumentSnapshot>> loadShops(String userID) {
//        return io.reactivex.Observable.create(
//                emitter -> firebaseFirestore.collection("shops").document(userID).collection("shops")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    if (task.getResult().isEmpty() || task.getResult() == null)
//                                        Log.d("shops--> ", "empty");
//                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                                        queryDocumentSnapshots.add(documentSnapshot);
//                                        Log.d("QueryDocumentSnapshot..", documentSnapshot.getId() + " => " + documentSnapshot.getData().toString());
//                                    }
//                                    emitter.onNext(queryDocumentSnapshots);
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("loadPreferences", "" + e.getMessage());
//                            }
//                        }));
//    }


//    public Observable<DocumentSnapshot> loadShops(String userID) {
//        return io.reactivex.Observable.create(
//                emitter -> firebaseFirestore.collection("shops").document(userID)
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                Log.d("Load_Shops-> ", documentSnapshot.toString());
//                                emitter.onNext(documentSnapshot);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("loadPreferences", "" + e.getMessage());
//                                emitter.onError(e);
//                            }
//                        }));
//    }

    public Observable<DocumentSnapshot> loadShops(String userID) {
        return io.reactivex.Observable.create(
                emitter -> firebaseFirestore.collection("shops").document(userID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d("Load_Shops-> ", documentSnapshot.toString());
                                emitter.onNext(documentSnapshot);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("loadPreferences", "" + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<List<QueryDocumentSnapshot>> getOffers() {
        return io.reactivex.Observable.create(
                emitter -> firebaseFirestore.collection("offers").document("roshan.nimje@nihilent.com")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.d("ViewShopsRepository__", "" + documentSnapshot.getData());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("ViewShopsRepository__", "" + e.getMessage());
                            }
                        }));
    }
}
