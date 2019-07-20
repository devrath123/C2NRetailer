package com.example.c2n.add_product.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.c2n.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul.singhal on 01-06-2018.
 */

public class SelectProductOfferApplicableBottomFragment extends BottomSheetDialogFragment {

    @BindView(R.id.et_percent_discount_bottom)
    EditText discountPercent;
    @BindView(R.id.tv_discount_perc_off_bottom)
    TextView textDiscountPercentOff;
    @BindView(R.id.tv_or_bottom)
    TextView textOr;
    @BindView(R.id.tv_buy_bottom)
    TextView textBuy;
    @BindView(R.id.et_buy_quantity_offer_bottom)
    EditText etBuyQuantity;
    @BindView(R.id.tv_get_bottom)
    TextView textGet;
    @BindView(R.id.et_get_quantity_offer_bottom)
    EditText etGetQuantity;
    @BindView(R.id.tv_free_bottom)
    TextView textFree;
    @BindView(R.id.btn_apply_scheme_bottom)
    AppCompatButton buttonApplyScheme;

    Boolean isDiscountOffer;
    Boolean isBuyGetOffer;
    String discountPercentage;
    String buyQuantity;
    String getQuantity;


    public SelectProductOfferApplicableBottomFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_select_product_scheme_alternate, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.discount_layout_bottom)
    public void applyDiscountOffer() {
        isDiscountOffer = true;
        isBuyGetOffer = false;
        discountOfferActive(true);
    }

    @OnClick(R.id.buy_get_layout_bottom)
    public void applyBuyGetOffer() {
        isBuyGetOffer = true;
        isDiscountOffer = false;
        buyGetOfferActive(true);
    }

    public void discountOfferActive(Boolean active) {

        if (active) {
            textDiscountPercentOff.setTextColor(getResources().getColor(R.color.themecolor));
            if (!TextUtils.isEmpty(etBuyQuantity.getText()))
                buyQuantity = etBuyQuantity.getText().toString();
            if (!TextUtils.isEmpty(etGetQuantity.getText()))
                getQuantity = etGetQuantity.getText().toString();
            buyGetOfferActive(false);

        } else {

        }
    }

    public void buyGetOfferActive(Boolean active) {

        if(active)
        {

        }
        else
        {
            textBuy.setTextColor(getResources().getColor(R.color.inactiveText));
            textGet.setTextColor(getResources().getColor(R.color.inactiveText));
            textFree.setTextColor(getResources().getColor(R.color.inactiveText));
        }
    }
}
