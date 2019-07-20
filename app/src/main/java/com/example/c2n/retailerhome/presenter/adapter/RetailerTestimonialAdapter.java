package com.example.c2n.retailerhome.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.model.RetailerDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shivani.singh on 21-06-2018.
 */

public class RetailerTestimonialAdapter  {

 /*   List<RetailerDataModel> retailerDataModels;
    Context context;
    LayoutInflater layoutInflater;

    @BindView(R.id.tv_testimonial_user_name)
    TextView retailerName;

    @BindView(R.id.tv_testimonial_user_description)
    TextView retailerStory;

    @BindView(R.id.tv_image_view)
    CircleImageView retailerPic;


    @Inject
    public RetailerTestimonialAdapter(List<RetailerDataModel> retailerDataModels, Context context) {
        this.retailerDataModels = retailerDataModels;
        this.context = context;

    }

    @Override
    public int getCount()
    {
        Log.d("getCount",""+retailerDataModels.size());
        return retailerDataModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myTestimonialLayout = layoutInflater.inflate(R.layout.testimonial_slider_image, view, false);
        ButterKnife.bind(this, myTestimonialLayout);
        Log.d("",""+retailerDataModels.size());
        retailerName.setText(retailerDataModels.get(position).getUserName());
        Picasso.with(context).load(retailerDataModels.get(position).getUserPhotoUrl()).into(retailerPic);
        retailerStory.setText(retailerDataModels.get(position).getUserSuccessStory());
        view.addView(myTestimonialLayout, 0);

        Log.d("sliderViewPager",retailerDataModels.get(position).getUserName()+"---"+retailerDataModels.get(position).getUserPhotoUrl()+"----"+retailerDataModels.get(position).getUserSuccessStory());
        return myTestimonialLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }*/
}
