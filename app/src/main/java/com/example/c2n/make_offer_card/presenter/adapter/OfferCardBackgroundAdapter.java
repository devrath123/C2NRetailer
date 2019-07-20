package com.example.c2n.make_offer_card.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.c2n.R;
import com.example.c2n.core.GlideApp;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 13-07-2018.
 */

public class OfferCardBackgroundAdapter extends RecyclerView.Adapter<OfferCardBackgroundAdapter.MyViewHolder> implements View.OnClickListener {

    private OfferCardBackgroundAdapter.OfferBackgroundInterface offerBackgroundInterface;
    private ArrayList<StorageReference> bgImages;
    private Context context;

    public OfferCardBackgroundAdapter(Context context, ArrayList<StorageReference> bgImages, OfferCardBackgroundAdapter.OfferBackgroundInterface offerBackgroundInterface) {
        this.context = context;
        this.bgImages = bgImages;
        this.offerBackgroundInterface = offerBackgroundInterface;
    }

    @Override
    public OfferCardBackgroundAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_offercard_background, parent, false);
        return new OfferCardBackgroundAdapter.MyViewHolder(view, offerBackgroundInterface);
    }

    @Override
    public void onBindViewHolder(OfferCardBackgroundAdapter.MyViewHolder holder, int position) {
        GlideApp.with(context).load(bgImages.get(position)).into(holder.offerBackgroundImage);
//        Glide.with(context).download(new FirebaseImageLoader()).load(bgImages.get(position)).into(holder.offerBackgroundImage);
    }

    @Override
    public int getItemCount() {
        return bgImages.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OfferCardBackgroundAdapter.OfferBackgroundInterface offerBackgroundInterface;

        @BindView(R.id.iv_offercard_background)
        ImageView offerBackgroundImage;

        public MyViewHolder(View itemView, OfferCardBackgroundAdapter.OfferBackgroundInterface offerBackgroundInterface) {
            super(itemView);
            this.offerBackgroundInterface = offerBackgroundInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            offerBackgroundInterface.backgroundSelected(bgImages.get(pos), pos);
        }
    }

    public interface OfferBackgroundInterface {
        void backgroundSelected(StorageReference selectedBackground, int pos);
    }
}