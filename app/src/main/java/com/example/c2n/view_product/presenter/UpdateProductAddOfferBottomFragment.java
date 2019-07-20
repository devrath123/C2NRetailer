package com.example.c2n.view_product.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.c2n.R;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.edit_product_offer.presenter.UpdateProductOfferActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul.singhal on 25-06-2018.
 */

public class UpdateProductAddOfferBottomFragment extends BottomSheetDialogFragment {

    ProductDataModel productDataModel;
    @BindView(R.id.bt_update_product_add_discount_offer)
    Button buttonAddDiscountOffer;
    @BindView(R.id.bt_update_product_add_buy_get_scheme)
    Button bttonAddBuyGetOffer;

    Intent intent;
    String shopEmail;
    String productCategory;

    public UpdateProductAddOfferBottomFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_update_product_add_offer, container, false);
        ButterKnife.bind(this, view);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            try {
                productDataModel = (ProductDataModel) intent.getSerializableExtra("productDataModel");
                Log.d("bottom_product_data", productDataModel.toString());
            } catch (Exception e) {
                Log.d("product_view_exception", e.getMessage());
            }
            shopEmail = this.getArguments().getString("shopEmail");
            productCategory = this.getArguments().getString("categorySelected");
        }

        return view;
    }

    @OnClick(R.id.bt_update_product_add_discount_offer)
    public void addDiscountOffer() {

        intent = new Intent(getActivity(), UpdateProductOfferActivity.class);
        intent.putExtra("productDataModel", productDataModel);
        intent.putExtra("shopEmail", shopEmail);
        intent.putExtra("categorySelected", productCategory);
        intent.putExtra("offer_background_gradient", "1");
        startActivity(intent);

//        UpdateProductEditDiscountOfferBottomFragment updateProductEditDiscountOfferBottomFragment = new UpdateProductEditDiscountOfferBottomFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("productDataModel", productDataModel);
//        updateProductEditDiscountOfferBottomFragment.setArguments(args);
//        updateProductEditDiscountOfferBottomFragment.setCancelable(true);
//        updateProductEditDiscountOfferBottomFragment.show(getFragmentManager(), updateProductEditDiscountOfferBottomFragment.getTag());
//        dismiss();
    }

    @OnClick(R.id.bt_update_product_add_buy_get_scheme)
    public void addBuyGetOffer() {
        intent = new Intent(getActivity(), UpdateProductOfferActivity.class);
        intent.putExtra("productDataModel", productDataModel);
        intent.putExtra("shopEmail", shopEmail);
        intent.putExtra("categorySelected", productCategory);
        intent.putExtra("offer_background_gradient", "2");
        startActivity(intent);
        dismiss();

//        UpdateProductEditBuyGetOfferBottomFragment updateProductEditBuyGetOfferBottomFragment = new UpdateProductEditBuyGetOfferBottomFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("productDataModel", productDataModel);
//        updateProductEditBuyGetOfferBottomFragment.setArguments(args);
//        updateProductEditBuyGetOfferBottomFragment.setCancelable(true);
//        updateProductEditBuyGetOfferBottomFragment.show(getFragmentManager(), updateProductEditBuyGetOfferBottomFragment.getTag());
//        dismiss();
    }
}
