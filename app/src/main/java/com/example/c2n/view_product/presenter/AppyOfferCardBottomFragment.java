package com.example.c2n.view_product.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.add_product.presenter.adapter.OfferCardsAdapter;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.make_offer_card.presenter.MakeOfferCardActivity;
import com.example.c2n.offer_cards_list.di.OffersListDI;
import com.example.c2n.retailerhome.di.RetailerHomeDI;
import com.example.c2n.view_product.di.ViewUpdateProductDI;
import com.example.c2n.view_product.presenter.view.ApplyOfferCardBottomFragmentView;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul.singhal on 09-07-2018.
 */

public class AppyOfferCardBottomFragment extends BottomSheetDialogFragment implements ApplyOfferCardBottomFragmentView, OfferCardsAdapter.OfferCardAdapterInterface {

    @BindView(R.id.tv_editproduct_applied_offercard_discount)
    TextView textViewAppliedOffer;

    @BindView(R.id.layout_editproduct_applied_offercard)
    LinearLayout layoutAppliedOfferCard;

    @BindView(R.id.cv_editproduct_applied_offercard)
    CardView appliedCard;

    @BindView(R.id.edit_product_offer_picker)
    DiscreteScrollView discreteScrollViewOfferPicker;

    @BindView(R.id.btn_apply_offercard)
    AppCompatButton buttonApply;

    @Inject
    ApplyOfferCardBottomPresenter applyOfferCardBottomPresenter;
    private InfiniteScrollAdapter infiniteAdapter;
    OfferDataModel offerListDataModel;
    Boolean isButtonActive = false;
    ProductDataModel productDataModel;
    ShopDataModel shopDataModel;
    //    String shopEmail;
    String productCategory;
    public ProgressDialog progressDialog;
    String productDocumentId;


    public AppyOfferCardBottomFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_apply_offercard, container, false);
        ButterKnife.bind(this, view);
        RetailerHomeDI.getRetailerHomeComponent().inject(this);
        OffersListDI.getOfferComponent().inject(this);
        ViewUpdateProductDI.getViewUpdateProductComponent().inject(this);
        applyOfferCardBottomPresenter.bind(this, getContext());
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            try {
                productDataModel = (ProductDataModel) intent.getSerializableExtra("productDataModel");
                shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
                Log.d("bottom_product_data", productDataModel.toString());
            } catch (Exception e) {
                Log.d("product_view_exception", e.getMessage());
            }
        }
//        shopEmail = this.getArguments().getString("shopEmail");
        productCategory = this.getArguments().getString("categorySelected");
        loadOfferCards();
        return view;
    }

    public void loadOfferCards() {
        discreteScrollViewOfferPicker.setVisibility(View.VISIBLE);
        applyOfferCardBottomPresenter.loadOfferCards();
    }

    @Override
    public void showOfferCards(List<OfferDataModel> offerListDataModels) {
        if (offerListDataModels == null || offerListDataModels.size() == 0) {
            Intent intent = new Intent(getActivity(), MakeOfferCardActivity.class);
            intent.putExtra("addProductMaster", true);
            startActivity(intent);
            return;
        }

        Log.d("AddProductPriceFragment", "" + offerListDataModels.size());
        discreteScrollViewOfferPicker.setOrientation(DSVOrientation.HORIZONTAL);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new OfferCardsAdapter(offerListDataModels, getContext(), this));
        discreteScrollViewOfferPicker.setAdapter(infiniteAdapter);
        discreteScrollViewOfferPicker.setItemTransitionTimeMillis(250);
        discreteScrollViewOfferPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        infiniteAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void updateProductDetails() {
//
//    }


    @Override
    public void showUpdateProductProgress(Boolean progress) {
        if (progress) {
            showProgressDialog("Updating...");
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

//    @Override
//    public void showLoadingOfferProgress(Boolean progress) {
//        if (progress) {
//            showProgressDialog("Loading...");
//        } else
//            dismissProgressDialog();
//    }

    @Override
    public void isProductUpdationSuccess(Boolean success) {
        if (success) {
            productDataModel.setProductOffer(offerListDataModel);
            Intent intent = new Intent(getContext(), ViewProductActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            intent.putExtra("productDataModel", productDataModel);
            intent.putExtra("categorySelected", productCategory);
            startActivity(intent);
            Toast.makeText(getContext(), "Product Updated.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
//            getActivity().finish();
        } else
            Toast.makeText(getContext(), "Please try again...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDocumentIdSuccess(String documentId) {
        if (documentId != null && !documentId.equals("")) {
            Log.d("product_document_id", documentId.toString());
            productDocumentId = documentId;
            applyOfferCardBottomPresenter.updateProductOffer();
        } else
            Toast.makeText(getContext(), "Please Retry...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getProductDocumentId() {
        if (productDataModel != null)
            return productDataModel.getProductID();
        return productDocumentId;
    }

    @Override
    public void showProgress(Boolean show) {
        if (show) {
            showProgressDialog("Loading Offers...");
        } else
            dismissProgressDialog();
    }

    @Override
    public String getShopEmail() {
        return shopDataModel.getShopEmail();
    }

    @Override
    public ProductDataModel getProductDataModel() {
        return productDataModel;
    }

    @Override
    public ShopDataModel getShopDataModel() {
        return shopDataModel;
    }

    @Override
    public OfferDataModel getOfferListDataModel() {
        return offerListDataModel;
    }

    @Override
    public String getShopID() {
        return shopDataModel.getShopID();
    }

    @Override
    public String getProductCategory() {
        return productCategory;
    }

    @Override
    public OfferDataModel getProductOffer() {
        if (offerListDataModel != null)
            return offerListDataModel;
        return null;
    }

    @Override
    public void offerClicked(OfferDataModel offerListDataModel) {
        this.offerListDataModel = offerListDataModel;
        Log.d("applied_offer", this.offerListDataModel.toString());
        buttonApply.setBackgroundResource(R.drawable.active_button_background);
        isButtonActive = true;
        layoutAppliedOfferCard.setVisibility(View.VISIBLE);
        textViewAppliedOffer.setText(offerListDataModel.getOfferDiscount() + "% OFF");
        setOfferApplied((offerListDataModel.getOfferDiscount()));
        Log.d("offer_id", offerListDataModel.getOfferID());
    }

    @OnClick(R.id.btn_apply_offercard)
    public void applyOfferCard() {
        applyOfferCardBottomPresenter.updateProductOffer();
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

//    private void changeButtonText(Boolean isOffer) {
//        if (isOffer)
//
//        else
//            buttonDone.setText("No Offer");
//    }
//
//    @OnClick(R.id.iv_remove_offercard)
//    public void removeAppliedOfferCard() {
//        layoutAppliedOfferCard.setVisibility(View.GONE);
//        offerListDataModel = null;
//        changeButtonText(false);
//        addSchemeLayout.setVisibility(View.VISIBLE);
//        discreteScrollViewOfferPicker.setVisibility(View.GONE);
//    }

}
