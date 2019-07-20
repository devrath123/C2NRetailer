package com.example.c2n.addshop.presenter;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.addshop.di.AddshopDI;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.retailerhome.presenter.RetailerHomeActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

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

public class AddShop1Fragment extends Fragment implements AddshopView {

    public ProgressDialog progressDialog;
    private boolean currentLocationFlag;

    private LocationManager lm;
    private static final String TAG = AddShop1Fragment.class.getSimpleName();
    private String shopName, shopEmail;
    private double latitude = 0, longitude = 0;
    private String shopPicURL = "";
    private boolean locationFlag;
    private int GPS_LOCATION = 1234;

    FirebaseStorage storage;
    StorageReference reference;

    @BindView(R.id.iv_map)
    ImageView imageViewMap;

    @BindView(R.id.et_shop_address)
    EditText editTextShopAddress;

    @BindView(R.id.et_shop_landmark)
    EditText editTextLandmark;

    @BindView(R.id.et_shop_shop_no)
    EditText editTextShopNo;

    @BindView(R.id.et_shop_contact_no)
    EditText editTextShopMobileNo;

    @BindView(R.id.bt_add_shop)
    AppCompatButton appCompatButton;

    @Inject
    AddshopPresenter addshopPresenter;

    Boolean isAddShopButtonActive = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_shop, container, false);
        ButterKnife.bind(this, view);
        AddshopDI.getAddshopComponent().inject(this);
        addshopPresenter.bind(this, getContext());

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        progressDialog = new ProgressDialog(getContext());

        deactivateAddShopButton();

        SharedPrefManager.Init(getContext());

        shopName = getArguments().getString("shopName");
        shopEmail = getArguments().getString("shopEmail");

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        getGEOLocation();

        editTextShopAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    deactivateAddShopButton();
                } else
                    activateAddShopButton();

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
                if (start == 0 && count == 0) {
                    deactivateAddShopButton();
                } else
                    activateAddShopButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextLandmark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    deactivateAddShopButton();
                } else
                    activateAddShopButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextShopMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    deactivateAddShopButton();
                } else
                    activateAddShopButton();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        getGEOLocation();

        return view;
    }


    private void activateAddShopButton() {
        if ((!TextUtils.isEmpty(editTextShopNo.getText().toString()) && (!TextUtils.isEmpty(editTextLandmark.getText().toString())) && (!TextUtils.isEmpty(getShopAddress())) && (editTextShopMobileNo.getText().toString().length() == 10))) {
            isAddShopButtonActive = true;
            appCompatButton.setTextColor(getResources().getColor(R.color.white));
            appCompatButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_hollow));
            Log.d("addshopNextButton", "activateappbutton");
        } else
            deactivateAddShopButton();
    }


    private void deactivateAddShopButton() {
        isAddShopButtonActive = false;
        appCompatButton.setTextColor(getResources().getColor(R.color.white));
        appCompatButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.inactive_button_background_hollow));
        Log.d("deactivateAppButton", "deactivateAppButton");
    }

    @OnClick(R.id.iv_map)
    @Override
    public void getGEOLocation() {
//        Intent intent = new LocationPickerActivity.Builder()
//                .withGooglePlacesEnabled()
////                .withLocation(41.4036299, 2.1743558)
//                .withGeolocApiKey("AIzaSyD0y9-9FPMyNGKy6cHcRVtszs3ruBZ6ajE")
////                .withSearchZone("es_ES")
//                .shouldReturnOkOnBackPressed()
//                .withStreetHidden()
//                .withCityHidden()
//                .withZipCodeHidden()
//                .withSatelliteViewHidden()
//                .build(getApplicationContext());
//
//        startActivityForResult(intent, 1);

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

//                                Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                                if (location != null) {
//
//                                showLocationProgress(false);
//
//                                    Intent intent = new LocationPickerActivity.Builder()
//                                            .withGooglePlacesEnabled()
//                                            .withLocation(location.getLatitude(), location.getLongitude())
//                                            .withGeolocApiKey("AIzaSyCeGNexl9P4dVBj0T5CbgPRcaZDbHSI2MU")
//                                            .shouldReturnOkOnBackPressed()
//                                            .withStreetHidden()
//                                            .withCityHidden()
//                                            .withZipCodeHidden()
//                                            .withSatelliteViewHidden()
//                                            .build(getContext());
//
//                                    intent.putExtra(LocationPickerActivity.BACK_PRESSED_RETURN_OK, false);
//
//                                    startActivityForResult(intent, 1);
//
//                                } else {

                                getLocation();

//                                locationFlag = false;
//
//                                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                                }
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Log.d(TAG, "onPermissionsChecked: Location failed");
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

    @Override
    public String getShopName() {
        return shopName;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_LOCATION) {
            switch (resultCode) {
                case RESULT_OK: {
                    // All required changes were successfully made
                    Toast.makeText(getActivity(), "Location enabled by user!", Toast.LENGTH_LONG).show();

                    showLocationProgress(true);

                    getLocation();

//                    locationFlag = false;
//
//                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

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
        if (requestCode == 1) {
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
                    setAddress(fullAddress.getAddressLine(0));
                }
            }
            if (resultCode == getActivity().RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void uploadImage() {
        AddShopActivity addShopActivity = (AddShopActivity) getActivity();
        Uri filePath = addShopActivity.filePath;
        if (filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Adding Shop");
            dialog.show();
            dialog.setCancelable(false);
            StorageReference ref = reference.child("images/" + UUID.randomUUID().toString());
            UploadTask uploadTask = ref.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        Toast.makeText(getActivity(), "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        shopPicURL = String.valueOf(downloadUrl);
                        Log.d("shopPicUrl", shopPicURL);
                        addshopPresenter.addShop();

                        activateAddShopButton();
                    } else {
                        dialog.dismiss();

                        addshopPresenter.addShop();

                        deactivateAddShopButton();

//                            editProfilePresenter.checkProfileFields();
                        Toast.makeText(getActivity(), "Image upload failed" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("uploadImage_" +
                                "Failed", task.getException().getMessage());
                    }
                }
            });
//            ref.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                            Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();
//
////                            SharedPrefManager.set_userProfilePic(String.valueOf(downloadUrl));
////                            SharedPrefManager.StoreToPref();
//
//                            Toast.makeText(getActivity(), "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            shopPicURL = String.valueOf(downloadUrl);
//                            Log.d("shopPicUrl", shopPicURL);
//                            addshopPresenter.addShop();
//
//                            activateAddShopButton();
//
////                            editProfilePresenter.checkProfileFields();
////                            Picasso.with(MainActivity.this).load(downloadUrl).into(upload_image);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            dialog.dismiss();
//
//                            addshopPresenter.addShop();
//
//                            deactivateAddShopButton();
//
////                            editProfilePresenter.checkProfileFields();
//                            Toast.makeText(getActivity(), "Image upload failed" + e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("uploadImage_" +
//                                    "Failed", e.getMessage());
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                    .getTotalByteCount());
////                            dialog.setMessage("Uploaded " + (int) progress + "%");
//                            dialog.setMessage("Adding Shop");// + (int) progress + "%");
//                        }
//                    });
        }
    }

    @Override
    public String getShopPicURL() {
        return shopPicURL;
    }

    @Override
    public String getShopAddress() {
        return editTextShopAddress.getText().toString().trim();
    }

    @Override
    public String getShopLandmark() {
        return editTextLandmark.getText().toString().trim();
    }

    @Override
    public String getShopMobileNo() {
        return editTextShopMobileNo.getText().toString().trim();
    }

    @Override
    public String getShopEmail() {
        return shopEmail;
    }

    @Override
    public String getShopNo() {
        return editTextShopNo.getText().toString().trim();
    }

    @Override
    public String getUserEmail() {
        SharedPrefManager.LoadFromPref();
        return SharedPrefManager.get_userEmail();
    }

    @Override
    public double getShopLatitude() {
        return latitude;
    }

    @Override
    public double getShopLongitude() {
        return longitude;
    }

    @OnClick(R.id.bt_add_shop)
    @Override
    public void validateShop() {
        if (isAddShopButtonActive)
            addShop();
        else
            addshopPresenter.validateShopDetails();
    }

    @Override
    public void addShop() {

        AddShopActivity addShopActivity = (AddShopActivity) getActivity();
        Uri filePath = addShopActivity.filePath;
        if (filePath != null)
            uploadImage();
        else
            addshopPresenter.addShop();
    }

    @Override
    public void showPregressDialog(Boolean aBoolean) {
        if (aBoolean) {
            showProgressDialog("Adding Shop");
        } else
            dismissProgressDialog();
    }

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

    @Override
    public void openViewShop() {
        Intent intent = new Intent(getContext(), RetailerHomeActivity.class);
        intent.putExtra("navigation", "2");
        startActivity(intent);
        getActivity().finish();
    }

    public void setAddress(String address) {
        editTextShopAddress.setMinLines(3);
        editTextShopAddress.setText(address);
    }

    @Override
    public void setLandmarkError() {
        editTextLandmark.setError("Please enter landmark");
        editTextLandmark.requestFocus();
    }

    @Override
    public void setMobileNoError(String msg) {
        editTextShopMobileNo.setError(msg);
        editTextShopMobileNo.requestFocus();
    }

    @Override
    public void setShopNoError() {
        editTextShopNo.setError("Please enter Home / Shop No.");
        editTextShopNo.requestFocus();
    }

    public void showLocationProgress(Boolean flag) {
        if (flag) {
            showProgressDialog("Getting location...");
        } else {
            dismissProgressDialog();
        }
    }
}
