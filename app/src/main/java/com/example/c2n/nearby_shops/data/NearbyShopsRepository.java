package com.example.c2n.nearby_shops.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 22-08-2018.
 */

public class NearbyShopsRepository {

    private static final String TAG = NearbyShopsRepository.class.getSimpleName();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Inject
    NearbyShopsRepository() {

    }

    public Observable<List<QueryDocumentSnapshot>> getAllShops() {
        return Observable.create(emitter ->
                database.collection("shops")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<QueryDocumentSnapshot> allShops = new ArrayList<>();

                                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                    allShops.add(queryDocumentSnapshot);
                                }
                                Log.e("Nearby_shops- ", queryDocumentSnapshots.toString());
                                emitter.onNext(allShops);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(shops.)..- ", "getAllShopIDs Error : " + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

}
