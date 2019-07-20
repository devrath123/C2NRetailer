package com.example.c2n.core.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.example.c2n.core.models.OfferDataModel;

/**
 * Created by vipul.singhal on 24-05-2018.
 */

public abstract class BaseFragment extends Fragment {

    public static int offerApplicable;
    public static String productName;
    public static String productCategory;
    public static String productImagePath;
    public static double productMRPText;
    public static String productPrice;
    public static String productDiscountPercent;
    public static String productFinalPrice;
    public static String productBuyOfferQuantity;
    public static String productGetOfferQuantity;
    public static OfferDataModel productOffer;
    public static String productOfferId;
    public ProgressDialog progressDialog;


    protected void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
