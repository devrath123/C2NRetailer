package com.example.c2n.customer_home.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.MasterProductDetailsDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerHomeSearchProductAdapter extends RecyclerView.Adapter<CustomerHomeSearchProductAdapter.ViewHolder> {

    private CustomerHomeSearchProductInterface customerHomeSearchProductInterface;
    private List<MasterProductDetailsDataModel> masterProductDetailsDataModels;
    private Context context;

    public CustomerHomeSearchProductAdapter(List<MasterProductDetailsDataModel> masterProductDetailsDataModels, Context context, CustomerHomeSearchProductInterface customerHomeSearchProductInterface) {
        this.masterProductDetailsDataModels = masterProductDetailsDataModels;
        this.context = context;
        this.customerHomeSearchProductInterface = customerHomeSearchProductInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_product_view, parent, false);

        return new CustomerHomeSearchProductAdapter.ViewHolder(itemView, customerHomeSearchProductInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MasterProductDetailsDataModel masterProductDetailsDataModel = masterProductDetailsDataModels.get(position);
        Picasso.get().load(masterProductDetailsDataModel.getMasterProductDataModel().getProductImageURL()).fit().into(holder.imageViewProductImage);
        holder.linearLayoutPrice.setVisibility(View.GONE);
        holder.textViewProductName.setText(masterProductDetailsDataModel.getMasterProductDataModel().getProductName());
        holder.textViewProductCategory.setText(masterProductDetailsDataModel.getMasterProductDataModel().getProductCategory());
        if (masterProductDetailsDataModel.isWhishlisted()) {
            holder.imageViewBookmark.setBackgroundResource(R.drawable.cart_plus_primary);
        }
//        holder.textViewProductDiscountedPrice.setText(String.valueOf(masterProductDetailsDataModel.getMasterProductDataModel().getProductMRP()));
        holder.linearLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerHomeSearchProductInterface.productClicked(masterProductDetailsDataModel);
            }
        });
        holder.imageViewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (masterProductDetailsDataModel.isWhishlisted()) {
                    Log.d("CustomerHomeSearch__", "if : " + masterProductDetailsDataModel.isWhishlisted());

                    masterProductDetailsDataModels.get(position).setMylisted(false);
                    holder.imageViewBookmark.setBackgroundResource(R.drawable.cart_plus);
                    customerHomeSearchProductInterface.removeFromMylist(masterProductDetailsDataModel);
                } else {
                    Log.d("CustomerHomeSearch__", "else : " + masterProductDetailsDataModel.isWhishlisted());

                    masterProductDetailsDataModels.get(position).setMylisted(true);
                    holder.imageViewBookmark.setBackgroundResource(R.drawable.cart_plus_primary);
                    customerHomeSearchProductInterface.addToMylist(masterProductDetailsDataModel);
                }
            }
        });
//        Log.d("CustomerHomeSearch__", "" + productDetailsDataModel.toString());
//        if (productDetailsDataModel.getProductDataModel().getProductOffer() != null) {
//            holder.textViewProductDiscount.setText(String.valueOf(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount()) + "% Off");
//            holder.textViewProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.textViewProductDiscountedPrice.setText("₹" + round(productDetailsDataModel.getProductDataModel().getProductMRP() - (productDetailsDataModel.getProductDataModel().getProductMRP() * productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount()) / 100, 2));
//            holder.textViewProductMRP.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
//        } else {
//            holder.textViewProductDiscount.setVisibility(View.GONE);
//            holder.textViewProductMRP.setVisibility(View.GONE);
//            holder.textViewProductDiscountedPrice.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
//        }
//        holder.imageViewBookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (productDetailsDataModel.isWhishlisted()) {
//                    Log.d("CustomerHomeSearch__", "if : " + productDetailsDataModel.isWhishlisted());
//
//                    productDetailsDataModelList.get(position).setMylisted(false);
//                    holder.imageViewBookmark.setBackgroundResource(R.drawable.bookmark_outline);
//                    customerHomeSearchProductInterface.removeFromMylist(productDetailsDataModel);
//                } else {
//                    Log.d("CustomerHomeSearch__", "else : " + productDetailsDataModel.isWhishlisted());
//
//                    productDetailsDataModelList.get(position).setMylisted(true);
//                    holder.imageViewBookmark.setBackgroundResource(R.drawable.bookmark);
//                    customerHomeSearchProductInterface.addToMylist(productDetailsDataModel);
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
        return masterProductDetailsDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomerHomeSearchProductInterface customerHomeSearchProductInterface;

        @BindView(R.id.ll_main)
        LinearLayout linearLayoutMain;

        @BindView(R.id.iv_bookmark)
        ImageView imageViewBookmark;

        @BindView(R.id.iv_product_image)
        ImageView imageViewProductImage;

        @BindView(R.id.tv_product_name)
        TextView textViewProductName;

        @BindView(R.id.tv_product_discounted_price)
        TextView textViewProductDiscountedPrice;

        @BindView(R.id.textview_product_mrp)
        TextView textViewProductMRP;

        @BindView(R.id.tv_product_discount)
        TextView textViewProductDiscount;

        @BindView(R.id.tv_product_category)
        TextView textViewProductCategory;

        @BindView(R.id.ll_price)
        LinearLayout linearLayoutPrice;


        public ViewHolder(View itemView, CustomerHomeSearchProductInterface customerHomeSearchProductInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customerHomeSearchProductInterface = customerHomeSearchProductInterface;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public interface CustomerHomeSearchProductInterface {

        void productClicked(MasterProductDetailsDataModel masterProductDetailsDataModel);

        void addToMylist(MasterProductDetailsDataModel masterProductDetailsDataModel);

        void removeFromMylist(MasterProductDetailsDataModel masterProductDetailsDataModel);
    }
}
