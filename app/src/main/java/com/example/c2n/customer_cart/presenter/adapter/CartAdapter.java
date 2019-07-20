package com.example.c2n.customer_cart.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.c2n.R;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopCartDataModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CartAdapter extends ExpandableRecyclerAdapter<CartShopViewHolder, CartProductViewHolder> {

    LayoutInflater inflater;
    private Context context;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    int todysDay = new Date().getDay();
    Date todayDate = null;

    public CartAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

   /* public CartAdapter(List<ShopDataModel> shopDataModelList, Context context) {
        this.shopDataModelList = shopDataModelList;
        this.context = context;
        inflater=LayoutInflater.from(context);

    }*/
  /*  @NonNull
    @Override
    public CartShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.item_cust_cart,parent,false);
        return new CartShopViewHolder(view,context) ;
    }*/

    @Override
    public CartShopViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.item_customer_cart, viewGroup, false);
        return new CartShopViewHolder(view);
    }

    @Override
    public CartProductViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.item_cart_product, viewGroup, false);
        return new CartProductViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(CartShopViewHolder holder, int i, Object o) {
        ShopCartDataModel shop = (ShopCartDataModel) o;
        if (!shop.getShopDataModel().getShopImageURL().equals("")) {
            Picasso.get().load(shop.getShopDataModel().getShopImageURL()).into(holder.shopimage);
        } else {
            holder.shopimage.setBackgroundResource(R.drawable.no_image);
        }
        holder.textViewProductCount.setText("Product Count : " + shop.getProductDataModel().size());
        holder.shopname.setText(shop.getShopDataModel().getShopName());
        holder.bill.setText(context.getString(R.string.rupee_symbol) + shop.getTotalBill());
        holder.shopdistance.setText("Distance : " + shop.getDistance() + "KM");
        holder.removeshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "comming soon...", Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:" + shop.getShopDataModel().getShopLatitude() + "," + shop.getShopDataModel().getShopLongitude() + "?q=" + shop.getShopDataModel().getShopLatitude() + "," + shop.getShopDataModel().getShopLongitude() + "(" + shop.getShopDataModel().getShopName() + ")");
//                Uri gmmIntentUri = Uri.parse("geo:" + shop.getShopDataModel().getShopLatitude() + "," + shop.getShopDataModel().getShopLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public void onBindChildViewHolder(CartProductViewHolder holder, int i, Object o) {
        ProductDataModel product = (ProductDataModel) o;
        Picasso.get().load(product.getProductImageURL()).into(holder.circleImageViewProductImage);
        holder.textViewProductName.setText(product.getProductName());
        try {
            todayDate = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (product.getProductOffer() != null) {
            if (product.getProductOffer().isOfferStatus()) {
                if (isOfferCardActive(todysDay, product.getProductOffer())) {
                    if (product.getProductOffer().getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(product.getProductOffer().getOfferEndDate()) >= 0) {
                        holder.textViewProductMRP.setVisibility(View.VISIBLE);
                        holder.textViewProductDiscount.setVisibility(View.VISIBLE);
                        holder.textViewProductDiscountedPrice.setText(context.getString(R.string.rupee_symbol) + String.valueOf(round(product.getProductMRP() - (product.getProductMRP() * product.getProductOffer().getOfferDiscount()) / 100, 2)));
                        holder.textViewProductMRP.setText(context.getString(R.string.rupee_symbol) + String.valueOf(product.getProductMRP()));
                        holder.textViewProductMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.textViewProductDiscount.setText(product.getProductOffer().getOfferDiscount() + "% OFF");
                    } else {
                        holder.textViewProductDiscountedPrice.setText(context.getString(R.string.rupee_symbol) + String.valueOf(product.getProductMRP()));
                        holder.textViewProductMRP.setVisibility(View.GONE);
                        holder.textViewProductDiscount.setVisibility(View.GONE);
                    }
                } else {
                    holder.textViewProductDiscountedPrice.setText(context.getString(R.string.rupee_symbol) + String.valueOf(product.getProductMRP()));
                    holder.textViewProductMRP.setVisibility(View.GONE);
                    holder.textViewProductDiscount.setVisibility(View.GONE);
                }
            } else {
                holder.textViewProductDiscountedPrice.setText(context.getString(R.string.rupee_symbol) + String.valueOf(product.getProductMRP()));
                holder.textViewProductMRP.setVisibility(View.GONE);
                holder.textViewProductDiscount.setVisibility(View.GONE);
            }
        } else {
            holder.textViewProductDiscountedPrice.setText(context.getString(R.string.rupee_symbol) + String.valueOf(product.getProductMRP()));
            holder.textViewProductMRP.setVisibility(View.GONE);
            holder.textViewProductDiscount.setVisibility(View.GONE);
        }
        holder.textViewProductCategory.setText(product.getProductCategory());
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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

/*
    @Override
    public void onBindViewHolder(@NonNull CartShopViewHolder holder, int position) {
        ShopDataModel shop=shopDataModelList.get(position);
        holder.shopimage.setImageResource(R.drawable.kitchenware);
        holder.shopname.setText(shop.getShopName());
        holder.bill.setText("Your total bill : 250");
        holder.shopdistance.setText("Distance From you : 20km");


    }*/

   /* @Override
    public int getItemCount() {
        return shopDataModelList.size();
    }*/

}
