package com.example.c2n.customer_offer_cards.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferDetailsDataModel;
import com.example.c2n.core.model1.OfferedProductsDataModel;
import com.example.c2n.core.model1.ProductDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 10/09/18.
 */

public class SectionProductsRecyclerViewAdapter extends RecyclerView.Adapter<SectionProductsRecyclerViewAdapter.SectionViewHolder> implements OfferedProductsListAdapter.ProductRowInterface {


    @Override
    public void productClicked(com.example.c2n.core.model1.ProductDataModel productDataModel) {
        Log.d("offered_product- ", productDataModel.toString());

    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.section_offer_label)
        TextView sectionLabel;
        @BindView(R.id.section_show_all_button)
        TextView showAllButton;
        @BindView(R.id.item_recycler_view)
        RecyclerView itemRecyclerView;

        OfferedProductsAdapterInterface offeredProductsAdapterInterface;

        public SectionViewHolder(View itemView, OfferedProductsAdapterInterface offeredProductsAdapterInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.offeredProductsAdapterInterface = offeredProductsAdapterInterface;
        }
    }

    private Context context;
    private ArrayList<OfferedProductsDataModel> sectionModelArrayList;
    private OfferedProductsAdapterInterface offeredProductsAdapterInterface;

    public SectionProductsRecyclerViewAdapter(Context context, ArrayList<OfferedProductsDataModel> sectionModelArrayList, OfferedProductsAdapterInterface offeredProductsAdapterInterface) {
        this.context = context;
        this.sectionModelArrayList = sectionModelArrayList;
        this.offeredProductsAdapterInterface = offeredProductsAdapterInterface;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_offer_products_row_layout, parent, false);
        return new SectionViewHolder(view, offeredProductsAdapterInterface);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        final OfferedProductsDataModel offeredProductsDataModel = sectionModelArrayList.get(position);
//        allOfferedProductsList = new ArrayList<>();
        List<ProductDataModel> offeredProductsList = new ArrayList<>();
        holder.sectionLabel.setText(offeredProductsDataModel.getOfferDataModel().getOfferName() + " - " + offeredProductsDataModel.getOfferDataModel().getOfferDiscount() + "% OFF");

        //recycler view for items
        holder.itemRecyclerView.setHasFixedSize(true);
        holder.itemRecyclerView.setNestedScrollingEnabled(false);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        holder.itemRecyclerView.setLayoutManager(layoutManager);
        final List<ProductDataModel>  allOfferedProductsList = offeredProductsDataModel.getProducts();

        if (offeredProductsDataModel.getProducts().size() <= 2)
            holder.showAllButton.setVisibility(View.GONE);
        else {
            holder.showAllButton.setVisibility(View.VISIBLE);
            holder.showAllButton.setText("View " + (allOfferedProductsList.size() - 2) + " more...");
        }
        if (allOfferedProductsList.size() > 2) {
            for (int i = 0; i < 2; i++) {
                offeredProductsList.add(allOfferedProductsList.get(i));
            }
            offeredProductsDataModel.setProducts(offeredProductsList);
        } else
            offeredProductsDataModel.setProducts(allOfferedProductsList);

        OfferedProductsListAdapter adapter = new OfferedProductsListAdapter(context, offeredProductsDataModel, this);
        holder.itemRecyclerView.setAdapter(adapter);

        //show toast on click of show all button
        holder.showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "You clicked on Show All of : " + offeredProductsDataModel.getProducts(), Toast.LENGTH_SHORT).show();
           holder.offeredProductsAdapterInterface.showAllOfferedProducts(offeredProductsDataModel.getOfferDataModel(),allOfferedProductsList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sectionModelArrayList.size();
    }

    public interface OfferedProductsAdapterInterface {
        void showAllOfferedProducts(OfferDataModel offerDataModel,List<ProductDataModel> allOferedProductsList);
    }

}
