package com.example.c2n.preferences.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 18-05-2018.
 */

public class PreferencesRepository {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> queryDocumentSnapshots = new ArrayList<>();

    @Inject
    PreferencesRepository() {

    }

    public Observable<List<QueryDocumentSnapshot>> loadPreferences() {
        return io.reactivex.Observable.create(
                emitter -> firebaseFirestore.collection("categories")
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

}
