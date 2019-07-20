package com.example.c2n.edit_profile.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.FileUtil;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.edit_profile.di.EditProfileDI;
import com.example.c2n.mobileverification.presentation.presenter.MobileVerificationActivity;
import com.example.c2n.preferences.presenter.PreferencesActivity;
import com.example.c2n.viewshops.presenter.ViewShopsFragment;
import com.facebook.shimmer.ShimmerFrameLayout;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.schibstedspain.leku.LocationPickerActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import id.zelory.compressor.Compressor;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.schibstedspain.leku.LocationPickerActivityKt.ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.BACK_PRESSED_RETURN_OK;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LAYOUTS_TO_HIDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;

public class EditProfileFragment extends Fragment implements EditProfileView {

    public static final String TAG = EditProfileFragment.class.getSimpleName();

    private int GPS_LOCATION = 1234;
    LocationManager locationManager;
    private boolean currentLocationFlag;

    private boolean locationFlag;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    public ProgressDialog progressDialog;
    private Bitmap bitmap;

    private String userType, userDocumentID, userEmail;
    private String profilePicURL = "";

    @BindView(R.id.tv_customer_type)
    TextView textViewUserType;

    @BindView(R.id.et_name)
    TextView editTextUserName;

    @BindView(R.id.et_address)
    EditText editTextUserAddress;

    @BindView(R.id.et_mobile_no)
    TextView editTextUserMobileNo;

    @BindView(R.id.bt_location)
    TextView buttonGetLocation;

    @BindView(R.id.et_email)
    TextView editTextUserEmail;

    @BindView(R.id.et_dob)
    TextView editTextDOB;

    @BindView(R.id.iv_profile_picture)
    ImageView imageViewProfilePic;
// CircleImageView imageViewProfilePic;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.layout_edit_profile_details)
    RelativeLayout editProfileLayout;

    @Inject
    EditProfilePresenter editProfilePresenter;

    private int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference reference;
    private double latitude = 0, longitude = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);
        EditProfileDI.getUserEditProfileComponent().inject(this);
        editProfilePresenter.bind(this, getActivity());
        SharedPrefManager.Init(getActivity());
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, GPS_LOCATION);

        progressDialog = new ProgressDialog(getContext());

        SharedPrefManager.LoadFromPref();
        userType = SharedPrefManager.get_userType();
//       userDocumentID = SharedPrefManager.get_userDocumentID();
//        Log.d("EdirProfile_init", "" + SharedPrefManager.get_userDocumentID());

//        userDocumentID = "test@gmail.com";
//        Log.d("EdirProfile_init",userDocumentID);
        getUserDetails();
        return view;
    }

    @OnClick(R.id.iv_profile_picture)
    void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    if (result)
//                    clickPhoto();
                    Dexter.withActivity(getActivity())
                            .withPermissions(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        // do you work now
                                        clickPhoto();
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
                    Toast.makeText(getActivity(), "Take Photo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask = "Choose from Library";
//                    if (result)
                    pickProfileImage();
                    Toast.makeText(getActivity(), "Choose from Library", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
//                    getActivity().finish();
//                    if (imageStatusFlag == false)
//                        relativeLayoutShopImage.setVisibility(View.GONE);
                }
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    getFragmentManager().popBackStackImmediate();
                }
                return true;
            }
        });
        builder.show();
    }

    public void clickPhoto() {
//        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
//                Log.i(TAG, "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {

//        // create a File object for the parent directory
//        File wallpaperDirectory = new File("/storage/emulated/0/roshan");
//        // have the object build the directory structure, if needed.
//        wallpaperDirectory.mkdirs();

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

   /* @Optional
    @OnClick(R.id.bt_submit_data)
    @Override
    public void checkProfileFields() {
        uploadImage();
        if (bitmap == null)
            editProfilePresenter.checkProfileFields();
    }*/

//    @Optional
//    @OnClick(R.id.bt_location)
//    @Override
//    public void getGEOLocation() {
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps();
//
//        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            getGEOLocation();
//        }
//    }
//
//    private void getGEOLocation() {
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_LOCATION);
//
//        } else {
//            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////            showLocationProgress(true);
////            locationFlag = false;
////            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//
//            if (location != null) {
//                openMap(location);
//            } else if (location1 != null) {
//                openMap(location1);
//
//            } else if (location2 != null) {
//                openMap(location2);
//            } else {
//                Toast.makeText(getContext(), "Unble to Trace your location", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    @Optional
//    @OnClick(R.id.bt_location)
//    public void getGEOLocation() {
//
//    }

    @Optional
    @OnClick(R.id.bt_location)
    @Override
    public void getGEOLocation() {
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

//                                Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                //check network location then access gps location

//                                Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//                                showLocationProgress(false);
//
//                                Intent intent = new LocationPickerActivity.Builder()
//                                        .withGooglePlacesEnabled()
//                                        .withLocation(location.getLatitude(), location.getLongitude())
//                                        .withGeolocApiKey("AIzaSyCeGNexl9P4dVBj0T5CbgPRcaZDbHSI2MU")
////                .withGeolocApiKey("AIzaSyAJEflASbXRU1z7YWF2X9fflCoxE7upaG0")
////                .withSearchZone("es_ES")
//                                        .shouldReturnOkOnBackPressed()
//                                        .withStreetHidden()
//                                        .withCityHidden()
//                                        .withZipCodeHidden()
//                                        .withSatelliteViewHidden()
//                                        .build(getContext());
//
//                                intent.putExtra(LocationPickerActivity.LAYOUTS_TO_HIDE, "street|city|zipcode");
//
//                                intent.putExtra(LocationPickerActivity.BACK_PRESSED_RETURN_OK, false);
//
//                                startActivityForResult(intent, 1);

//                                locationFlag = false;
//
//                                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

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

                                getLocation();

//                                SmartLocation.with(getContext()).location()
//                                        .oneFix()
//                                        .config(LocationParams.BEST_EFFORT)
//                                        .start(new OnLocationUpdatedListener() {
//                                            @Override
//                                            public void onLocationUpdated(Location location) {
//                                                showLocationProgress(false);
//                                                currentLocationFlag = true;
//                                                Log.d(TAG, "onLocationUpdated: Latitude : " + location.getLatitude() + " Longitude : " + location.getLongitude());
//                                                openMap(location);
//                                            }
//                                        });
//
//                                Runnable runnable = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (!currentLocationFlag) {
//                                            showLocationProgress(false);
//                                            showAlertDialog();
//                                        }
//                                    }
//                                };
//
//                                Handler handler = new Handler();
//                                handler.postDelayed(runnable, 10000);
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

                intent.putExtra(LAYOUTS_TO_HIDE, "street|city|zipcode");

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

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void updateProfile() {
        editProfilePresenter.updateProfile();
    }

    @Override
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
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @OnClick(R.id.et_mobile_no)
    @Override
    public void changeContactNo() {
        startActivity(new Intent(getContext(), MobileVerificationActivity.class));
//         SharedPrefManager.LoadFromPref();

       /* Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(getMobileNo()));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getActivity().startActivity(callIntent);*/

    }

    public void showLocationProgress(Boolean flag) {
        if (flag) {
            showProgressDialog("Getting location...");
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    public void isEditProfileSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {
            Toast.makeText(getActivity(), "Update Successfull", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getActivity(), "An error occured in Update Profile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Boolean isDOBEmpty() {
        if (TextUtils.isEmpty(editTextDOB.getText().toString())) {
            editTextDOB.setError("Enter valid DOB");
            editTextDOB.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public Boolean isMobileNoEmpty() {
        if (TextUtils.isEmpty(editTextUserMobileNo.getText().toString())) {
            editTextUserMobileNo.setError("Enter valid Mobile No.");
            editTextUserMobileNo.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public Boolean isAddressEmpty() {
        if (TextUtils.isEmpty(editTextUserAddress.getText().toString())) {
            editTextUserAddress.setError("Enter valid Address");
            editTextUserAddress.requestFocus();
            return true;
        }
        return false;
    }


    @Override
    public void pickProfileImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @SuppressLint({"RxLeakedSubscription", "MissingPermission"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

//                    SmartLocation.with(getContext()).location()
//                            .oneFix()
//                            .config(LocationParams.BEST_EFFORT)
//                            .start(new OnLocationUpdatedListener() {
//                                @Override
//                                public void onLocationUpdated(Location location) {
//                                    Log.d(TAG, "onLocationUpdated: Latitude : " + location.getLatitude() + " Longitude : " + location.getLongitude());
//                                    showLocationProgress(false);
//                                    openMap(location);
//                                }
//                            });

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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && mCurrentPhotoPath != null) {
//            filePath = CustomerSingleProductRepository.getData();
            try {
                filePath = Uri.parse(mCurrentPhotoPath);
                new Compressor(getActivity())
                        .compressToFileAsFlowable(FileUtil.from(getActivity(), filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
//                                compressedImage = file;
//                                setCompressedImage();
                                filePath = Uri.fromFile(file);
                                imageViewProfilePic.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
//                imageViewProfilePic.setImageBitmap(bitmap);
//                filePath = Uri.parse(mCurrentPhotoPath);

                FileUtil.from(getActivity(), filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                new Compressor(getActivity())
                        .compressToFileAsFlowable(FileUtil.from(getActivity(), filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
//                                compressedImage = file;
//                                setCompressedImage();
                                filePath = Uri.fromFile(file);
                                imageViewProfilePic.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageViewProfilePic.setImageBitmap(bitmap);
////                uploadImage();
            } catch (Exception e) {
                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
            }
        } else if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
//                textViewLocationPicked.setVisibility(View.VISIBLE);
//                buttonGetLocation.setText("Update Location");
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
                    Log.d("FULL_ADDRESS****", fullAddress.toString());
                    setAddress(fullAddress.getAddressLine(0));
                }
            }
            if (resultCode == getActivity().RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Uploading...");
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
//                        Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();

                        SharedPrefManager.set_userProfilePic(String.valueOf(downloadUrl));
                        SharedPrefManager.StoreToPref();

                        Toast.makeText(getActivity(), "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        profilePicURL = String.valueOf(downloadUrl);
                        editProfilePresenter.checkProfileFields();
//                            Picasso.with(MainActivity.this).load(downloadUrl).into(upload_image);
                    } else {
                        dialog.dismiss();
                        editProfilePresenter.checkProfileFields();
                        Toast.makeText(getActivity(), "Image upload failed" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("uploadImage_" + "Failed", task.getException().getMessage());
                    }
                }
            });
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                            Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();
//
//                            SharedPrefManager.set_userProfilePic(String.valueOf(downloadUrl));
//                            SharedPrefManager.StoreToPref();
//
//                            Toast.makeText(getActivity(), "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            profilePicURL = String.valueOf(downloadUrl);
//                            editProfilePresenter.checkProfileFields();
////                            Picasso.with(MainActivity.this).load(downloadUrl).into(upload_image);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            dialog.dismiss();
//                            editProfilePresenter.checkProfileFields();
//                            Toast.makeText(getActivity(), "Image upload failed" + e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("uploadImage_" + "Failed", e.getMessage());
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                    .getTotalByteCount());
//                            dialog.setMessage("Updating...");
//                        }
//                    });
        }
    }

    @Override
    public String getUserName() {
        return editTextUserName.getText().toString().trim();
    }

    @Override
    public String getDOB() {
        return editTextDOB.getText().toString().trim();
    }

    @Override
    public String getMobileNo() {
//        return editTextUserMobileNo.getText().toString().trim().substring(editTextUserMobileNo.getText().toString().trim().length() - 10, editTextUserEmail.getText().toString().trim().length());
        return editTextUserMobileNo.getText().toString().trim().substring(4, 14);
    }

    @Override
    public String getEmail() {
        return editTextUserEmail.getText().toString().trim();
    }

    /* @Override
     public String getGender() {
         return spinnerGender.getSelectedItem().toString().trim();
     }
 */
    @Override
    public String getAddress() {
        return editTextUserAddress.getText().toString().trim();
    }

    @Override
    public String getUserDocumentID() {
//        SharedPrefManager.LoadFromPref();
        Log.d("getRetailerDocumentID", "" + SharedPrefManager.get_userDocumentID());
        return SharedPrefManager.get_userDocumentID();
//        return null;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String getProfilePicURL() {
        return profilePicURL;
    }

    @Override
    public void getUserDetails() {
//        showProgressDialog("Please wait");
        editProfilePresenter.getUser();
//        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public String getUserType() {
//        showProgressDialog("Please wait");
        return userType;
    }

//    @Override
//    public void setUserType(String userType) {
//        Log.d("setUserType", "" + userType);
//        if (userType.equals("R"))
//            textViewUserType.setText("User Type : Retailer");
//        else if (userType.equals("C"))
//            textViewUserType.setText("User Type : Customer");
//    }

    @Override
    public void setUserName(String retailerName) {
        editTextUserName.setText(retailerName);
    }

    @Override
    public void setDOB(String userDOB) {
        editTextDOB.setText(userDOB);
    }

    @Override
    public void setMobileNo(String userMobileNo) {

        shimmerFrameLayout.stopShimmer();
        editProfileLayout.setVisibility(View.VISIBLE);
//       parentLayout.removeView(shimmerFrameLayout);
        editTextUserMobileNo.setText("+91 " + userMobileNo);
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void setEmail(String retailerEmail) {
        editTextUserEmail.setText(SharedPrefManager.get_userEmail());
    }

    /* @Override
     public void setGender(String userGender) {
         if (userGender.equals("Male"))
             spinnerGender.setSelection(1);
         else if (userGender.equals("Female"))
             spinnerGender.setSelection(2);
     }
 */
    @Override
    public void setAddress(String userAddress) {
        editTextUserAddress.setText(userAddress);
    }

    @Override
    public void setLatitude(double userLatitude) {
        this.latitude = userLatitude;
        if (userLatitude != 0) {
//            textViewLocationPicked.setVisibility(View.VISIBLE);
//            buttonGetLocation.setText("Update Location");
        }
    }

    @Override
    public void setLongitude(double userLongitude) {
        this.longitude = userLongitude;
        if (userLongitude != 0) {
//            textViewLocationPicked.setVisibility(View.VISIBLE);
//            buttonGetLocation.setText("Update Location");
        }
    }

    @Override
    public void setProfilePicURL(String userProfilePicURL) {
        if (!userProfilePicURL.equals("")) {
            profilePicURL = userProfilePicURL;
            Picasso.get().load(userProfilePicURL).fit().into(imageViewProfilePic);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            uploadImage();
            if (bitmap == null)
                if (ConnectivityReceiver.isConnected()) {
                    editProfilePresenter.checkProfileFields();
                } else {
                    Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    //
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_skip:
//                SharedPrefManager.set_skippedEditProfile(true);
//                SharedPrefManager.StoreToPref();
//                SharedPrefManager.LoadFromPref();
//                Toast.makeText(getActivity(), "" + SharedPrefManager.is_skippedEditProfile(), Toast.LENGTH_SHORT).show();
//                if (SharedPrefManager.get_userType().equals("C"))
//                    startActivity(new Intent(getActivity(), PreferencesActivity.class));
//                else
//                    startActivity(new Intent(getActivity(), ViewShopsFragment.class));
////                finish();
//        }
//        return true;
//    }

    @OnClick(R.id.et_dob)
    @Override
    public void pickDate() {
//        Toast.makeText(EditProfileFragment.this, "pick date", Toast.LENGTH_SHORT).show();
        Calendar cal = Calendar.getInstance();

//        datePickerDialog.setTitle("Payment Date");
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Log.d("Orignal", "Got clicked");
                String date = dayOfMonth + "/" + String.valueOf(month + 1) + "/" + year;
                editTextDOB.setText(date);
            }
        },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();

//        editTextDOB.setText("12/12/12");
    }

    @Override
    public void openPreferences() {

        SharedPrefManager.LoadFromPref();
        if (SharedPrefManager.get_userType().equals("C"))
            startActivity(new Intent(getActivity(), PreferencesActivity.class));
        else
            startActivity(new Intent(getActivity(), ViewShopsFragment.class));
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();

    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }


}


