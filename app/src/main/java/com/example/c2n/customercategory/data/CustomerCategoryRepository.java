package com.example.c2n.customercategory.data;

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
 * Created by shivani.singh on 17-08-2018.
 */

public class CustomerCategoryRepository {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    List<QueryDocumentSnapshot> documentList = new ArrayList<>();


    @Inject
    public CustomerCategoryRepository() {
    }


    public Observable<List<QueryDocumentSnapshot>> getCategories() {
        return Observable.create(emitter ->
                database.collection("categories")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for(QueryDocumentSnapshot document : task.getResult())
                                    {
                                        documentList.add(document);
                                        Log.d("QueryDocSnapshotCat", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                }

                                else{
                                    emitter.onError(task.getException());
                                    Log.e("Error category ", "Error getting documents.", task.getException());
                                }

                            }
                        })) ;

    }

    public Observable<List<QueryDocumentSnapshot>> loadProducts(String[] param) {
        if (documentList.size() != 0)
            documentList.clear();
        return Observable.create(emitter ->
                database.collection("products").document(param[0]).collection(param[1])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        documentList.add(document);
//                                        document.getDocumentReference();
//                                        DocumentReference documentReference;
                                        Log.d("QueryDocSnapproducts", document.getId() + " => " + document.getData());
                                    }
                                    emitter.onNext(documentList);
                                } else {
                                    emitter.onError(task.getException());
                                    Log.e("Errorproducts ", "Error getting documents.", task.getException());
                                }

                            }
                        }));
    }
}






