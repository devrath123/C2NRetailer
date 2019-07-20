package com.example.c2n.viewshopdetails.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.models.ShopDataModel;

public class ViewShopActivity extends BaseActivity {
    //implements ViewShopView {

//    @BindView(R.id.shopImage)
//    ImageView imageViewShopImage;
//
//    @BindView(R.id.tv_shop_name)
//    EditText editTextShopName;
//
//    //    @BindView(R.id.et_shop_address)
//    @BindView(R.id.et_shop_addresss)
//    TextView editTextShopAddress;
//
////    @BindView(R.id.tv_shop_address)
////    EditText editTextShopAddress;
//
////    @BindView(R.id.tv_shop_landmark)
////    EditText editTextShopLandmark;
//
//    @BindView(R.id.tv_shop_mobile_no)
//    EditText editTextShopMobile1;
//
////    @BindView(R.id.tv_shop_mobile_no_2)
////    TextView editTextShopMobile2;
//
//    @BindView(R.id.tv_shop_email)
//    EditText editTextShopEmail;
//
//    private Bitmap bitmap;
//    private int PICK_IMAGE_REQUEST = 71;
//    private Uri filePath;
//    private String shopDocumentID;
//    private ShopDataModel shopDataModel;
//    private String shopImageURL = "";
//    private String shopAddress;
//
//    FirebaseStorage storage;
//    StorageReference reference;
//
//    @Inject
//    ViewShopPresenter viewShopPresenter;
//    private double latitude, longitude;
//    private String shopImage;

    public ShopDataModel shopDataModel;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_shop;
    }

    @Override
    protected void initActivity() {

        Intent intent = getIntent();
        if (intent != null) {
            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
        }
        ViewShoppFragment fragment = new ViewShoppFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.frame, fragment, "viewShop");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void updateAddress(String addresss, double latitude, double longitude) {
        ViewShoppFragment viewShoppFragment = (ViewShoppFragment) getSupportFragmentManager().findFragmentByTag("viewShop");
        viewShoppFragment.setShopAddress(addresss);
        viewShoppFragment.setLatitude(latitude);
        viewShoppFragment.setLongitude(longitude);

    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
//        ButterKnife.bind(this);
//        ViewShopDI.getViewShopComponent().inject(this);
//        viewShopPresenter.bind(this, this);
//        SharedPrefManager.Init(this);
//
//
//        storage = FirebaseStorage.getInstance();
//        reference = storage.getReference();
//
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
//            Log.d("ViewShopActivity__", shopDataModel.toString());
////            setShopDocumentID(shopDataModel.getShopDocumentID());
//            setShopName(shopDataModel.getShopName());
//            setShopAddress(shopDataModel.getShopAddress());
////            setShopLandmark(shopDataModel.getShopLandmark());
//            setShopMobileNo1(shopDataModel.getShopCellNo());
//            setShopEmail(shopDataModel.getShopID());
//            setShopImage(shopDataModel.getShopImageURL());
//        }
//        getSupportActionBar().setTitle(shopDataModel.getShopName());
//
////        Bundle bundle = getIntent().getExtras();
////        if (bundle != null)
////            shopDocumentID = bundle.getString("shopDocumentId");
////        loadShop();
//    }

    //    @OnClick(R.id.imageViewSelectImage)
//    public void pickImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//
//    //    @OnClick(R.id.bt_location)
//    @Override
//    public void getGEOLocation() {
////        Intent intent = new LocationPickerActivity.Builder()
////                .withGooglePlacesEnabled()
//////                .withLocation(41.4036299, 2.1743558)
////                .withGeolocApiKey("AIzaSyCeGNexl9P4dVBj0T5CbgPRcaZDbHSI2MU")
//////                .withGeolocApiKey("AIzaSyAJEflASbXRU1z7YWF2X9fflCoxE7upaG0")
//////                .withSearchZone("es_ES")
////                .shouldReturnOkOnBackPressed()
////                .withStreetHidden()
////                .withCityHidden()
////                .withZipCodeHidden()
////                .withSatelliteViewHidden()
////                .build(this);
////
////        startActivityForResult(intent, 1);
//    }
//
//    @OnClick(R.id.bt_location)
//    public void updateShopAddress() {
//        ViewShopFragment fragment = new ViewShopFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.frame, fragment);
//        fragmentTransaction.commitAllowingStateLoss();
////        Toast.makeText(ViewShopActivity.this, "Update Location", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent CustomerSingleProductRepository) {
//        super.onActivityResult(requestCode, resultCode, CustomerSingleProductRepository);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && CustomerSingleProductRepository != null && CustomerSingleProductRepository.getData() != null) {
//            filePath = CustomerSingleProductRepository.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageViewShopImage.setImageBitmap(bitmap);
////                uploadImage();
//            } catch (Exception e) {
//                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
//            }
//        } else if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                double latitude = CustomerSingleProductRepository.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
//                Log.d("LATITUDE****", String.valueOf(latitude));
//                this.latitude = latitude;
//                double longitude = CustomerSingleProductRepository.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
//                Log.d("LONGITUDE****", String.valueOf(longitude));
//                this.longitude = longitude;
//                String address = CustomerSingleProductRepository.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
//                Log.d("ADDRESS****", String.valueOf(address));
//                String postalcode = CustomerSingleProductRepository.getStringExtra(LocationPickerActivity.ZIPCODE);
//                Log.d("POSTALCODE****", String.valueOf(postalcode));
////                Bundle bundle = CustomerSingleProductRepository.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
////                Log.d("BUNDLE TEXT****", bundle.getString("test"));
//                Address fullAddress = CustomerSingleProductRepository.getParcelableExtra(LocationPickerActivity.ADDRESS);
//                if (fullAddress != null) {
//                    Log.d("FULL ADDRESS****", fullAddress.toString());
//                    setShopAddress(fullAddress.getAddressLine(0));
//                }
//            }
//            if (resultCode == RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
//        }
//    }
//
//    @Override
//    public void uploadImage() {
//        if (filePath != null) {
//            final ProgressDialog dialog = new ProgressDialog(this);
//            dialog.setTitle("Uploading...");
//            dialog.setCancelable(false);
//            dialog.show();
//            StorageReference ref = reference.child("images/" + UUID.randomUUID().toString());
//            ref.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//
////                            SharedPrefManager.set_userProfilePic(String.valueOf(downloadUrl));
////                            SharedPrefManager.StoreToPref();
//
//                            Toast.makeText(ViewShopActivity.this, "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            shopImageURL = String.valueOf(downloadUrl);
//                            Log.d("ViewShopActivity", shopImageURL);
//                            viewShopPresenter.updateShopDetails();
////                            Picasso.with(MainActivity.this).load(downloadUrl).into(upload_image);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            dialog.dismiss();
//                            viewShopPresenter.updateShopDetails();
//                            Toast.makeText(ViewShopActivity.this, "Image upload failed" + e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("uploadImage_" +
//                                    "Failed", e.getMessage());
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                    .getTotalByteCount());
//                            dialog.setMessage("Uploaded " + (int) progress + "%");
//                        }
//                    });
//        }
//        if (bitmap == null)
//            viewShopPresenter.updateShopDetails();
//    }
//
//    @OnClick(R.id.bt_save_details)
//    @Override
//    public void updateShopDetails() {
////        uploadImage();
////        if (bitmap == null)
//        viewShopPresenter.validateShopDetails();
//    }
//
//    @Override
//    public String getShopDocumentID() {
//        return shopDocumentID;
//    }
//
//    @Override
//    public void isShowShopSuccess(Boolean success) {
//        if (success)
//            showProgressDialog("Please wait");
//        else
//            dismissProgressDialog();
//    }
//
//    @Override
//    public void setShopName(String shopName) {
//        if (!TextUtils.isEmpty(shopName))
//            editTextShopName.setText(shopName);
//    }
//
//    @Override
//    public void setShopAddress(String shopAddress) {
//        this.shopAddress = shopAddress;
//    }
//
////    public void setShopLandmark(String shopLandmark) {
////        if (!TextUtils.isEmpty(shopLandmark))
////            editTextShopLandmark.setText(shopLandmark);
////    }
//
//    @Override
//    public void setShopEmail(String shopEmail) {
//        if (!TextUtils.isEmpty(shopEmail))
//            editTextShopEmail.setText(shopEmail);
//    }
//
//    @Override
//    public void setRetailerID(String retailerID) {
//        if (!TextUtils.isEmpty(retailerID)) {
//        }
////            textViewRetailerID.setText(retailerID);
//    }
//
//    @Override
//    public void setShopMobileNo1(String shopMobileNo1) {
//        if (!TextUtils.isEmpty(shopMobileNo1))
//            editTextShopMobile1.setText(shopMobileNo1);
//    }
//
//    @Override
//    public void setShopMobileNo2(String shopMobileNo2) {
//        if (!TextUtils.isEmpty(shopMobileNo2)) {
//        }
////            textShopMobile2.setText("+91 " + shopMobileNo2);
//
//    }
//
//    @Override
//    public void setShopDocumentID(String shopDocumentID) {
//        this.shopDocumentID = shopDocumentID;
//    }
//
//    @Override
//    public String getShopName() {
//        return editTextShopName.getText().toString().trim();
//    }
//
//    @Override
//    public String getShopAddress() {
////        return editTextShopAddress.getText().toString().trim();
//        return shopAddress;
//    }
//
//    @Override
//    public String getShopLandmark() {
////        return editTextShopLandmark.getText().toString().trim();
//        return "";
//    }
//
//    @Override
//    public String getShopContactNo() {
//        return editTextShopMobile1.getText().toString().trim();
//    }
//
//    @Override
//    public String getShopEmail() {
//        return editTextShopEmail.getText().toString().trim();
//    }
//
//    @Override
//    public double getLatitude() {
//        return latitude;
//    }
//
//    @Override
//    public double getLongitude() {
//        return longitude;
//    }
//
//    @Override
//    public String getShopImageURL() {
//        return shopImageURL;
//    }
//
//    @Override
//    public void setShopNameError() {
//        editTextShopName.setError("Please enter shop name");
//        editTextShopName.requestFocus();
//    }
//
//    @Override
//    public void setShopLandmarkError() {
////        editTextShopLandmark.setError("Please enter Shop No. / Landmark");
////        editTextShopLandmark.requestFocus();
//    }
//
//    @Override
//    public void setShopContactNoError(String msg) {
//        editTextShopMobile1.setError(msg);
//        editTextShopMobile1.requestFocus();
//    }
//
//    @Override
//    public void setShopEmailError(String msg) {
//        editTextShopEmail.setError(msg);
//        editTextShopEmail.requestFocus();
//    }
//
//    @Override
//    public void openRetailerHome() {
//        Intent intent = new Intent(ViewShopActivity.this, RetailerHomeActivity.class);
//        intent.putExtra("navigation", "2");
//        startActivity(intent);
//    }
//
//    public void setShopImage(String shopImage) {
//        this.shopImage = shopImage;
//        if (!shopImage.equals("")) {
//            Log.d("ViewShopActivity", shopImage);
//            Picasso.with(this).load(shopImage).into(imageViewShopImage);
//        }
//    }
}
