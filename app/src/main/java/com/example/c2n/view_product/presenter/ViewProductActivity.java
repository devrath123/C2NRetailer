package com.example.c2n.view_product.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.FileUtil;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.view_product.di.ViewUpdateProductDI;
import com.example.c2n.view_product.presenter.view.ViewUpdateProductView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
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
import java.text.ParseException;
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

public class ViewProductActivity extends BaseActivity implements ViewUpdateProductView {

    private final String TAG = "ViewProductActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    public ProductDataModel productDataModel;
    @BindView(R.id.iv_view_product_image)
    ImageView productImage;

    @BindView(R.id.et_product_mrp_price)
    EditText productMRP;

    @BindView(R.id.et_edit_product_name)
    TextView productName;

    @BindView(R.id.layout_discounted_price)
    LinearLayout discountedLayout;

    @BindView(R.id.et_discounted_product_price)
    TextView discountedPrice;

    @BindView(R.id.cv_editproduct_applied_offercard)
    CardView appliedOfferCard;

    @BindView(R.id.tv_editproduct_offercard_discount_perc)
    TextView offercardDiscount;

    @BindView(R.id.tv_editproduct_offercard_startdate)
    TextView offercardStartDate;

    @BindView(R.id.tv_editproduct_offercard_enddate)
    TextView offercardEndDate;

    @BindView(R.id.tv_editproduct_offercard_applicable_days)
    TextView offercardApplicableDays;

    @BindView(R.id.tv_editproduct_offer_apply)
    TextView applyOfferText;

    @BindView(R.id.tv_editproduct_discount_perc_text)
    TextView offercardDiscountPercText;

    @BindView(R.id.tv_editproduct_discount_off_text)
    TextView offercardDiscountOFFText;

    @BindView(R.id.tv_editproduct_offer_date_to_text)
    TextView offercardToText;

    @BindView(R.id.tv_remove_offercard)
    TextView removeOffer;

    @BindView(R.id.tv_editproduct_offercardname)
    TextView offercardName;

    @BindView(R.id.et_product_description)
    TextView productDescription;

    private int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private String productImageUrl = "";
    Bitmap bitmap;
    FirebaseStorage storage;
    StorageReference reference;
    String productDocumentId;
    String productCategory;
    String shopEmail;
    Boolean selectedOfferCard;
    Boolean isOfferActiveToday = false;
    Boolean isOfferCardApplicable = false;
    @Inject
    ViewUpdateProductPresenter viewUpdateProductPresenter;
    private ShopDataModel shopDataModel;
    OfferDataModel appliedOferDataModel;

    ProductDataModel updatedProductDataModel;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate;
    Boolean isOfferCardApplied = false;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_product;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        ViewUpdateProductDI.getViewUpdateProductComponent().inject(this);
        viewUpdateProductPresenter.bind(this, this);

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productCategory = bundle.getString("categorySelected");
//            shopEmail = bundle.getString("shopEmail");

            Log.d("product_category", productCategory);
//            Log.d("shop_email", shopEmail);
        }
        Intent intent = getIntent();
        if (intent != null) {
            try {
                productDataModel = (ProductDataModel) intent.getSerializableExtra("productDataModel");
                shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
                Log.d("product_data_view", productDataModel.toString() + " - " + shopDataModel.toString());
                setProductDetails();
            } catch (Exception e) {
                Log.d("product_view_exception", e.getMessage());
            }
        }

        if (bundle != null) {
            if (bundle.getString("selected") != null) {
                openAddOfferBottomSheet();
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
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

    private void setProductDetails() {

        getSupportActionBar().setTitle(productDataModel.getProductCategory());
        String productImage = productDataModel.getProductImageURL();
        double productMRP = productDataModel.getProductMRP();

//        String productOfferId = productDataModel.getProductOffer().getOfferID();
        String productName = productDataModel.getProductName();
        String productDescription = productDataModel.getProductDescription();

        this.productName.setText(productName);
        Picasso.get().load(productImage).into(this.productImage);
        this.productMRP.setText(productMRP + "");
        this.productDescription.setText(productDescription);

        if (productDataModel.getProductOffer() != null) {
//            if (productDataModel.getProductOffer().getOfferID() != null && !productDataModel.getProductOffer().getOfferID().equals(""))
//                viewUpdateProductPresenter.getAppliedOffer();

            isLoadOfferSuccess(productDataModel.getProductOffer());
//            String[] productSchemeArray = productScheme.split("=");
//            Log.d("scheme", productScheme.toString());
//            Log.d("scheme", productSchemeArray[0] + productSchemeArray[1] + productSchemeArray[2]);
//            if (productSchemeArray[0].equals("D")) {
//                discountedLayout.setVisibility(View.VISIBLE);
//                this.discountedPrice.setText("₹ " + productSchemeArray[2]);
//                offerCardText.setText(productSchemeArray[1] + "%" + " OFF");
//            } else if (productSchemeArray[0].equals("BG")) {
//                offerCardText.setText("Buy  " + productSchemeArray[1] + "Get  " + productSchemeArray[2]);
//            }
        } else {
//            offerCardText.setText("Add Offer");
        }
    }


    @OnClick(R.id.iv_edit_product_image)
    void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ViewProductActivity.this);
        builder.setTitle("Add Photo!");
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    if (result)
                    Dexter.withActivity(ViewProductActivity.this)
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
                    Toast.makeText(ViewProductActivity.this, "Take Photo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask = "Choose from Library";
//                    if (result)
                    changeProductImage();
                    Toast.makeText(ViewProductActivity.this, "Choose from Library", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
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
                }
                return true;
            }
        });
        builder.show();
    }

    public void changeProductImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void clickPhoto() {
//        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(ViewProductActivity.this.getPackageManager()) != null) {
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
                ".jpg",   // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

//    @OnClick(R.id.et_product_mrp_price)
//    public void updateProductMrp() {
//        UpdateProductMrpPriceBottomFragment updateProductMrpPriceBottomFragment = new UpdateProductMrpPriceBottomFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("productDataModel", productDataModel);
//        args.putString("shopEmail", shopEmail);
//        args.putString("categorySelected", productCategory);
//        updateProductMrpPriceBottomFragment.setArguments(args);
//        updateProductMrpPriceBottomFragment.setCancelable(true);
//        updateProductMrpPriceBottomFragment.show(getSupportFragmentManager(), updateProductMrpPriceBottomFragment.getTag());
//    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            filePath = CustomerSingleProductRepository.getData();
            try {
                filePath = Uri.parse(mCurrentPhotoPath);
                new Compressor(this)
                        .compressToFileAsFlowable(FileUtil.from(this, filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
//                                compressedImage = file;
//                                setCompressedImage();
                                filePath = Uri.fromFile(file);
                                productImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                Toast.makeText(ViewProductActivity.this, "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(ViewProductActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                bitmap = MediaStore.Images.Media.getBitmap(ViewProductActivity.this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
//                filePath = Uri.parse(mCurrentPhotoPath);
//                productImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                new Compressor(this)
                        .compressToFileAsFlowable(FileUtil.from(this, filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
//                                compressedImage = file;
//                                setCompressedImage();
                                filePath = Uri.fromFile(file);
                                productImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                Toast.makeText(ViewProductActivity.this, "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(ViewProductActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                productImage.setImageBitmap(bitmap);
//                uploadImage();
            } catch (Exception e) {
                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
            }
        }
    }

    @Override
    public void uploadImage() {
        if (filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading...");
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
                        Toast.makeText(ViewProductActivity.this, "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        productImageUrl = String.valueOf(downloadUrl);
                        Log.d("AddProductActivity", productImageUrl);
                        viewUpdateProductPresenter.updateProductDetails();
                    } else {
                        dialog.dismiss();
//                            viewUpdateProductPresenter.getProductDocumentId(productDataModel);
                        viewUpdateProductPresenter.updateProductDetails();
                        Toast.makeText(ViewProductActivity.this, "Image upload failed" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
//                            Toast.makeText(ViewProductActivity.this, "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            productImageUrl = String.valueOf(downloadUrl);
//                            Log.d("AddProductActivity", productImageUrl);
////                            viewUpdateProductPresenter.getProductDocumentId(productDataModel);
//                            viewUpdateProductPresenter.updateProductDetails();
////                            Picasso.with(MainActivity.this).load(downloadUrl).into(upload_image);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            dialog.dismiss();
////                            viewUpdateProductPresenter.getProductDocumentId(productDataModel);
//                            viewUpdateProductPresenter.updateProductDetails();
//                            Toast.makeText(ViewProductActivity.this, "Image upload failed" + e.getMessage(), Toast.LENGTH_LONG).show();
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
        }
        if (bitmap == null)
//            viewUpdateProductPresenter.getProductDocumentId(productDataModel);
            viewUpdateProductPresenter.updateProductDetails();
    }


    @Override
    public void isProductUpdationSuccess(Boolean success) {
        if (success) {
//            Intent intent = new Intent(ViewProductActivity.this, RetailerShopProductsActivity.class);
//            intent.putExtra("categorySelected", productCategory);
//            intent.putExtra("shopDataModel", shopDataModel);
            finish();
        } else
            showToast("Please try again...");
    }

    @Override
    public void getDocumentIdSuccess(String documentId) {
        if (documentId != null && !documentId.equals("")) {
            Log.d("product_document_id", documentId.toString());
            productDocumentId = documentId;
            viewUpdateProductPresenter.updateProductDetails();
        } else
            showToast("Please Retry...");
    }

    @Override
    public void setProductMrp(String price) {

    }

    @Override
    public void setProductDiscountedPrice(String discountedPrice) {

    }

    @Override
    public void setProductOffer(String offer) {

    }

    @Override
    public void setProductNameError() {
        productName.setError("Please enter Product Name");
        productName.requestFocus();
    }

    @Override
    public void setProductMrpError() {
        productName.setError("Please enter Product M.R.P");
        productName.requestFocus();
    }

    @Override
    public String getShopEmail() {
        if (shopDataModel.getShopEmail() != null)
            return shopDataModel.getShopEmail();
        return "";
    }

    @Override
    public String getProductCategory() {
        return productCategory;
    }

    @Override
    public String getProductDocumentId() {
        if (productDataModel.getProductID() != null)
            return productDataModel.getProductID();
        return "";
    }

    @Override
    public String getProductImageURL() {
        if (productImageUrl.equals("") || productImageUrl == null)
            return productDataModel.getProductImageURL();
        else
            return productImageUrl;
    }

    @Override
    public String getProductName() {
        return productName.getText().toString().trim();
    }


    @Override
    public double getProductMrp() {
        return Double.parseDouble(productMRP.getText().toString());
    }

    @Override
    public String getProductDiscountedPrice() {
        return null;
    }

    @Override
    public String getProductOfferId() {
        if (productDataModel.getProductOffer().getOfferID() != null)
            return productDataModel.getProductOffer().getOfferID();
        else
            return "";
    }

    @Override
    public String getShopID() {
        return shopDataModel.getShopID();
    }

    @Override
    public OfferDataModel getProductOffer() {
        if (appliedOferDataModel != null)
            return appliedOferDataModel;
        if (productDataModel.getProductOffer() != null)
            return productDataModel.getProductOffer();
        return null;
    }

//    @Override
//    public Boolean getProductOfferStatus() {
//        return productDataModel.getProductOfferStatus();
//    }

    @Override
    public String getProductDescription() {
        if (!TextUtils.isEmpty(productDescription.getText())) {
//            productDataModel.setProductDescription(productDescription.getText().toString().trim());
            return productDescription.getText().toString().trim();
        } else
            return "";
    }

    @Override
    public void setProductDataModel(ProductDataModel productDataModel) {
        this.productDataModel = productDataModel;
    }

    @Override
    public void removeOfferSuccess() {
        productDataModel.setProductOffer(null);
        Toast.makeText(this, "Offer Removed.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ViewProductActivity.class);
        intent.putExtra("shopDataModel", shopDataModel);
        intent.putExtra("productDataModel", productDataModel);
        intent.putExtra("categorySelected", productCategory);
        startActivity(intent);
        finish();
    }

//
//    @OnClick(R.id.iv_remove_product_offer)
//    public void removeOffer() {
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//
//        // Setting Dialog Title
//        alertDialog.setTitle(productDataModel.getProductName());
//
//        alertDialog.setMessage("Do you want to remove this Offer?");
//
//
//        // Setting Icon to Dialog
////        alertDialog.setIcon(R.drawable.delete);
//
//        // Setting Positive "Yes" Button
//        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // Write your code here to invoke YES event
////          removeOfferButton.setVisibility(View.INVISIBLE);
//                productDataModel.setProductScheme("");
//                discountedLayout.setVisibility(View.GONE);
//                offerCardText.setText("Add Offer");
//                removeOfferButton.setVisibility(View.INVISIBLE);
//                dialog.cancel();
//            }
//        });
//
//        // Setting Negative "NO" Button
//        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // Write your code here to invoke NO event
//                dialog.cancel();
//            }
//        });
//
//        // Showing Alert Message
//        alertDialog.show();
//
//    }
//
//    @OnClick(R.id.card_edit_product_\offer)
//    public void addOrEditProductOffer() {
//        if (offerCardText.getText().toString().equals("Add Offer")) {
//            openAddOfferBottomSheet();
//        } else if (!productDataModel.getProductScheme().equals("") && productDataModel.getProductScheme() != null) {
//
//            Intent intent = new Intent(this, UpdateProductOfferActivity.class);
//            intent.putExtra("productDataModel", productDataModel);
//            intent.putExtra("categorySelected", productCategory);
//            intent.putExtra("shopEmail", shopEmail);
//            startActivity(intent);
//
////            String[] productSchemeArray = productDataModel.getProductScheme().split("=");
////            Log.d("scheme", productSchemeArray[0] + productSchemeArray[1] + productSchemeArray[2]);
////            if (productSchemeArray[0].equals("D")) {
////                openEditDiscountOfferBottomSheet(productSchemeArray[1], productSchemeArray[2]);
//////                this.discountedPrice.setText("₹ " + productSchemeArray[2]);
//////                offerCardText.setText(productSchemeArray[1] + "%" + " OFF");
////            } else if (productSchemeArray[0].equals("BG")) {
////                openEditBuyGetOfferBottomSheet(productSchemeArray[1], productSchemeArray[2]);
//////                offerCardText.setText("Buy        " + productSchemeArray[1] + "Get        " + productSchemeArray[2]);
////            }
//        }
//    }

    private void openEditBuyGetOfferBottomSheet(String s, String s1) {
        UpdateProductEditBuyGetOfferBottomFragment updateProductEditBuyGetOfferBottomFragment = new UpdateProductEditBuyGetOfferBottomFragment();
        Bundle args = new Bundle();
        args.putSerializable("productDataModel", productDataModel);
        args.putString("shopEmail", shopEmail);
        args.putString("categorySelected", productCategory);
        updateProductEditBuyGetOfferBottomFragment.setArguments(args);
        updateProductEditBuyGetOfferBottomFragment.setCancelable(true);
        updateProductEditBuyGetOfferBottomFragment.show(getSupportFragmentManager(), updateProductEditBuyGetOfferBottomFragment.getTag());
    }

    private void openEditDiscountOfferBottomSheet(String s, String s1) {
        UpdateProductEditDiscountOfferBottomFragment updateProductEditDiscountOfferBottomFragment = new UpdateProductEditDiscountOfferBottomFragment();
        Bundle args = new Bundle();
        args.putSerializable("productDataModel", productDataModel);
        args.putString("shopEmail", shopEmail);
        args.putString("categorySelected", productCategory);
        updateProductEditDiscountOfferBottomFragment.setArguments(args);
        updateProductEditDiscountOfferBottomFragment.setCancelable(true);
        updateProductEditDiscountOfferBottomFragment.show(getSupportFragmentManager(), updateProductEditDiscountOfferBottomFragment.getTag());

    }

    private void openAddOfferBottomSheet() {

        UpdateProductAddOfferBottomFragment updateProductAddOfferBottomFragment = new UpdateProductAddOfferBottomFragment();
        Bundle args = new Bundle();
        args.putSerializable("productDataModel", productDataModel);
        args.putSerializable("shopDataModel", shopDataModel);
        args.putString("categorySelected", productCategory);
        updateProductAddOfferBottomFragment.setArguments(args);
        updateProductAddOfferBottomFragment.setCancelable(true);
        updateProductAddOfferBottomFragment.show(getSupportFragmentManager(), updateProductAddOfferBottomFragment.getTag());


    }

    @OnClick(R.id.btn_save_product_details)
    @Override
    public void updateProductDetails() {
        if (ConnectivityReceiver.isConnected()) {
            viewUpdateProductPresenter.validateProductDetails();
        } else {
            Toast.makeText(ViewProductActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showUpdateProductProgress(Boolean progress) {
        if (progress) {
            showProgressDialog("Updating...");
        } else
            dismissProgressDialog();
    }

    @Override
    public void showLoadingOfferProgress(Boolean progress) {
        if (progress) {
            showProgressDialog("Removing Offer...");
        } else
            dismissProgressDialog();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void isLoadOfferSuccess(OfferDataModel offerDataModel) {
        if (offerDataModel != null) {
            appliedOferDataModel = offerDataModel;
            discountedLayout.setVisibility(View.VISIBLE);
//            productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            removeOffer.setVisibility(View.VISIBLE);
            offercardName.setText(offerDataModel.getOfferName());
            this.discountedPrice.setText("" + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * offerDataModel.getOfferDiscount()) / 100, 2));
            appliedOfferCard.setVisibility(View.VISIBLE);
            offercardDiscount.setText(offerDataModel.getOfferDiscount() + "");
            offercardStartDate.setText(format.format(offerDataModel.getOfferStartDate()));
            offercardEndDate.setText(format.format(offerDataModel.getOfferEndDate()));


            Boolean[] days = new Boolean[]{offerDataModel.isSun(), offerDataModel.isMon(), offerDataModel.isTue(), offerDataModel.isWed(), offerDataModel.isThu(), offerDataModel.isFri(), offerDataModel.isSat()};
            String offerDays = "";
            for (int i = 0; i <= 6; i++) {
                if (days[i] == true) {
                    offerDays = offerDays + setOfferDay(i);
                }
            }
            offercardApplicableDays.setText(offerDays.substring(0, offerDays.length() - 2));
//        holder.offerDay.setText(offerDays);


            int discount = offerDataModel.getOfferDiscount();
            if (discount > 0 && discount <= 10)
                setOfferCardBackground(1);
            else {
                if (discount > 10 && discount <= 20)
                    setOfferCardBackground(2);
                else {
                    if (discount > 20 && discount <= 30)
                        setOfferCardBackground(3);
                    else {
                        if (discount > 30 && discount <= 50)
                            setOfferCardBackground(4);
                        else {
                            if (discount > 50 && discount <= 75)
                                setOfferCardBackground(5);
                            else {
                                if (discount > 75 && discount <= 100)
                                    setOfferCardBackground(6);
                            }
                        }
                    }
                }
            }

            try {
                todayDate = format.parse(format.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int today = new Date().getDay();
            Log.d(TAG, "Date : " + today);

            if (productDataModel.getProductOffer().isOfferStatus())
                isOfferCardApplicable = true;
            if (isOfferCardActive(today, offerDataModel)) {
                if (todayDate != null && offerDataModel.getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(offerDataModel.getOfferEndDate()) >= 0) {
//                    applyOfferText.setText("Active Today");
//                    applyOfferText.setTextColor(getResources().getColor(R.color.activeoffer));
                    isOfferActiveToday = true;
                } else
                    isOfferActiveToday = false;
                //                    applyOfferText.setText("Not Active");
            } else
                isOfferActiveToday = false;
//                applyOfferText.setText("Not Active");
            applyOfferText.setClickable(false);
            if (isOfferCardApplicable) {
                if (isOfferActiveToday) {
                    productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    applyOfferText.setText("Active Today");
                    applyOfferText.setTextColor(getResources().getColor(R.color.activeoffer));
                } else {
                    discountedPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    applyOfferText.setText("Not Active Today");
                }

            } else {
                discountedPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                appliedOfferCard.getBackground().setAlpha(70);
                offercardName.setTextColor(getResources().getColor(R.color.inactiveOffer));
                offercardApplicableDays.setTextColor(getResources().getColor(R.color.inactiveOffer));
                offercardDiscount.setTextColor(getResources().getColor(R.color.inactiveOffer));
                offercardDiscountPercText.setTextColor(getResources().getColor(R.color.inactiveOffer));
                offercardDiscountOFFText.setTextColor(getResources().getColor(R.color.inactiveOffer));
                offercardToText.setTextColor(getResources().getColor(R.color.inactiveOffer));
                offercardStartDate.setTextColor(getResources().getColor(R.color.inactiveOffer));
                offercardEndDate.setTextColor(getResources().getColor(R.color.inactiveOffer));

                applyOfferText.setText("Not Active");

            }
        } else {
            discountedLayout.setVisibility(View.GONE);
            removeOffer.setVisibility(View.VISIBLE);
        }

    }


    private void setOfferCardBackground(int offerRange) {
        switch (offerRange) {
            case 1: {
                setOfferCardTextColor(1);
                appliedOfferCard.setBackgroundResource(R.drawable.offer1_background_gradient);
                return;
            }
            case 2: {
                setOfferCardTextColor(1);
                appliedOfferCard.setBackgroundResource(R.drawable.offer2_background_gradient);
                return;
            }
            case 3: {
                setOfferCardTextColor(1);
                appliedOfferCard.setBackgroundResource(R.drawable.offer3_background_gradient);
                return;
            }
            case 4: {
                setOfferCardTextColor(1);
                appliedOfferCard.setBackgroundResource(R.drawable.offer4_background_gradient);
                return;
            }
            case 5: {
                setOfferCardTextColor(2);
                appliedOfferCard.setBackgroundResource(R.drawable.offer5_background_gradient);
                return;
            }
            case 6: {
                setOfferCardTextColor(2);
                appliedOfferCard.setBackgroundResource(R.drawable.offer6_background_gradient);
                return;
            }
        }

    }

    public void setOfferCardTextColor(int color) {
        if (color == 1) {
            offercardName.setTextColor(Color.BLACK);
            offercardDiscount.setTextColor(Color.BLACK);
            offercardStartDate.setTextColor(Color.BLACK);
            offercardEndDate.setTextColor(Color.BLACK);
            offercardApplicableDays.setTextColor(Color.BLACK);
            offercardDiscountOFFText.setTextColor(Color.BLACK);
            offercardDiscountPercText.setTextColor(Color.BLACK);
            offercardToText.setTextColor(Color.BLACK);
        } else if (color == 2) {
            offercardName.setTextColor(Color.WHITE);
            offercardDiscount.setTextColor(Color.WHITE);
            offercardStartDate.setTextColor(Color.WHITE);
            offercardEndDate.setTextColor(Color.WHITE);
            offercardApplicableDays.setTextColor(Color.WHITE);
            offercardDiscountOFFText.setTextColor(Color.WHITE);
            offercardDiscountPercText.setTextColor(Color.WHITE);
            offercardToText.setTextColor(Color.WHITE);
        }
    }


    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
            case 1:
                if (offerCard.isMon())
                    return true;
            case 2:
                if (offerCard.isTue())
                    return true;
            case 3:
                if (offerCard.isWed())
                    return true;
            case 4:
                if (offerCard.isThu())
                    return true;
            case 5:
                if (offerCard.isFri())
                    return true;
            case 6:
                if (offerCard.isSat())
                    return true;
        }
        return false;
    }

    public String setOfferDay(int i) {
        switch (i) {
            case 0:
                return "Sun, ";

            case 1:
                return "Mon, ";

            case 2:
                return "Tue, ";

            case 3:
                return "Wed, ";

            case 4:
                return "Thu, ";

            case 5:
                return "Fri, ";

            case 6:
                return "Sat, ";
            default:
                return null;
        }
    }

    @OnClick(R.id.tv_editproduct_offer_apply)
    public void applyOffer() {
        AppyOfferCardBottomFragment appyOfferCardBottomFragment = new AppyOfferCardBottomFragment();
        Bundle args = new Bundle();
        args.putSerializable("productDataModel", productDataModel);
        args.putString("shopEmail", shopDataModel.getShopEmail());
        args.putString("categorySelected", productCategory);
//        args.putString("productImage", imgPath);
        appyOfferCardBottomFragment.setArguments(args);
        appyOfferCardBottomFragment.setCancelable(true);
        appyOfferCardBottomFragment.show(getSupportFragmentManager(), appyOfferCardBottomFragment.getTag());
    }

    @OnClick(R.id.tv_remove_offercard)
    public void remoceOffer() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Remove Offer Card?");
        alertDialog.setMessage("Do you want to remove this Offer permanently?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                viewUpdateProductPresenter.removeOfferCard();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    //
    @Override
    protected void onPause() {
        super.onPause();
        isOfferCardApplied = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (isOfferCardApplied)
//            initActivity();
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("updatedProductDataModel", productDataModel);
        // setResult(RESULT_OK);
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null CustomerSingleProductRepository.
        super.finish();
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
