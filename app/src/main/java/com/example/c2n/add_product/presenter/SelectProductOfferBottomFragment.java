package com.example.c2n.add_product.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c2n.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.c2n.core.base.BaseFragment.offerApplicable;
import static com.example.c2n.core.base.BaseFragment.productName;

/**
 * Created by vipul.singhal on 03-06-2018.
 */

public class SelectProductOfferBottomFragment extends BottomSheetDialogFragment {

    public SelectProductOfferBottomFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_select_product_scheme, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

   @OnClick(R.id.bt_apply_discount_offer)
    public void applyDiscount()
   {
       SetProductOfferFragment setProductOfferFragment = new SetProductOfferFragment();
       Bundle args = new Bundle();
       args.putInt("offer_background_gradient",1);
       setProductOfferFragment.setArguments(args);
       offerApplicable=1;
       ((AddProductActivity) getActivity()).replaceFragment(setProductOfferFragment, productName,false);
       dismiss();
   }

   @OnClick(R.id.bt_apply_buy_get_scheme)
    public void applyBuyGetScheme()
   {
       SetProductOfferFragment setProductOfferFragment  = new SetProductOfferFragment();
       Bundle args = new Bundle();
       args.putInt("offer_background_gradient",2);
       setProductOfferFragment.setArguments(args);
       offerApplicable=2;
       dismiss();
       ((AddProductActivity) getActivity()).replaceFragment(setProductOfferFragment, productName,false);
   }
}
