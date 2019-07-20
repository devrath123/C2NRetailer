package com.example.c2n.master_list.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.MasterProductDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MyViewHolder> {

    private List<MasterProductDataModel> masterProductList;
    private Context context;
    private MasterListAdapterInterface masterListAdapterInterface;

    public MasterListAdapter(List<MasterProductDataModel> masterProductList, Context context, MasterListAdapterInterface masterListAdapterInterface) {
        this.masterProductList = masterProductList;
        this.context = context;
        this.masterListAdapterInterface = masterListAdapterInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_products_item_view, parent, false);
        return new MasterListAdapter.MyViewHolder(view, masterListAdapterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MasterProductDataModel masterProduct = masterProductList.get(position);
        holder.textViewProductName.setText(masterProduct.getProductName());
//        holder.textViewProductMRP.setText(context.getResources().getString(R.string.rupee_symbol) + masterProduct.getProductMRP());
        holder.textViewProductCategory.setText(masterProduct.getProductCategory());
        if (masterProduct.getProductImageURL() != null) {
            Picasso.get().load(masterProduct.getProductImageURL()).error(R.drawable.alert_circle).into(holder.imageViewProductImage);
        } else {
            Picasso.get().load(R.drawable.alert_circle).into(holder.imageViewProductImage);
        }
        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterListAdapterInterface.productClicked(masterProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return masterProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private MasterListAdapterInterface masterListAdapterInterface;

        @BindView(R.id.product_card_view)
        CardView cardViewProduct;

        @BindView(R.id.tv_product_name)
        TextView textViewProductName;

//        @BindView(R.id.tv_product_mrp)
//        TextView textViewProductMRP;

        @BindView(R.id.tv_product_category)
        TextView textViewProductCategory;

        @BindView(R.id.iv_product_image)
        ImageView imageViewProductImage;

        public MyViewHolder(View itemView, MasterListAdapterInterface masterListAdapterInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.masterListAdapterInterface = masterListAdapterInterface;
        }
    }

    public interface MasterListAdapterInterface {
        void productClicked(MasterProductDataModel masterProductDataModel);
    }
}
