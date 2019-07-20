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
import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.ProductDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivani.singh on 30-08-2018.
 */

public class SingleOfferProductsAdapter extends RecyclerView.Adapter<SingleOfferProductsAdapter.ViewHolder> {
    private List<ProductDataModel> productDataModelList;
    private SingleOfferProductsAdapter.SingleOfferProductsAdapterInterface offerCardAdapterInterface;
    Context context;
    OfferDataModel offerDataModel;

    @Inject
    public SingleOfferProductsAdapter(OfferDataModel offerDataModel, List<ProductDataModel> productDataModelList, Context context, SingleOfferProductsAdapterInterface offerCardAdapterInterface) {
        this.offerDataModel = offerDataModel;
        this.productDataModelList = productDataModelList;
        this.context = context;
        this.offerCardAdapterInterface = offerCardAdapterInterface;
    }

    @NonNull
    @Override
    public SingleOfferProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.customer_product_view, parent, false);
        return new SingleOfferProductsAdapter.ViewHolder(v, offerCardAdapterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleOfferProductsAdapter.ViewHolder holder, int position) {
        ProductDataModel productDataModel = productDataModelList.get(position);
  /*      Log.d("product_data_n", "" + productDataModel.getProductCategory());
        Log.d("product_data_i", "" + productDataModel.getProductImageURL());*/

        holder.productDiscount.setVisibility(View.GONE);
        holder.productBookmark.setVisibility(View.GONE);
        holder.productName.setText(productDataModel.getProductName());
        holder.productMRP.setText("₹ " + productDataModel.getProductMRP());

        holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productDiscountedPrice.setText("₹ " + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * offerDataModel.getOfferDiscount()) / 100, 2));
//            Log.d("product_offer- ", productDataModel.getProductOffer().getOfferID());

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
        Log.d("products_size", productDataModelList.size() + "");
        return productDataModelList.size();
    }

//    @Override
//    public void onClick(View v) {
//        offerCardAdapterInterface.offeredProductClicked(productDataModelList.get(v.ge));
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleOfferProductsAdapter.SingleOfferProductsAdapterInterface productRowInterface;
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

        public ViewHolder(View itemView, SingleOfferProductsAdapter.SingleOfferProductsAdapterInterface offerCardAdapterInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Log.d("getItemCount", productDataModelList.toString());

            this.productRowInterface = offerCardAdapterInterface;
        }
    }

    public interface SingleOfferProductsAdapterInterface {
        void offeredProductClicked(ProductDataModel productDataModel);
    }
}
