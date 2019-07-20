package com.example.c2n.retailer_deal.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.c2n.core.models.DealResponseEntity;
import com.example.c2n.core.models.RetailerDealDataModel;
import com.example.c2n.customer_cart.data.APIClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RetailerDealRepository {

    private static final String TAG = RetailerDealRepository.class.getSimpleName();
    private FirebaseDatabase firebaseFirestore = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseFirestore.getReference();

    @Inject
    public RetailerDealRepository() {
    }

    public Observable<List<RetailerDealDataModel>> getRetailerDeal(String userID) {
        return Observable.create(
                emitter ->
                        databaseReference.child("retailer-deals").child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<RetailerDealDataModel> retailerDealDataModels = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    RetailerDealDataModel retailerDealDataModel = postSnapshot.getValue(RetailerDealDataModel.class);
                                    retailerDealDataModels.add(retailerDealDataModel);
                                }
                                emitter.onNext(retailerDealDataModels);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        }));
    }

    public Observable<DatabaseReference> updateCustomerAccepted(String[] param) {
        return Observable.create(
                emitter ->
                        databaseReference.child("customer-deals").child(param[0].replace(".", "-")).child(param[1]).child("status").setValue("accepted", new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                emitter.onNext(databaseReference);
                            }
                        }));
    }

    public Observable<DatabaseReference> updateRetailerAccepted(String key, String userID) {
        return Observable.create(
                emitter ->
                        databaseReference.child("retailer-deals").child(userID.replace(".", "-")).child(key).child("status").setValue("accepted", new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                emitter.onNext(databaseReference);
                            }
                        }));
    }

    public Observable<DatabaseReference> updateCustomerDeclined(String[] param) {
        return Observable.create(
                emitter ->
                        databaseReference.child("customer-deals").child(param[0].replace(".", "-")).child(param[1]).child("status").setValue("declined", new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                emitter.onNext(databaseReference);
                            }
                        }));
    }

    public Observable<DatabaseReference> updateRetailerDeclined(String key, String userID) {
        return Observable.create(
                emitter ->
                        databaseReference.child("retailer-deals").child(userID.replace(".", "-")).child(key).child("status").setValue("declined", new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                emitter.onNext(databaseReference);
                            }
                        }));
    }

    public Observable<DealResponseEntity> sendNotification(String[] params) {
        return APIClient.getClient().create(RetailerDealAPI.class).sendNotificatoin(params[0], params[1], params[2], params[3], params[4]);
    }
}