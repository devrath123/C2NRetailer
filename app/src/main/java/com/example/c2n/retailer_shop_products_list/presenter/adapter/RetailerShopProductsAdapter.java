package com.example.c2n.retailer_shop_products_list.presenter.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class RetailerShopProductsAdapter extends RecyclerView.Adapter<RetailerShopProductsAdapter.MyViewHolder> implements Filterable, View.OnClickListener {

    private RetailerShopProductsAdapter.ProductRowInterface productRowInterface;
    List<ProductDataModel> filteredProducts;
    private List<ProductDataModel> productDataModels;
    private Context context;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate;

    Boolean isOfferActiveToday = false;
    Boolean isOfferCardApplicable = false;

    public RetailerShopProductsAdapter(Context context, List<ProductDataModel> productDataModels, RetailerShopProductsAdapter.ProductRowInterface productRowInterface) {
        this.context = context;
        this.productDataModels = productDataModels;
        this.productRowInterface = productRowInterface;
    }

    @Override
    public RetailerShopProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new RetailerShopProductsAdapter.MyViewHolder(view, productRowInterface);
    }

    @Override
    public void onBindViewHolder(RetailerShopProductsAdapter.MyViewHolder holder, int position) {
        final ProductDataModel productDataModel = productDataModels.get(position);

        Picasso.get().load(productDataModel.getProductImageURL()).fit().into(holder.productImage);

//        String imgPath = productDataModel.getProductPhotoUrl();
//
//        try {
//            String[] FILE = {MediaStore.Images.Media.DATA};
//
//            Uri uri = Uri.parse(imgPath);
//
//            Log.d("Image_uri", uri.toString());
//            Cursor cursor = context.getContentResolver().query(uri,
//                    FILE, null, null, null);
//
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(FILE[0]);
//            String ImageDecode = cursor.getString(columnIndex);
//            cursor.close();
//            Log.d("Image_decode", ImageDecode);
//            Bitmap bitmap = BitmapFactory.decodeFile(ImageDecode);
//            Log.d("Image_bitmap", bitmap + "");
//            holder.productImage.setImageBitmap(bitmap);
//
//        } catch (Exception e) {
//            Log.d("Image", e.getMessage());
//            Toast.makeText(context, "Please try again", Toast.LENGTH_LONG)
//                    .show();
//
//        }

        holder.productName.setText(productDataModel.getProductName());
        holder.productMRP.setText("₹ " + productDataModel.getProductMRP());
//        if (productDataModel.getProductOffer().getOfferDiscount() != 0) {
        if (productDataModel.getProductOffer() != null) {
            try {
                todayDate = format.parse(format.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int today = new Date().getDay();

            if (productDataModel.getProductOffer().isOfferStatus())
                isOfferCardApplicable = true;
            if (isOfferCardActive(today, productDataModel.getProductOffer())) {
                if (todayDate != null && productDataModel.getProductOffer().getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(productDataModel.getProductOffer().getOfferEndDate()) >= 0) {

                    isOfferActiveToday = true;
                } else
                    isOfferActiveToday = false;
                //                    applyOfferText.setText("Not Active");
            } else
                isOfferActiveToday = false;

            if (!isOfferActiveToday || !isOfferCardApplicable) {
                holder.textViewOfferNotActive.setVisibility(View.VISIBLE);
                holder.offerText1.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
                holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            } else
                holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            holder.offerText1.setText(productDataModel.getProductOffer().getOfferDiscount() + "% Off");
            holder.productFinalPrice.setText("₹ " + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * productDataModel.getProductOffer().getOfferDiscount()) / 100, 2));
//            holder.offerText1.setText(productDataModel.getProductOffer().getOfferDiscount() + "%");

//            if (!productDataModel.getProductOfferStatus()) {
//                holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
////                holder.offerText1.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//
//            }

        } else {
            holder.productMRP.setTextColor(Color.BLACK);
        }


//       if (!productDataModel.getProductStockStatus()) {
//            Log.d("out_of_stock", productDataModel.toString());
//
//            holder.outOfStockText.setVisibility(View.VISIBLE);
//            holder.markAvailableButton.setVisibility(View.VISIBLE);
//            holder.productImage.setAlpha(70);
//            holder.productLayout.setAlpha((float) 0.7);
//            holder.editProduct.setAlpha(70);
////            holder.activateOfferButton.setVisibility(View.VISIBLE);
//
//            holder.productName.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//            holder.productMRP.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//            holder.productFinalPrice.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//            holder.offerText1.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//        }

        holder.markAvailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productDataModel.getProductStockStatus() == true) {
                    Toast.makeText(context, "Product is already In Stock.", Toast.LENGTH_SHORT).show();
                } else {
                    productRowInterface.markProductAvailable(productDataModel, position);
                }
            }
        });


        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.editProduct);
                popupMenu.inflate(R.menu.edit_product_list);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_product: {
                                productRowInterface.productSelected(productDataModel, position);
                                Log.d("edit_product", "" + productDataModel);
                                break;
                            }
                            case R.id.mark_unavailable: {
                                if (productDataModel.getProductStockStatus() == false) {
                                    Toast.makeText(context, "Product is already Out of Stock.", Toast.LENGTH_SHORT).show();
                                } else {
                                    productRowInterface.markProductUnavailable(productDataModel, position);
                                }
                                break;
                            }
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

    }

    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
            case 1:
                if (offerCard.isMon())
                    return true;
            case 2:
                if (offerCard.isTue())
                    return true;
            case 3:
                if (offerCard.isWed())
                    return true;
            case 4:
                if (offerCard.isThu())
                    return true;
            case 5:
                if (offerCard.isFri())
                    return true;
            case 6:
                if (offerCard.isSat())
                    return true;
        }
        return false;
    }


//        if (productDataModel.getProductScheme() == null || productDataModel.getProductScheme().equals("")) {
//            holder.productMRP.setText("");
//            holder.productFinalPrice.setText("₹ " + productDataModel.getProductMRP());
//            holder.offerCard.setCardBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
//            holder.offerText1.setTextColor(context.getResources().getColor(R.color.offerColor));
//            holder.offerText2.setTextColor(context.getResources().getColor(R.color.offerColor));
//            holder.offerText1.setText("Add");
//            holder.offerText2.setText("Offer");
//        } else {
//            String[] productScheme = productDataModel.getProductScheme().split("=");
//            Log.d("scheme", productScheme.toString());
////            Log.d("scheme", productScheme[0] + productScheme[1] + productScheme[2]);
//            if (productScheme[0].equals("D")) {
//                holder.productMRP.setText(productDataModel.getProductMRP());
//                holder.productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                holder.productFinalPrice.setText("₹ " + productScheme[2]);
//                holder.offerText1.setText(productScheme[1] + "%");
//                holder.offerText2.setText("OFF");
//            } else if (productScheme[0].equals("BG")) {
//                holder.productMRP.setText("");
//                holder.productFinalPrice.setText("₹ " + productDataModel.getProductMRP());
//                holder.offerText1.setText("Buy        " + productScheme[1]);
//                holder.offerText2.setText("Get        " + productScheme[2]);
//            }
//        }

//        holder.offerCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("product_offer_clicked", " ....");
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//
//                // Setting Dialog Title
//                alertDialog.setTitle(productDataModel.getProductName());
//
////                if (productDataModel.getProductScheme() == null || productDataModel.getProductScheme().equals("")) {
////                    alertDialog.setMessage("Do you want to add any Offer?");
////                } else
////                    alertDialog.setMessage("Do you want to change the Offer?");
//
//                // Setting Icon to Dialog
////        alertDialog.setIcon(R.drawable.delete);
//
//                // Setting Positive "Yes" Button
//                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Write your code here to invoke YES event
//                        productRowInterface.addOrChangeOffer(productDataModel);
//
//                    }
//                });
//
//                // Setting Negative "NO" Button
//                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Write your code here to invoke NO event
//                        dialog.cancel();
//                    }
//                });
//
//                // Showing Alert Message
//                alertDialog.show();
//
//            }
//        });


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

    @Override
    public void onClick(View v) {

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredProducts = productDataModels;
                    Log.d("filter_all_products", productDataModels.toString());
                    Log.d("filter_products", filteredProducts.toString());
                } else {
                    List<ProductDataModel> filteredList = new ArrayList<>();
                    for (ProductDataModel row : productDataModels) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
//                        if (row.getProductName().toLowerCase().contains(charString.toLowerCase()) || row.getProductMRP().contains(constraint)) {
//                            filteredList.add(row);
//                        }
                    }

                    filteredProducts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProducts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProducts = (ArrayList<ProductDataModel>) results.values;
                Log.d("filter_publish_prods.", filteredProducts.toString());
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RetailerShopProductsAdapter.ProductRowInterface productRowInterface;

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

        @BindView(R.id.layout_product_item)
        LinearLayout productLayout;

        @BindView(R.id.tv_out_of_stock)
        TextView outOfStockText;

        @BindView(R.id.tv_offer_not_active)
        TextView textViewOfferNotActive;

        @BindView(R.id.bt_mark_available_product)
        Button markAvailableButton;

//        @BindView(R.id.tv_offerText2)
//        TextView offerText2;
//
//        @BindView(R.id.card_product_offer)
//        CardView offerCard;


        @BindView(R.id.edit_product_card)
        ImageView editProduct;


        public MyViewHolder(View itemView, RetailerShopProductsAdapter.ProductRowInterface productRowInterface) {
            super(itemView);
            this.productRowInterface = productRowInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            ProductDataModel productSelected = productDataModels.get(pos);
            Log.d("product_clicked", " ....");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            // Setting Dialog Title
            alertDialog.setTitle(productSelected.getProductName());

            alertDialog.setMessage("Do you want to edit the Product Details?");

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke YES event
                    productRowInterface.productSelected(productSelected, pos);
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    dialog.cancel();
                }
            });
            // Showing Alert Message
            alertDialog.show();
        }
    }

    public interface ProductRowInterface {
        void productSelected(ProductDataModel productDataModel, int position);

//        void editProduct(ProductDataModel productDataModel);

        void markProductUnavailable(ProductDataModel productDataModel, int position);

        void addOrChangeOffer(ProductDataModel productDataModel);

        void markProductAvailable(ProductDataModel productDataModel, int position);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
