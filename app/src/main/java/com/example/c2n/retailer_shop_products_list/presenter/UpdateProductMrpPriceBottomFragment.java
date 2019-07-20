package com.example.c2n.retailer_shop_products_list.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.c2n.R;
import com.example.c2n.core.model.ProductDataModel;
import com.example.c2n.view_product.presenter.ViewProductActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul.singhal on 25-06-2018.
 */

public class UpdateProductMrpPriceBottomFragment extends BottomSheetDialogFragment {

    ProductDataModel productDataModel;
    String shopEmail;
    String productCategory;
    Boolean isButtonActive;
    public int offerApplied;
    String updatedProductScheme;
    String discountPercent;
    Intent passIntent;

    @BindView(R.id.et_bottom_update_product_mrp)
    EditText updatedProductMrp;
    @BindView(R.id.btn_bottom_update_product_mrp)
    AppCompatButton buttonUpdateProductMrp;

    public UpdateProductMrpPriceBottomFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_update_product_mrp, container, false);
        ButterKnife.bind(this, view);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            try {
                productDataModel = (ProductDataModel) intent.getSerializableExtra("productDataModel");
                Log.d("bottom_product_data", productDataModel.toString());
            } catch (Exception e) {
                Log.d("product_view_exception", e.getMessage());
            }
        }
        shopEmail = this.getArguments().getString("shopEmail");
        productCategory = this.getArguments().getString("categorySelected");
        updatedProductMrp.setText(productDataModel.getProductMRP());
        doneButton(true);

        if (productDataModel.getProductScheme() != null && !productDataModel.getProductScheme().equals("")) {
            String[] productScheme = productDataModel.getProductScheme().split("=");
            Log.d("scheme", productScheme.toString());
            Log.d("scheme", productScheme[0] + productScheme[1] + productScheme[2]);
            if (productScheme[0].equals("D")) {
                discountPercent = productScheme[1];
                offerApplied = 1;
            } else if (productScheme[0].equals("BG"))
                offerApplied = 2;
        }


        updatedProductMrp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (start == 0 && count == 0) {
                    doneButton(false);
                } else {
                    passIntent = new Intent(getActivity(), ViewProductActivity.class);
                    if (offerApplied == 1) {

                        productDataModel.setProductMRP(updatedProductMrp.getText().toString());
                        productDataModel.setProductScheme("D=" + discountPercent + "=" + (Integer.parseInt(updatedProductMrp.getText().toString()) - Integer.parseInt((Integer.parseInt(updatedProductMrp.getText().toString()) * Integer.parseInt(discountPercent)) / 100 + "")));
                        passIntent.putExtra("productDataModel", productDataModel);
                        passIntent.putExtra("categorySelected", productCategory);
                        passIntent.putExtra("shopEmail", shopEmail);

                        doneButton(true);
                        return;
//                    } else if (offerApplied == 2) {
//                        productDataModel.setProductMRP(updatedProductMrp.getText().toString());
//                        passIntent.putExtra("productDataModel", productDataModel);
//                        passIntent.putExtra("categorySelected", productCategory);
//                        passIntent.putExtra("shopEmail", shopEmail);
//
//                        doneButton(true);
//                        return;
                    } else {
                        productDataModel.setProductMRP(updatedProductMrp.getText().toString());
                        passIntent.putExtra("productDataModel", productDataModel);
                        passIntent.putExtra("categorySelected", productCategory);
                        passIntent.putExtra("shopEmail", shopEmail);

                        doneButton(true);
                        return;
                    }
//                    doneButton(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }

    @OnClick(R.id.btn_bottom_update_product_mrp)
    public void updateProductMrp() {
        if (isButtonActive) {
            dismiss();
            startActivity(passIntent);
        }
    }

    private void doneButton(boolean buttonActive) {
        if (buttonActive) {
            buttonUpdateProductMrp.setBackgroundResource(R.drawable.active_button_background);
            isButtonActive = true;
        } else {
            buttonUpdateProductMrp.setBackgroundResource(R.drawable.inactive_button_background);
            isButtonActive = false;
        }
    }

}
