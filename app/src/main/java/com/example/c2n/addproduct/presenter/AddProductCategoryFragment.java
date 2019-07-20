package com.example.c2n.addproduct.presenter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c2n.R;
import com.example.c2n.addproduct.presenter.adapter.AddProductCategoryAdapter;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.retailercategory.di.RetailerCategoryDI;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProductCategoryFragment extends Fragment implements AddProductCategoryView, AddProductCategoryAdapter.AddProductCategoryInterface {

    @Inject
    AddProductCategoryPresenter addProductInfoPresenter;

    @BindView(R.id.rv_category)
    RecyclerView recyclerViewCategory;

    private AddProductCategoryAdapter addProductCategoryAdapter;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product_info, container, false);
        ButterKnife.bind(this, view);
        RetailerCategoryDI.getRetailerCategoryComponent().inject(this);
        addProductInfoPresenter.bind(getContext(), this);

        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
        addProductActivity.setActionBarTitle("Select Category");

        getAllCategory();

        return view;
    }

    @Override
    public void getAllCategory() {
        addProductInfoPresenter.getAllCategory();
    }

    @Override
    public void showLoadingOffersProgress(Boolean progress, String msg) {
        if (progress)
            showProgressDialog(msg);
        else
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
    public void loadAallCategory(List<CategoryDataModel> categoryDataModelList) {
        addProductCategoryAdapter = new AddProductCategoryAdapter(categoryDataModelList, getContext(), this);
        recyclerViewCategory.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewCategory.setLayoutManager(gridLayoutManager);
        recyclerViewCategory.setAdapter(addProductCategoryAdapter);
        addProductCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void categoryClicked(String category) {
        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
        addProductActivity.productCategory = category;
        AddProductDetailsFragment addProductDetailsFragment = new AddProductDetailsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.product_fragment_container, addProductDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
