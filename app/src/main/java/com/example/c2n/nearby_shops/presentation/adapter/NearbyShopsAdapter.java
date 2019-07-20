package com.example.c2n.nearby_shops.presentation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.ShopDistanceDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyShopsAdapter extends RecyclerView.Adapter<NearbyShopsAdapter.ViewHolder> {

    private NearbyShopsAdapterInterface nearbyShopsAdapterInterface;
    private List<ShopDistanceDataModel> shopDataModels;
    private Context context;

    public NearbyShopsAdapter(List<ShopDistanceDataModel> shopDataModels, Context context, NearbyShopsAdapterInterface nearbyShopsAdapterInterface) {
        this.shopDataModels = shopDataModels;
        this.context = context;
        this.nearbyShopsAdapterInterface = nearbyShopsAdapterInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_shop_item_view, parent, false);
        return new NearbyShopsAdapter.ViewHolder(itemView, nearbyShopsAdapterInterface);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopDistanceDataModel shopDataModel = shopDataModels.get(position);
        holder.textViewShopName.setText(shopDataModel.getShopDataModel().getShopName());
        holder.textViewShopAddress.setText(shopDataModel.getShopDataModel().getShopAddress());
        holder.imageViewShopImage.setColorFilter(R.color.bgImageTransparent);
//        holder.textViewDisatance.setText(round(shopDataModel.getShopDistance(), 2) + " KM");
        holder.textViewDisatance.setText(shopDataModel.getShopDistance() + " KM");
        if (!shopDataModel.getShopDataModel().getShopImageURL().equals("")) {
            Picasso.get().load(shopDataModel.getShopDataModel().getShopImageURL()).fit().into(holder.imageViewShopImage);
        } else {
            Picasso.get().load(R.drawable.white_background).fit().into(holder.imageViewShopImage);
        }
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearbyShopsAdapterInterface.shopClicked(shopDataModel);
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
        return shopDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private NearbyShopsAdapterInterface nearbyShopsAdapterInterface;

        @BindView(R.id.iv_shop_image)
        ImageView imageViewShopImage;

        @BindView(R.id.tv_shop_name)
        TextView textViewShopName;

        @BindView(R.id.tv_shop_address)
        TextView textViewShopAddress;

        @BindView(R.id.tv_distance)
        TextView textViewDisatance;

        @BindView(R.id.fl_shop_image)
        FrameLayout frameLayout;

        public ViewHolder(View itemView, NearbyShopsAdapterInterface nearbyShopsAdapterInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.nearbyShopsAdapterInterface = nearbyShopsAdapterInterface;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface NearbyShopsAdapterInterface {
        void shopClicked(ShopDistanceDataModel shopDistanceDataModel);
    }

    public void clear() {
        shopDataModels.clear();
        notifyDataSetChanged();
    }
}
