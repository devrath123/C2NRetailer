package com.example.c2n.edit_profile.data;

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

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

public class EditProfileRepository {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference profile;
    private DocumentReference documentReference = firebaseFirestore.collection("retailers").document();
    QueryDocumentSnapshot queryDocumentSnapshot;

    @Inject
    EditProfileRepository() {

    }


    public Observable<DocumentReference> updateUser(Object[] user) {
        if (user[0] != null && user[1] == null) {
            RetailerDataModel retailerDataModel = (RetailerDataModel) user[0];
            Log.d("getRetailer", retailerDataModel.getRetailerID());


            profile = firebaseFirestore.collection("retailers").document(retailerDataModel.getRetailerID());
            profile.update("retailerName", retailerDataModel.getRetailerName());
            profile.update("retailerDOB", retailerDataModel.getRetailerDOB());
            profile.update("retailerMobileNo", retailerDataModel.getRetailerMobileNo());
            profile.update("retailerImageURL", retailerDataModel.getRetailerImageURL());
            profile.update("retailerLatitude", retailerDataModel.getRetailerLatitude());
            profile.update("retailerLongitude", retailerDataModel.getRetailerLongitude());
//        profile.update("retailerID",retailerDataModel.getRetailerID());
//        profile.update("retailerAddress",retailerDataModel.getRetailerAddress());

            return Observable.create(emitter ->
                    profile.update("retailerAddress", retailerDataModel.getRetailerAddress())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("updateRetailerAccepted", "success");
                                    emitter.onNext(documentReference);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("updateUser", "" + e.getMessage());
                                }
                            }));
        } else {
            CustomerDataModel customerDataModel = (CustomerDataModel) user[1];
            Log.d("getCustomer", customerDataModel.getCustomerID());


            profile = firebaseFirestore.collection("customers").document(customerDataModel.getCustomerID());
            profile.update("customerName", customerDataModel.getCustomerName());
            profile.update("customerDOB", customerDataModel.getCustomerDOB());
            profile.update("customerMobileNo", customerDataModel.getCustomerMobileNo());
            profile.update("customerImageURL", customerDataModel.getCustomerImageURL());
            profile.update("customerLatitude", customerDataModel.getCustomerLatitude());
            profile.update("customerLongitude", customerDataModel.getCustomerLongitude());
//        profile.update("retailerID",retailerDataModel.getRetailerID());
//        profile.update("retailerAddress",retailerDataModel.getRetailerAddress());

            return Observable.create(emitter ->
                    profile.update("customerAddress", customerDataModel.getCustomerAddress())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("updateCustomerAccepted", "success");
                                    emitter.onNext(documentReference);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("updateCustomerAccepted", "" + e.getMessage());
                                }
                            }));
        }
    }

    public Observable<DocumentSnapshot> getRetailerDetails(RetailerDataModel retailerDataModel) {
        Log.d("getUser", "" + retailerDataModel.getRetailerID());
        return Observable.create(
                emitter -> {
                    firebaseFirestore.collection("retailers").document(retailerDataModel.getRetailerID())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Log.d("getRetailer", "success");
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    Log.d("getRetailerNumber", "success " + documentSnapshot.get("retailerMobileNo"));

                                    emitter.onNext(documentSnapshot);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("getFailure", "" + e.getMessage());
                                    emitter.onError(e);
                                }
                            });
                }
        );
    }

    public Observable<DocumentSnapshot> getCustomerDetails(CustomerDataModel customerDataModel) {
        Log.d("getUser", "" + customerDataModel.getCustomerID());
        return Observable.create(
                emitter -> {
                    firebaseFirestore.collection("customers").document(customerDataModel.getCustomerID())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Log.d("getCustomer", "success");
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    Log.d("getCustomerNumber", "success " + documentSnapshot.get("customerMobileNo"));

                                    emitter.onNext(documentSnapshot);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("getFailure", "" + e.getMessage());
                                    emitter.onError(e);
                                }
                            });
                }
        );
    }

}




   /* public Observable<DocumentReference> updateUser(UserDataModel userDataModel) {
        profile = firebaseFirestore.collection("retailers").document(userDataModel.getUserID());
        profile.update("userName", userDataModel.getUserName());
        profile.update("userDOB", userDataModel.getUserDOB());
        profile.update("userMobileNo", userDataModel.getUserMobileNo());
        profile.update("userGender", userDataModel.getUserGender());
        profile.update("userProfilePicUrl", userDataModel.getUserProfilePicUrl());
        profile.update("userLatitude", userDataModel.getUserLatitude());
        profile.update("userLongitude", userDataModel.getUserLongitude());

        return Observable.create(emitter ->
                profile.update("userAddress", userDataModel.getUserAddress())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("updateUserS", "success");
                                emitter.onNext(documentReference);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("updateUser", "" + e.getMessage());
                            }
                        }));
    }

    public Observable<DocumentSnapshot> getUser(UserDataModel userDataModel) {
        Log.d("getUser", "" + userDataModel.getUserID());
        return Observable.create(
                emitter -> {
                    firebaseFirestore.collection("users").document(userDataModel.getUserID())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Log.d("getUser", "success");
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    Log.d("getUser", "success " + documentSnapshot.get("userMobileNo"));

//                                    if (documentSnapshot.exists()) {
                                    emitter.onNext(documentSnapshot);
//                                    } else {
//
//                                    }
                                }
                            })
//                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    Log.d("getUser", "success");
//                                    Log.d("getUser", "" + documentSnapshot.getData());
//
//                                    emitter.onNext(documentReference);
//                                }
//                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("getUser", "" + e.getMessage());
                                    emitter.onError(e);
                                }
                            });
                }
        );
    }
}
*/