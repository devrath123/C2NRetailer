package com.example.c2n.customer_single_shop_products.presenter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.CategoryDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerSingleShopCategoryAdapter extends RecyclerView.Adapter<CustomerSingleShopCategoryAdapter.ViewHolder> {
    private int mClickedPosition = -1;
    private int previousClickedPosition = -1;
    private int selectedItem;

    private CustomerSingleShopCategoryInterface customerSingleShopCategoryInterface;
    private List<CategoryDataModel> categoryDataModels;
    private Context context;

    public CustomerSingleShopCategoryAdapter(Context context, List<CategoryDataModel> categoryDataModels, CustomerSingleShopCategoryInterface customerSingleShopCategoryInterface) {
        this.context = context;
        this.categoryDataModels = categoryDataModels;
        this.customerSingleShopCategoryInterface = customerSingleShopCategoryInterface;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_category_view, parent, false);
        return new CustomerSingleShopCategoryAdapter.ViewHolder(itemView, customerSingleShopCategoryInterface);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDataModel categoryDataModel = categoryDataModels.get(position);
        if (categoryDataModel.getCategoryImageURL() != null)
            Picasso.get().load(categoryDataModel.getCategoryImageURL()).fit().into(holder.circleImageViewCategoryImage);
        else
            Picasso.get().load(R.drawable.all).fit().into(holder.circleImageViewCategoryImage);
        holder.textViewCategoryName.setText(categoryDataModel.getCategoryName());

        final boolean isClicked = position == mClickedPosition;
        if (isClicked) {
//            holder.textViewCategoryName.setBackgroundColor(R.color.backgroundOrange);
            holder.circleImageViewCategoryImage.setBorderColor(R.color.colorPrimary);
            holder.circleImageViewCategoryImage.setBorderWidth(12);
            holder.textViewCategoryName.setTypeface(holder.textViewCategoryName.getTypeface(), Typeface.BOLD);
        } else {
//            holder.textViewCategoryName.setBackgroundColor(0);
            holder.circleImageViewCategoryImage.setBorderWidth(0);
            holder.textViewCategoryName.setTypeface(holder.textViewCategoryName.getTypeface(), Typeface.NORMAL);
        }
        if (selectedItem == position) {
//            holder.textViewCategoryName.setBackgroundColor(R.color.backgroundOrange);
            holder.circleImageViewCategoryImage.setBorderColor(R.color.colorPrimary);
            holder.circleImageViewCategoryImage.setBorderWidth(12);
            holder.textViewCategoryName.setTypeface(holder.textViewCategoryName.getTypeface(), Typeface.BOLD);
            previousClickedPosition = position;
        }

        holder.linearLayoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickedPosition = isClicked ? -1 : position;
                notifyItemChanged(previousClickedPosition);
                notifyItemChanged(position);

                customerSingleShopCategoryInterface.categoryClicked(categoryDataModel.getCategoryName());
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomerSingleShopCategoryInterface customerSingleShopCategoryInterface;

        @BindView(R.id.civ_category_image)
        CircleImageView circleImageViewCategoryImage;

        @BindView(R.id.tv_category_name)
        TextView textViewCategoryName;

        @BindView(R.id.ll_category)
        LinearLayout linearLayoutCategory;

        public ViewHolder(View itemView, CustomerSingleShopCategoryInterface customerSingleShopCategoryInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customerSingleShopCategoryInterface = customerSingleShopCategoryInterface;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface CustomerSingleShopCategoryInterface {
        void categoryClicked(String categoryName);
    }
}
