package com.example.c2n.customercategory.presenter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c2n.R;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.customercategory.di.CustomerCategoryDI;
import com.example.c2n.customercategory.presenter.adapter.CustomerCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomerCategoryFragment extends Fragment implements CustomerCategoryView, CustomerCategoryAdapter.CustomerCategoryInterface {

    @BindView(R.id.category_recycler_view)
    RecyclerView recyclerView;

    @Inject
    CustomerCategoryPresenter customerCategoryPresenter;

    //    int REQUEST_CODE = 2323;
    public ProgressDialog progressDialog;
    private CustomerCategoryAdapter customerCategoryAdapter;

    private List<CategoryDataModel> categoryDataModels;

    private List<ProductDataModel> productDataModels = new ArrayList<>();
    public ShopDataModel shopDataModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_category, container, false);
        ButterKnife.bind(this, view);
        CustomerCategoryDI.getCategoryComponent().inject(this);
        customerCategoryPresenter.bind(this, getContext());

        progressDialog = new ProgressDialog(getContext());
        categoryDataModels = new ArrayList<>();

        getCategories();


        return view;
    }

    @Override
    public void loadCategories(List<CategoryDataModel> categoryDataModels, String error) {
        this.categoryDataModels = categoryDataModels;

        Log.d("CustCategoryFragme_", "" + categoryDataModels.size());
        customerCategoryAdapter = new CustomerCategoryAdapter(getContext(), this.categoryDataModels, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customerCategoryAdapter);
        customerCategoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void getCategories() {
        customerCategoryPresenter.getCategory();
    }


    @Override
    public void getCategory() {
    }

    @Override
    public void showProgress(Boolean flag) {
        if (flag) {
            showProgressDialog("Loading Categories");
        } else
            dismissProgressDialog();
    }


    protected void showProgressDialog(String msg) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void categoryClicked(CategoryDataModel category) {

    }


}
