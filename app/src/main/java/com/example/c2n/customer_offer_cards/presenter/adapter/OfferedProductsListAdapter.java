package com.example.c2n.customer_offer_cards.presenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.model1.OfferedProductsDataModel;
import com.example.c2n.core.model1.ProductDataModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class OfferedProductsListAdapter extends RecyclerView.Adapter<OfferedProductsListAdapter.MyViewHolder> implements View.OnClickListener {

    private OfferedProductsDataModel offeredProducts;
    private Context context;
    OfferedProductsListAdapter.ProductRowInterface productRowInterface;

    public OfferedProductsListAdapter(Context context, OfferedProductsDataModel offeredProducts, OfferedProductsListAdapter.ProductRowInterface productRowInterface) {
        this.offeredProducts = offeredProducts;
        this.context = context;
        this.productRowInterface = productRowInterface;
        Log.d("ProductsListAdapter", "count : " + offeredProducts.getProducts().size());
    }

    @Override
    public OfferedProductsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_product_view, parent, false);

        return new OfferedProductsListAdapter.MyViewHolder(itemView, productRowInterface);
    }

    @Override
    public void onBindViewHolder(OfferedProductsListAdapter.MyViewHolder holder, int position) {
        Log.d("category_list_holdr", offeredProducts.toString());
        ProductDataModel productDataModel = offeredProducts.getProducts().get(position);
        Log.d("product_data_n", "" + productDataModel.getProductCategory());
        Log.d("product_data_i", "" + productDataModel.getProductImageURL());

        holder.productDiscount.setVisibility(View.GONE);
        holder.productBookmark.setVisibility(View.GONE);
        holder.productName.setText(productDataModel.getProductName());
        holder.productMRP.setText("₹ " + productDataModel.getProductMRP());
        if (offeredProducts.getOfferDataModel() != null) {
            holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.productDiscountedPrice.setText("₹ " + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * offeredProducts.getOfferDataModel().getOfferDiscount()) / 100, 2));
//            Log.d("product_offer- ", productDataModel.getProductOffer().getOfferID());
        }
        holder.productCategory.setText(productDataModel.getProductCategory());
        Picasso.get().load(productDataModel.getProductImageURL()).fit().into(holder.productImage);
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
        Log.d("category_size", offeredProducts.getProducts().size() + "");
        return offeredProducts.getProducts().size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OfferedProductsListAdapter.ProductRowInterface productRowInterface;
        @BindView(R.id.iv_product_image)
        ImageView productImage;
        @BindView(R.id.tv_product_name)
        TextView productName;
        @BindView(R.id.textview_product_mrp)
        TextView productMRP;
        @BindView(R.id.tv_product_discounted_price)
        TextView productDiscountedPrice;
        @BindView(R.id.tv_product_category)
        TextView productCategory;
        @BindView(R.id.tv_product_discount)
        TextView productDiscount;
        @BindView(R.id.iv_bookmark)
        ImageView productBookmark;

        public MyViewHolder(View itemView, OfferedProductsListAdapter.ProductRowInterface productRowInterface) {
            super(itemView);
//            categoryIamage = itemView.findViewById(R.id.imageview_item_home);
//            categoryName = itemView.findViewById(R.id.textview_item_home);
            this.productRowInterface = productRowInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            productRowInterface.productClicked(offeredProducts.getProducts().get(pos));
        }
    }

    public interface ProductRowInterface {
        void productClicked(ProductDataModel productDataModel);
    }
}
