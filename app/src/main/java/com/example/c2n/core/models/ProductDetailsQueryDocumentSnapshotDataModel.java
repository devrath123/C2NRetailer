package com.example.c2n.core.models;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ProductDetailsQueryDocumentSnapshotDataModel {

    private String shopID;
    private QueryDocumentSnapshot queryDocumentSnapshot;

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public QueryDocumentSnapshot getQueryDocumentSnapshot() {
        return queryDocumentSnapshot;
    }

    public void setQueryDocumentSnapshot(QueryDocumentSnapshot queryDocumentSnapshot) {
        this.queryDocumentSnapshot = queryDocumentSnapshot;
    }

    @Override
    public String toString() {
        return "ProductDetailsQueryDocumentSnapshotDataModel{" +
                "shopID='" + shopID + '\'' +
                ", queryDocumentSnapshot=" + queryDocumentSnapshot +
                '}';
    }
}
