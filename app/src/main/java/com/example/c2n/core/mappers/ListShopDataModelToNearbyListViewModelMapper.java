package com.example.c2n.core.mappers;

import android.location.Location;
import android.util.Log;

import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.models.ShopDistanceDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 25-07-2018.
 */

public class ListShopDataModelToNearbyListViewModelMapper {

    List<ShopDistanceDataModel> nearbyShops = new ArrayList<>();

    @Inject
    ListShopDataModelToNearbyListViewModelMapper() {
    }

    public List<ShopDistanceDataModel> mapshopsListToNearbyShopsList(List<ShopDataModel> shopsList, Location customerLocation, int range) {

        for (ShopDataModel shop : shopsList) {
            Location endPoint = new Location("locationA");
            endPoint.setLatitude(shop.getShopLatitude());
            endPoint.setLongitude(shop.getShopLongitude());

            double distance = round(customerLocation.distanceTo(endPoint) / 1000, 2);

            if (distance < range) {
                ShopDistanceDataModel shopDistanceDataModel = new ShopDistanceDataModel();
                shopDistanceDataModel.setShopDistance(distance);
                shopDistanceDataModel.setShopDataModel(shop);
                nearbyShops.add(shopDistanceDataModel);
            }
        }
        Log.d("Nearby_shops- ", nearbyShops.toString());

        return nearbyShops;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
