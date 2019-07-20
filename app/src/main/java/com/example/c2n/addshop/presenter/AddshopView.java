package com.example.c2n.addshop.presenter;

import com.example.c2n.R;

import butterknife.OnClick;

/**
 * Created by roshan.nimje on 16-05-2018.
 */

public interface AddshopView {

    void getGEOLocation();

    String getShopName();

    String getShopPicURL();

    String getShopAddress();

    String getShopLandmark();

    String getShopMobileNo();

    String getShopEmail();

    String getShopNo();

    String getUserEmail();

    double getShopLatitude();

    double getShopLongitude();

    void validateShop();

    void addShop();

    void showPregressDialog(Boolean aBoolean);

    void openViewShop();

    void setLandmarkError();

    void setMobileNoError(String msg);

    void setShopNoError();
}
