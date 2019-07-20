package com.example.c2n.customer_cart.data;

import com.example.c2n.core.models.DealResponseEntity;
import com.example.c2n.core.models.ShopEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomerCartAPI {

    @GET("getShops?")
    Observable<List<ShopEntity>> getCart(@Query("lat") String lat, @Query("long") String longt,
                                         @Query("products") String products, @Query("radius") int radius);

    @GET("sendNotificationToMultiple?")
    Observable<DealResponseEntity> sendNotificatoin(@Query("custIDs") String custIDs, @Query("userName") String shopName,
                                                    @Query("rate") String rate, @Query("status") String status,
                                                    @Query("userType") String userType);
}
