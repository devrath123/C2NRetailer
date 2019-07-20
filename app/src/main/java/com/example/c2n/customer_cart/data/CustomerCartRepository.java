package com.example.c2n.customer_cart.data;

import com.example.c2n.core.models.DealResponseEntity;
import com.example.c2n.core.models.ShopEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerCartRepository {

    public static final String TAG = CustomerCartRepository.class.getSimpleName();

    @Inject
    CustomerCartRepository() {

    }

    public Observable<List<ShopEntity>> getCart(double lat, double longt, String productIDs, int radius) {
//        return APIClient.getClient().create(CustomerCartAPI.class).getCart(String.valueOf(lat), String.valueOf(longt), "%5B%22QVopIjRPAcZYCQowh4W07%22%5D", radius);
        return APIClient.getClient().create(CustomerCartAPI.class).getCart(String.valueOf(lat), String.valueOf(longt), productIDs, radius);
    }

    public Observable<DealResponseEntity> sendNotification(String[] params) {
        return APIClient.getClient().create(CustomerCartAPI.class).sendNotificatoin(params[0], params[1], params[2], params[3], params[4]);
    }
}
