package com.example.c2n.viewshopdetails.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.FileUtil;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.retailerhome.presenter.RetailerHomeActivity;
import com.example.c2n.viewshopdetails.di.ViewShopDI;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.schibstedspain.leku.LocationPickerActivityKt.ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;

public class ViewShoppFragment extends Fragment implements ViewShopView {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    public ProgressDialog progressDialog;

    @BindView(R.id.shopImage)
    ImageView imageViewShopImage;

    @BindView(R.id.tv_shop_name)
    EditText editTextShopName;

    //    @BindView(R.id.et_shop_address)
    @BindView(R.id.et_shop_addresss)
    TextView editTextShopAddress;

    @BindView(R.id.tv_no_image)
    TextView textViewNoImage;

//    @BindView(R.id.tv_shop_address)
//    EditText editTextShopAddress;

//    @BindView(R.id.tv_shop_landmark)
//    EditText editTextShopLandmark;

    @BindView(R.id.tv_shop_mobile_no)
    EditText editTextShopMobile1;

//    @BindView(R.id.tv_shop_mobile_no_2)
//    TextView editTextShopMobile2;

    @BindView(R.id.tv_shop_email)
    EditText editTextShopEmail;

    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private String shopDocumentID;
    private ShopDataModel shopDataModel;
    private String shopImageURL = "";
    private String shopAddress = "";
    private String retailerID = "";
    private String previousShopID = "";

    FirebaseStorage storage;
    StorageReference reference;

    @Inject
    ViewShopPresenter viewShopPresenter;
    private double latitude, longitude;
    private String shopID, shopImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_shopp, container, false);
        ButterKnife.bind(this, view);
        ViewShopDI.getViewShopComponent().inject(this);
        viewShopPresenter.bind(this, getActivity());
        SharedPrefManager.Init(getActivity());

        SharedPrefManager.LoadFromPref();
        retailerID = SharedPrefManager.get_retailerDocumentID();

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();


        progressDialog = new ProgressDialog(getActivity());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        ViewShopActivity viewShopActivity = (ViewShopActivity) getActivity();
        shopDataModel = viewShopActivity.shopDataModel;
        viewShopActivity.setTitle(shopDataModel.getShopName());

        previousShopID = shopDataModel.getShopEmail();

        shopID = shopDataModel.getShopID();
        setShopImageURL(shopDataModel.getShopImageURL());

        setShopName(shopDataModel.getShopName());
        setShopAddress(shopDataModel.getShopAddress());
//            setShopLandmark(shopDataModel.getShopLandmark());
        setShopMobileNo1(shopDataModel.getShopCellNo());
        setShopEmail(shopDataModel.getShopEmail());
        setShopImage(shopDataModel.getShopImageURL());
        setLatitude(shopDataModel.getShopLatitude());
        setLongitude(shopDataModel.getShopLongitude());
        if (shopDataModel.getShopImageURL().equals("")) {
            textViewNoImage.setVisibility(View.VISIBLE);
        }
//        setShopImage(shopDataModel.getShopImageURL());

//        Intent intent = getActivity().getIntent();
//        if (intent != null) {
//            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
//            Log.d("ViewShopActivity__", shopDataModel.toString());
////            setShopDocumentID(shopDataModel.getShopDocumentID());
//            setShopName(shopDataModel.getShopName());
//            setShopAddress(shopDataModel.getShopAddress());
////            setShopLandmark(shopDataModel.getShopLandmark());
//            setShopMobileNo1(shopDataModel.getShopCellNo());
//            setShopEmail(shopDataModel.getShopEmail());
//            setShopImage(shopDataModel.getShopImageURL());
//        }
//        getActivity().getActionBar().setTitle(shopDataModel.getShopName());

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null)
//            shopDocumentID = bundle.getString("shopDocumentId");
//        loadShop();
        return view;
    }

    @OnClick(R.id.imageViewSelectImage)
    void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    if (result)
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
                    pickImage();
                    Toast.makeText(getActivity(), "Choose from Library", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
//                    if (imageStatusFlag == false)
//                        relativeLayoutShopImage.setVisibility(View.GONE);
                }
            }
        });
        builder.show();
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
        }
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //    @OnClick(R.id.bt_location)
    @Override
    public void getLocation() {
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
//                .build(this);
//
//        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.bt_location)
    public void updateShopAddress() {
        ViewShopFragment fragment = new ViewShopFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.frame, fragment, "shopAddress");
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commitAllowingStateLoss();
//        Toast.makeText(ViewShopActivity.this, "Update Location", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK && mCurrentPhotoPath != null) {
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
                                imageViewShopImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                textViewNoImage.setVisibility(View.GONE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
//                filePath = Uri.parse(mCurrentPhotoPath);
//                imageViewShopImage.setImageBitmap(bitmap);

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
                                imageViewShopImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                textViewNoImage.setVisibility(View.GONE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//                imageViewShopImage.setImageBitmap(bitmap);
//                uploadImage();
            } catch (Exception e) {
                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
            }
        } else if (requestCode == 1) {
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
    }

    @Override
    public void uploadImage() {
        if (filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
//            dialog.setTitle("Uploading...");
            dialog.setMessage("Please wait");
            dialog.setCancelable(false);
            dialog.show();
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
                        shopImageURL = String.valueOf(downloadUrl);
                        Log.d("ViewShopActivity", shopImageURL);
                        viewShopPresenter.updateShopDetails();
                    } else {
                        dialog.dismiss();
                        if (previousShopID.equals(getShopEmail()))
                            viewShopPresenter.updateShopDetails();
                        else
                            viewShopPresenter.deleteShop();
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
//                            ;
//
////                            SharedPrefManager.set_userProfilePic(String.valueOf(downloadUrl));
////                            SharedPrefManager.StoreToPref();
//
//                            Toast.makeText(getActivity(), "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            shopImageURL = String.valueOf(downloadUrl);
//                            Log.d("ViewShopActivity", shopImageURL);
////                            if (previousShopID.equals(getShopEmail()))
//                            viewShopPresenter.updateShopDetails();
////                            else
////                                viewShopPresenter.deleteShop();
////                            Picasso.with(MainActivity.this).load(downloadUrl).into(upload_image);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            dialog.dismiss();
//                            if (previousShopID.equals(getShopEmail()))
//                                viewShopPresenter.updateShopDetails();
//                            else
//                                viewShopPresenter.deleteShop();
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
//                            dialog.setMessage("Please wait");
//                        }
//                    });
        } else {
            viewShopPresenter.updateShopDetails();
        }
        if (bitmap == null)
//            if (previousShopID.equals(getShopEmail()))
            viewShopPresenter.updateShopDetails();
//            else
//                viewShopPresenter.deleteShop();
    }

    @OnClick(R.id.bt_save_details)
    @Override
    public void updateShopDetails() {
//        uploadImage();
//        if (bitmap == null)
        viewShopPresenter.validateShopDetails();
    }

    @Override
    public String getShopDocumentID() {
        return shopDocumentID;
    }

    @Override
    public void isShowShopSuccess(Boolean success) {
        if (success)
            showProgressDialog("Please wait");
        else
            dismissProgressDialog();
    }

    @Override
    public void setShopName(String shopName) {
        if (!TextUtils.isEmpty(shopName))
            editTextShopName.setText(shopName);
    }

    @Override
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
        editTextShopAddress.setText(shopAddress);
    }

    @Override
    public void setShopID(String shopID) {

    }

//    public void setShopLandmark(String shopLandmark) {
//        if (!TextUtils.isEmpty(shopLandmark))
//            editTextShopLandmark.setText(shopLandmark);
//    }

    @Override
    public void setShopEmail(String shopEmail) {
        if (!TextUtils.isEmpty(shopEmail))
            editTextShopEmail.setText(shopEmail);
    }

    @Override
    public void setRetailerID(String retailerID) {
        if (!TextUtils.isEmpty(retailerID)) {
        }
//            textViewRetailerID.setText(retailerID);
    }

    @Override
    public void setShopMobileNo1(String shopMobileNo1) {
        if (!TextUtils.isEmpty(shopMobileNo1))
            editTextShopMobile1.setText(shopMobileNo1);
    }

    @Override
    public void setShopMobileNo2(String shopMobileNo2) {
        if (!TextUtils.isEmpty(shopMobileNo2)) {
        }
//            textShopMobile2.setText("+91 " + shopMobileNo2);

    }

    @Override
    public void setShopDocumentID(String shopDocumentID) {
        this.shopDocumentID = shopDocumentID;
    }

    @Override
    public String getRetailerID() {
        return retailerID;
    }

    @Override
    public String getShopName() {
        return editTextShopName.getText().toString().trim();
    }

    @Override
    public String getShopAddress() {
//        return editTextShopAddress.getText().toString().trim();
        return editTextShopAddress.getText().toString().trim();
    }

    @Override
    public String getShopLandmark() {
//        return editTextShopLandmark.getText().toString().trim();
        return "";
    }

    @Override
    public String getShopContactNo() {
        return editTextShopMobile1.getText().toString().trim();
    }

    @Override
    public String getShopID() {
        return shopID;
    }

    @Override
    public String getShopEmail() {
        return editTextShopEmail.getText().toString().trim();
    }

    @Override
    public String getPreviousShopID() {
        return previousShopID;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setShopImageURL(String shopImageURL) {
        this.shopImageURL = shopImageURL;
    }

    @Override
    public String getShopImageURL() {
        return shopImageURL;
    }

    @Override
    public void setShopNameError() {
        editTextShopName.setError("Please enter shop name");
        editTextShopName.requestFocus();
    }

    @Override
    public void setShopLandmarkError() {
//        editTextShopLandmark.setError("Please enter Shop No. / Landmark");
//        editTextShopLandmark.requestFocus();
    }

    @Override
    public void setShopContactNoError(String msg) {
        editTextShopMobile1.setError(msg);
        editTextShopMobile1.requestFocus();
    }

    @Override
    public void setShopEmailError(String msg) {
        editTextShopEmail.setError(msg);
        editTextShopEmail.requestFocus();
    }

    @Override
    public void openRetailerHome() {
        Intent intent = new Intent(getActivity(), RetailerHomeActivity.class);
        intent.putExtra("navigation", "2");
        startActivity(intent);
    }

    @Override
    public void setShopAddressError(String msg) {
        editTextShopAddress.setError(msg);
        editTextShopAddress.requestFocus();
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
        if (!shopImage.equals("")) {
            Log.d("ViewShopActivity", shopImage);
            Picasso.get().load(shopImage).fit().into(imageViewShopImage);
        }
    }

    protected void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
