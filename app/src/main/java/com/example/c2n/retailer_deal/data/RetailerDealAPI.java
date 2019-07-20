package com.example.c2n.retailer_deal.data;

import com.example.c2n.core.models.DealResponseEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetailerDealAPI {

    @GET("sendNotification?")
    Observable<DealResponseEntity> sendNotificatoin(@Query("custID") String custID, @Query("shopName") String shopName,
                                           @Query("rate") String rate, @Query("status") String status,
                                           @Query("userType") String userType);
}
