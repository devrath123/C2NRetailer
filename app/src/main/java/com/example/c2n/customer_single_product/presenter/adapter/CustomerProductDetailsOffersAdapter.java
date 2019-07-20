package com.example.c2n.customer_single_product.presenter.adapter;

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
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerProductDetailsOffersAdapter extends RecyclerView.Adapter<CustomerProductDetailsOffersAdapter.MyViewHolder> {

    private ProductDataModel productDataModel;
    private Context context;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    int todysDay = new Date().getDay();
    Date todayDate = null;

    public CustomerProductDetailsOffersAdapter(ProductDataModel productDataModel, Context context) {
        this.productDataModel = productDataModel;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cust_offer_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShopDataModel shopDataModel = productDataModel.getShopDataModels().get(position);
        holder.textViewShopName.setText(shopDataModel.getShopName());
        holder.textViewShopAddress.setText(shopDataModel.getShopAddress());
        try {
            todayDate = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (shopDataModel.getOfferDataModel() != null) {
            if (shopDataModel.getOfferDataModel().isOfferStatus()) {
                if (isOfferCardActive(todysDay, shopDataModel.getOfferDataModel())) {
                    if (shopDataModel.getOfferDataModel().getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(shopDataModel.getOfferDataModel().getOfferEndDate()) >= 0) {
                        holder.textViewProductDiscount.setText(shopDataModel.getOfferDataModel().getOfferDiscount() + "% Off");
                        holder.textViewProductMRP.setText(String.valueOf(shopDataModel.getProductMRP()));
                        holder.textViewProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.textViewDiscountedPrice.setText("₹" + round(shopDataModel.getProductMRP() - (shopDataModel.getProductMRP() * shopDataModel.getOfferDataModel().getOfferDiscount()) / 100, 2));
                        holder.textViewProductDiscount.setVisibility(View.VISIBLE);
                        holder.textViewProductMRP.setVisibility(View.VISIBLE);
                        holder.textViewDiscountedPrice.setVisibility(View.VISIBLE);
                    } else {
                        holder.textViewDiscountedPrice.setText("₹" + shopDataModel.getProductMRP());
                        holder.textViewProductDiscount.setVisibility(View.GONE);
                        holder.textViewProductMRP.setVisibility(View.GONE);
                        holder.textViewDiscountedPrice.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.textViewDiscountedPrice.setText("₹" + shopDataModel.getProductMRP());
                    holder.textViewProductDiscount.setVisibility(View.GONE);
                    holder.textViewProductMRP.setVisibility(View.GONE);
                    holder.textViewDiscountedPrice.setVisibility(View.VISIBLE);
                }
            } else {
                holder.textViewDiscountedPrice.setText("₹" + shopDataModel.getProductMRP());
                holder.textViewProductDiscount.setVisibility(View.GONE);
                holder.textViewProductMRP.setVisibility(View.GONE);
                holder.textViewDiscountedPrice.setVisibility(View.VISIBLE);
            }
        } else {
            holder.textViewDiscountedPrice.setText("₹" + shopDataModel.getProductMRP());
            holder.textViewProductDiscount.setVisibility(View.GONE);
            holder.textViewProductMRP.setVisibility(View.GONE);
            holder.textViewDiscountedPrice.setVisibility(View.VISIBLE);
        }
        if (!shopDataModel.getShopImageURL().equals("")) {
            Picasso.get().load(shopDataModel.getShopImageURL()).fit().into(holder.imageViewShopImage);
        }
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
        return productDataModel.getShopDataModels().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_offer_shop_name)
        TextView textViewShopName;

        @BindView(R.id.tv_offer_shop_Address)
        TextView textViewShopAddress;

        @BindView(R.id.tv_product_discounted_price)
        TextView textViewDiscountedPrice;

        @BindView(R.id.textview_product_mrp)
        TextView textViewProductMRP;

        @BindView(R.id.tv_product_discount)
        TextView textViewProductDiscount;

        @BindView(R.id.iv_offer_shop)
        ImageView imageViewShopImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
                break;
            case 1:
                if (offerCard.isMon())
                    return true;
                break;
            case 2:
                if (offerCard.isTue())
                    return true;
                break;
            case 3:
                if (offerCard.isWed())
                    return true;
                break;
            case 4:
                if (offerCard.isThu())
                    return true;
                break;
            case 5:
                if (offerCard.isFri())
                    return true;
                break;
            case 6:
                if (offerCard.isSat())
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }
}
