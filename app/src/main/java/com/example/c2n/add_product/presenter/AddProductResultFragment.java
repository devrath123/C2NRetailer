package com.example.c2n.add_product.presenter;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.add_product.di.AddProductDI;
import com.example.c2n.add_product.presenter.view.AddProductResultView;
import com.example.c2n.core.base.BaseFragment;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.retailer_shop_products_list.presenter.RetailerShopProductsActivity;
import com.example.c2n.retailerhome.presenter.RetailerHomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul.singhal on 11-06-2018.
 */

public class AddProductResultFragment extends BaseFragment implements AddProductResultView {

    @Inject
    AddProductPresenter addProductPresenter;
    @BindView(R.id.iv_success)
    ImageView imageSuccess;
    @BindView(R.id.tv_success)
    TextView textSuccess;
    @BindView(R.id.layout_add_description)
    LinearLayout layoutAddDescription;

    String profilePicUrl, productDescription, productDocumentID;
    Bitmap bitmapProductImage;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference reference;
    String shopEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_success, container, false);
        ButterKnife.bind(this, view);

        AddProductDI.getAddProductComponent().inject(this);
        addProductPresenter.bind(this, getContext());
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        storeProduct();

        return view;
    }

    private void storeProduct() {
        uploadImage();
    }

    private void uploadImage() {
        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
        shopEmail = addProductActivity.shopDataModel.getShopID();
        bitmapProductImage = addProductActivity.productImageBitmap;
        this.filePath = addProductActivity.filePath;
        if (filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Uploading...");
            dialog.setCancelable(false);
            dialog.show();
            StorageReference ref = reference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();;

//                            SharedPrefManager.set_userProfilePic(String.valueOf(downloadUrl));
//                            SharedPrefManager.StoreToPref();

                            Toast.makeText(getActivity(), "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            profilePicUrl = String.valueOf(downloadUrl);
                            addProductPresenter.addProduct(shopEmail);
//                            Picasso.with(MainActivity.this).load(downloadUrl).into(upload_image);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
//                                editProfilePresenter.checkProfileFields();
                            addProductPresenter.addProduct(shopEmail);
                            Toast.makeText(getActivity(), "Image upload failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("uploadImage_" +
                                    "Failed", e.getMessage());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    @OnClick(R.id.btn_product_details_back)
    public void backToHome() {

//        AddProductActivity addProductActivity = (AddProductActivity) getActivity();

        Intent intent = new Intent(getActivity(), RetailerHomeActivity.class);
//        intent.putExtra("categorySelected", getProductCategory());
//        intent.putExtra("shopDataModel", addProductActivity.shopDataModel);
        startActivity(intent);

    }

    @OnClick(R.id.layout_add_description)
    public void addDescriptipon() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_description);
        dialog.setTitle("Add Description");

        final EditText edit_description = dialog.findViewById(R.id.et_enter_description);

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        Button des_btn = dialog.findViewById(R.id.bt_dialog_add_description);
        des_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "" + edit_description.getText().toString());
                productDescription = edit_description.getText().toString().trim();
//                textViewDescription.setText(edit_description.getText().toString());
                addProductPresenter.updateProductDescription();
                dialog.dismiss();
//                layoutAddDescription.setVisibility(View.GONE);
//                Snackbar.make(getView(), "Product Details Updated", Snackbar.LENGTH_LONG).setAction("View", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d("View_action", "updated");
//                        AddProductFragment addProductFragment = new AddProductFragment();
//                        Bundle args = new Bundle();
////                args.putString("productImage",filePath.getPath());
//                        args.putString("productImage", productImagePath);
//
//                        addProductFragment.setArguments(args);
//                        ((AddProductActivity) getActivity()).replaceFragment(addProductFragment, "", true);
//                    }
//                }).show();
            }
        });

    }

    @Override
    public void updateProductDescriptionSuccess() {
        layoutAddDescription.setVisibility(View.GONE);
        Snackbar.make(getView(), "Product Details Updated", Snackbar.LENGTH_LONG).setAction("View", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("View_action", "updated");
//                AddProductFragment addProductFragment = new AddProductFragment();
//                Bundle args = new Bundle();
//                args.putString("productImage",filePath.getPath());
//                args.putString("productImage", productImagePath);
                AddProductActivity addProductActivity = (AddProductActivity) getActivity();

                Intent intent = new Intent(getActivity(), RetailerShopProductsActivity.class);
                intent.putExtra("categorySelected", getProductCategory());
                intent.putExtra("shopEmail", getShopEmail());
                intent.putExtra("shopDataModel", addProductActivity.shopDataModel);
                intent.putExtra("productDataModel", addProductActivity.productDataModel);
                startActivity(intent);

                getActivity().finish();

//                addProductFragment.setArguments(args);
//                ((AddProductActivity) getActivity()).replaceFragment(addProductFragment, "", true);
            }
        }).show();
    }

    @Override
    public void setProductDocumentID(String productDocumentID) {
        this.productDocumentID = productDocumentID;
    }

    @Override
    public String getShopEmail() {
        return shopEmail;
    }

//    @Override
//    public String getOfferProducts() {
//        return productDocumentID;
//    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public String getProductCategory() {
        return productCategory;
    }

    @Override
    public String getProductID() {
        if (productDocumentID != null)
            return productDocumentID;
        return "";
    }

    @Override
    public double getProductMRP() {
        return productMRPText;
    }

    @Override
    public String getProductPhotoUrl() {
        return profilePicUrl;
    }

    @Override
    public OfferDataModel getProductOffer() {
        return productOffer;
    }

    @Override
    public String getProductDescription() {
        return productDescription;
    }

    @Override
    public void showAddProductProgress(Boolean progress, String msg) {
        if (progress)
            showProgressDialog(msg);
        else
            dismissProgressDialog();
    }

    @Override
    public void isAddProductSuccess(Boolean success) {
        if (progressDialog.isShowing())
            dismissProgressDialog();
        if (success) {
            AddProductActivity addProductActivity = (AddProductActivity) getActivity();
            addProductActivity.status = true;
            imageSuccess.setVisibility(View.VISIBLE);
            textSuccess.setVisibility(View.VISIBLE);
            layoutAddDescription.setVisibility(View.VISIBLE);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.success_anim);
            imageSuccess.startAnimation(animation1);
            Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.success_text_anim);
            textSuccess.startAnimation(animation2);
            layoutAddDescription.startAnimation(animation2);
        } else {
            textSuccess.setText("Please Retry...");
            textSuccess.setVisibility(View.VISIBLE);
        }
    }
}

