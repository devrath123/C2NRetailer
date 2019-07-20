package com.example.c2n.offer_cards_list.presenter.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.models.OfferDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivani.singh on 29-06-2018.
 */

public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.MyViewHolder> implements View.OnClickListener {


    List<OfferDataModel> offerDataModels;
    Context context;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private final OffersListAdapter.OfferCardSelected offerCardInterface;
    Date todayDate;


    @Inject
    public OffersListAdapter(List<OfferDataModel> offerDataModels, Context context, OffersListAdapter.OfferCardSelected offerCardInterface) {
        this.offerDataModels = offerDataModels;
        this.context = context;
        this.offerCardInterface = offerCardInterface;
        Collections.sort(this.offerDataModels);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_offers, parent, false);

        return new OffersListAdapter.MyViewHolder(view, offerCardInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OfferDataModel offerDataModel = offerDataModels.get(position);
        holder.discountPrice.setText(offerDataModel.getOfferDiscount() + "");
        if (offerDataModel.getOfferName() != null && !offerDataModel.getOfferName().equals("")) {
            holder.offerCardName.setVisibility(View.VISIBLE);
            holder.offerCardName.setText(offerDataModel.getOfferName() + "");
        }
        holder.offerDate.setText(format.format(offerDataModel.getOfferStartDate()) + " to " + format.format(offerDataModel.getOfferEndDate()));

        Boolean[] days = new Boolean[]{offerDataModel.isSun(), offerDataModel.isMon(), offerDataModel.isTue(), offerDataModel.isWed(), offerDataModel.isThu(), offerDataModel.isFri(), offerDataModel.isSat()};
        String offerDays = "";
        for (int i = 0; i <= 6; i++) {
            if (days[i] == true) {
                offerDays = offerDays + setOfferDay(i, holder);
            }
        }
        holder.offerDay.setText(offerDays.substring(0, offerDays.length() - 2));
//        holder.offerDay.setText(offerDays);


        int discount = offerDataModel.getOfferDiscount();
        if (discount >= 0 && discount <= 10)
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

        try {
            todayDate = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int today = new Date().getDay();
//        if (isOfferCardActive(today, offerDataModel)) {
//            if (todayDate != null && offerDataModel.getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(offerDataModel.getOfferEndDate()) >= 0) {
////                holder.activeOfferIndicator.setVisibility(View.VISIBLE);
////                holder.totalProducts.setTypeface(null, Typeface.BOLD);
////                holder.activateOfferButton.setVisibility(View.GONE);
//
//            }

        if (offerDataModel.isOfferStatus()) {
//            holder.productCountLayout.setVisibility(View.VISIBLE);
//            holder.productCountLayout.setBackgroundColor(context.getResources().getColor(R.color.themecolor));
            holder.activateOfferButton.setVisibility(View.GONE);

//            holder.totalProductsImage.setVisibility(View.VISIBLE);
        } else {
            holder.offerCardLayout.getBackground().setAlpha(70);
            holder.productCountLayout.setAlpha((float) 0.27);
            holder.activateOfferButton.setVisibility(View.VISIBLE);
//            holder.totalProductsImage.setVisibility(View.VISIBLE);
//            holder.totalProductsImage.setAlpha(70);
//            holder.offerCardName.setAlpha((float) 0.35);
//            holder.offerDate.setAlpha((float) 0.35);
//            holder.offerDay.setAlpha((float) 0.35);
//            holder.discountPrice.setAlpha((float) 0.35);
            holder.offerCardName.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
            holder.offerDate.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
            holder.offerDay.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
            holder.discountPrice.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
            holder.textOFF.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
            holder.textPERC.setTextColor(context.getResources().getColor(R.color.inactiveOffer));
        }

        holder.activateOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle("Do you really want to activate this Offer now?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        offerCardInterface.activateOfferCard(offerDataModel);
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }

        });

        if (offerDataModel.getOfferEndDate().before(new Date())) {
            holder.offerCardLayout.getBackground().setAlpha(70);
            holder.productCountLayout.setAlpha((float) 0.27);
            holder.activateOfferButton.setVisibility(View.VISIBLE);
            holder.activateOfferButton.setText("Offer Expired");
            holder.activateOfferButton.setClickable(false);
            holder.itemView.setClickable(false);
        }


//            holder.totalProducts.setText("Not Active");

        holder.editOfferCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.editOfferCard);
                popupMenu.inflate(R.menu.edit_offercard);

                if (!offerDataModel.isOfferStatus()) {
                    popupMenu.getMenu().findItem(R.id.deactivate_offer_card).setVisible(false);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_offer_card: {

                                offerCardInterface.editOfferCard(offerDataModel);
                                Log.d("menu", "edit_offercard");
                                break;
                            }
                            case R.id.delete_offer_card: {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                                alertDialog.setTitle("Delete this Offer Card permanently?");
                                alertDialog.setMessage("This Offer Card will automatically remove from all Products.");

                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        offerCardInterface.deleteOfferCard(offerDataModel);

                                    }
                                });

                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                alertDialog.show();
                                break;
                            }
                            case R.id.deactivate_offer_card: {
                                if (holder.activateOfferButton.getVisibility() == View.VISIBLE) {
                                    Toast.makeText(context, "Offer Already Deactivated.", Toast.LENGTH_SHORT).show();
                                } else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                                    alertDialog.setTitle("Do you really want to Deactivate this Offer?");

                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            offerCardInterface.deactivateOfferCard(offerDataModel);

                                        }

                                    });

                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    alertDialog.show();
                                }
                                break;
                            }
                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });

//
//        if (offerDataModel.getOfferProducts() != null) {
//            int count = 0;
//            List<HashMap<String, List<String>>> offerProducts = offerDataModel.getOfferProducts();
//            for (int i = 0; i < offerProducts.size(); i++) {
//                HashMap<String, List<String>> shopID = offerProducts.get(i);
//                Set<String> shops = shopID.keySet();
//                Log.d("count__", "" + shops.toString());
//                for (String s : shops) {
//                    List<String> productIDs = shopID.get(s);
//                    count += productIDs.size();
//                }
//            }
//            if (count != 0) {
//                Log.d("products_count-", count + "");
//                holder.productCountLayout.setVisibility(View.VISIBLE);
//                holder.textViewProductCount.setText(count + " Products");
//            }
//        }
//


    }

    private void setOfferCardBackground(int offerRange, MyViewHolder myViewHolder) {
        switch (offerRange) {
            case 1: {
                setOfferCardTextColor(1, myViewHolder);
                myViewHolder.offerCardLayout.setBackgroundResource(R.drawable.offer1_background_gradient);
                return;
            }
            case 2: {
                setOfferCardTextColor(1, myViewHolder);
                myViewHolder.offerCardLayout.setBackgroundResource(R.drawable.offer2_background_gradient);
                return;
            }
            case 3: {
                setOfferCardTextColor(1, myViewHolder);
                myViewHolder.offerCardLayout.setBackgroundResource(R.drawable.offer3_background_gradient);
                return;
            }
            case 4: {
                setOfferCardTextColor(1, myViewHolder);
                myViewHolder.offerCardLayout.setBackgroundResource(R.drawable.offer4_background_gradient);
                return;
            }
            case 5: {
                setOfferCardTextColor(2, myViewHolder);
                myViewHolder.offerCardLayout.setBackgroundResource(R.drawable.offer5_background_gradient);
                return;
            }
            case 6: {
                setOfferCardTextColor(2, myViewHolder);
                myViewHolder.offerCardLayout.setBackgroundResource(R.drawable.offer6_background_gradient);
                return;
            }
        }

    }

    public void setOfferCardTextColor(int color, MyViewHolder myViewHolder) {
        if (color == 1) {
            myViewHolder.discountPrice.setTextColor(Color.BLACK);
            myViewHolder.offerDate.setTextColor(Color.BLACK);
            myViewHolder.offerDay.setTextColor(Color.BLACK);
            myViewHolder.offerCardName.setTextColor(Color.BLACK);
//            myViewHolder.totalProducts.setTextColor(Color.BLACK);
            myViewHolder.textOFF.setTextColor(Color.BLACK);
            myViewHolder.textPERC.setTextColor(Color.BLACK);
        } else if (color == 2) {
            myViewHolder.discountPrice.setTextColor(Color.WHITE);
            myViewHolder.offerDate.setTextColor(Color.WHITE);
            myViewHolder.offerDay.setTextColor(Color.WHITE);
            myViewHolder.offerCardName.setTextColor(Color.WHITE);
//            myViewHolder.totalProducts.setTextColor(Color.WHITE);
            myViewHolder.textOFF.setTextColor(Color.WHITE);
            myViewHolder.textPERC.setTextColor(Color.WHITE);
        }
    }


    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
            case 1:
                if (offerCard.isMon())
                    return true;
            case 2:
                if (offerCard.isTue())
                    return true;
            case 3:
                if (offerCard.isWed())
                    return true;
            case 4:
                if (offerCard.isThu())
                    return true;
            case 5:
                if (offerCard.isFri())
                    return true;
            case 6:
                if (offerCard.isSat())
                    return true;
        }
        return false;
    }

    public String setOfferDay(int i, MyViewHolder holder) {
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

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OffersListAdapter.OfferCardSelected offerCardInterface;

        @BindView(R.id.tv_product_count)
        TextView textViewProductCount;

        @BindView(R.id.text_discount_price)
        TextView discountPrice;

        @BindView(R.id.test_date)
        TextView offerDate;

        @BindView(R.id.test_day)
        TextView offerDay;
        //        @BindView(R.id.iv_total_offer_products)
//        ImageView totalProductsImage;
        @BindView(R.id.tv_offercard_name)
        TextView offerCardName;
//        @BindView(R.id.iv_active_offer)
//        ImageView activeOfferIndicator;

        //        @BindView(R.id.tv_offercard_total_products)
//        TextView totalProducts;
        @BindView(R.id.bt_activate_offercard)
        Button activateOfferButton;

        @BindView(R.id.layout_offerCard)
        FrameLayout layoutOfferCard;

        @BindView(R.id.layout_list_offer_card)
        LinearLayout offerCardLayout;

        @BindView(R.id.tv_text_offercard_OFF)
        TextView textOFF;

        @BindView(R.id.tv_text_offercard_PERC)
        TextView textPERC;

        @BindView(R.id.iv_edit_offer_card)
        ImageView editOfferCard;

        @BindView(R.id.layout_product_count)
        LinearLayout productCountLayout;

        public MyViewHolder(View itemView, OffersListAdapter.OfferCardSelected offerCardInterface) {
            super(itemView);
            this.offerCardInterface = offerCardInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("offer_selcted-", offerDataModels.get(pos).toString());
            offerCardInterface.offerCardSelected(offerDataModels.get(pos));
        }
    }

    public interface OfferCardSelected {
        void offerCardSelected(OfferDataModel offerDataModel);

        void editOfferCard(OfferDataModel offerDataModel);

        void deleteOfferCard(OfferDataModel offerDataModel);

        void activateOfferCard(OfferDataModel offerDataModel);

        void deactivateOfferCard(OfferDataModel offerDataModel);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
