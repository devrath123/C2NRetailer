package com.example.c2n.customer_products_list.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.model.ProductDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 23-05-2018.
 */

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.MyViewHolder> implements View.OnClickListener {

    private List<ProductDataModel> products;
    private Context context;
    ProductsListAdapter.ProductRowInterface productRowInterface;

    public ProductsListAdapter(List<ProductDataModel> products, Context context, ProductsListAdapter.ProductRowInterface productRowInterface) {
        this.products = products;
        this.context = context;
        this.productRowInterface = productRowInterface;
        Log.d("ProductsListAdapter", "count : " + products.size());
    }

    @Override
    public ProductsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_layout, parent, false);

        return new ProductsListAdapter.MyViewHolder(itemView, productRowInterface);
    }

    @Override
    public void onBindViewHolder(ProductsListAdapter.MyViewHolder holder, int position) {
        Log.d("category_list_holdr", products.toString());
        ProductDataModel productDataModel = products.get(position);
        Log.d("product_data_n", "" + productDataModel.getProductCategory());
        Log.d("product_data_i", "" + productDataModel.getProductPhotoUrl());

        holder.productName.setText(productDataModel.getProductName());
        holder.productMRP.setText("₹ " + productDataModel.getProductMRP());
        Picasso.get().load(productDataModel.getProductPhotoUrl()).fit().into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        Log.d("category_size", products.size() + "");
        return products.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProductsListAdapter.ProductRowInterface productRowInterface;
        @BindView(R.id.imageview_item_product)
        ImageView productImage;
        @BindView(R.id.textview_item_productName)
        TextView productName;
        @BindView(R.id.textview_item_productMRP)
        TextView productMRP;


        public MyViewHolder(View itemView, ProductsListAdapter.ProductRowInterface productRowInterface) {
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
            productRowInterface.productClicked(products.get(pos));
        }
    }

    public interface ProductRowInterface {
        void productClicked(ProductDataModel productDataModel);
    }
}
