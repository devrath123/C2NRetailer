package com.example.c2n.master_list.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.models.MasterProductDataModel;
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

public class MasterListRepository {
    private final String TAG = "MasterListRepository";
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Inject
    MasterListRepository() {

    }

    public Observable<List<QueryDocumentSnapshot>> getAllProducts() {
        List<QueryDocumentSnapshot> masterProducts = new ArrayList<>();
        return Observable.create(emitter ->
                database.collection("masterproducts")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        masterProducts.add(document);
                                    }
                                    emitter.onNext(masterProducts);
                                } else {
                                    emitter.onError(task.getException());
                                    Log.d(TAG, "Error : " + task.getException());
                                }
                            }
                        }));
    }
}
