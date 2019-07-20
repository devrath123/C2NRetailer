package com.example.c2n.mobileverification.presentation.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.c2n.core.model.UserDataModel;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public class MobileVerificationRepository {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference profile;
    private DocumentReference documentReference = firebaseFirestore.collection("users").document();

    @Inject
    MobileVerificationRepository() {

    }

    public Observable<DocumentReference> updateRetailerMobileNo(RetailerDataModel retailerDataModel) {
        profile = firebaseFirestore.collection("retailers").document(retailerDataModel.getRetailerID());
        return Observable.create(emitter ->
                profile.update("retailerMobileNo", retailerDataModel.getRetailerMobileNo())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("updateMobileNo", "success");
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("updateMobileNo", "" + e.getMessage());
                            }
                        }));
    }


    public Observable<DocumentReference> updateCustomerMobileNo(CustomerDataModel customerDataModel) {
        profile = firebaseFirestore.collection("customers").document(customerDataModel.getCustomerID());
        return Observable.create(emitter ->
                profile.update("customerMobileNo", customerDataModel.getCustomerMobileNo())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("updateMobileNo", "success");
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("updateMobileNo", "" + e.getMessage());
                            }
                        }));
    }
}
