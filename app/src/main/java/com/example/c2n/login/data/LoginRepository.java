package com.example.c2n.login.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vipul.singhal on 14-05-2018.
 */

public class LoginRepository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();
    private DocumentReference documentReference = database.collection("categories").document();


    @Inject
    LoginRepository() {

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


    public Observable<DocumentSnapshot> getRetailers(String retailerEmail) {
        return Observable.create(emitter ->
                database.collection("retailers").document(retailerEmail)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                Log.d("QueryDocSnap(retail)-", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                emitter.onNext(documentSnapshot);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(retailers)..- ", "Error getting documents." + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<DocumentReference> addRetailer(RetailerDataModel retailerDataModel) {

        return Observable.create(emitter ->
                database.collection("customers").document(retailerDataModel.getRetailerID())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.get("customerImageURL") != null)
                                    retailerDataModel.setRetailerImageURL((String) documentSnapshot.get("customerImageURL"));
                                if (documentSnapshot.get("customerDOB") != null)
                                    retailerDataModel.setRetailerDOB((String) documentSnapshot.get("customerDOB"));
                                if (documentSnapshot.get("customerAddress") != null)
                                    retailerDataModel.setRetailerAddress((String) documentSnapshot.get("customerAddress"));
                                retailerDataModel.setRetailerMobileNo((String) documentSnapshot.get("customerMobileNo"));
                                retailerDataModel.setRetailerName((String) documentSnapshot.get("customerName"));
                                retailerDataModel.setRetailerLatitude((Double) documentSnapshot.get("customerLatitude"));
                                retailerDataModel.setRetailerLongitude((Double) documentSnapshot.get("customerLongitude"));


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
                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("error-addRetailer", e.getMessage());
                                emitter.onError(e);
                            }
                        })
        );
    }

    public Observable<DocumentSnapshot> getCustomers(String customerEmail) {
        return Observable.create(emitter ->
                database.collection("customers").document(customerEmail)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                Log.d("QueryDocSnap(retail)-", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                emitter.onNext(documentSnapshot);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error(customers)..- ", "Error getting documents." + e.getMessage());
                                emitter.onError(e);
                            }
                        }));
    }

    public Observable<DocumentReference> addCustomerFromRetailer(CustomerDataModel customerDataModel) {

        return Observable.create(emitter ->
                database.collection("retailers").document(customerDataModel.getCustomerID())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.get("retailerImageURL") != null)
                                    customerDataModel.setCustomerImageURL((String) documentSnapshot.get("retailerImageURL"));
                                if (documentSnapshot.get("retailerDOB") != null)
                                    customerDataModel.setCustomerDOB((String) documentSnapshot.get("retailerDOB"));
                                if (documentSnapshot.get("retailerAddress") != null)
                                    customerDataModel.setCustomerAddress((String) documentSnapshot.get("retailerAddress"));
                                customerDataModel.setCustomerMobileNo((String) documentSnapshot.get("retailerMobileNo"));
                                customerDataModel.setCustomerName((String) documentSnapshot.get("retailerName"));
                                customerDataModel.setCustomerLatitude((Double) documentSnapshot.get("retailerLatitude"));
                                customerDataModel.setCustomerLongitude((Double) documentSnapshot.get("retailerLongitude"));


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
                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("error-addCustomer", e.getMessage());
                                emitter.onError(e);
                            }
                        })
        );
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
                                Log.w("Error_adding_user-", "...Error adding document", e);
                                emitter.onError(e);
                            }
                        })
        );
    }
}
