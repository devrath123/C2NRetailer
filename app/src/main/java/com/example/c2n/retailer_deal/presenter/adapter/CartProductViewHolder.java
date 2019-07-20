package com.example.c2n.retailer_deal.presenter.adapter;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.c2n.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CartProductViewHolder extends ChildViewHolder {

    @BindView(R.id.civ_product_image)
    CircleImageView circleImageViewProductImage;

    @BindView(R.id.tv_product_name)
    TextView textViewProductName;

    @BindView(R.id.tv_product_discounted_price)
    TextView textViewProductDiscountedPrice;

    @BindView(R.id.tv_product_mrp)
    TextView textViewProductMRP;

    @BindView(R.id.tv_product_discount)
    TextView textViewProductDiscount;

    @BindView(R.id.tv_product_category)
    TextView textViewProductCategory;

    public CartProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
