package com.example.c2n.signup.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
 * Created by vipul.singhal on 10-05-2018.
 */

public class SignUpRepository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();
    private DocumentReference documentReference = database.collection("categories").document();


    @Inject
    SignUpRepository() {

    }

    public Observable<DocumentReference> addRetailer(RetailerDataModel retailerDataModel) {
        return Observable.create(emitter ->
                database.collection("retailers").document(retailerDataModel.getRetailerID())
                        .set(retailerDataModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Error in adding user-", "...Error adding document", e);
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<DocumentReference> addCustomer(CustomerDataModel customerDataModel) {
        return Observable.create(emitter ->
                database.collection("customers").document(customerDataModel.getCustomerID())
                        .set(customerDataModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Error in adding user-", "...Error adding document", e);
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<List<QueryDocumentSnapshot>> getUsers() {
        return Observable.create(emitter ->
                database.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        documentList.add(document);
                                        Log.d("QueryDocSnap(users)-", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                } else {
                                    emitter.onError(task.getException());
                                    Log.e("Error(users)..- ", "Error getting documents.", task.getException());
                                }

                            }
                        }));
    }

}
