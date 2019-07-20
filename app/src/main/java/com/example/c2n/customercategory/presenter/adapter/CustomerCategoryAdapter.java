package com.example.c2n.customercategory.presenter.adapter;

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
import com.example.c2n.core.models.CategoryDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivani.singh on 17-08-2018.
 */

public class CustomerCategoryAdapter extends RecyclerView.Adapter<CustomerCategoryAdapter.MyViewHolder> implements View.OnClickListener{

    Context context;
    List<CategoryDataModel>categoryDataModels;
    CustomerCategoryInterface customerCategoryInterface;

    @Inject
    public CustomerCategoryAdapter(Context context, List<CategoryDataModel> categoryDataModels,CustomerCategoryInterface customerCategoryInterface) {
        this.context = context;
        this.categoryDataModels = categoryDataModels;
        this.customerCategoryInterface = customerCategoryInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_customer_category,parent,false);
        return new MyViewHolder(view,customerCategoryInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryDataModel category = categoryDataModels.get(position);
        holder.categoryName.setText(category.getCategoryName());
        Picasso.get().load(category.getCategoryImageURL()).placeholder(R.drawable.white_background).into(holder.imageView);
        holder.imageView.setColorFilter(R.color.bgImageTransparent);
        Log.d("onBindViewHolder",""+categoryDataModels.size());

    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount()",""+categoryDataModels.size());
        return categoryDataModels.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.category_imageview)
        ImageView imageView;

        @BindView(R.id.tv_category_name)
        TextView categoryName;
        CustomerCategoryInterface customerCategoryInterface;


        public MyViewHolder(View itemView,CustomerCategoryInterface customerCategoryInterface) {
            super(itemView);
            this.customerCategoryInterface = customerCategoryInterface;
            ButterKnife.bind(this,itemView);


            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int pos =getAdapterPosition();
            customerCategoryInterface.categoryClicked(categoryDataModels.get(pos));
            Log.d("CategoryInterface",""+categoryDataModels.get(pos));
        }
    }

public interface CustomerCategoryInterface {
    void categoryClicked(CategoryDataModel category);
}

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}