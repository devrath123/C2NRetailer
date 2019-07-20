package com.example.c2n.nearby_shops.di;

import com.example.c2n.nearby_shops.presentation.NearbyShopsFragment;

import dagger.Component;

/**
 * Created by shivani.singh on 22-08-2018.
 */

@Component(modules = NearbyShopsModule.class)
public interface NearbyShopsComponent {
    void inject(NearbyShopsFragment nearbyShopsFragment);
}
