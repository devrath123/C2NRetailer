package com.example.c2n.add_product.presenter;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 06-06-2018.
 */

public class SetProductOfferFragment extends BaseFragment {

    @BindView(R.id.tv_product_mrp)
    TextView productMRP;
    @BindView(R.id.seekbar_discount)
    SeekBar discountSeekbar;
    @BindView(R.id.tv_discount_tag_value)
    TextView discountPercent;
    @BindView(R.id.btn_set_product_offer_done)
    AppCompatButton button_done;
    @BindView(R.id.layout_discount_tag)
    LinearLayout discountTagLayout;
    @BindView(R.id.tv_final_price)
    TextView finalPrice;
    @BindView(R.id.layout_set_discount)
    LinearLayout discountLayout;
    @BindView(R.id.layout_set_buy_get_scheme)
    LinearLayout buyGetSchemeLayout;
    @BindView(R.id.et_buy_quantity)
    EditText buyQuantity;
    @BindView(R.id.et_get_quantity)
    EditText getQuantity;
    Boolean applyButton = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_product_offer, container, false);
        ButterKnife.bind(this, view);

        productMRP.setText(productMRPText + "");
        finalPrice.setText("₹ " + productMRPText);

        if (offerApplicable == 1) {
            productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            setDiscountOfferLayout();
        } else if (offerApplicable == 2)
            setBuyGetSchemeLayout();

//        discountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.d("discount", "" + (Integer.parseInt(productMRP.getText().toString()) - (Integer.parseInt(productMRP.getText().toString()) * seekBar.getProgress() / 100)));
//                discountedPrice.setText("" + (Integer.parseInt(productMRP.getText().toString()) - Integer.parseInt((Integer.parseInt(productMRP.getText().toString()) * seekBar.getProgress()) / 100 + "")));
//                discountPercentage.setText("" + seekBar.getProgress());
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }


        return view;
    }
//
//    @OnClick(R.id.btn_set_product_offer_done)
//    public void offerDone() {
//        if (applyButton) {
//            if (offerApplicable == 1)
//                productOffer = "D=" + discountPercent.getText().toString() + "=" + productFinalPrice;
//            else if (offerApplicable == 2)
//                productOffer = "BG=" + buyQuantity.getText().toString() + "=" + getQuantity.getText().toString();
//
//            Log.d("product_offer", productOffer);
//            AddProductResultFragment addProductResultFragment = new AddProductResultFragment();
//            ((AddProductActivity) getActivity()).replaceFragment(addProductResultFragment, productName, false);
//        }
//    }

    private void setBuyGetSchemeLayout() {
        Log.d("buy_get--> ", "" + productMRPText);
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
        Log.d("discount--> ", "" + productMRPText);
        discountLayout.setVisibility(View.VISIBLE);
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
//                productFinalPrice = "" + (Integer.parseInt(productMRPText) - Integer.parseInt((Integer.parseInt(productMRPText) * progress) / 100 + ""));
                productFinalPrice = "" + (productMRPText - Integer.parseInt((productMRPText * progress) / 100 + ""));
                finalPrice.setText("₹ " + productFinalPrice);

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
