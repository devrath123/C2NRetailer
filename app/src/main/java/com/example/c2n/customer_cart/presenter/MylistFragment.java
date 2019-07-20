package com.example.c2n.customer_cart.presenter;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c2n.R;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.customer_cart.presenter.adapter.CustomerCartMylistAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MylistFragment extends DialogFragment {

    List<MasterProductDataModel> masterProductDataModelList;

    @BindView(R.id.rcv_mylist)
    RecyclerView recyclerViewMylist;

    CustomerCartMylistAdapter customerCartMylistAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fraglayout, container);
        ButterKnife.bind(this, rootView);

        recyclerViewMylist.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //ADAPTER
        customerCartMylistAdapter = new CustomerCartMylistAdapter(this.getActivity(), masterProductDataModelList);
        recyclerViewMylist.setAdapter(customerCartMylistAdapter);

        this.getDialog().setTitle("Mylist");

        return rootView;
    }

    @OnClick(R.id.bt_close)
    public void closeMylistDialog() {
        this.getDialog().dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public void setMylist(List<MasterProductDataModel> masterProductDataModels) {
        this.masterProductDataModelList = masterProductDataModels;
    }
}
