package com.example.c2n.customer_single_shop_products.presenter.adapter;

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
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDetailsDataModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerSingleShopProductsAdapter extends RecyclerView.Adapter<CustomerSingleShopProductsAdapter.ViewHolder> {

    private Context context;
    private List<ProductDetailsDataModel> productDataModelList;
    private CustomerSingleShopProductInterface customerSingleShopProductInterface;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    int todysDay = new Date().getDay();
    Date todayDate = null;

    public CustomerSingleShopProductsAdapter(List<ProductDetailsDataModel> productDataModelList, Context context, CustomerSingleShopProductInterface customerSingleShopProductInterface) {
        this.productDataModelList = productDataModelList;
        this.context = context;
        this.customerSingleShopProductInterface = customerSingleShopProductInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_product_view, parent, false);
        return new CustomerSingleShopProductsAdapter.ViewHolder(itemView, customerSingleShopProductInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetailsDataModel productDetailsDataModel = productDataModelList.get(position);
        Picasso.get().load(productDetailsDataModel.getProductDataModel().getProductImageURL()).fit().into(holder.imageViewProductImage);
        holder.textViewProductName.setText(productDetailsDataModel.getProductDataModel().getProductName());
        holder.textViewProductCategory.setText(productDetailsDataModel.getProductDataModel().getProductCategory());
        if (productDetailsDataModel.isWhishlisted()) {
            holder.imageViewBookmark.setBackgroundResource(R.drawable.cart_plus_primary);
        }
        try {
            todayDate = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("CustomerHomeSearch__", "" + productDetailsDataModel.toString());
        if (productDetailsDataModel.getProductDataModel().getProductOffer() != null) {
            if (productDetailsDataModel.getProductDataModel().getProductOffer().isOfferStatus()) {
                if (isOfferCardActive(todysDay, productDetailsDataModel.getProductDataModel().getProductOffer())) {
                    if (productDetailsDataModel.getProductDataModel().getProductOffer().getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferEndDate()) >= 0) {
                        holder.textViewProductDiscount.setText(String.valueOf(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount()) + "% Off");
                        holder.textViewProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.textViewProductMRP.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
                        holder.textViewProductDiscountedPrice.setText("₹" + round(productDetailsDataModel.getProductDataModel().getProductMRP() - (productDetailsDataModel.getProductDataModel().getProductMRP() * productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount()) / 100, 2));
                        holder.textViewProductDiscount.setVisibility(View.VISIBLE);
                        holder.textViewProductMRP.setVisibility(View.VISIBLE);
                    } else {
                        holder.textViewProductDiscount.setVisibility(View.GONE);
                        holder.textViewProductMRP.setVisibility(View.GONE);
                        holder.textViewProductDiscountedPrice.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
                    }
                } else {
                    holder.textViewProductDiscount.setVisibility(View.GONE);
                    holder.textViewProductMRP.setVisibility(View.GONE);
                    holder.textViewProductDiscountedPrice.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
                }
            } else {
                holder.textViewProductDiscount.setVisibility(View.GONE);
                holder.textViewProductMRP.setVisibility(View.GONE);
                holder.textViewProductDiscountedPrice.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
            }
        } else {
            holder.textViewProductDiscount.setVisibility(View.GONE);
            holder.textViewProductMRP.setVisibility(View.GONE);
            holder.textViewProductDiscountedPrice.setText("₹" + String.valueOf(productDetailsDataModel.getProductDataModel().getProductMRP()));
        }
        holder.imageViewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!productDetailsDataModel.isWhishlisted()) {
                    productDataModelList.get(position).setWhishlisted(true);
                    holder.imageViewBookmark.setBackgroundResource(R.drawable.cart_plus_primary);
                    customerSingleShopProductInterface.addToMylist(productDetailsDataModel);
                } else {
                    productDataModelList.get(position).setWhishlisted(false);
                    holder.imageViewBookmark.setBackgroundResource(R.drawable.cart_plus);
                    customerSingleShopProductInterface.removeFromMylist(productDetailsDataModel);
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
        return productDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomerSingleShopProductInterface customerSingleShopProductInterface;

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

        public ViewHolder(View itemView, CustomerSingleShopProductInterface customerSingleShopProductInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customerSingleShopProductInterface = customerSingleShopProductInterface;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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

    public interface CustomerSingleShopProductInterface {
        void addToMylist(ProductDetailsDataModel productDetailsDataModel);

        void removeFromMylist(ProductDetailsDataModel productDetailsDataModel);
    }
}
