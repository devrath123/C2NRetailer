package com.example.c2n.customer_cart.presenter.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.c2n.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartShopViewHolder extends ParentViewHolder {

    @BindView(R.id.cart_shop_name)
    TextView shopname;

    @BindView(R.id.cart_shop_image)
    ImageView shopimage;

    @BindView(R.id.cart_shop_bill)
    TextView bill;

    @BindView(R.id.cart_shop_distance)
    TextView shopdistance;

    @BindView(R.id.cart_remove_shop)
    ImageView removeshop;

    @BindView(R.id.cart_your_products_title)
    TextView textViewProductCount;

    @BindView(R.id.imageview_map)
    ImageView imageViewMap;


    public CartShopViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
