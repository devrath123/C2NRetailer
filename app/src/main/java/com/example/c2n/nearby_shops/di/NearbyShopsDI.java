package com.example.c2n.nearby_shops.di;

/**
 * Created by shivani.singh on 22-08-2018.
 */

public class NearbyShopsDI {

    private static NearbyShopsComponent nearbyShopsComponent;

    public static NearbyShopsComponent getNearbyShopsComponent() {
        if (nearbyShopsComponent == null) {
            nearbyShopsComponent = DaggerNearbyShopsComponent.builder().build();
        }
        return nearbyShopsComponent;
    }
}
