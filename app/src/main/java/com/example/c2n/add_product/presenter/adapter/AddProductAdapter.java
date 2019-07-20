package com.example.c2n.add_product.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.model1.CategoryDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 29-05-2018.
 */

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.MyViewHolder> implements View.OnClickListener {

    private List<CategoryDataModel> categories;
    private Context context;
    AddProductAdapter.CategoryRowInterface categoryRowInterface;

    public AddProductAdapter(List<CategoryDataModel> categories, Context context, AddProductAdapter.CategoryRowInterface categoryRowInterface) {
        this.categories = categories;
        this.context = context;
        this.categoryRowInterface = categoryRowInterface;
        Log.d("HomeFragmentAdapter", "count : " + categories.size());
    }

    @Override
    public AddProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_product_category, parent, false);

        return new AddProductAdapter.MyViewHolder(itemView, categoryRowInterface);
    }

    @Override
    public void onBindViewHolder(AddProductAdapter.MyViewHolder holder, int position) {
        Log.d("category_list_holdr", categories.toString());
        CategoryDataModel categoryDataModel = categories.get(position);
        Log.d("category_data_n", "" + categoryDataModel.getCategoryName());
        Log.d("category_data_i", "" + categoryDataModel.getCategoryImageURL());

        holder.categoryName.setText(categoryDataModel.getCategoryName());
        Picasso.get().load(categoryDataModel.getCategoryImageURL()).fit().into(holder.categoryIamage);
    }

    @Override
    public int getItemCount() {
        Log.d("category_size", categories.size() + "");
        return categories.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AddProductAdapter.CategoryRowInterface categoryRowInterface;
        @BindView(R.id.item_product_category_image)
        ImageView categoryIamage;
        @BindView(R.id.item_product_category_name)
        TextView categoryName;

        public MyViewHolder(View itemView, AddProductAdapter.CategoryRowInterface categoryRowInterface) {
            super(itemView);
//            categoryIamage = itemView.findViewById(R.id.imageview_item_home);
//            categoryName = itemView.findViewById(R.id.textview_item_home);
            this.categoryRowInterface = categoryRowInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            categoryRowInterface.categoryClicked(categories.get(pos));
        }
    }

    public interface CategoryRowInterface {
        void categoryClicked(CategoryDataModel categoryDataModel);
    }

}
