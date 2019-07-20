package com.example.c2n.customer_offer_cards.presenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferDetailsDataModel;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class CustomerOfferAdapter extends RecyclerView.Adapter<CustomerOfferAdapter.ViewHolder> {
    private List<OfferDetailsDataModel> offerDetailsDataModels;
    private OfferCardAdapterInterface offerCardAdapterInterface;
    Context context;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    @Inject
    public CustomerOfferAdapter(List<OfferDetailsDataModel> offerDetailsDataModels, Context context, OfferCardAdapterInterface offerCardAdapterInterface) {
        this.offerDetailsDataModels = offerDetailsDataModels;
        this.context = context;
        Log.d("OfferCardsAdapter_", "" + this.offerDetailsDataModels.size());
        this.offerCardAdapterInterface = offerCardAdapterInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.todays_offer_card, parent, false);
        return new ViewHolder(v, offerCardAdapterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("OfferCardsAdapter", "" + "in onBindViewHolder");
        OfferDetailsDataModel offerDetailsDataModel = offerDetailsDataModels.get(position);
        Log.d("OfferCardsAdapter_", "" + offerDetailsDataModel.toString());
        Boolean[] days = new Boolean[]{offerDetailsDataModel.getOfferDataModel().isSun(), offerDetailsDataModel.getOfferDataModel().isMon(), offerDetailsDataModel.getOfferDataModel().isTue(), offerDetailsDataModel.getOfferDataModel().isWed(), offerDetailsDataModel.getOfferDataModel().isThu(), offerDetailsDataModel.getOfferDataModel().isFri(), offerDetailsDataModel.getOfferDataModel().isSat()};
        holder.textViewDiscountPercentage.setText(offerDetailsDataModel.getOfferDataModel().getOfferDiscount() + "");
        if (offerDetailsDataModel.getOfferDataModel().getOfferName() != null && !offerDetailsDataModel.getOfferDataModel().getOfferName().equals(""))
            holder.offerCardName.setText(offerDetailsDataModel.getOfferDataModel().getOfferName() + "");
        int discount = offerDetailsDataModel.getOfferDataModel().getOfferDiscount();
        if (discount > 0 && discount <= 10)
            setOfferCardBackground(1, holder);
        else {
            if (discount > 10 && discount <= 20)
                setOfferCardBackground(2, holder);
            else {
                if (discount > 20 && discount <= 30)
                    setOfferCardBackground(3, holder);
                else {
                    if (discount > 30 && discount <= 50)
                        setOfferCardBackground(4, holder);
                    else {
                        if (discount > 50 && discount <= 75)
                            setOfferCardBackground(5, holder);
                        else {
                            if (discount > 75 && discount <= 100)
                                setOfferCardBackground(6, holder);
                        }
                    }
                }
            }
        }
//        Picasso.with(context).load(offerListDataModels.get(position).getUserPhotoUrl()).into(retailerPic);

        String offerDays = "";
        for (int i = 0; i <= 6; i++) {
            if (days[i] == true) {
                offerDays = offerDays + setOfferDay(i);
            }
        }
        holder.offerCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerCardAdapterInterface.offerClicked(offerDetailsDataModel);
            }
        });



    }

    private void setOfferCardBackground(int offerRange, ViewHolder holder) {
        switch (offerRange) {
            case 1: {
                setOfferCardTextColor(1, holder);
                holder.offerCardLayout.setBackgroundResource(R.drawable.offer1_background_gradient);
                return;
            }
            case 2: {
                setOfferCardTextColor(1, holder);
                holder.offerCardLayout.setBackgroundResource(R.drawable.offer2_background_gradient);
                return;
            }
            case 3: {
                setOfferCardTextColor(1, holder);
                holder.offerCardLayout.setBackgroundResource(R.drawable.offer3_background_gradient);
                return;
            }
            case 4: {
                setOfferCardTextColor(1, holder);
                holder.offerCardLayout.setBackgroundResource(R.drawable.offer4_background_gradient);
                return;
            }
            case 5: {
                setOfferCardTextColor(2, holder);
                holder.offerCardLayout.setBackgroundResource(R.drawable.offer5_background_gradient);
                return;
            }
            case 6: {
                setOfferCardTextColor(2, holder);
                holder.offerCardLayout.setBackgroundResource(R.drawable.offer6_background_gradient);
                return;
            }
        }

    }

    public void setOfferCardTextColor(int color, ViewHolder holder) {
        if (color == 1) {
            holder.textViewDiscountPercentage.setTextColor(Color.BLACK);
            holder.offerCardName.setTextColor(Color.BLACK);
            holder.discountPercText.setTextColor(Color.BLACK);
            holder.discountOffText.setTextColor(Color.BLACK);
        } else if (color == 2) {
            holder.textViewDiscountPercentage.setTextColor(Color.WHITE);
            holder.offerCardName.setTextColor(Color.WHITE);
            holder.discountPercText.setTextColor(Color.WHITE);
            holder.discountOffText.setTextColor(Color.WHITE);
        }
    }

    public String setOfferDay(int i) {
        switch (i) {
            case 0:
                return "Sun, ";

            case 1:
                return "Mon, ";

            case 2:
                return "Tue, ";

            case 3:
                return "Wed, ";

            case 4:
                return "Thu, ";

            case 5:
                return "Fri, ";

            case 6:
                return "Sat, ";
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {

        return offerDetailsDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OfferCardAdapterInterface offerCardAdapterInterface;

        @BindView(R.id.tv_todays_offercard_discount_perc)
        TextView textViewDiscountPercentage;

        @BindView(R.id.tv_discount_perc_text)
        TextView discountPercText;

        @BindView(R.id.tv_discount_off_text)
        TextView discountOffText;

        @BindView(R.id.layout_todays_offer_card)
        CardView offerCardLayout;

        @BindView(R.id.tv_todays_offerCardName)
        TextView offerCardName;

//        @BindView(R.id.bt_todays_offer_total_products)
//        Button totalProducts;


        public ViewHolder(View itemView, OfferCardAdapterInterface offerCardAdapterInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.offerCardAdapterInterface = offerCardAdapterInterface;
        }
    }

    public interface OfferCardAdapterInterface {
        void offerClicked(OfferDetailsDataModel offerDetailsDataModel);
    }


}

