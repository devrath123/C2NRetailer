package com.example.c2n.retailerhome.presenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.models.OfferDataModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 02-07-2018.
 */

public class RetailerOfferCardsAdapter extends PagerAdapter {
    List<OfferDataModel> offerDataModels;
    Context context;
    LayoutInflater layoutInflater;

    @BindView(R.id.tv_offercard_offername)
    TextView offerCardName;

    @BindView(R.id.tv_offercard_discount_perc)
    TextView discountPercentage;

    @BindView(R.id.tv_offercard_startdate)
    TextView startDate;

    @BindView(R.id.tv_offercard_enddate)
    TextView endDate;

    @BindView(R.id.tv_offercard_applicable_days)
    TextView offerDaysText;

    @BindView(R.id.tv_discount_perc_text)
    TextView discountPercText;

    @BindView(R.id.tv_discount_off_text)
    TextView discountOffText;

    @BindView(R.id.tv_offer_date_to_text)
    TextView dateToText;

    @BindView(R.id.layout_home_offer_card)
    FrameLayout offerCardLayout;

    @BindView(R.id.btn_card_offer_explore)
    Button exploreButton;

    private OfferCardInterface offerCardInterface;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    String[] imageReferenceNames = {"offer0.jpg", "offer1.png", "offer2.jpg", "offer3.jpg", "offer4.jpg", "offer5.jpg", "offer6.jpg", "offer7.jpg", "offer8.jpg", "offer9.jpg", "offer10.jpg", "offer12.jpg", "offer13.jpg", "offer14.jpg"};
    private StorageReference mStorage;

    @Inject
    public RetailerOfferCardsAdapter(List<OfferDataModel> offerDataModels, Context context, OfferCardInterface offerCardInterface) {
        this.offerDataModels = offerDataModels;
        this.offerCardInterface = offerCardInterface;
        this.context = context;
        Collections.sort(this.offerDataModels, OfferDataModel.offerDiscountDescendingComparator);
        mStorage = FirebaseStorage.getInstance().getReference().child("offer_templates");
    }

    @Override
    public int getCount() {
        Log.d("getCount", "" + offerDataModels.size());
        return offerDataModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View offerCardsLayout = layoutInflater.inflate(R.layout.view_pager_home_offers, view, false);
        ButterKnife.bind(this, offerCardsLayout);
        OfferDataModel offerDataModel = offerDataModels.get(position);
        Boolean[] days = new Boolean[]{offerDataModel.isSun(), offerDataModel.isMon(), offerDataModel.isTue(), offerDataModel.isWed(), offerDataModel.isThu(), offerDataModel.isFri(), offerDataModel.isSat()};
        Log.d("", "" + offerDataModels.size());
        if (offerDataModels.get(position).getOfferName() != null && !offerDataModels.get(position).getOfferName().equals(""))
            offerCardName.setText(offerDataModels.get(position).getOfferName());
        else
            offerCardName.setVisibility(View.GONE);
        discountPercentage.setText(offerDataModels.get(position).getOfferDiscount() + "");
        int discount = offerDataModels.get(position).getOfferDiscount();
//        int backgroundPosition = offerDataModels.get(position).getOfferBackground();
//        Log.d("back_position", backgroundPosition + "");

        if (discount > 0 && discount <= 10)
            setOfferCardBackground(1);
        else {
            if (discount > 10 && discount <= 20)
                setOfferCardBackground(2);
            else {
                if (discount > 20 && discount <= 30)
                    setOfferCardBackground(3);
                else {
                    if (discount > 30 && discount <= 50)
                        setOfferCardBackground(4);
                    else {
                        if (discount > 50 && discount <= 75)
                            setOfferCardBackground(5);
                        else {
                            if (discount > 75 && discount <= 100)
                                setOfferCardBackground(6);
                        }
                    }
                }
            }
        }
//        else {
//            Glide.with(context).using(new FirebaseImageLoader()).load(mStorage.child(imageReferenceNames[backgroundPosition])).asBitmap().into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    Drawable drawable = new BitmapDrawable(context.getResources(), resource);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                        offerCardLayout.setBackground(drawable);
//                    setCustomOfferCardTextColor(backgroundPosition);
//                }
//
//            });
//        }
//        Picasso.with(context).load(offerListDataModels.get(position).getUserPhotoUrl()).into(retailerPic);
        startDate.setText("" + format.format(new Date(offerDataModel.getOfferStartDate().getTime())));
        endDate.setText("" + format.format(new Date(offerDataModel.getOfferEndDate().getTime())));
        String offerDays = "";
        for (int i = 0; i <= 6; i++) {
            if (days[i] == true) {
//                setOfferDay(i, holder);
                offerDays = offerDays + setOfferDay(i);
            }
        }
        offerDaysText.setText("(" + offerDays.substring(0, offerDays.length() - 2) + ")");

        view.addView(offerCardsLayout, 0);

        offerCardsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("offerCard_clicked", offerDataModels.get(position).toString());
                offerCardInterface.offerCardClicked(offerDataModel);
            }
        });
//        Log.d("sliderViewPager", offerListDataModels.get(position).getUserName() + "---" + retailerDataModels.get(position).getUserPhotoUrl() + "----" + retailerDataModels.get(position).getUserSuccessStory());
        return offerCardsLayout;
    }

    private void setCustomOfferCardTextColor(int position) {
        switch (position) {
            case 1: {
                setOfferCardTextColor(2);
                break;
            }
            case 2: {
                setOfferCardTextColor(1);
                break;
            }
            case 3: {
                setOfferCardTextColor(1);
                break;
            }
            case 4: {
                setOfferCardTextColor(3);
                break;
            }
            case 5: {
                setOfferCardTextColor(2);
                break;
            }
            case 6: {
                setOfferCardTextColor(4);
                break;
            }
            case 7: {
                setOfferCardTextColor(5);
                break;
            }
            case 8: {
                setOfferCardTextColor(6);
                break;
            }
            case 9: {
                setOfferCardTextColor(1);
                break;
            }
            case 10: {
                setOfferCardTextColor(7);
                break;
            }
            case 11: {
                setOfferCardTextColor(1);
                break;
            }
            case 12: {
                setOfferCardTextColor(8);
                break;
            }
            case 13: {
                setOfferCardTextColor(8);
                break;
            }
        }
    }

    public void setOfferCardTextColor(int color) {
        if (color == 1) {
            offerCardName.setTextColor(Color.BLACK);
            discountPercentage.setTextColor(Color.BLACK);
            startDate.setTextColor(Color.BLACK);
            endDate.setTextColor(Color.BLACK);
            offerDaysText.setTextColor(Color.BLACK);
            discountPercText.setTextColor(Color.BLACK);
            discountOffText.setTextColor(Color.BLACK);
            dateToText.setTextColor(Color.BLACK);
        } else if (color == 2) {
            offerCardName.setTextColor(Color.WHITE);
            discountPercentage.setTextColor(Color.WHITE);
            startDate.setTextColor(Color.WHITE);
            endDate.setTextColor(Color.WHITE);
            offerDaysText.setTextColor(Color.WHITE);
            discountPercText.setTextColor(Color.WHITE);
            discountOffText.setTextColor(Color.WHITE);
            dateToText.setTextColor(Color.WHITE);
        }


        switch (color) {
            case 1: {
                offerCardName.setTextColor(Color.BLACK);
                discountPercentage.setTextColor(Color.BLACK);
                discountOffText.setTextColor(Color.BLACK);
                discountPercText.setTextColor(Color.BLACK);
                startDate.setTextColor(Color.BLACK);
                endDate.setTextColor(Color.BLACK);
                dateToText.setTextColor(Color.BLACK);
                offerDaysText.setTextColor(Color.BLACK);
                break;
            }
            case 2: {
                offerCardName.setTextColor(Color.WHITE);
                discountPercentage.setTextColor(Color.WHITE);
                discountPercText.setTextColor(Color.WHITE);
                discountOffText.setTextColor(Color.WHITE);
                startDate.setTextColor(Color.WHITE);
                endDate.setTextColor(Color.WHITE);
                dateToText.setTextColor(Color.WHITE);
                offerDaysText.setTextColor(Color.WHITE);
                break;
            }
            case 3: {
                offerCardName.setTextColor(context.getResources().getColor(R.color.offerNameColor));
                discountPercentage.setTextColor(Color.BLACK);
                discountOffText.setTextColor(Color.BLACK);
                discountPercText.setTextColor(Color.BLACK);
                startDate.setTextColor(Color.BLACK);
                endDate.setTextColor(Color.BLACK);
                dateToText.setTextColor(Color.BLACK);
                offerDaysText.setTextColor(Color.BLACK);
                break;
            }
            case 4: {
                offerCardName.setTextColor(Color.WHITE);
                discountPercentage.setTextColor(Color.WHITE);
                discountPercText.setTextColor(Color.WHITE);
                discountOffText.setTextColor(Color.WHITE);
                startDate.setTextColor(Color.BLACK);
                endDate.setTextColor(Color.BLACK);
                dateToText.setTextColor(Color.BLACK);
                offerDaysText.setTextColor(Color.BLACK);
                break;
            }
            case 5: {
                offerCardName.setTextColor(Color.WHITE);
                discountPercentage.setTextColor(Color.BLACK);
                discountOffText.setTextColor(Color.BLACK);
                discountPercText.setTextColor(Color.BLACK);
                startDate.setTextColor(Color.BLACK);
                endDate.setTextColor(Color.BLACK);
                dateToText.setTextColor(Color.BLACK);
                offerDaysText.setTextColor(Color.BLACK);
                break;
            }
            case 6: {
                offerCardName.setTextColor(Color.BLACK);
                discountPercentage.setTextColor(Color.BLACK);
                discountPercText.setTextColor(Color.BLACK);
                discountOffText.setTextColor(Color.BLACK);
                startDate.setTextColor(Color.WHITE);
                endDate.setTextColor(Color.WHITE);
                dateToText.setTextColor(Color.WHITE);
                offerDaysText.setTextColor(Color.WHITE);
                break;
            }
            case 7: {
                offerCardName.setTextColor(Color.BLACK);
                discountPercentage.setTextColor(Color.RED);
                discountOffText.setTextColor(Color.RED);
                discountPercText.setTextColor(Color.RED);
                startDate.setTextColor(Color.BLACK);
                endDate.setTextColor(Color.BLACK);
                dateToText.setTextColor(Color.BLACK);
                offerDaysText.setTextColor(Color.BLACK);
                break;
            }
            case 8: {
                offerCardName.setTextColor(Color.BLACK);
                discountPercentage.setTextColor(Color.WHITE);
                discountOffText.setTextColor(Color.WHITE);
                discountPercText.setTextColor(Color.WHITE);
                startDate.setTextColor(Color.BLACK);
                endDate.setTextColor(Color.BLACK);
                dateToText.setTextColor(Color.BLACK);
                offerDaysText.setTextColor(Color.BLACK);
                break;
            }
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

    private void setOfferCardBackground(int offerRange) {
        switch (offerRange) {
            case 1: {
                setOfferCardTextColor(1);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer1_bg);
                return;
            }
            case 2: {
                setOfferCardTextColor(1);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer2_bg);
                return;
            }
            case 3: {
                setOfferCardTextColor(1);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer3_bg);
                return;
            }
            case 4: {
                setOfferCardTextColor(1);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer4_bg);
                return;
            }
            case 5: {
                setOfferCardTextColor(2);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer5_bg);
                return;
            }
            case 6: {
                setOfferCardTextColor(2);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer6_bg);
                return;
            }
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OfferCardInterface {
        void offerCardClicked(OfferDataModel offerDataModel);
    }

    public void clear() {
        if (offerDataModels != null) {
            offerDataModels.clear();
        }
        notifyDataSetChanged();
    }

}
