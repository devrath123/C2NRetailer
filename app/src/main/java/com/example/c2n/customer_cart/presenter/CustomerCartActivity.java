package com.example.c2n.customer_cart.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.database.ProductDatabase;
import com.example.c2n.core.models.CartProductDataModel;
import com.example.c2n.core.models.CustomerDealDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ProductDetailsDataModel;
import com.example.c2n.core.models.RetailerDealDataModel;
import com.example.c2n.core.models.ShopCartDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.customer_cart.di.CustomerCartDI;
import com.example.c2n.customer_cart.presenter.adapter.CartAdapter;
import com.example.c2n.customer_cart.presenter.adapter.CustomerCartAdapter;
import com.example.c2n.customer_home.di.CustomerHomeDI;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;

public class CustomerCartActivity extends BaseActivity implements CustomerCartView, CustomerCartAdapter.CustomerCartInterface {

    private static final String TAG = CustomerCartActivity.class.getSimpleName();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private Query query;
    private ValueEventListener listener;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private FragmentManager fragmentManager;
    private boolean currentLocationFlag;
    private static final String DATABASE_NAME = "product_db";
    private ProductDatabase productDatabase;
    private int GPS_LOCATION = 1234;
    private boolean locationFlag;

    private List<ShopDataModel> shopDataModels;
    private List<MasterProductDataModel> masterProductDataModels;
    private List<ProductDetailsDataModel> cartProducts = new ArrayList<>();
    private double latitude;
    private double longitude;
    private int radious = 50;
    private CustomerCartAdapter customerCartAdapter;
    private String productIDs;

    private String rate;
    private String retailerIDs;

    @BindView(R.id.no_product)
    LinearLayout linearLayoutNoProduct;

    @BindView(R.id.recycler_view_product_list)
    RecyclerView recyclerViewProductList;

    @BindView(R.id.no_product_text)
    TextView textViewNoProduct;

    @Inject
    CustomerCartPresenter customerCartPresenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_customer_cart;
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        CustomerCartDI.getCustomerCartComponent().inject(this);
        CustomerHomeDI.getCustomerHomeComponent().inject(this);
        customerCartPresenter.bind(this, this);

        SharedPrefManager.Init(this);

        fragmentManager = getSupportFragmentManager();

        productDatabase = Room.databaseBuilder(this.getApplication(), ProductDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
//        productDatabase.daoAccess().loadAllProducts()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(cartProductDataModels -> handleResponse(cartProductDataModels), throwable -> handleError(throwable));

        getMylist();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Cart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getMylist();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_mylist) {
            if (masterProductDataModels.size() != 0) {
                MylistFragment mylistFragment = new MylistFragment();
                mylistFragment.setMylist(masterProductDataModels);
                mylistFragment.show(fragmentManager, null);
            } else {
                Toast.makeText(this, "Cart is empty.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public void getMylist() {
        customerCartPresenter.getMylist();
    }

    @Override
    public void loadMylist(List<MasterProductDataModel> productss) {
        this.masterProductDataModels = productss;
        if (productss.size() != 0) {
            recyclerViewProductList.setVisibility(View.VISIBLE);
            linearLayoutNoProduct.setVisibility(View.GONE);

            String products = "%5B";
            for (int i = 0; i < productss.size(); i++) {
                products = products + "%22" + productss.get(i).getProductID() + "%22,";
            }
            products = products.substring(0, products.length() - 1);
            products = products + "%5D";
            productIDs = products;
            getCurrentLocation();
        } else {
            recyclerViewProductList.setVisibility(View.GONE);
            linearLayoutNoProduct.setVisibility(View.VISIBLE);
            textViewNoProduct.setText("Your cart is empty");
        }
    }

//    private void handleResponse(List<CartProductDataModel> cartProductDataModels) {
//        this.cartProductDataModels = cartProductDataModels;
//        Log.d(TAG, "HandelResponse Size : " + cartProductDataModels.size());
//        String products = "[";
//        for (int i = 0; i < cartProductDataModels.size(); i++) {
//            products = products + "\"" + cartProductDataModels.get(i).getProductID() + "\",";
//        }
//        products = products.substring(0, products.length() - 1);
//        products = products + "]";
//        productIDs = products;
//        Log.d(TAG, "HandelResponse Data : " + products);
////        loadProducts(cartProductDataModels);
//        getCurrentLocation();
//    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, "Handle Error : " + throwable.getMessage());
    }

    private void getCurrentLocation() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                                buildAlertMessageNoGps();
                                displayLocationSettingsRequest();
                            } else {

                                showLocationProgress(true);

//                                locationFlag = false;
//
//                                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                                getLocation();
                                Log.d(TAG, "onPermissionsChecked: getCurrentLocatoin - else");

                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    public void getLocation() {
        try {
            int mode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            Log.d(TAG, "onPermissionsChecked: Mode: " + mode);
            if (mode != 3) {
                showLocationProgress(false);
                Toast.makeText(CustomerCartActivity.this, "Please change location mode to 'High accuracy'", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else {
                SmartLocation.with(this).location()
                        .oneFix()
                        .config(LocationParams.BEST_EFFORT)
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                showLocationProgress(false);
                                currentLocationFlag = true;
                                Log.d(TAG, "onLocationUpdated: Latitude : " + location.getLatitude() + " Longitude : " + location.getLongitude());
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                getCart();
                            }
                        });

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!currentLocationFlag) {
                            showLocationProgress(false);
                            showAlertDialog();
                        }
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(runnable, 10000);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "onPermissionsChecked: Error: " + e.getMessage());
        }
    }

    private void showAlertDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.location_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText editTextLatitude = mView.findViewById(R.id.et_latitude);
        final EditText editTextLongitude = mView.findViewById(R.id.et_longitude);

        alertDialogBuilderUserInput.setCancelable(true)
                .setPositiveButton("Submit Location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        submitOTP(editTextOTP.getText().toString().trim());
                        if (!TextUtils.isEmpty(editTextLatitude.getText().toString().trim()) && !TextUtils.isEmpty(editTextLongitude.getText().toString().trim())) {
//                            Location location = new Location("Current");
//                            location.setLatitude(Double.parseDouble(editTextLatitude.getText().toString().trim()));
//                            location.setLongitude(Double.parseDouble(editTextLongitude.getText().toString().trim()));
//
//                            openMap(location);
                            latitude = Double.parseDouble(editTextLatitude.getText().toString().trim());
                            longitude = Double.parseDouble(editTextLongitude.getText().toString().trim());

                            getCart();
                        } else {
                            showAlertDialog();
                        }
                    }
                });
        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            if (!locationFlag) {
                locationFlag = true;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Log.d(TAG, "Location : " + location.getLatitude() + " - " + location.getLongitude());

                showLocationProgress(false);
                getCart();
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private void displayLocationSettingsRequest() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
//                            startIntentSenderForResult(status.getResolution().getIntentSender(),
//                                    GPS_LOCATION, null, 0, 0, 0, null);
                            status.startResolutionForResult(CustomerCartActivity.this, GPS_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private void getCart() {
        customerCartPresenter.getCart();
    }

    @Override
    public String getProductsList() {
        return productIDs;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public int getRadious() {
        return radious;
    }

    private void loadProducts(List<CartProductDataModel> cartProductDataModels) {
//        for (CartProductDataModel cartProductDataModel : cartProductDataModels) {
//            ProductDetailsDataModel productDetailsDataModel = new ProductDetailsDataModel();
//            productDetailsDataModel.setWhishlisted(true);
//            productDetailsDataModel.setShopID(cartProductDataModel.getShopID());
//            productDetailsDataModel.setRetailerID(cartProductDataModel.getRetailerID());
//
//            ProductDataModel productDataModel = new ProductDataModel();
//            productDataModel.setProductID(cartProductDataModel.getProductID());
//            productDataModel.setProductName(cartProductDataModel.getProductName());
//            productDataModel.setProductImageURL(cartProductDataModel.getProductImageURL());
//            productDataModel.setProductMRP(cartProductDataModel.getProductMRP());
//            productDataModel.setProductCategory(cartProductDataModel.getProductCategory());
//            productDataModel.setProductDescription(cartProductDataModel.getProductDescription());
//            productDataModel.setProductStockStatus(cartProductDataModel.getProductStockStatus());
//            productDataModel.setProductOfferStatus(cartProductDataModel.getProductOfferStatus());
//
//            if (cartProductDataModel.getOfferID() != null) {
//                OfferDataModel offerDataModel = new OfferDataModel();
//                offerDataModel.setOfferID(cartProductDataModel.getOfferID());
//                offerDataModel.setOfferDiscount(cartProductDataModel.getOfferDiscount());
//                offerDataModel.setOfferStartDate(cartProductDataModel.getOfferStartDate());
//                offerDataModel.setOfferEndDate(cartProductDataModel.getOfferEndDate());
//                offerDataModel.setOfferName(cartProductDataModel.getOfferName());
//                offerDataModel.setSun(cartProductDataModel.isSun());
//                offerDataModel.setMon(cartProductDataModel.isMon());
//                offerDataModel.setTue(cartProductDataModel.isTue());
//                offerDataModel.setWed(cartProductDataModel.isWed());
//                offerDataModel.setThu(cartProductDataModel.isThu());
//                offerDataModel.setFri(cartProductDataModel.isFri());
//                offerDataModel.setSat(cartProductDataModel.isSat());
//                offerDataModel.setOfferStatus(cartProductDataModel.isOfferStatus());
//                productDataModel.setProductOffer(offerDataModel);
//            } else {
//                productDataModel.setProductOffer(null);
//            }
//            productDetailsDataModel.setProductDataModel(productDataModel);
//            cartProducts.add(productDetailsDataModel);
//        }
//        showCartProducts();
    }

    private void showCartProducts() {
        customerCartAdapter = new CustomerCartAdapter(cartProducts, this, this);
        recyclerViewProductList.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewProductList.setLayoutManager(linearLayout);
        recyclerViewProductList.setAdapter(customerCartAdapter);
        customerCartAdapter.notifyDataSetChanged();
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void removeFromCart(ProductDetailsDataModel productDetailsDataModel) {
//        int index = cartProducts.indexOf(productDetailsDataModel);
//        cartProducts.remove(index);
//        CartProductDataModel cartProductDataModel = new CartProductDataModel();
//        cartProductDataModel.setRetailerID(productDetailsDataModel.getRetailerID());
//        cartProductDataModel.setShopID(productDetailsDataModel.getShopID());
//        cartProductDataModel.setProductID(productDetailsDataModel.getProductDataModel().getProductID());
//        cartProductDataModel.setProductName(productDetailsDataModel.getProductDataModel().getProductName());
//        cartProductDataModel.setProductImageURL(productDetailsDataModel.getProductDataModel().getProductImageURL());
//        cartProductDataModel.setProductMRP(productDetailsDataModel.getProductDataModel().getProductMRP());
//        cartProductDataModel.setProductCategory(productDetailsDataModel.getProductDataModel().getProductCategory());
//        cartProductDataModel.setProductDescription(productDetailsDataModel.getProductDataModel().getProductDescription());
//        cartProductDataModel.setProductStockStatus(productDetailsDataModel.getProductDataModel().getProductStockStatus());
//        cartProductDataModel.setProductOfferStatus(productDetailsDataModel.getProductDataModel().getProductOfferStatus());
//        if (productDetailsDataModel.getProductDataModel().getProductOffer() != null) {
//            cartProductDataModel.setOfferID(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferID());
//            cartProductDataModel.setOfferDiscount(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount());
//            cartProductDataModel.setOfferStartDate(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferStartDate());
//            cartProductDataModel.setOfferEndDate(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferEndDate());
//            cartProductDataModel.setOfferName(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferName());
//            cartProductDataModel.setSun(productDetailsDataModel.getProductDataModel().getProductOffer().isSun());
//            cartProductDataModel.setMon(productDetailsDataModel.getProductDataModel().getProductOffer().isMon());
//            cartProductDataModel.setTue(productDetailsDataModel.getProductDataModel().getProductOffer().isTue());
//            cartProductDataModel.setWed(productDetailsDataModel.getProductDataModel().getProductOffer().isWed());
//            cartProductDataModel.setThu(productDetailsDataModel.getProductDataModel().getProductOffer().isThu());
//            cartProductDataModel.setFri(productDetailsDataModel.getProductDataModel().getProductOffer().isFri());
//            cartProductDataModel.setSat(productDetailsDataModel.getProductDataModel().getProductOffer().isSat());
//            cartProductDataModel.setOfferStatus(productDetailsDataModel.getProductDataModel().getProductOffer().isOfferStatus());
//        }
//        Log.d("QWERTYUIOPASDFGHJKL", "" + cartProductDataModel.toString());
////
//        Observable.fromCallable(() -> productDatabase.daoAccess().removeProduct(cartProductDataModel))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposabele -> showProgressDialog("Removing"))
//                .subscribe(id -> handleRemoveProductToMylistProgress(id), throwable -> handleRemoveError(throwable));
    }

    private void handleRemoveProductToMylistProgress(Integer id) {
        dismissProgressDialog();
        customerCartAdapter.notifyDataSetChanged();
    }

    private void handleRemoveError(Throwable throwable) {
        Log.d(TAG, "Error : " + throwable.getMessage());
    }

    public void showLocationProgress(Boolean flag) {
        if (flag) {
            showProgressDialog("Getting location...");
        } else {
            dismissProgressDialog();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_LOCATION) {
            switch (resultCode) {
                case RESULT_OK: {
                    // All required changes were successfully made
                    Toast.makeText(this, "Location enabled by user!", Toast.LENGTH_LONG).show();

//                    showLocationProgress(true);

//                    locationFlag = false;
//
//                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

//                    getLocation();
                    Log.d(TAG, "onPermissionsChecked: onActivityResult");

                    break;
                }
                case RESULT_CANCELED: {
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                    break;
                }
                default: {
                    Toast.makeText(this, "Default", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }

    @Override
    public void showProgress(boolean flag, String msg) {
        if (flag) {
            showProgressDialog(msg);
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    public void loadShops(List<ShopDataModel> shopDataModels) {
//        for (ShopDataModel shopDataModel : shopDataModels) {
//            double totalBill = 0;
//            for (int i = 0; i < shopDataModel.getProductsList().size(); i++) {
//                ProductDataModel productDataModel = shopDataModel.getProductsList().get(i);
//                if (productDataModel.getProductOffer() != null) {
//                    totalBill = totalBill + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * productDataModel.getProductOffer().getOfferDiscount()) / 100, 2);
//                } else {
//                    totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
//                }
//            }
//
//        }
        this.shopDataModels = shopDataModels;
        if (shopDataModels.size() != 0) {

            recyclerViewProductList.setVisibility(View.VISIBLE);
            linearLayoutNoProduct.setVisibility(View.GONE);
            showCart(shopDataModels);
        } else {
            recyclerViewProductList.setVisibility(View.GONE);
            linearLayoutNoProduct.setVisibility(View.VISIBLE);
            textViewNoProduct.setText("Your cart is empty");
        }
    }

    public void showCart(List<ShopDataModel> shopDataModels) {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();

        for (ShopDataModel shopDataModel : shopDataModels) {
            ArrayList<Object> objects = new ArrayList<>();
            double totalBill = 0;
            int todysDay = new Date().getDay();
            Date todayDate = null;
            try {
                todayDate = format.parse(format.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            Log.d(TAG, "loadShops: " + date + " Day : " + todysDay);
            for (int i = 0; i < shopDataModel.getProductsList().size(); i++) {
                objects.add(shopDataModel.getProductsList().get(i));
                ProductDataModel productDataModel = shopDataModel.getProductsList().get(i);
                if (productDataModel.getProductOffer() != null) {
                    if (productDataModel.getProductOffer().isOfferStatus()) {
                        if (isOfferCardActive(todysDay, productDataModel.getProductOffer())) {
                            if (productDataModel.getProductOffer().getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(productDataModel.getProductOffer().getOfferEndDate()) >= 0) {
                                totalBill = totalBill + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * productDataModel.getProductOffer().getOfferDiscount()) / 100, 2);
                            } else {
                                totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                            }
                        } else {
                            totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                        }
                    } else {
                        totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                    }
                } else {
                    totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                }
            }

            ShopCartDataModel shopCartDataModel = new ShopCartDataModel();
            shopCartDataModel.setShopDataModel(shopDataModel);
            shopCartDataModel.setProductDataModel(objects);
            shopCartDataModel.setTotalBill(totalBill);

            Location startPoint = new Location("locationA");
            startPoint.setLatitude(getLatitude());
            startPoint.setLongitude(getLongitude());
            Log.d(TAG, "showCart: locationA : " + startPoint.getLatitude() + " " + startPoint.getLongitude());

            Location endPoint = new Location("locationB");
            endPoint.setLatitude(shopDataModel.getShopLatitude());
            endPoint.setLongitude(shopDataModel.getShopLongitude());
            Log.d(TAG, "showCart: locationB : " + endPoint.getLatitude() + " " + endPoint.getLongitude());

            shopCartDataModel.setDistance(round(startPoint.distanceTo(endPoint) / 1000, 2));

            parentObjects.add(shopCartDataModel);
        }

        CartAdapter cartAdapter = new CartAdapter(this, parentObjects);

        cartAdapter.setCustomParentAnimationViewId(R.id.cart_arrow_down);
        cartAdapter.setParentClickableViewAnimationDefaultDuration();
        cartAdapter.setParentAndIconExpandOnClick(true);

        recyclerViewProductList.setAdapter(cartAdapter);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
                break;
            case 1:
                if (offerCard.isMon())
                    return true;
                break;
            case 2:
                if (offerCard.isTue())
                    return true;
                break;
            case 3:
                if (offerCard.isWed())
                    return true;
                break;
            case 4:
                if (offerCard.isThu())
                    return true;
                break;
            case 5:
                if (offerCard.isFri())
                    return true;
                break;
            case 6:
                if (offerCard.isSat())
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mylist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.bt_all_products)
    public void filiterByAllProducts() {
        List<ShopDataModel> shopDataModels = new ArrayList<>();
        for (ShopDataModel shopDataModel : this.shopDataModels) {
            if (shopDataModel.getProductsList().size() == masterProductDataModels.size()) {
                shopDataModels.add(shopDataModel);
            }
        }
        if (shopDataModels.size() != 0) {
            recyclerViewProductList.setVisibility(View.VISIBLE);
            linearLayoutNoProduct.setVisibility(View.GONE);
            showCart(shopDataModels);
        } else {
            recyclerViewProductList.setVisibility(View.GONE);
            linearLayoutNoProduct.setVisibility(View.VISIBLE);
            textViewNoProduct.setText("No shop contains all products");
        }
    }

    @OnClick(R.id.bt_distance)
    public void filiterByDistance() {

        recyclerViewProductList.setVisibility(View.VISIBLE);
        linearLayoutNoProduct.setVisibility(View.GONE);

        List<ShopCartDataModel> shopCartDataModels = new ArrayList<>();
        for (ShopDataModel shopDataModel : this.shopDataModels) {

            Location startPoint = new Location("locationA");
            startPoint.setLatitude(getLatitude());
            startPoint.setLongitude(getLongitude());
            Log.d(TAG, "showCart: locationA : " + startPoint.getLatitude() + " " + startPoint.getLongitude());

            Location endPoint = new Location("locationB");
            endPoint.setLatitude(shopDataModel.getShopLatitude());
            endPoint.setLongitude(shopDataModel.getShopLongitude());
            Log.d(TAG, "showCart: locationB : " + endPoint.getLatitude() + " " + endPoint.getLongitude());

            ShopCartDataModel shopCartDataModel = new ShopCartDataModel();
            shopCartDataModel.setShopDataModel(shopDataModel);
            shopCartDataModel.setDistance(round(startPoint.distanceTo(endPoint) / 1000, 2));

            shopCartDataModels.add(shopCartDataModel);
        }

        ShopCartDataModel shopCartDataModel;

        for (int i = 0; i < shopCartDataModels.size(); i++) {
            for (int j = 1; j < (shopCartDataModels.size() - i); j++) {
                if (shopCartDataModels.get(j - 1).getDistance() > shopCartDataModels.get(j).getDistance()) {
                    shopCartDataModel = shopCartDataModels.get(j - 1);
                    shopCartDataModels.set(j - 1, shopCartDataModels.get(j));
                    shopCartDataModels.set(j, shopCartDataModel);
                }
            }
        }

        List<ShopDataModel> shopDataModels = new ArrayList<>();
        for (ShopCartDataModel cartDataModel : shopCartDataModels) {
            shopDataModels.add(cartDataModel.getShopDataModel());
        }
        showCart(shopDataModels);
    }

    @OnClick(R.id.bt_price)
    public void filiterByPrice() {

        recyclerViewProductList.setVisibility(View.VISIBLE);
        linearLayoutNoProduct.setVisibility(View.GONE);

        List<ShopCartDataModel> shopCartDataModels = new ArrayList<>();
        for (ShopDataModel shopDataModel : this.shopDataModels) {
            double totalBill = 0;
            int todysDay = new Date().getDay();
            Date date = new Date();
            Log.d(TAG, "loadShops: " + date);
            for (int i = 0; i < shopDataModel.getProductsList().size(); i++) {
                ProductDataModel productDataModel = shopDataModel.getProductsList().get(i);
                if (productDataModel.getProductOffer() != null) {
                    totalBill = totalBill + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * productDataModel.getProductOffer().getOfferDiscount()) / 100, 2);
                } else {
                    totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                }
            }

            ShopCartDataModel shopCartDataModel = new ShopCartDataModel();
            shopCartDataModel.setShopDataModel(shopDataModel);
            shopCartDataModel.setTotalBill(totalBill);

            shopCartDataModels.add(shopCartDataModel);
        }

        ShopCartDataModel shopCartDataModel;

        for (int i = 0; i < shopCartDataModels.size(); i++) {
            for (int j = 1; j < (shopCartDataModels.size() - i); j++) {
                if (shopCartDataModels.get(j - 1).getTotalBill() > shopCartDataModels.get(j).getTotalBill()) {
                    shopCartDataModel = shopCartDataModels.get(j - 1);
                    shopCartDataModels.set(j - 1, shopCartDataModels.get(j));
                    shopCartDataModels.set(j, shopCartDataModel);
                }
            }
        }

        List<ShopDataModel> shopDataModels = new ArrayList<>();
        for (ShopCartDataModel cartDataModel : shopCartDataModels) {
            shopDataModels.add(cartDataModel.getShopDataModel());
        }
        showCart(shopDataModels);
    }

    @Override
    public void showErrorMsg() {
//        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab_make_offer)
    public void makeDeal() {
        List<ShopDataModel> shopDataModels = new ArrayList<>();
        for (ShopDataModel shopDataModel : this.shopDataModels) {
            if (shopDataModel.getProductsList().size() == masterProductDataModels.size()) {
                shopDataModels.add(shopDataModel);
            }
        }
        if (shopDataModels.size() != 0) {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
            View mView = layoutInflaterAndroid.inflate(R.layout.deal_dialog, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
            alertDialogBuilderUserInput.setView(mView);

            final EditText editTextPercent = mView.findViewById(R.id.et_percent);

            alertDialogBuilderUserInput.setCancelable(true)
                    .setPositiveButton("Make Deal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!TextUtils.isEmpty(editTextPercent.getText().toString().trim())) {
                                rate = editTextPercent.getText().toString().trim();
                                makeOffer(rate);
                                showProgress(true, "Making Offer...");
                            } else {
                                makeDeal();
                            }
                        }
                    });
            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            alertDialogAndroid.show();
        } else {
            Toast.makeText(this, "Sorry! Can't make deals.", Toast.LENGTH_SHORT).show();
        }
    }

    public void makeOffer(String percent) {
        List<ShopDataModel> shopDataModels = new ArrayList<>();
        for (ShopDataModel shopDataModel : this.shopDataModels) {
            if (shopDataModel.getProductsList().size() == masterProductDataModels.size()) {
                shopDataModels.add(shopDataModel);
            }
        }

        sendNotification(shopDataModels);

        HashMap<String, CustomerDealDataModel> stringCustomerDealDataModelHashMap = new HashMap<>();
        HashMap<String, RetailerDealDataModel> stringRetailerDealDataModelHashMap = new HashMap<>();
        databaseReference = firebaseDatabase.getReference("customer-deals");

        for (ShopDataModel shopDataModel : shopDataModels) {
            CustomerDealDataModel customerDealDataModel = new CustomerDealDataModel();
            customerDealDataModel.setPercent(Integer.parseInt(percent));
            customerDealDataModel.setStatus("proposed");
            customerDealDataModel.setShopDataModel(shopDataModel);

            RetailerDealDataModel retailerDealDataModel = new RetailerDealDataModel();
            retailerDealDataModel.setPercent(Integer.parseInt(percent));
            retailerDealDataModel.setStatus("proposed");
            retailerDealDataModel.setShopDataModel(shopDataModel);

            SharedPrefManager.LoadFromPref();
            retailerDealDataModel.setUserName(SharedPrefManager.get_userFullName());
            retailerDealDataModel.setUserID(SharedPrefManager.get_userEmail());

            String key = databaseReference.push().getKey();

            retailerDealDataModel.setKey(key);

            stringCustomerDealDataModelHashMap.put(key, customerDealDataModel);
            stringRetailerDealDataModelHashMap.put(key, retailerDealDataModel);
        }

        SharedPrefManager.LoadFromPref();
        query = databaseReference.child(SharedPrefManager.get_userEmail().replace(".", "-"));
        listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    HashMap<String, CustomerDealDataModel> stringDealDataModelHashMap = (HashMap<String, CustomerDealDataModel>) dataSnapshot.getValue();
                    stringDealDataModelHashMap.putAll(stringCustomerDealDataModelHashMap);
                    databaseReference.child(SharedPrefManager.get_userEmail().replace(".", "-")).setValue(stringDealDataModelHashMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                            Toast.makeText(CustomerCartActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            setCount(stringRetailerDealDataModelHashMap);
//                            query.removeEventListener(listener);
                        }
                    });
                } else {
                    databaseReference.child(SharedPrefManager.get_userEmail().replace(".", "-")).setValue(stringCustomerDealDataModelHashMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                            Toast.makeText(CustomerCartActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            setCount(stringRetailerDealDataModelHashMap);
//                            query.removeEventListener(listener);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    int count = 0;

    public void setCount(HashMap<String, RetailerDealDataModel> stringRetailerDealDataModelHashMap) {
        count = stringRetailerDealDataModelHashMap.size() - 1;
        makeDealRetailer(stringRetailerDealDataModelHashMap);
    }

    public void makeDealRetailer(HashMap<String, RetailerDealDataModel> stringRetailerDealDataModelHashMap) {
        if (count >= 0) {
            updateRetailerDatabase(stringRetailerDealDataModelHashMap);
        } else {
//            sendNotification(shopDataModels);
            showProgress(false, null);
//            Toast.makeText(this, "Done Updation", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRetailerDatabase(HashMap<String, RetailerDealDataModel> stringRetailerDealDataModelHashMap) {
        Set<String> strings = stringRetailerDealDataModelHashMap.keySet();
        List<String> stringList = new ArrayList<>();
        for (String s : strings) {
            stringList.add(s);
        }
        databaseReference = firebaseDatabase.getReference("retailer-deals");
        query = databaseReference.child(stringRetailerDealDataModelHashMap.get(stringList.get(count)).getShopDataModel().getRetailerID().replace(".", "-"));
        listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    HashMap<String, RetailerDealDataModel> stringRetailerDealDataModelHashMap1 = (HashMap<String, RetailerDealDataModel>) dataSnapshot.getValue();
                    stringRetailerDealDataModelHashMap1.putAll(stringRetailerDealDataModelHashMap);
                    try {
                        databaseReference.child(stringRetailerDealDataModelHashMap.get(stringList.get(count)).getShopDataModel().getRetailerID().replace(".", "-")).setValue(stringRetailerDealDataModelHashMap1, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                --count;
                                makeDealRetailer(stringRetailerDealDataModelHashMap);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "updateRetailerDatabase: Error" + e.getMessage());
                    }
                } else {
                    try {
                        databaseReference.child(stringRetailerDealDataModelHashMap.get(stringList.get(count)).getShopDataModel().getRetailerID().replace(".", "-")).child(stringList.get(count)).setValue(stringRetailerDealDataModelHashMap.get(stringList.get(count)), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                --count;
                                makeDealRetailer(stringRetailerDealDataModelHashMap);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "updateRetailerDatabase: Error" + e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendNotification(List<ShopDataModel> shopDataModels) {
        String retailerIDs = "[";
        for (ShopDataModel shopDataModel : shopDataModels) {
            retailerIDs = retailerIDs + "\"" + shopDataModel.getRetailerID() + "\",";
        }
        retailerIDs = retailerIDs.substring(0, retailerIDs.length() - 1);
        retailerIDs = retailerIDs + "]";
        this.retailerIDs = retailerIDs;
        customerCartPresenter.sendNotification();
    }

    @Override
    public String getRetailerIDs() {
        return retailerIDs;
    }

    @Override
    public String getRate() {
        return rate;
    }

    @Override
    public String getUserName() {
        SharedPrefManager.LoadFromPref();
        return SharedPrefManager.get_userFullName();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (query != null) {
            query.removeEventListener(listener);
        }
        Log.d(TAG, "onPause: Listner Removed");
    }
}