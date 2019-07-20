package com.example.c2n.retailerhome.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.ShopDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 20-06-2018.
 */

public class RetailerHomeRecentShopsAdapter extends RecyclerView.Adapter<RetailerHomeRecentShopsAdapter.MyViewHolder> implements View.OnClickListener {


    private RetailerHomeRecentShopsAdapter.ShopRowInterface shopRowInterface;
    private List<ShopDataModel> shopDataModels;
    private Context context;

    public RetailerHomeRecentShopsAdapter(Context context, List<ShopDataModel> shopDataModels, RetailerHomeRecentShopsAdapter.ShopRowInterface shopRowInterface) {
        this.context = context;
        this.shopDataModels = shopDataModels;
        this.shopRowInterface = shopRowInterface;
    }

    @Override
    public RetailerHomeRecentShopsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_shop_view, parent, false);
        return new RetailerHomeRecentShopsAdapter.MyViewHolder(view, shopRowInterface);
    }

    @Override
    public void onBindViewHolder(RetailerHomeRecentShopsAdapter.MyViewHolder holder, int position) {
        final ShopDataModel shopDataModel = shopDataModels.get(position);
        holder.textViewShopName.setText(shopDataModel.getShopName());
        holder.textViewShopAddress.setText("Address : " + shopDataModel.getShopAddress());
//        holder.shopProductsCount.setText("("+shopDataModel.getTotalProducts()+")");
        if (shopDataModel.getShopImageURL() != null && !shopDataModel.getShopImageURL().equals(""))
            Picasso.get().load(shopDataModel.getShopImageURL()).fit().into(holder.shopImage);
        else
            holder.shopImage.setBackgroundResource(R.drawable.no_image);
//            Picasso.with(context).load("https://www.gozermatt.com/images/shops_sports/fullsize/zermatt_reinhold_perren_fs.jpg").fit().into(holder.shopImage);

        holder.layoutShowProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopRowInterface.showShopProducts(shopDataModel);
            }
        });

        holder.layoutAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopRowInterface.addShopProduct(shopDataModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopDataModels.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RetailerHomeRecentShopsAdapter.ShopRowInterface shopRowInterface;
        @BindView(R.id.tv_recent_shopName)
        TextView textViewShopName;

        @BindView(R.id.tv_recent_shopAddress)
        TextView textViewShopAddress;

        @BindView(R.id.tv_recent_shopProductsCount)
        TextView shopProductsCount;

        @BindView(R.id.iv_recent_shop_image)
        ImageView shopImage;

        @BindView(R.id.layout_recent_shop_products)
        LinearLayout layoutShowProducts;

        @BindView(R.id.layout_recent_shop_add_product)
        LinearLayout layoutAddProduct;

        @BindView(R.id.iv_recent_shop_add_product)
        ImageView imageViewAddProduct;

        public MyViewHolder(View itemView, RetailerHomeRecentShopsAdapter.ShopRowInterface shopRowInterface) {
            super(itemView);
            this.shopRowInterface = shopRowInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            shopRowInterface.shopClicked(shopDataModels.get(pos));
        }
    }

    public interface ShopRowInterface {
        void shopClicked(ShopDataModel shopDataModel);

        void showShopProducts(ShopDataModel shopDataModel);

        void addShopProduct(ShopDataModel shopDataModel);
    }

}
