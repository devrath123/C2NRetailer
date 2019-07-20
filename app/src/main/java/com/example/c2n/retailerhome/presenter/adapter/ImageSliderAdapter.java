package com.example.c2n.retailerhome.presenter.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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
 * Created by vipul.singhal on 19-06-2018.
 */

public class ImageSliderAdapter extends PagerAdapter {
    private ArrayList<StorageReference> images;
    private LayoutInflater inflater;
    private Context context;
    @BindView(R.id.iv_slide_image)
    ImageView sliderImage;

    public ImageSliderAdapter(Context context, ArrayList<StorageReference> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.image_slider_retailer_home, view, false);
        ButterKnife.bind(this, myImageLayout);
//        sliderImage.setImageResource(images.get(position));
//        Picasso.with(context).load(images.get(position)).into(sliderImage);
//        Glide.with(context).using(new FirebaseImageLoader()).load(images.get(position)).into(sliderImage);
        GlideApp.with(context).load(images.get(position)).into(sliderImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void clear() {
        if (images != null) {
            images.clear();
        }
        notifyDataSetChanged();
    }
}
