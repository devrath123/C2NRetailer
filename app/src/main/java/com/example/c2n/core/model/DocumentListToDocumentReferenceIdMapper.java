package com.example.c2n.core.model;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 16-05-2018.
 */

public class DocumentListToDocumentReferenceIdMapper {

    @Inject
    DocumentListToDocumentReferenceIdMapper() {

    }

    public String mapDocumentListToDocumentReferenceId(List<QueryDocumentSnapshot> documentSnapshotList, String userEmail) {
        for (QueryDocumentSnapshot documentSnapshot : documentSnapshotList) {
            if (documentSnapshot.getData().get("userEmail").toString().equals(userEmail)) {
                Log.d("document_id..", documentSnapshot.getId());
                return documentSnapshot.getId();
            }
        }
        return null;
    }
}