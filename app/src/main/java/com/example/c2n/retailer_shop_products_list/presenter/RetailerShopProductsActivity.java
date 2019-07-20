package com.example.c2n.retailer_shop_products_list.presenter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.master_list.presenter.MasterListActivity;
import com.example.c2n.retailer_shop_products_list.di.RetailerShopProductsDI;
import com.example.c2n.retailer_shop_products_list.presenter.adapter.RetailerShopProductsAdapter;
import com.example.c2n.view_product.presenter.ViewProductActivity;
import com.example.c2n.viewshops.di.ViewShopsDI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RetailerShopProductsActivity extends BaseActivity implements RetailerShopProductsView, RetailerShopProductsAdapter.ProductRowInterface {

    int REQUEST_CODE = 2323;
    int fragmentStackCount = 0;
    String shopName = "";
    String categoryName = "";
    String shopEmail = "";
    private ShopDataModel shopDataModel;
    private int clickedPosition = 0;

    ProductDataModel productDataModel;
    private List<ProductDataModel> productDataModels = new ArrayList<>();
    private List<ProductDataModel> productDataModelss = new ArrayList<>();

    SearchView searchView;

    @BindView(R.id.fab_add_shop)
    FloatingActionButton floatingActionButtonAddShop;

    @BindView(R.id.recycler_view_retailer_shop_products)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_add_shop_hint)
    TextView textViewAddShopHint;

    @BindView(R.id.appbarlayout)
    AppBarLayout appBarLayout;

    @Inject
    RetailerShopProductsPresenter retailerShopProductsPresenter;
    RetailerShopProductsAdapter mAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_retailer_shop_products;
    }

    @Override
    protected void initActivity() {

        ButterKnife.bind(this);
        RetailerShopProductsDI.getRetailerShopProductsComponent().inject(this);
        ViewShopsDI.getViewShopsComponent().inject(this);
        retailerShopProductsPresenter.bind(this, this);

        appBarLayout.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();

        int count = 0;
        if (bundle != null) {
//            shopName = bundle.getString("shopSelected");
            count = bundle.getInt("count");
            categoryName = bundle.getString("categorySelected");
//            shopEmail=bundle.getString("shopEmail");
        }
        Intent intent = getIntent();
        if (intent != null) {
            for (int i = 0; i < count; i++) {
                productDataModels.add((ProductDataModel) intent.getSerializableExtra("productDataModel" + i));
            }
            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
//            shopName = shopDataModel.getShopName();
//            shopEmail = shopDataModel.getShopEmail();
        }

        getSupportActionBar().setTitle(shopName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


//        shopName = this.getArguments().getString("shopSelected");
//        shopEmail = this.getArguments().getString("shopEmail");

        textViewAddShopHint.setVisibility(View.GONE);

        Log.d("RetailerShopProductsA_", "size : " + productDataModels.size());
//        loadProducts();

//        showProductsList(productDataModels);

        whiteNotificationBar(mRecyclerView);


//        RetailerShopProductsFragment retailerShopProductsFragment = new RetailerShopProductsFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("shopDataModel", shopDataModel);
//        args.putString("categorySelected", categoryName);
////        args.putString("shopEmail", shopEmail);
//        retailerShopProductsFragment.setArguments(args);
//        replaceFragment(retailerShopProductsFragment, categoryName + "(" + shopName + ")", true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductsList();
    }

    public void getProductsList() {
        retailerShopProductsPresenter.getProductCategory();
    }

    public void replaceFragment(Fragment fragment, String tag, Boolean isTransparent) {

//        if (isTransparent) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            setToolbarTitle(tag);
//        } else
//            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.themecolor));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setToolbarTitle(tag);
//        closeNavigationDrawer();

        fragmentStackCount++;
        fragmentCount++;
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.shop_products_fragment_container, fragment, tag)
//                .commit();

//        if (!isInitial) {
//            toolbarIcon.setVisibility(View.INVISIBLE);
//            toolbarIconBack.setVisibility(View.VISIBLE);
//        } else {
//            toolbarIconBack.setVisibility(View.INVISIBLE);
//            toolbarIcon.setVisibility(View.VISIBLE);
//        }
//
    }

//    private void setToolbarTransparent() {
////        productToolbar.getBackground().setAlpha(0);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//    }

    public void setToolbarTitle(String title) {
//        toolbarText.setText(title);
        getSupportActionBar().setTitle(title);
    }


    private void loadProducts() {
//        retailerShopProductsPresenter.loadProducts(shopEmail, categoryName);
    }

    @Override
    public void showProgress(Boolean progress) {
        if (progress)
            showProgressDialog("Please Wait...");
        else
            dismissProgressDialog();
    }

    @Override
    public void showProductsList(List<ProductDataModel> products) {
        List<ProductDataModel> productDataModelss = new ArrayList<>();
        Log.d("RetailerShopProductsA_", "size : " + products.toString());
        for (int i = 0; i < products.size(); i++) {
            ProductDataModel productDataModel = products.get(i);
            if (productDataModel.getProductCategory().equals(categoryName)) {
                productDataModelss.add(productDataModel);
            }
        }
//        for (int i = 0; i < products.size(); i++) {
//            ProductDataModel productDataModel = products.get(i);
//            Log.d("RetailerShopProducts", "" + productDataModel.toString());
//            if (productDataModel.getProductCategory().equals(categoryName)) {
//                productDataModelss.add(productDataModel);
//            }
//        }
        Log.d("products_list", products.toString());
        if (productDataModelss.size() != 0) {
            if (productDataModelss.size() == 0) {
                productDataModelss.clear();
            }
            this.productDataModelss = productDataModelss;
//            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mAdapter = new RetailerShopProductsAdapter(this, this.productDataModelss, this);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(linearLayout);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else
            textViewAddShopHint.setVisibility(View.VISIBLE);
    }


    @Override
    public void productSelected(ProductDataModel productDataModel, int position) {

        if (ConnectivityReceiver.isConnected()) {
            clickedPosition = position;
            Log.d("product_selected", productDataModel.toString());
            Log.d("shop", shopDataModel.toString());
            Log.d("category_Selcted", categoryName);
            Intent intent = new Intent(this, ViewProductActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            intent.putExtra("productDataModel", productDataModel);
            intent.putExtra("categorySelected", categoryName);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(RetailerShopProductsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ProductDataModel updatedProductDataModel = (ProductDataModel) data.getExtras().get("updatedProductDataModel");
            Log.d("RetailerShopProductsAc_", clickedPosition + " : " + updatedProductDataModel.toString());
//            ProductDataModel model = productDataModels.get(clickedPosition);
//            model = updatedProductDataModel;
            this.productDataModelss.set(clickedPosition, updatedProductDataModel);
            mAdapter.notifyItemChanged(clickedPosition);
        }
    }

//    @Override
//    public void editProduct(ProductDataModel productDataModel) {
//        Intent intent = new Intent(this,ViewProductActivity.class);
//        intent.putExtra("productDataModel",productDataModel);
//        startActivity(intent);
//
//    }

    @Override
    public void markProductUnavailable(ProductDataModel productDataModel, int position) {
        clickedPosition = position;
        this.productDataModel = productDataModel;
        retailerShopProductsPresenter.markProductUnavailable();
    }

    @Override
    public void addOrChangeOffer(ProductDataModel productDataModel) {

//        if (productDataModel.getProductScheme() == null || productDataModel.getProductScheme().equals("")) {
//
//            Intent intent = new Intent(this, ViewProductActivity.class);
//            intent.putExtra("productDataModel", productDataModel);
//            intent.putExtra("categorySelected", categoryName);
//            intent.putExtra("shopEmail", shopEmail);
//            intent.putExtra("selected", "yes");
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(this, UpdateProductOfferActivity.class);
//            intent.putExtra("productDataModel", productDataModel);
//            intent.putExtra("categorySelected", categoryName);
//            intent.putExtra("shopEmail", shopEmail);
//            startActivity(intent);
//        }
    }

    @Override
    public void markProductAvailable(ProductDataModel productDataModel, int position) {
        clickedPosition = position;
        this.productDataModel = productDataModel;
        retailerShopProductsPresenter.markProductAvailable();
    }

    @OnClick(R.id.fab_add_shop)
    @Override
    public void addProduct() {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(this, MasterListActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(RetailerShopProductsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getShopId() {
        return shopDataModel.getShopID();
    }

    @Override
    public String getProductId() {
        if (productDataModel != null)
            return productDataModel.getProductID();
        return "";
    }

    @Override
    public void isProductUnavailabilitySuccess(Boolean success) {
        if (success) {
            showToast("Product is now Out of Stock.");

            ProductDataModel updatedProductDataModel = productDataModel;
            updatedProductDataModel.setProductStockStatus(false);
            Log.d("RetailerShopProductsAc_", clickedPosition + " : " + updatedProductDataModel.toString());
//            ProductDataModel model = productDataModels.get(clickedPosition);
//            model = updatedProductDataModel;
            this.productDataModelss.set(clickedPosition, updatedProductDataModel);
            mAdapter.notifyItemChanged(clickedPosition);
        } else
            showToast("Please Retry...");
    }

    @Override
    public void isProductAvailabilitySuccess(Boolean success) {
        if (success) {
            showToast("Product is now In Stock.");

            ProductDataModel updatedProductDataModel = productDataModel;
            updatedProductDataModel.setProductStockStatus(true);
            Log.d("RetailerShopProductsAc_", clickedPosition + " : " + updatedProductDataModel.toString());
//            ProductDataModel model = productDataModels.get(clickedPosition);
//            model = updatedProductDataModel;
            this.productDataModelss.set(clickedPosition, updatedProductDataModel);
            mAdapter.notifyItemChanged(clickedPosition);

        } else
            showToast("Please Retry...");
    }

    @Override
    public String getShopID() {
        return shopDataModel.getShopID();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_search_products, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("options", "in_options");
        if (item.getItemId() == android.R.id.home) {
            Log.d("options_in_method", "in_options");
            popFragment();
        } else {
            if (item.getItemId() == R.id.action_search)
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void popFragment() {
        fragmentStackCount--;
        if (fragmentStackCount <= 0) {
            finish();
            return;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("on_back", "shopproducts_back_pressed");

    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("count", productDataModelss.size());
        for (int i = 0; i < productDataModelss.size(); i++) {
            returnIntent.putExtra("updatedProductDataModel" + i, productDataModelss.get(i));
        }
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null CustomerSingleProductRepository.
        super.finish();
    }
}