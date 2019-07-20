package com.example.c2n.viewproducts.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.c2n.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ViewProductsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_products, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fab_add_product)
    public void addProduct() {
        Toast.makeText(getContext(), "Product add kr", Toast.LENGTH_SHORT).show();
    }
}
