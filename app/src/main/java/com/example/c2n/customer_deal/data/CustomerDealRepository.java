package com.example.c2n.customer_deal.data;

import android.support.annotation.NonNull;

import com.example.c2n.core.models.CustomerDealDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerDealRepository {
    private FirebaseDatabase firebaseFirestore = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseFirestore.getReference();

    @Inject
    public CustomerDealRepository() {

    }

    public Observable<List<CustomerDealDataModel>> getCusomerDeal(String userID) {
        return Observable.create(
                emitter ->
                        databaseReference.child("customer-deals").child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<CustomerDealDataModel> retailerDealDataModels = new ArrayList<>();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    CustomerDealDataModel retailerDealDataModel = postSnapshot.getValue(CustomerDealDataModel.class);
                                    retailerDealDataModels.add(retailerDealDataModel);
                                }
                                emitter.onNext(retailerDealDataModels);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        }));
    }
}
