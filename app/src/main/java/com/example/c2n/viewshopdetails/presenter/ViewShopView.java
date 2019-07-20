package com.example.c2n.viewshopdetails.presenter;

/**
 * Created by roshan.nimje on 24-05-2018.
 */

public interface ViewShopView {

    void getLocation();

    void uploadImage();

    void updateShopDetails();

    String getShopDocumentID();

    void isShowShopSuccess(Boolean success);

    void setShopName(String shopName);

    void setShopAddress(String shopAddress);

    void setShopID(String shopID);

    void setShopEmail(String shopEmail);

    void setRetailerID(String retailerID);

    void setShopMobileNo1(String shopMobileNo1);

    void setShopMobileNo2(String shopMobileNo2);

    void setShopDocumentID(String shopDocumentID);

    String getRetailerID();

    String getShopName();

    String getShopAddress();

    String getShopLandmark();

    String getShopContactNo();

    String getShopID();

    String getShopEmail();

    String getPreviousShopID();

    double getLatitude();

    double getLongitude();

    String getShopImageURL();

    void setShopNameError();

    void setShopLandmarkError();

    void setShopContactNoError(String msg);

    void setShopEmailError(String msg);

    void openRetailerHome();

    void setShopAddressError(String msg);
}
