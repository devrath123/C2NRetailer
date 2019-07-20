package com.example.c2n.customer_cart.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.MasterProductDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerCartMylistAdapter extends RecyclerView.Adapter<CustomerCartMylistAdapter.ViewHolder> {

    private Context context;
    private List<MasterProductDataModel> masterProductDataModelList;

    public CustomerCartMylistAdapter(Context context, List<MasterProductDataModel> masterProductDataModelList) {
        this.context = context;
        this.masterProductDataModelList = masterProductDataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_product, parent, false);
        return new CustomerCartMylistAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Picasso.get().load(masterProductDataModelList.get(i).getProductImageURL()).into(viewHolder.circleImageViewProductImage);
        viewHolder.textViewProductName.setText(masterProductDataModelList.get(i).getProductName());
        viewHolder.textViewProductCategory.setText(masterProductDataModelList.get(i).getProductCategory());
        viewHolder.linearLayoutPrice.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return masterProductDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_product_image)
        CircleImageView circleImageViewProductImage;

        @BindView(R.id.tv_product_name)
        TextView textViewProductName;

        @BindView(R.id.ll_price)
        LinearLayout linearLayoutPrice;

        @BindView(R.id.tv_product_category)
        TextView textViewProductCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
