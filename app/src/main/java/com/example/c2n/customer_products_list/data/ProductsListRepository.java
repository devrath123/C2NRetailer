package com.example.c2n.customer_products_list.data;

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
 * Created by vipul.singhal on 23-05-2018.
 */

public class ProductsListRepository {
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();

    @Inject
    ProductsListRepository() {

    }


    public Observable<List<QueryDocumentSnapshot>> getProducts() {
        return Observable.create(emitter ->
                database.collection("products")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for(QueryDocumentSnapshot document : task.getResult())
                                    {
                                        documentList.add(document);
                                        Log.d("QueryDocSnap(pro.)-", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                }

                                else{
                                    emitter.onError(task.getException());
                                    Log.e("Error(pro.)..- ", "Error getting documents.", task.getException());
                                }

                            }
                        })) ;
    }
}
