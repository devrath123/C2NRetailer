package com.example.c2n.nearby_shops.presentation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.models.ShopDistanceDataModel;
import com.example.c2n.customer_home.di.CustomerHomeDI;
import com.example.c2n.customer_single_shop_products.presenter.CustomerSingleShopProductsActivity;
import com.example.c2n.nearby_shops.di.NearbyShopsDI;
import com.example.c2n.nearby_shops.presentation.adapter.NearbyShopsAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class NearbyShopsFragment extends Fragment implements NearbyShopsView, NearbyShopsAdapter.NearbyShopsAdapterInterface {

    private static final String TAG = NearbyShopsFragment.class.getSimpleName();

    private boolean currentLocationFlag;
    private boolean locationFlag;
    private int range = 5;
    private int GPS_LOCATION = 1234;

    private ProgressDialog progressDialog;

    private NearbyShopsAdapter nearbyShopsAdapter;

    @BindView(R.id.recycler_view_shop_list)
    RecyclerView recyclerViewShopList;
    @BindView(R.id.relative_layout_nearby_shops_progress)
    RelativeLayout relativeLayoutProgress;

    @BindView(R.id.rl_no_shop)
    RelativeLayout relativeLayoutNoShop;

    @Inject
    NearbyShopsPresenter nearbyShopsPresenter;

    private LocationManager mLocationManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: In onCreateView");
        View view = inflater.inflate(R.layout.fragment_nearby_shops, container, false);
        ButterKnife.bind(this, view);
        NearbyShopsDI.getNearbyShopsComponent().inject(this);
        CustomerHomeDI.getCustomerHomeComponent().inject(this);
        nearbyShopsPresenter.bind(this, getContext());

        progressDialog = new ProgressDialog(getContext());

//        recyclerViewShopList.setVisibility(View.GONE);
//        relativeLayoutProgress.setVisibility(View.VISIBLE);
//        relativeLayoutNoShop.setVisibility(View.GONE);
////        showProgressDialog("Loading...");
//
////        getNearbyShops();
//        getCurrentLocation();
        return view;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: In onResume");
        super.onResume();
        recyclerViewShopList.setVisibility(View.GONE);
        relativeLayoutProgress.setVisibility(View.VISIBLE);
        relativeLayoutNoShop.setVisibility(View.GONE);
        if (nearbyShopsAdapter != null) {
            nearbyShopsAdapter.clear();
        }
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        Dexter.withActivity(getActivity())
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

                            final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                                buildAlertMessageNoGps();
                                displayLocationSettingsRequest();
                            } else {

                                showLocationProgress(true);

//                                locationFlag = false;
//
//                                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                                getLocation();

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
            int mode = Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.LOCATION_MODE);
            Log.d(TAG, "onPermissionsChecked: Mode: " + mode);
            if (mode != 3) {
                showLocationProgress(false);
                Toast.makeText(getContext(), "Please change location mode to 'High accuracy'", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else {
                SmartLocation.with(getContext()).location()
                        .oneFix()
                        .config(LocationParams.BEST_EFFORT)
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                showLocationProgress(false);
                                currentLocationFlag = true;
                                Log.d(TAG, "onLocationUpdated: Latitude : " + location.getLatitude() + " Longitude : " + location.getLongitude());
                                nearbyShopsPresenter.setLocation(location);
                                getNearbyShops();
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
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.location_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
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
                            Location location = new Location("currentLocation");
                            location.setLatitude(Double.parseDouble(editTextLatitude.getText().toString().trim()));
                            location.setLongitude(Double.parseDouble(editTextLongitude.getText().toString().trim()));
                            nearbyShopsPresenter.setLocation(location);

//                            showLocationProgress(false);

                            getNearbyShops();
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
                Log.d("NearbyShopsFragment__", location.getLatitude() + " - " + location.getLongitude());
                nearbyShopsPresenter.setLocation(location);

                showLocationProgress(false);

                getNearbyShops();
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
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getContext())
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
                            status.startResolutionForResult(getActivity(), GPS_LOCATION);
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

    public void getNearbyShops() {
        nearbyShopsPresenter.getNearbyShops();
    }

    @Override
    public void loadShops(List<ShopDistanceDataModel> shopDataModels) {
        relativeLayoutProgress.setVisibility(View.GONE);

//        dismissProgressDialog();
        if (shopDataModels.size() != 0) {
            recyclerViewShopList.setVisibility(View.VISIBLE);
            relativeLayoutNoShop.setVisibility(View.GONE);

            nearbyShopsAdapter = new NearbyShopsAdapter(shopDataModels, getContext(), this);
            recyclerViewShopList.setHasFixedSize(true);
            LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewShopList.setLayoutManager(linearLayout);
            recyclerViewShopList.setAdapter(nearbyShopsAdapter);
            nearbyShopsAdapter.notifyDataSetChanged();
        } else {
            recyclerViewShopList.setVisibility(View.GONE);
            relativeLayoutNoShop.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_LOCATION) {
            switch (resultCode) {
                case RESULT_OK: {
                    // All required changes were successfully made
                    Toast.makeText(getContext(), "Location enabled by user!", Toast.LENGTH_LONG).show();

                    showLocationProgress(true);

//                    locationFlag = false;
//
//                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    getLocation();

                    break;
                }
                case RESULT_CANCELED: {
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(getContext(), "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                    break;
                }
                default: {
                    Toast.makeText(getContext(), "Default", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public void shopClicked(ShopDistanceDataModel shopDistanceDataModel) {
        Log.d(TAG, "shopClicked: " + shopDistanceDataModel.toString());
        Intent intent = new Intent(getActivity(), CustomerSingleShopProductsActivity.class);
        intent.putExtra("shopDataModel", shopDistanceDataModel);
        startActivity(intent);
    }

    protected void showProgressDialog(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showLocationProgress(Boolean flag) {
        if (flag) {
            showProgressDialog("Getting location...");
        } else {
            dismissProgressDialog();
        }
    }
}
