package com.example.c2n.customer_products_list.presenter;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.model.ProductDataModel;
import com.example.c2n.customer_products_list.di.ProductsListDI;
import com.example.c2n.customer_products_list.presenter.adapter.ProductsListAdapter;
import com.example.c2n.customer_products_list.presenter.view.ProductsListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsListFragment extends Fragment implements ProductsListView, ProductsListAdapter.ProductRowInterface {

    @BindView(R.id.product_recycler_view)
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    List<ProductDataModel> productsList;
    @Inject
    ProductsListPresenter productsListPresenter;

    String selectedProductCategory = "";
    String currentFragment = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        ButterKnife.bind(this, view);
        ProductsListDI.getProductsListComponent().inject(this);
        productsListPresenter.bind(this, getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedProductCategory = bundle.getString("productCategory");
            Log.d("Selected_Product", "bundle : " + selectedProductCategory);
        }
        getProducts();
        return view;
    }


    public static ProductsListFragment newInstance() {
        ProductsListFragment productsListFragment = new ProductsListFragment();
//        Bundle args = new Bundle();
//        productsListFragment.setArguments(args);
        return productsListFragment;
    }


    private void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showProgress(Boolean processing) {
        if (processing)
            showProgressDialog("Loading...");
        else {
            if (progressDialog.isShowing())
                dismissProgressDialog();
        }
    }

    public void getProducts() {
        productsListPresenter.getProducts();
    }

    @Override
    public void postProductsList(List<ProductDataModel> productsList, String error) {
        showProgress(false);
        if (error != null) {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.productsList != null)
            this.productsList.clear();
        this.productsList = productsList;
        ProductsListAdapter adapter = new ProductsListAdapter(this.productsList, getContext(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String getSelectedProductCategory() {
        return selectedProductCategory;
    }

    @Override
    public void productClicked(ProductDataModel productDataModel) {
        String selectedProduct = productDataModel.getProductName();
        Toast.makeText(getContext(), selectedProduct + " is clicked...", Toast.LENGTH_SHORT).show();
    }
}
