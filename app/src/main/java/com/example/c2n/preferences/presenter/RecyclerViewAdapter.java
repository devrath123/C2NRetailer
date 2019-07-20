package com.example.c2n.preferences.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.model.CategoryDataModel;

import java.util.List;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<CategoryDataModel> categoryDataModels;
    private MyInterface myInterface;
    private Context context;

    public RecyclerViewAdapter(Context context, MyInterface myInterface, List<CategoryDataModel> categoryDataModels) {
        this.myInterface = myInterface;
        this.categoryDataModels = categoryDataModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_preferences, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CategoryDataModel categoryDataModel = categoryDataModels.get(position);
        holder.textView.setText(categoryDataModel.getCategory());
//        Picasso.with(context).load(categoryDataModel.getPicURL()).into(holder.imageView);
//        holder.view.setBackgroundColor(categoryDataModel.isSelected() ? Color.CYAN : Color.WHITE);
        holder.cardView.setCardBackgroundColor(categoryDataModel.isSelected() ? Color.CYAN : Color.WHITE);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDataModel.setSelected(!categoryDataModel.isSelected());
//                holder.view.setBackgroundColor(categoryDataModel.isSelected() ? Color.CYAN : Color.WHITE);
                holder.cardView.setCardBackgroundColor(categoryDataModel.isSelected() ? Color.CYAN : Color.WHITE);
                myInterface.addCategory(categoryDataModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDataModels == null ? 0 : categoryDataModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;
        //        private ImageView imageView;
        private CardView cardView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = (TextView) itemView.findViewById(R.id.text_view);
//            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public interface MyInterface {
        public void addCategory(CategoryDataModel categoryDataModel);
    }
}