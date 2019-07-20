package com.example.c2n.customer_cart.presenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.ProductDetailsDataModel;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerCartAdapter extends RecyclerView.Adapter<CustomerCartAdapter.ViewHolder> {

    private CustomerCartInterface customerCartInterface;
    private List<ProductDetailsDataModel> productDetailsDataModelList;
    private Context context;

    public CustomerCartAdapter(List<ProductDetailsDataModel> productDetailsDataModelList, Context context, CustomerCartInterface customerCartInterface) {
        this.productDetailsDataModelList = productDetailsDataModelList;
        this.context = context;
        this.customerCartInterface = customerCartInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_view, parent, false);
        return new CustomerCartAdapter.ViewHolder(itemView, customerCartInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetailsDataModel productDetailsDataModel = productDetailsDataModelList.get(position);
        Picasso.get().load(productDetailsDataModel.getProductDataModel().getProductImageURL()).networkPolicy(NetworkPolicy.OFFLINE).fit().into(holder.imageViewProductImage);
        holder.textViewProductName.setText(productDetailsDataModel.getProductDataModel().getProductName());
        if (productDetailsDataModel.getProductDataModel().getProductOffer() != null) {
            holder.textViewProductDiscount.setText(String.valueOf(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount()) + "% Off");
            holder.textViewProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewProductDiscountedPrice.setText("₹" + round(productDetailsDataModel.getProductDataModel().getProductMRP() - (productDetailsDataModel.getProductDataModel().getProductMRP() * productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount()) / 100, 2));
            holder.textViewProductMRP.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
        } else {
            holder.textViewProductDiscount.setVisibility(View.GONE);
            holder.textViewProductMRP.setVisibility(View.GONE);
            holder.textViewProductDiscountedPrice.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
        }
        holder.imageViewRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerCartInterface.removeFromCart(productDetailsDataModel);
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public int getItemCount() {
        return productDetailsDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomerCartInterface customerCartInterface;

        @BindView(R.id.iv_product_image)
        ImageView imageViewProductImage;

        @BindView(R.id.tv_product_name)
        TextView textViewProductName;

        @BindView(R.id.tv_product_discounted_price)
        TextView textViewProductDiscountedPrice;

        @BindView(R.id.tv_product_mrp)
        TextView textViewProductMRP;

        @BindView(R.id.tv_product_discount)
        TextView textViewProductDiscount;

        @BindView(R.id.iv_remove_product)
        ImageView imageViewRemoveProduct;

        public ViewHolder(View itemView, CustomerCartInterface customerCartInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customerCartInterface = customerCartInterface;
        }
    }

    public interface CustomerCartInterface {
        public void removeFromCart(ProductDetailsDataModel productDetailsDataModel);
    }
}
