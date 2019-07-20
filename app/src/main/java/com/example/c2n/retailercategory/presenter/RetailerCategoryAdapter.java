package com.example.c2n.retailercategory.presenter;

import android.content.Context;
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

/**
 * Created by roshan.nimje on 01-06-2018.
 */

public class RetailerCategoryAdapter extends RecyclerView.Adapter<RetailerCategoryAdapter.MyViewHolder> implements View.OnClickListener {

    private static final String TAG = RetailerCategoryAdapter.class.getSimpleName();

    private RetailerCategoryInterface retailerCategoryInterface;
    private List<CategoryDataModel> categories;
    private Context context;

    public RetailerCategoryAdapter(List<CategoryDataModel> categories, Context context, RetailerCategoryInterface retailerCategoryInterface) {
        this.categories = categories;
        this.context = context;
        this.retailerCategoryInterface = retailerCategoryInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demo_recyclearview_item_view, parent, false);
        return new MyViewHolder(itemView, retailerCategoryInterface);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryDataModel category = categories.get(position);
        holder.label.setText(category.getCategoryName());
        Picasso.get().load(category.getCategoryImageURL()).placeholder(R.drawable.white_background).fit().into(holder.imageView);
//        Picasso.with(context).load(R.drawable.white_background).into(holder.imageView);
        holder.imageView.setColorFilter(R.color.bgImageTransparent);
//        holder.imageView.setImageResource(categoryDataModel.getPicURL());
//        Blurry.with(context).from(BitmapFactory.decodeResource(context.getResources(),
//                demoPojoClass.getImage())).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView label;
        public ImageView imageView;
        private final RetailerCategoryInterface retailerCategoryInterface;

        public MyViewHolder(View itemView, RetailerCategoryInterface retailerCategoryInterface) {
            super(itemView);
            this.retailerCategoryInterface = retailerCategoryInterface;
            label = itemView.findViewById(R.id.textview);
            imageView = itemView.findViewById(R.id.imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            retailerCategoryInterface.categoryClicked(categories.get(pos));
        }
    }

    public interface RetailerCategoryInterface {
        //        void categoryClicked(CategoryDataModel categoryDataModel);
        void categoryClicked(CategoryDataModel category);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void clear() {
        if (categories != null) {
            categories.clear();
            Log.d(TAG, "clear: " + categories.size());
        }
        notifyDataSetChanged();
    }
}
