package com.example.c2n.nearby_shops.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.c2n.core.mappers.ListShopDataModelToNearbyListViewModelMapper;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.nearby_shops.domain.NearbyShopsLoadAllShopsUseCase;
import com.example.c2n.nearby_shops.domain.NearbyShopsLoadAllUserIDsUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 22-08-2018.
 */

public class NearbyShopsPresenter {

    private static final String TAG = NearbyShopsPresenter.class.getSimpleName();

    private Context context;
    private NearbyShopsView nearbyShopsView;

    private NearbyShopsLoadAllUserIDsUseCase nearbyShopsLoadAllUserIDsUseCase;
    private NearbyShopsLoadAllShopsUseCase nearbyShopsLoadAllShopsUseCase;
    private ListShopDataModelToNearbyListViewModelMapper listShopDataModelToNearbyListViewModelMapper;

    private List<String> allUsers = new ArrayList<>();
    private List<ShopDataModel> allShops = new ArrayList<>();

    private int currentUserCount;
    private Location currentLocation;

    @Inject
    NearbyShopsPresenter(NearbyShopsLoadAllUserIDsUseCase nearbyShopsLoadAllUserIDsUseCase, NearbyShopsLoadAllShopsUseCase nearbyShopsLoadAllShopsUseCase, ListShopDataModelToNearbyListViewModelMapper listShopDataModelToNearbyListViewModelMapper) {
        this.nearbyShopsLoadAllUserIDsUseCase = nearbyShopsLoadAllUserIDsUseCase;
        this.nearbyShopsLoadAllShopsUseCase = nearbyShopsLoadAllShopsUseCase;
        this.listShopDataModelToNearbyListViewModelMapper = listShopDataModelToNearbyListViewModelMapper;
    }

    public void bind(NearbyShopsView nearbyShopsView, Context context) {
        this.nearbyShopsView = nearbyShopsView;
        this.context = context;
    }

    @SuppressLint("RxLeakedSubscription")
    public void getAllUsers() {
        if (allShops.size() != 0) {
            allShops.clear();
        }
        nearbyShopsLoadAllUserIDsUseCase.execute(null, context)
                .subscribe(this::handleLoadAllUserIDsResponse, throwable -> handleError(throwable));
    }

    private void handleLoadAllUserIDsResponse(List<String> strings) {
        Log.d("NearbyShopsPresenter__", "Size : " + strings.size());
        currentUserCount = strings.size() - 1;
        if (allUsers.size() != 0) {
            allUsers.clear();
            allUsers.addAll(strings);
        } else {
            allUsers.addAll(strings);
        }
        getNearbyShops();
    }

    @SuppressLint("RxLeakedSubscription")
    public void getNearbyShops() {
        nearbyShopsLoadAllShopsUseCase.execute(null, context)
                .subscribe(this::handleLoadAllShopsResponse, throwable -> handleError(throwable));
    }

    private void handleLoadAllShopsResponse(List<ShopDataModel> shopDataModels) {
        Log.d(TAG, "handleLoadAllShopsResponse: " + shopDataModels.size());
        nearbyShopsView.loadShops(listShopDataModelToNearbyListViewModelMapper.mapshopsListToNearbyShopsList(shopDataModels, currentLocation, nearbyShopsView.getRange()));
    }

    private void handleError(Throwable throwable) {
        Log.d("NearbyShopsPresenter__", "" + throwable.getMessage());
    }

    public void setLocation(Location location) {
        this.currentLocation = location;
    }
}
