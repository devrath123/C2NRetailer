package com.example.c2n.core.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.c2n.core.models.CartProductDataModel;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DaoAccess {

    @Insert
    long insertProduct(CartProductDataModel cartProductDataModel);

    @Delete
    int removeProduct(CartProductDataModel cartProductDataModel);

    @Query("SELECT * FROM CartProductDataModel WHERE product_id = :productID")
    Single<CartProductDataModel> checkAvailability(String productID);

    @Query("SELECT * FROM CartProductDataModel")
    Single<List<CartProductDataModel>> loadAllProducts();
}