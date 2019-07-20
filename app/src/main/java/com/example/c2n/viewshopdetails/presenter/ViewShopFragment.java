package com.example.c2n.viewshopdetails.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c2n.R;
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
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.schibstedspain.leku.LocationPickerActivityKt.ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.BACK_PRESSED_RETURN_OK;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LAYOUTS_TO_HIDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;

public class ViewShopFragment extends Fragment {

    private static final String TAG = ViewShopFragment.class.getSimpleName();

    private boolean currentLocationFlag;
    private double latitude, longitude;
    private int GPS_LOCATION = 1234;
    private boolean locationFlag;
    public ProgressDialog progressDialog;

    @BindView(R.id.et_shop_address)
    EditText editTextShopAddress;

    @BindView(R.id.et_shop_shop_no)
    EditText editTextShopNo;

    @BindView(R.id.et_shop_landmark)
    EditText editTextShopLandmark;

    @BindView(R.id.bt_update_address)
    Button buttonUpdateAddress;

    private String shopAddress = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_shop, container, false);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getActivity());

        editTextShopAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeButtonColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextShopNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeButtonColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextShopLandmark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeButtonColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public void changeButtonColor() {
        if (!getShopAddress().equals("") && !getShopNo().equals("") && !getShopLandmark().equals("")) {
            buttonUpdateAddress.setBackgroundResource(R.drawable.button_background_hollow);
        } else {
            buttonUpdateAddress.setBackgroundResource(R.drawable.button_background);
        }
    }

    @OnClick(R.id.iv_map)
    public void getGEOLocation() {
//        Intent intent = new LocationPickerActivity.Builder()
//                .withGooglePlacesEnabled()
////                .withLocation(41.4036299, 2.1743558)
//                .withGeolocApiKey("AIzaSyCeGNexl9P4dVBj0T5CbgPRcaZDbHSI2MU")
////                .withGeolocApiKey("AIzaSyAJEflASbXRU1z7YWF2X9fflCoxE7upaG0")
////                .withSearchZone("es_ES")
//                .shouldReturnOkOnBackPressed()
//                .withStreetHidden()
//                .withCityHidden()
//                .withZipCodeHidden()
//                .withSatelliteViewHidden()
//                .build(getActivity());
//
//        startActivityForResult(intent, 1);

        showProgressDialog("Please wait...");
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        try {
//            Intent intent = builder.build(this);
//            startActivityForResult(intent, PLACE_PICKER_REQUEST_CODE);
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//            Log.d("RepairableException", "" + e.getMessage());
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//            Log.d("NotAvailableException", "" + e.getMessage());
//        }
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
                            dismissProgressDialog();
                            final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                                buildAlertMessageNoGps();
                                displayLocationSettingsRequest(getContext());
                            } else {

                                showLocationProgress(true);

//                                locationFlag = false;
//
//                                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                                getLocation();

//                                Intent intent = new LocationPickerActivity.Builder()
//                                        .withGooglePlacesEnabled()
////                .withLocation(41.4036299, 2.1743558)
//                                        .withGeolocApiKey("AIzaSyCeGNexl9P4dVBj0T5CbgPRcaZDbHSI2MU")
////                .withGeolocApiKey("AIzaSyAJEflASbXRU1z7YWF2X9fflCoxE7upaG0")
////                .withSearchZone("es_ES")
//                                        .shouldReturnOkOnBackPressed()
//                                        .withStreetHidden()
//                                        .withCityHidden()
//                                        .withZipCodeHidden()
//                                        .withSatelliteViewHidden()
//                                        .build(getActivity());
//
//                                intent.putExtra(LocationPickerActivity.BACK_PRESSED_RETURN_OK, false);
//
//                                startActivityForResult(intent, 1);
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
                                openMap(location);
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
                            Location location = new Location("Current");
                            location.setLatitude(Double.parseDouble(editTextLatitude.getText().toString().trim()));
                            location.setLongitude(Double.parseDouble(editTextLongitude.getText().toString().trim()));

                            openMap(location);
                        } else {
                            showAlertDialog();
                        }
                    }
                });
        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    public void openMap(Location location) {
        Intent intent = new LocationPickerActivity.Builder()
                .withGooglePlacesEnabled()
                .withLocation(location.getLatitude(), location.getLongitude())
                .withGeolocApiKey("AIzaSyCeGNexl9P4dVBj0T5CbgPRcaZDbHSI2MU")
//                .withGeolocApiKey("AIzaSyAJEflASbXRU1z7YWF2X9fflCoxE7upaG0")
                .shouldReturnOkOnBackPressed()
                .withStreetHidden()
                .withCityHidden()
                .withZipCodeHidden()
                .withSatelliteViewHidden()
                .build(getContext());

        intent.putExtra(LAYOUTS_TO_HIDE, "street|city|zipcode");

        intent.putExtra(BACK_PRESSED_RETURN_OK, false);

        startActivityForResult(intent, 1);
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            if (!locationFlag) {
                locationFlag = true;
                Log.d(TAG, "Location : " + location.getLatitude() + " - " + location.getLongitude());
//                nearbyShopsPresenter.setLocation(location);
//                getNearbyShops();

                showLocationProgress(false);

                Intent intent = new LocationPickerActivity.Builder()
                        .withGooglePlacesEnabled()
                        .withLocation(location.getLatitude(), location.getLongitude())
                        .withGeolocApiKey("AIzaSyCeGNexl9P4dVBj0T5CbgPRcaZDbHSI2MU")
//                .withGeolocApiKey("AIzaSyAJEflASbXRU1z7YWF2X9fflCoxE7upaG0")
//                .withSearchZone("es_ES")
                        .shouldReturnOkOnBackPressed()
                        .withStreetHidden()
                        .withCityHidden()
                        .withZipCodeHidden()
                        .withSatelliteViewHidden()
                        .build(getContext());

                intent.putExtra(BACK_PRESSED_RETURN_OK, false);

                startActivityForResult(intent, 1);

            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
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
                            startIntentSenderForResult(status.getResolution().getIntentSender(),
                                    GPS_LOCATION, null, 0, 0, 0, null);
//                            status.startResolutionForResult(getActivity(), GPS_LOCATION);
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

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && CustomerSingleProductRepository != null && CustomerSingleProductRepository.getData() != null) {
//            filePath = CustomerSingleProductRepository.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageViewShopImage.setImageBitmap(bitmap);
////                uploadImage();
//            } catch (Exception e) {
//                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
//            }if (requestCode == 1) {
        if (requestCode == GPS_LOCATION) {
            switch (resultCode) {
                case RESULT_OK: {
                    // All required changes were successfully made
                    Toast.makeText(getActivity(), "Location enabled by user!", Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getActivity(), "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                    break;
                }
                default: {
                    Toast.makeText(getActivity(), "Default", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
        if (resultCode == getActivity().RESULT_OK) {
            double latitude = data.getDoubleExtra(LATITUDE, 0);
            Log.d("LATITUDE****", String.valueOf(latitude));
            this.latitude = latitude;
            double longitude = data.getDoubleExtra(LONGITUDE, 0);
            Log.d("LONGITUDE****", String.valueOf(longitude));
            this.longitude = longitude;
            String address = data.getStringExtra(LOCATION_ADDRESS);
            Log.d("ADDRESS****", String.valueOf(address));
            String postalcode = data.getStringExtra(ZIPCODE);
            Log.d("POSTALCODE****", String.valueOf(postalcode));
//                Bundle bundle = CustomerSingleProductRepository.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
//                Log.d("BUNDLE TEXT****", bundle.getString("test"));
            Address fullAddress = data.getParcelableExtra(ADDRESS);
            if (fullAddress != null) {
                Log.d("FULL ADDRESS****", fullAddress.toString());
                setShopAddress(fullAddress.getAddressLine(0));
            }
        }
        if (resultCode == getActivity().RESULT_CANCELED) {
            //Write your code if there's no result
        }
    }

    @OnClick(R.id.bt_update_address)
    public void updateAddress() {
        if (getShopAddress().equals("")) {
            Toast.makeText(getContext(), "Please pick shop address", Toast.LENGTH_SHORT).show();
        } else if (getShopNo().equals("")) {
            Toast.makeText(getContext(), "Please enter shop no.", Toast.LENGTH_SHORT).show();
        } else if (getShopLandmark().equals("")) {
            Toast.makeText(getContext(), "Please enter shop landmark", Toast.LENGTH_SHORT).show();
        } else {
            ViewShopActivity viewShopActivity = (ViewShopActivity) getActivity();
            viewShopActivity.updateAddress(getShopNo() + ", " + getShopLandmark() + ", " + getShopAddress(), latitude, longitude);
            getActivity().onBackPressed();
        }
//        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
//        getActivity().getFragmentManager().popBackStack();

    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
        editTextShopAddress.setText(shopAddress);
    }

    public String getShopAddress() {
        return editTextShopAddress.getText().toString().trim();
    }

    public String getShopNo() {
        return editTextShopNo.getText().toString().trim();
    }

    public String getShopLandmark() {
        return editTextShopLandmark.getText().toString().trim();
    }

//    public interface ViewShoppInterface {
//        public void setAddress(String shopAddress);
//    }

    public void showEditProfileProgress(Boolean bool) {
        if (bool)
            showProgressDialog("Updating...");
        else
            dismissProgressDialog();
    }

    private void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void showLocationProgress(Boolean flag) {
        if (flag) {
            showProgressDialog("Getting location...");
        } else {
            dismissProgressDialog();
        }
    }
}
