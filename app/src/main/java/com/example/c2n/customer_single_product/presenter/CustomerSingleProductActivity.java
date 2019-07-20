package com.example.c2n.customer_single_product.presenter;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.customer_home.di.CustomerHomeDI;
import com.example.c2n.customer_single_product.di.CustomerSingleProductDI;
import com.example.c2n.customer_single_product.presenter.adapter.CustomerProductDetailsOffersAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerSingleProductActivity extends BaseActivity implements CustomerSingleProductView {

    private String productID;
    private ProductDataModel productDataModel;
    private boolean flag;

    @BindView(R.id.iv_cust_product)
    ImageView imageViewProductImage;

    @BindView(R.id.cust_product_category)
    TextView textViewProductCategory;

    @BindView(R.id.cust_product_name)
    TextView textViewProductName;

//    @BindView(R.id.cust_product_price)
//    TextView textViewProductMRP;

    @BindView(R.id.offers_recylerview)
    RecyclerView recyclerViewOffers;

    @BindView(R.id.tv_offers_available)
    TextView textViewOffersAvailable;

    @BindView(R.id.button_add_to_cart)
    Button buttonAddToCart;

    @Inject
    CustomerSingleProductPresenter customerSingleProductPresenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_customer_single_product;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        CustomerSingleProductDI.getCustomerSingleProductDI().inject(this);
        CustomerHomeDI.getCustomerHomeComponent().inject(this);
        customerSingleProductPresenter.bind(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productID = bundle.getString("productID");
        }

        getMylist();
//        getProuctDetails();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    getSupportActionBar().setTitle("");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("Product Details");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @OnClick(R.id.button_add_to_cart)
    public void addProductToCart() {
        if (flag) {
            buttonAddToCart.setText("Add To Cart");
            customerSingleProductPresenter.removeFromMylist(productID);
        } else {
            buttonAddToCart.setText("Remove From Cart");
            MasterProductDataModel masterProductDataModel = new MasterProductDataModel();
            masterProductDataModel.setProductID(productDataModel.getProductID());
            masterProductDataModel.setProductName(productDataModel.getProductName());
            masterProductDataModel.setProductCategory(productDataModel.getProductCategory());
            masterProductDataModel.setProductDescription(productDataModel.getProductDescription());
            masterProductDataModel.setProductImageURL(productDataModel.getProductImageURL());
            customerSingleProductPresenter.addToMyList(masterProductDataModel);
        }
    }

    public void getProuctDetails() {
        customerSingleProductPresenter.getProductOffers();
    }

    @Override
    public void loadProductDetails(ProductDataModel productDataModel) {
        this.productDataModel = productDataModel;
        Picasso.get().load(productDataModel.getProductImageURL()).fit().into(imageViewProductImage);
        textViewProductCategory.setText(productDataModel.getProductCategory());
        textViewProductName.setText(productDataModel.getProductName());
//        textViewProductMRP.setText(getResources().getString(R.string.rupee_symbol) + String.valueOf(productDataModel.getProductMRP()));

        if (productDataModel.getShopDataModels() != null) {
            CustomerProductDetailsOffersAdapter customerProductDetailsOffersAdapter = new CustomerProductDetailsOffersAdapter(productDataModel, getBaseContext());
            recyclerViewOffers.hasFixedSize();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerViewOffers.setLayoutManager(linearLayoutManager);
            recyclerViewOffers.setAdapter(customerProductDetailsOffersAdapter);
            customerProductDetailsOffersAdapter.notifyDataSetChanged();
        } else {
        }
    }

    @Override
    public String getProductID() {
        return productID;
    }

    @Override
    public void showProgress(boolean flag, String msg) {
        if (flag) {
            showProgressDialog(msg);
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    public void getMylist() {
        customerSingleProductPresenter.getMylist();
    }

    @Override
    public void loadMylist(List<MasterProductDataModel> products) {
        for (MasterProductDataModel product : products) {
            if (this.productID.equals(product.getProductID())) {
                flag = true;
            }
        }
        if (flag) {
            buttonAddToCart.setText("Remove From Cart");
        }
        getProuctDetails();
    }
}
