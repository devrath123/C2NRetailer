package com.example.c2n.retailer_offer_products.presenter.presenter.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c2n.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roshan.nimje on 29-06-2018.
 */

public class OfferProductsCategoryAdapter extends RecyclerView.Adapter<OfferProductsCategoryAdapter.MyViewHolder> {

    private int mClickedPosition = -1;
    private int previousClickedPosition = -1;
    private int selectedItem;

    private OfferProductsCategoryInterface offerProductsCategoryInterface;
    private List<String> categoryDataModels;
    private Context context;

    public OfferProductsCategoryAdapter(List<String> categoryDataModelList, Context context, OfferProductsCategoryInterface offerProductsCategoryInterface) {
        this.categoryDataModels = categoryDataModelList;
        this.context = context;
        this.offerProductsCategoryInterface = offerProductsCategoryInterface;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_products_category_itemview, parent, false);
        return new MyViewHolder(itemView, offerProductsCategoryInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferProductsCategoryAdapter.MyViewHolder holder, int position) {
        String category = categoryDataModels.get(position);
        holder.textViewCategory.setText(category);
        final boolean isClicked = position == mClickedPosition;
//        holder.textViewCategory.setVisibility(isClicked ? View.GONE : View.VISIBLE);

        if (isClicked) {
            holder.textViewCategory.setBackgroundResource(R.drawable.button_background_category);
            holder.textViewCategory.setTextColor(context.getResources().getColor(R.color.white));
            previousClickedPosition = position;
        } else {
            holder.textViewCategory.setBackgroundResource(0);
            holder.textViewCategory.setTextColor(context.getResources().getColor(R.color.grey));
        }
        if (selectedItem == position) {
            holder.textViewCategory.setBackgroundResource(R.drawable.button_background_category);
            holder.textViewCategory.setTextColor(context.getResources().getColor(R.color.white));
            previousClickedPosition = position;
        }
        holder.textViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickedPosition = isClicked ? -1 : position;
                notifyItemChanged(previousClickedPosition);
                notifyItemChanged(position);

                offerProductsCategoryInterface.categoryClicked(category, position);
                selectTaskListItem(position);
            }
        });

    }

    public void selectTaskListItem(int pos) {

        int previousItem = selectedItem;
        selectedItem = pos;

        notifyItemChanged(previousItem);
        notifyItemChanged(pos);

    }

    @Override
    public int getItemCount() {
        return categoryDataModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final OfferProductsCategoryInterface offerProductsCategoryInterface;

        @BindView(R.id.textview_category)
        TextView textViewCategory;

        public MyViewHolder(View itemView, OfferProductsCategoryInterface offerProductsCategoryInterface) {
            super(itemView);
            this.offerProductsCategoryInterface = offerProductsCategoryInterface;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OfferProductsCategoryInterface {

        void shopClicked();

        void categoryClicked(String category, int categoryClicked);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
