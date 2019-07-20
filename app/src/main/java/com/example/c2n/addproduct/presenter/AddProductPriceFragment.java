package com.example.c2n.addproduct.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.add_product.presenter.adapter.OfferCardsAdapter;
import com.example.c2n.addproduct.di.AddProductDI;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.master_list.presenter.MasterListActivity;
import com.example.c2n.offer_cards_list.di.OffersListDI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddProductPriceFragment extends Fragment implements AddProductPriceView, OfferCardsAdapter.OfferCardAdapterInterface {

    private static final String TAG = AddProductPriceFragment.class.getSimpleName();
    private InfiniteScrollAdapter infiniteAdapter;
    private InputMethodManager imm;
    private ProgressDialog progressDialog;
    private AddProductActivity addProductActivity;
    FirebaseStorage storage;
    StorageReference reference;

    private OfferDataModel appliedOfferDataModel;

    @BindView(R.id.et_product_mrp)
    EditText editTextProductMRP;

    @BindView(R.id.add_scheme_layout)
    RelativeLayout relativeLayoutAddSchemeLayout;

    @BindView(R.id.offer_picker)
    DiscreteScrollView discreteScrollViewOfferPicker;

    @BindView(R.id.btn_set_product_price_discount_done)
    AppCompatButton appCompatButtonSetProductPrice;

    @BindView(R.id.layout_view_applied_offercard)
    LinearLayout linearLayoutAppliedOfferCard;

    @BindView(R.id.tv_product_discounted_price)
    TextView discountedPriceText;

    @BindView(R.id.tv_applied_offercard_discount)
    TextView textViewAppliedOffer;

    @BindView(R.id.product_applied_offercard)
    CardView appliedCard;

    @BindView(R.id.rl_no_offers)
    RelativeLayout relativeLayoutNoOffers;

    @Inject
    AddProductPricePresenter addProductPricePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product_price, container, false);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        ButterKnife.bind(this, view);
        AddProductDI.getAddProductComponent().inject(this);
        OffersListDI.getOfferComponent().inject(this);
        addProductPricePresenter.bind(getContext(), this);

        relativeLayoutNoOffers.setVisibility(View.GONE);

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        addProductActivity = (AddProductActivity) getActivity();

        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
        addProductActivity.setActionBarTitle("Enter Price");

//        if (addProductActivity.productMRP != 0) {
//            editTextProductMRP.setText(String.valueOf(addProductActivity.productMRP));
//            editTextProductMRP.setKeyListener(null);
//            relativeLayoutAddSchemeLayout.setVisibility(View.VISIBLE);
//            linearLayoutAppliedOfferCard.setVisibility(View.GONE);
//            appCompatButtonSetProductPrice.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        }

        editTextProductMRP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    relativeLayoutAddSchemeLayout.setVisibility(View.VISIBLE);
                    linearLayoutAppliedOfferCard.setVisibility(View.GONE);
                    appCompatButtonSetProductPrice.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    relativeLayoutAddSchemeLayout.setVisibility(View.GONE);
                    appCompatButtonSetProductPrice.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        appCompatButtonSetProductPrice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Apply Offer", Toast.LENGTH_SHORT).show();
//            }
//        });

        appCompatButtonSetProductPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductActivity.productMRP = Double.parseDouble(editTextProductMRP.getText().toString().trim());
//                if (addProductActivity.productImageBitmap != null) {
//                    uploadImage();
//                }
                if (!addProductActivity.fromMasterListFlag) {
                    uploadImage();
                } else {
                    addProductPricePresenter.addProductOffersExisting(addProductActivity.productID);
                }
            }
        });

        return view;
    }


    @OnClick(R.id.add_scheme_layout)
    void getAllOffers() {
        addProductPricePresenter.getAllOffers();
    }

    @Override
    public void showAllOffers(List<OfferDataModel> offerDataModels) {
        if (offerDataModels.size() == 0) {
            relativeLayoutNoOffers.setVisibility(View.VISIBLE);
        }
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        relativeLayoutAddSchemeLayout.setVisibility(View.GONE);
        discreteScrollViewOfferPicker.setVisibility(View.VISIBLE);
        discreteScrollViewOfferPicker.setOrientation(DSVOrientation.HORIZONTAL);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new OfferCardsAdapter(offerDataModels, getContext(), this));
        discreteScrollViewOfferPicker.setAdapter(infiniteAdapter);
        discreteScrollViewOfferPicker.setItemTransitionTimeMillis(250);
        discreteScrollViewOfferPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        infiniteAdapter.notifyDataSetChanged();
    }

    @Override
    public void offerClicked(OfferDataModel offerDataModel) {
        appliedOfferDataModel = offerDataModel;
        addProductActivity.offerDataModel = offerDataModel;
        discreteScrollViewOfferPicker.setVisibility(View.GONE);
        linearLayoutAppliedOfferCard.setVisibility(View.VISIBLE);
        editTextProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        discountedPriceText.setText(String.valueOf(round((Double.parseDouble(editTextProductMRP.getText().toString().trim())
                - Double.parseDouble(((Double.parseDouble(editTextProductMRP.getText().toString().trim())
                * offerDataModel.getOfferDiscount()) / 100 + ""))), 2)));
        textViewAppliedOffer.setText(offerDataModel.getOfferDiscount() + "% OFF");
        setOfferApplied(offerDataModel.getOfferDiscount());
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void setOfferApplied(int discount) {
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

    }

    private void setOfferCardBackground(int offerRange) {
        switch (offerRange) {
            case 1: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer1_background_gradient);
                return;
            }
            case 2: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer2_background_gradient);
                return;
            }
            case 3: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer3_background_gradient);
                return;
            }
            case 4: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer4_background_gradient);
                return;
            }
            case 5: {
                setOfferCardTextColor(2);
                appliedCard.setBackgroundResource(R.drawable.offer5_background_gradient);
                return;
            }
            case 6: {
                setOfferCardTextColor(2);
                appliedCard.setBackgroundResource(R.drawable.offer6_background_gradient);
                return;
            }
        }
    }

    private void setOfferCardTextColor(int color) {
        if (color == 1) {
            textViewAppliedOffer.setTextColor(Color.BLACK);
        } else if (color == 2) {
            textViewAppliedOffer.setTextColor(Color.WHITE);
        }
    }

    @OnClick(R.id.iv_remove_offercard)
    public void removeAppliedOfferCard() {
        addProductActivity.offerDataModel = null;
        linearLayoutAppliedOfferCard.setVisibility(View.GONE);
        appliedOfferDataModel = null;
//        changeButtonText(false);
        editTextProductMRP.setPaintFlags(editTextProductMRP.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        relativeLayoutAddSchemeLayout.setVisibility(View.VISIBLE);
        discreteScrollViewOfferPicker.setVisibility(View.GONE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void showLoadingOffersProgress(Boolean progress, String msg) {
        if (progress)
            showProgressDialog(msg);
        else
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

    public void uploadImage() {
        if (addProductActivity.filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setMessage("Please wait");
            dialog.show();
            StorageReference ref = reference.child("images/" + UUID.randomUUID().toString());
            UploadTask uploadTask = ref.putFile(addProductActivity.filePath);
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
                        addProductActivity.productImageURL = String.valueOf(downloadUrl);
                        Log.d("ViewShopActivity", addProductActivity.productImageURL);
                        addProduct();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Image upload failed" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("uploadImage_" +
                                "Failed", task.getException().getMessage());
                    }
                }
            });
//            ref.putFile(addProductActivity.filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();
//
////                            SharedPrefManager.set_userProfilePic(String.valueOf(downloadUrl));
////                            SharedPrefManager.StoreToPref();
//
//                            Toast.makeText(getActivity(), "Uploaded..." + downloadUrl, Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            addProductActivity.productImageURL = String.valueOf(downloadUrl);
//                            Log.d("ViewShopActivity", addProductActivity.productImageURL);
//                            addProduct();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            dialog.dismiss();
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
        }
    }

    public void addProduct() {
        addProductPricePresenter.addProductMaster();
    }

    @Override
    public MasterProductDataModel getMasterProduct() {
        MasterProductDataModel masterProductDataModel = new MasterProductDataModel();
        masterProductDataModel.setProductName(addProductActivity.productName);
        masterProductDataModel.setProductCategory(addProductActivity.productCategory);
        masterProductDataModel.setProductImageURL(addProductActivity.productImageURL);
        masterProductDataModel.setProductDescription(addProductActivity.productDescription);
        return masterProductDataModel;
    }

    @Override
    public ProductDataModel getProductDataModel() {
        ProductDataModel productDataModel = new ProductDataModel();
        productDataModel.setProductID(addProductActivity.productID);
        productDataModel.setProductName(addProductActivity.productName);
        productDataModel.setProductCategory(addProductActivity.productCategory);
        productDataModel.setProductImageURL(addProductActivity.productImageURL);
        productDataModel.setProductMRP(addProductActivity.productMRP);
        productDataModel.setProductDescription(addProductActivity.productDescription);
        productDataModel.setProductStockStatus(true);
        productDataModel.setProductOfferStatus(false);
        if (addProductActivity.offerDataModel != null) {
            Log.d(TAG, "getProductDataModel OfferID : " + addProductActivity.offerDataModel.getOfferID());
            addProductActivity.shopDataModel.setOfferDataModel(addProductActivity.offerDataModel);
        } else {
            addProductActivity.shopDataModel.setOfferDataModel(null);
        }
        List<ShopDataModel> shopDataModels = new ArrayList<>();
        shopDataModels.add(addProductActivity.shopDataModel);
        productDataModel.setShopDataModels(shopDataModels);
        return productDataModel;
    }

    @Override
    public void hanndleAddProductOffers() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Product added successfully");
        builder.setCancelable(false)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(getContext(), MasterListActivity.class);
//                        intent.putExtra("shopDataModel", addProductActivity.shopDataModel);
//                        startActivity(intent);
//                        getActivity().finish();
//                        getActivity().finish();
                        Intent intent = new Intent(getContext(), MasterListActivity.class);
                        intent.putExtra("shopDataModel", addProductActivity.shopDataModel);
                        startActivity(intent);
                        getActivity().finish();
                        addProductActivity.closeActivity();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void handleAppProductExisting(Boolean aBoolean) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (aBoolean) {
            builder.setMessage("Product is already added in shop");

        } else {
            builder.setMessage("Product added successfully");
        }
        builder.setCancelable(false)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getContext(), MasterListActivity.class);
                        intent.putExtra("shopDataModel", addProductActivity.shopDataModel);
                        startActivity(intent);
                        getActivity().finish();
                        addProductActivity.closeActivity();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
