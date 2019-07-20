package com.example.c2n.addproduct.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;

import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ShopDataModel;

public class AddProductActivity extends BaseActivity {
    public static final String TAG = AddProductActivity.class.getSimpleName();

    public Uri filePath;
    public Bitmap productImageBitmap;

    public String productID;
    public String productName;
    public String productImageURL;
    public double productMRP;
    public String productCategory;
    public String productDescription;
    public Boolean productStockStatus;
    public Boolean productOfferStatus;
    public OfferDataModel offerDataModel;
    public ShopDataModel shopDataModel;
    public boolean fromMasterListFlag;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_product2;
    }

    @Override
    protected void initActivity() {

        SharedPrefManager.Init(this);
        SharedPrefManager.LoadFromPref();
        Log.d(TAG, "User Email : " + SharedPrefManager.get_userEmail());

        Intent intent = getIntent();
        if (intent != null) {
            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
            if (intent.getSerializableExtra("masterProductDataModel") != null) {
                fromMasterListFlag = true;
                MasterProductDataModel masterProductDataModel = (MasterProductDataModel) intent.getSerializableExtra("masterProductDataModel");
                productID = masterProductDataModel.getProductID();
                productName = masterProductDataModel.getProductName();
//                productMRP = masterProductDataModel.getProductMRP();
                productCategory = masterProductDataModel.getProductCategory();
                productImageURL = masterProductDataModel.getProductImageURL();
                productDescription = masterProductDataModel.getProductDescription();

                AddProductPriceFragment addProductPriceFragment = new AddProductPriceFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.product_fragment_container, addProductPriceFragment);
                fragmentTransaction.commit();
            } else {
                fromMasterListFlag = false;
                AddProductCategoryFragment addProductCategoryFragment = new AddProductCategoryFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.product_fragment_container, addProductCategoryFragment);
                fragmentTransaction.commit();
            }
        }

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

//        AddProductCategoryFragment addProductCategoryFragment = new AddProductCategoryFragment();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.product_fragment_container, addProductCategoryFragment);
//        fragmentTransaction.commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void closeActivity() {
        finish();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            this.finish();
//        } else {
//            getFragmentManager().popBackStack();
//        }
//    }
}
