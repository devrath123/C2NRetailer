package com.example.c2n.addproduct.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.CategoryDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductCategoryAdapter extends RecyclerView.Adapter<AddProductCategoryAdapter.MyViewHolder> {

    private AddProductCategoryInterface addProductCategoryInterface;
    private List<CategoryDataModel> categoryDataModels;
    private Context context;

    public AddProductCategoryAdapter(List<CategoryDataModel> categoryDataModels, Context context, AddProductCategoryInterface addProductCategoryInterface) {
        this.categoryDataModels = categoryDataModels;
        this.context = context;
        this.addProductCategoryInterface = addProductCategoryInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.retailer_category_view, parent, false);
        return new AddProductCategoryAdapter.MyViewHolder(itemView, addProductCategoryInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryDataModel categoryDataModel = categoryDataModels.get(position);
        holder.textViewCategoryName.setText(categoryDataModel.getCategoryName());
        if (categoryDataModel.getCategoryImageURL() != null)
            Picasso.get().load(categoryDataModel.getCategoryImageURL()).fit().into(holder.circleImageViewCategoryImage);
        holder.cardViewLayoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductCategoryInterface.categoryClicked(categoryDataModel.getCategoryName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDataModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AddProductCategoryInterface addProductCategoryInterface;

        @BindView(R.id.civ_category_image)
        CircleImageView circleImageViewCategoryImage;

        @BindView(R.id.tv_category_name)
        TextView textViewCategoryName;

        @BindView(R.id.cv_category)
        CardView cardViewLayoutCategory;

        public MyViewHolder(View itemView, AddProductCategoryInterface addProductCategoryInterface) {
            super(itemView);
            this.addProductCategoryInterface = addProductCategoryInterface;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface AddProductCategoryInterface {
        void categoryClicked(String category);
    }
}
