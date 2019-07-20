package com.example.c2n.retailer_offer_products.presenter.presenter.presenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.ProductDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roshan.nimje on 29-06-2018.
 */

public class OfferProductsProductsAdapter extends RecyclerView.Adapter<OfferProductsProductsAdapter.MyViewHolder> {

    private OfferProductsProductsInterface offerProductsProductsInterface;
    private List<ProductDataModel> productDataModels;
    private Context context;
    private String offerID;

    public OfferProductsProductsAdapter(List<ProductDataModel> productDataModels, Context context, OfferProductsProductsInterface offerProductsProductsInterface, String offerID) {
        this.productDataModels = productDataModels;
        this.context = context;
        this.offerProductsProductsInterface = offerProductsProductsInterface;
        this.offerID = offerID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_item_view, parent, false);
        return new MyViewHolder(itemView, offerProductsProductsInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferProductsProductsAdapter.MyViewHolder holder, int position) {
        ProductDataModel productDataModel = productDataModels.get(position);
        Picasso.get().load(productDataModel.getProductImageURL()).fit().into(holder.productImage);
        holder.productName.setText(productDataModel.getProductName());
        holder.productMRP.setText("₹ " + productDataModel.getProductMRP());
//        holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        if (productDataModel.getProductOffer().getOfferDiscount() != 0) {
        if (productDataModel.getProductOffer() != null) {
            holder.offerText1.setText(productDataModel.getProductOffer().getOfferDiscount() + "% Off");
            holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.productFinalPrice.setText("₹ " + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * productDataModel.getProductOffer().getOfferDiscount()) / 100, 2));
            Log.d("QWERTYUIO", productDataModel.getProductOffer().getOfferID() + " - " + offerID);
            if (productDataModel.getProductOffer().getOfferID().equals(offerID))
                holder.checkBoxProduct.setChecked(true);
        }
//        Log.d("OfferProductsProductsA_", "" + productDataModel.getProductOfferID() + " - " + offerID);
//        if (productDataModel.getProductOfferID().equals(offerID))
//            holder.checkBoxProduct.setChecked(true);
//        if (productDataModel.getChecked()) {
//            offerProductsProductsInterface.productSelected(productDataModel, position);
//        }
//        Log.d("OfferProductsProductsA_", "** " + productDataModel.getProductDiscount());
        holder.checkBoxProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    offerProductsProductsInterface.productSelected(productDataModel, position);
                } else {
                    offerProductsProductsInterface.productDeselected(productDataModel, position);
                }
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
        return productDataModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final OfferProductsProductsInterface offerProductsProductsInterface;

        @BindView(R.id.iv_product_image)
        ImageView productImage;

        @BindView(R.id.tv_productName)
        TextView productName;

        @BindView(R.id.tv_product_final_price)
        TextView productFinalPrice;

        @BindView(R.id.tv_product_mrp)
        TextView productMRP;

        @BindView(R.id.tv_offerText1)
        TextView offerText1;

        @BindView(R.id.tv_offerText2)
        TextView offerText2;

        @BindView(R.id.card_product_offer)
        CardView offerCard;

        @BindView(R.id.cb_product_clicked)
        CheckBox checkBoxProduct;

        public MyViewHolder(View itemView, OfferProductsProductsInterface offerProductsProductsInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.offerProductsProductsInterface = offerProductsProductsInterface;
        }
    }

    public interface OfferProductsProductsInterface {
        void productSelected(ProductDataModel productDataModel, int position);

        void productDeselected(ProductDataModel productDataModel, int position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
