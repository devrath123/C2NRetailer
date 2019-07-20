package com.example.c2n.viewshops.presenter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.ShopDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roshan.nimje on 21-05-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {

    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;
    private RecyclerViewAdapter.ShopRowInterface shopRowInterface;
    private List<ShopDataModel> shopDataModels;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ShopDataModel> shopDataModels, ShopRowInterface shopRowInterface) {
        this.context = context;
        this.shopDataModels = shopDataModels;
        this.shopRowInterface = shopRowInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_view, parent, false);
        return new MyViewHolder(view, shopRowInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ShopDataModel shopDataModel = shopDataModels.get(position);
        holder.textViewShopName.setText(shopDataModel.getShopName());
        holder.textViewShoopAddress.setText(shopDataModel.getShopAddress());
//        Log.d("RecyclerViewAdapter_", shopDataModel.getShopImageURL());
        if (!shopDataModel.getShopImageURL().equals("")) {
            holder.imageViewShopBackground.setColorFilter(R.color.bgImageTransparent);
            Picasso.get().load(shopDataModel.getShopImageURL()).placeholder(R.drawable.white_background).fit().into(holder.imageViewShopBackground);
//            Picasso.with(context).load(shopDataModel.getShopImageURL()).placeholder(R.drawable.store).into(holder.circleImageViewShopImage);
        } else {
            holder.imageViewShopBackground.setColorFilter(R.color.bgImageTransparent);
            Picasso.get().load(R.drawable.white_background).fit().into(holder.imageViewShopBackground);
//            holder.imageViewShopBackground.setBackgroundResource(R.drawable.white_background);
        }
//            holder.circleImageViewShopImage.setImageResource(R.drawable.store);
//        boolean isExpanded = position == mExpandedPosition;
        final boolean isExpanded = position == mExpandedPosition;
        holder.details.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            holder.itemView.setActivated(isExpanded);
        }

       /* if (isExpanded)
            previousExpandedPosition = position;
*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopRowInterface.shopProducts(shopDataModel);
               /* mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);*/
            }
        });

        holder.textViewProdcits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopRowInterface.shopProducts(shopDataModel);
            }
        });

        holder.textViewAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopRowInterface.addProduct(shopDataModel);
            }
        });

        holder.textViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopRowInterface.editShop(shopDataModel);
            }
        });

        holder.editShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.editShops);
                popupMenu.inflate(R.menu.edit_shops);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_shop: {
                                shopRowInterface.editShop(shopDataModel);
                                Log.d("edit_shop", "" + shopDataModel);
                                break;
                            }
                            case R.id.add_product: {
                                shopRowInterface.addProduct(shopDataModel);
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

    @Override
    public int getItemCount() {
        return shopDataModels.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ShopRowInterface shopRowInterface;

//        @BindView(R.id.shop_image)
//        CircleImageView circleImageViewShopImage;

        @BindView(R.id.iv_shop_image)
        ImageView imageViewShopBackground;

        @BindView(R.id.details)
        CardView details;

        @BindView(R.id.tv_add_product)
        TextView textViewAddProduct;

        @BindView(R.id.tv_products)
        TextView textViewProdcits;

        @BindView(R.id.tv_edit)
        TextView textViewEdit;

        @BindView(R.id.tv_shopName)
        TextView textViewShopName;

        @BindView(R.id.tv_shopAddress)
        TextView textViewShoopAddress;

        @BindView(R.id.edit_shop_details)
        ImageView editShops;

//        @BindView(R.id.shop_image)
//        CircleImageView circleImageViewShopImage;

        public MyViewHolder(View itemView, RecyclerViewAdapter.ShopRowInterface shopRowInterface) {
            super(itemView);
            this.shopRowInterface = shopRowInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
//            shopRowInterface.shopClicked(shopDataModels.get(pos));
        }
    }

    public interface ShopRowInterface {
        void shopProducts(ShopDataModel shopDataModel);

        void editShop(ShopDataModel shopDataModel);

        void addProduct(ShopDataModel shopDataModel);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
