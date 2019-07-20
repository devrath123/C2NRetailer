package com.example.c2n.add_product.presenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.OfferDataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roshan.nimje on 06-07-2018.
 */

public class OfferCardsAdapter extends RecyclerView.Adapter<OfferCardsAdapter.ViewHolder> {
    private List<OfferDataModel> offerDataModels;
    private OfferCardAdapterInterface offerCardAdapterInterface;
    Context context;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public OfferCardsAdapter(List<OfferDataModel> offerDataModels, Context context, OfferCardAdapterInterface offerCardAdapterInterface) {
        this.offerDataModels = offerDataModels;
        this.context = context;
        Log.d("OfferCardsAdapter_", "" + this.offerDataModels.size());
        this.offerCardAdapterInterface = offerCardAdapterInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_offer_card, parent, false);
        return new ViewHolder(v, offerCardAdapterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("OfferCardsAdapter_", "" + "in onBindViewHolder");
        OfferDataModel offerDataModel = offerDataModels.get(position);
        Log.d("OfferCardsAdapter_", "" + offerDataModel.toString());
        Boolean[] days = new Boolean[]{offerDataModel.isSun(), offerDataModel.isMon(), offerDataModel.isTue(), offerDataModel.isWed(), offerDataModel.isThu(), offerDataModel.isFri(), offerDataModel.isSat()};
        holder.textViewDiscountPercentage.setText(offerDataModels.get(position).getOfferDiscount() + "");
        if (offerDataModel.getOfferName() != null && !offerDataModel.getOfferName().equals(""))
            holder.offerCardName.setText(offerDataModel.getOfferName() + "");
        int discount = offerDataModels.get(position).getOfferDiscount();
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
        holder.textViewStartDate.setText("" + format.format(new Date(offerDataModel.getOfferStartDate().getTime())));
        holder.textViewEndDate.setText("" + format.format(new Date(offerDataModel.getOfferEndDate().getTime())));
        String offerDays = "";
        for (int i = 0; i <= 6; i++) {
            if (days[i] == true) {
//                setOfferDay(i, holder);
                offerDays = offerDays + setOfferDay(i);
            }
        }
        holder.textViewApplicableDays.setText("(" + offerDays.substring(0, offerDays.length() - 2) + ")");
        holder.offerCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerCardAdapterInterface.offerClicked(offerDataModel);
            }
        });


        if (offerDataModel.isOfferStatus()) {

            holder.offerStatus.setText("Active Now");
            holder.offerStatus.setTextColor(Color.GREEN);
//                holder.offerCardLayout.getBackground().setAlpha(100);
//                holder.activateOfferButton.setVisibility(View.VISIBLE);
//
//                holder.offerCardName.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//                holder.textViewDiscountPercentage.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//                holder.textViewStartDate.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//                holder.textViewEndDate.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//                holder.textViewApplicableDays.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//                holder.discountPercText.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//                holder.discountOffText.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
//                holder.dateToText.setTextColor(context.getResources().getColor(R.color.inactiveOffer));

        } else {
            holder.offerStatus.setText("Currently Not Active");
            holder.offerStatus.setTextColor(Color.RED);
        }

    }

    private void setOfferCardBackground(int offerRange, OfferCardsAdapter.ViewHolder holder) {
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

    public void setOfferCardTextColor(int color, OfferCardsAdapter.ViewHolder holder) {
        if (color == 1) {
            holder.textViewDiscountPercentage.setTextColor(Color.BLACK);
            holder.offerCardName.setTextColor(Color.BLACK);
            holder.textViewStartDate.setTextColor(Color.BLACK);
            holder.textViewEndDate.setTextColor(Color.BLACK);
            holder.textViewApplicableDays.setTextColor(Color.BLACK);
            holder.discountPercText.setTextColor(Color.BLACK);
            holder.discountOffText.setTextColor(Color.BLACK);
            holder.dateToText.setTextColor(Color.BLACK);
        } else if (color == 2) {
            holder.textViewDiscountPercentage.setTextColor(Color.WHITE);
            holder.offerCardName.setTextColor(Color.WHITE);
            holder.textViewStartDate.setTextColor(Color.WHITE);
            holder.textViewEndDate.setTextColor(Color.WHITE);
            holder.textViewApplicableDays.setTextColor(Color.WHITE);
            holder.discountPercText.setTextColor(Color.WHITE);
            holder.discountOffText.setTextColor(Color.WHITE);
            holder.dateToText.setTextColor(Color.WHITE);
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
        return offerDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OfferCardAdapterInterface offerCardAdapterInterface;
        @BindView(R.id.tv_offercard_discount_perc)
        TextView textViewDiscountPercentage;

        @BindView(R.id.tv_offercard_startdate)
        TextView textViewStartDate;

        @BindView(R.id.tv_offercard_enddate)
        TextView textViewEndDate;

        @BindView(R.id.tv_offercard_applicable_days)
        TextView textViewApplicableDays;

        @BindView(R.id.tv_offer_date_to_text)
        TextView dateToText;

        @BindView(R.id.tv_discount_perc_text)
        TextView discountPercText;

        @BindView(R.id.tv_discount_off_text)
        TextView discountOffText;

        @BindView(R.id.layout_home_offer_card)
        CardView offerCardLayout;

        @BindView(R.id.tv_offerCardName)
        TextView offerCardName;

        @BindView(R.id.tv_offer_activeStatus)
        TextView offerStatus;

        public ViewHolder(View itemView, OfferCardAdapterInterface offerCardAdapterInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.offerCardAdapterInterface = offerCardAdapterInterface;
        }
    }

    public interface OfferCardAdapterInterface {
        void offerClicked(OfferDataModel offerDataModel);
    }
}
