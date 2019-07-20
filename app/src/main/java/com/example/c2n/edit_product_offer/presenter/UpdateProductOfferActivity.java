package com.example.c2n.edit_product_offer.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.model.ProductDataModel;
import com.example.c2n.view_product.presenter.ViewProductActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProductOfferActivity extends BaseActivity {

    String productCategory;
    String shopEmail;
    String offerApplicable = "";
    ProductDataModel productDataModel;

    @BindView(R.id.tv_update_product_mrp)
    TextView productMRP;
    @BindView(R.id.seekbar_update_discount)
    SeekBar discountSeekbar;
    @BindView(R.id.tv_update_discount_tag_value)
    TextView discountPercent;
    @BindView(R.id.btn_update_product_offer_done)
    AppCompatButton button_done;
    @BindView(R.id.layout_update_discount_tag)
    LinearLayout discountTagLayout;
    @BindView(R.id.tv_update_productfinal_price)
    TextView finalPrice;
    @BindView(R.id.layout_set_update_product_discount)
    LinearLayout discountLayout;
    @BindView(R.id.layout_update_set_buy_get_scheme)
    LinearLayout buyGetSchemeLayout;
    @BindView(R.id.et_update_buy_quantity)
    EditText buyQuantity;
    @BindView(R.id.et_update_get_quantity)
    EditText getQuantity;
    Boolean applyButton = true;

    String productDiscountedPrice;
    String oldDiscountPercent;
    String oldOffer = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_update_product_offer;
    }

    @Override
    protected void initActivity() {

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productCategory = bundle.getString("categorySelected");
            shopEmail = bundle.getString("shopEmail");
            try {
                offerApplicable = bundle.getString("offer_background_gradient");
            } catch (Exception e) {
                Log.d("ofer_exception", e.getMessage());
                offerApplicable = "";
            }

            Log.d("product_category", productCategory);
            Log.d("shop_email", shopEmail);
        }
        Intent intent = getIntent();
        if (intent != null)

        {
            try {
                productDataModel = (ProductDataModel) intent.getSerializableExtra("productDataModel");
                Log.d("product_data_view", productDataModel.toString());
            } catch (Exception e) {
                Log.d("product_view_exception", e.getMessage());
            }
        }


        productMRP.setText(productDataModel.getProductMRP());

        if (productDataModel.getProductScheme() == null || productDataModel.getProductScheme().equals("")) {
//            productMRP.setText(productDataModel.getProductMRP());
            finalPrice.setText("₹ " + productDataModel.getProductMRP());

            if (offerApplicable.equals("1")) {
                productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                setDiscountOfferLayout();
            } else if (offerApplicable.equals("2"))
                setBuyGetSchemeLayout();

        } else {
            String[] productScheme = productDataModel.getProductScheme().split("=");
            Log.d("scheme", productScheme.toString());
//            Log.d("scheme", productScheme[0] + productScheme[1] + productScheme[2]);
            if (productScheme[0].equals("D")) {
                oldOffer = "1";
//                productMRP.setText(productDataModel.getProductMRP());
                productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                finalPrice.setText("₹ " + productScheme[2]);
                oldDiscountPercent = productScheme[1];
                setDiscountOfferLayout();

            } else if (productScheme[0].equals("BG")) {
                oldOffer = "2";
                buyQuantity.setText(productScheme[1]);
                getQuantity.setText(productScheme[2]);
                setBuyGetSchemeLayout();
            }
        }


//        productMRP.setText(productDataModel.getProductMRP());
//        finalPrice.setText("₹ " + productDataModel.getProductMRP());
//
//        if (offerApplicable.equals("1")) {
//            productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            setDiscountOfferLayout();
//        } else if (offerApplicable.equals("2"))
//            setBuyGetSchemeLayout();
    }


    @OnClick(R.id.btn_update_product_offer_done)
    public void offerDone() {
        if (applyButton) {
            if ((offerApplicable != null && offerApplicable.equals("1")) || oldOffer.equals("1"))
                productDataModel.setProductScheme("D=" + discountPercent.getText().toString() + "=" + productDiscountedPrice);
            else if ((offerApplicable != null && offerApplicable.equals("2")) || oldOffer.equals("2"))
                productDataModel.setProductScheme("BG=" + buyQuantity.getText().toString() + "=" + getQuantity.getText().toString());

            Intent passIntent = new Intent(this, ViewProductActivity.class);
            passIntent.putExtra("productDataModel", productDataModel);
            passIntent.putExtra("categorySelected", productCategory);
            passIntent.putExtra("shopEmail", shopEmail);
            startActivity(passIntent);
            finish();
        }
    }

    private void setBuyGetSchemeLayout() {
//        Log.d("buy_get--> ", "" + productMRPText);
        buyGetSchemeLayout.setVisibility(View.VISIBLE);

        buyQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    applyButton(false);
                } else
                    applyButton(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    applyButton(false);
                } else
                    applyButton(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void applyButton(boolean active) {
        if (active) {
            applyButton = true;
            button_done.setTextColor(getResources().getColor(R.color.themecolor));
        } else {
            applyButton = false;
            button_done.setTextColor(Color.WHITE);
        }
    }

    private void setDiscountOfferLayout() {
//        Log.d("discount--> ", "" + productMRPText);
        discountLayout.setVisibility(View.VISIBLE);
        if (oldDiscountPercent != null)
            discountSeekbar.setProgress(Integer.valueOf(oldDiscountPercent));
        discountPercent.setText(oldDiscountPercent);
        discountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("progress: ", progress + "");
                discountPercent.setText(progress + "");
                int marginInDp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, (float) (10 + progress * 2.6), getResources()
                                .getDisplayMetrics());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(marginInDp, 0, 0, 0);
                Log.d("progress value: ", (float) (10 + progress * 2.6) + "");
                discountTagLayout.setLayoutParams(lp);
                button_done.setTextColor(getResources().getColor(R.color.themecolor));
                productDiscountedPrice = "" + (Integer.parseInt(productDataModel.getProductMRP()) - Integer.parseInt((Integer.parseInt(productDataModel.getProductMRP()) * progress) / 100 + ""));
                finalPrice.setText("₹ " + productDiscountedPrice);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


}
