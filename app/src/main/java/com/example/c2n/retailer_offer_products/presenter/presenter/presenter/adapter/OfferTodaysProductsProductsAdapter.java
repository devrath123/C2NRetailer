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
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roshan.nimje on 29-06-2018.
 */

public class OfferTodaysProductsProductsAdapter extends RecyclerView.Adapter<OfferTodaysProductsProductsAdapter.MyViewHolder> {

    //    private OfferProductsProductsInterface offerProductsProductsInterface;
    private List<ProductDataModel> productDataModels;
    private Context context;
    private OfferDataModel offerDataModel;

    public OfferTodaysProductsProductsAdapter(List<ProductDataModel> productDataModels, Context context, OfferDataModel offerDataModel) {
        this.productDataModels = productDataModels;
        this.context = context;
//        this.offerProductsProductsInterface = offerProductsProductsInterface;
        this.offerDataModel = offerDataModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferTodaysProductsProductsAdapter.MyViewHolder holder, int position) {
        ProductDataModel productDataModel = productDataModels.get(position);
        holder.checkBoxProduct.setVisibility(View.GONE);
        Picasso.get().load(productDataModel.getProductImageURL()).fit().into(holder.productImage);
        holder.productName.setText(productDataModel.getProductName());
        holder.productMRP.setText("₹ " + productDataModel.getProductMRP());
        holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.offerText1.setText(offerDataModel.getOfferDiscount() + "% off");
        holder.productFinalPrice.setText("₹ " + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * offerDataModel.getOfferDiscount()) / 100, 2));
//        Log.d("OfferProductsProductsA_", "" + productDataModel.getProductOfferID() + " - " + offerID);
//        if (productDataModel.getProductOfferID().equals(offerID))
//            holder.checkBoxProduct.setChecked(true);
//        if (productDataModel.getChecked()) {
//            offerProductsProductsInterface.productSelected(productDataModel, position);
//        }
//        if (productDataModel.getProductScheme() == null || productDataModel.getProductScheme().equals("")) {
//            holder.productMRP.setText("");
//            holder.productFinalPrice.setText("₹ " + productDataModel.getProductMRP());
//            holder.offerCard.setCardBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
//            holder.offerText1.setTextColor(context.getResources().getColor(R.color.offerColor));
//            holder.offerText2.setTextColor(context.getResources().getColor(R.color.offerColor));
//            holder.offerText1.setText("Add ");
//            holder.offerText2.setText("Offer");
//        } else {
//            String[] productScheme = productDataModel.getProductScheme().split("=");
//            Log.d("scheme", productScheme.toString());
////            Log.d("scheme", productScheme[0] + productScheme[1] + productScheme[2]);
//            if (productScheme[0].equals("D")) {
//                holder.productMRP.setText(productDataModel.getProductMRP());
//                holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                holder.productFinalPrice.setText("₹ " + productScheme[2]);
//                holder.offerText1.setText(productScheme[1] + "% ");
//                holder.offerText2.setText("OFF");
//            } else if (productScheme[0].equals("BG")) {
//                holder.productMRP.setText("");
//                holder.productFinalPrice.setText("₹ " + productDataModel.getProductMRP());
//                holder.offerText1.setText("Buy " + productScheme[1] + " ");
//                holder.offerText2.setText("Get " + productScheme[2]);
//            }
//        }
//        holder.checkBoxProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    offerProductsProductsInterface.productSelected(productDataModel, position);
//                } else {
//                    offerProductsProductsInterface.productDeselected(productDataModel, position);
//                }
//            }
//        });
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

//        private final OfferProductsProductsInterface offerProductsProductsInterface;

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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            this.offerProductsProductsInterface = offerProductsProductsInterface;
        }
    }

//    public interface OfferProductsProductsInterface {
//        void productSelected(ProductDataModel productDataModel, int position);
//
//        void productDeselected(ProductDataModel productDataModel, int position);
//    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
