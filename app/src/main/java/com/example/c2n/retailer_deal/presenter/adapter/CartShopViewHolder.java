package com.example.c2n.retailer_deal.presenter.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.c2n.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartShopViewHolder extends ParentViewHolder {

    private CartAdapter.CartAdapterInterface cartAdapterInterface;

    @BindView(R.id.tv_customer_name)
    TextView textViewCustomerName;

    @BindView(R.id.cart_shop_name)
    TextView shopname;

    @BindView(R.id.cart_shop_image)
    ImageView shopimage;

    @BindView(R.id.cart_shop_bill)
    TextView bill;

    @BindView(R.id.cart_discount_requested)
    TextView requestedDeal;

    @BindView(R.id.cart_remove_shop)
    ImageView removeshop;

    @BindView(R.id.cart_your_products_title)
    TextView textViewProductCount;

    @BindView(R.id.tv_accept_deal)
    TextView textViewAcceptDeal;

    @BindView(R.id.tv_decline_deal)
    TextView textViewDeclineDeal;


    public CartShopViewHolder(View itemView, CartAdapter.CartAdapterInterface cartAdapterInterface) {
        super(itemView);
        this.cartAdapterInterface = cartAdapterInterface;
        ButterKnife.bind(this, itemView);
    }
}