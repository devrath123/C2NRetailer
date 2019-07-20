package com.example.c2n.core.model;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 25-06-2018.
 */

public class DocumentListToProductDocumentIdMapper {
    @Inject
    DocumentListToProductDocumentIdMapper() {

    }

    public String mapDocumentListToProductDocumentId(List<QueryDocumentSnapshot> documentSnapshotList, String productName) {
        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            if (documentSnapshot.getData().get("productName").toString().equals(productName)) {
                Log.d("document_id..", documentSnapshot.getId());
                return documentSnapshot.getId();
            }
        }
        return "";
    }
}
