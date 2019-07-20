package com.example.c2n.signUp1.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 05-06-2018.
 */

public class SignUp1Repository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();

    @Inject
    public SignUp1Repository() {

    }

    public Observable<List<QueryDocumentSnapshot>> getRetailers() {
        documentList.clear();
        return Observable.create(emitter ->
                database.collection("retailers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        documentList.add(document);
                                        Log.d("QueryDocSnap(retails)-", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                } else {
                                    emitter.onError(task.getException());
                                    Log.e("Error(retails)..- ", "Error getting documents.", task.getException());
                                }

                            }
                        }));
    }

    public Observable<List<QueryDocumentSnapshot>> getCustomers() {
        documentList.clear();
        return Observable.create(emitter ->
                database.collection("customers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        documentList.add(document);
                                        Log.d("QueryDocSnap(retails)-", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                } else {
                                    emitter.onError(task.getException());
                                    Log.e("Error(retails)..- ", "Error getting documents.", task.getException());
                                }

                            }
                        }));
    }
}
