package com.example.c2n.customer_single_shop_products.presenter;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.database.ProductDatabase;
import com.example.c2n.core.models.CartProductDataModel;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ProductDetailsDataModel;
import com.example.c2n.core.models.ShopDistanceDataModel;
import com.example.c2n.customer_cart.presenter.CustomerCartActivity;
import com.example.c2n.customer_home.di.CustomerHomeDI;
import com.example.c2n.customer_single_shop_products.di.CustomerSingleShopProductsDI;
import com.example.c2n.customer_single_shop_products.presenter.adapter.CustomerSingleShopCategoryAdapter;
import com.example.c2n.customer_single_shop_products.presenter.adapter.CustomerSingleShopProductsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CustomerSingleShopProductsActivity extends BaseActivity implements CustomerSingleShopProductsView, CustomerSingleShopCategoryAdapter.CustomerSingleShopCategoryInterface, CustomerSingleShopProductsAdapter.CustomerSingleShopProductInterface {

    private static final String TAG = CustomerSingleShopProductsActivity.class.getSimpleName();

    private ProductDatabase productDatabase;
    private static final String DATABASE_NAME = "product_db";

    private int count = 0;
    private List<ProductDetailsDataModel> resultProducts = new ArrayList<>();
    private List<CategoryDataModel> categoryDataModels = new ArrayList<>();
    private List<ProductDetailsDataModel> products = new ArrayList<>();
    private String selectedCategory = "all";

    private ShopDistanceDataModel shopDistanceDataModel;
    private CustomerSingleShopCategoryAdapter customerSingleShopCategoryAdapter;
    private CustomerSingleShopProductsAdapter customerSingleShopProductsAdapter;

    @BindView(R.id.rl_no_product)
    RelativeLayout relativeLayoutNoProducts;

    @BindView(R.id.rl_product)
    RelativeLayout relativeLayoutProducts;

    @BindView(R.id.recycler_view_category_list)
    RecyclerView recyclerViewCategoryList;

    @BindView(R.id.recycler_view_product_list)
    RecyclerView recyclerViewProductList;

    @Inject
    CustomerSingleShopProductsPresenter customerSingleShopProductsPresenter;
    private Consumer<? super Observable<CartProductDataModel>> result;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_customer_single_shop_products;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        CustomerSingleShopProductsDI.getCustomerSingleShopProductsComponent().inject(this);
        CustomerHomeDI.getCustomerHomeComponent().inject(this);
        customerSingleShopProductsPresenter.bind(this, this);

        productDatabase = Room.databaseBuilder(this.getApplicationContext(), ProductDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        relativeLayoutNoProducts.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shopDistanceDataModel = (ShopDistanceDataModel) bundle.get("shopDataModel");
//            Log.d("CustomerSingleShopPro__", "" + shopDistanceDataModel.toString());
            getSupportActionBar().setTitle(shopDistanceDataModel.getShopDataModel().getShopName() + " - " + shopDistanceDataModel.getShopDistance() + " KM");
        }
//        getAllMasterProducts();
        getAllCategories();
    }

    public void getAllProducts() {
        customerSingleShopProductsPresenter.getAllProducts();
    }

    public void getAllCategories() {
        customerSingleShopProductsPresenter.getCategories();
    }

    @Override
    public void loadCategories(List<CategoryDataModel> categoryDataModels) {
        this.categoryDataModels = categoryDataModels;
//        getAllProducts();
        loadAllProducts(shopDistanceDataModel.getShopDataModel().getProductsList());
    }

    @Override
    public String getUserID() {
        return shopDistanceDataModel.getShopDataModel().getRetailerID();
    }

    @Override
    public String getShopID() {
        return shopDistanceDataModel.getShopDataModel().getShopID();
    }

    @Override
    public void loadAllProducts(List<ProductDataModel> productDataModels) {
//    public void loadAllProducts(List<ProductDetailsDataModel> productDetailsDataModels) {

        Log.d(TAG, "loadAllProducts: " + productDataModels.size());
        if (productDataModels.size() == 0) {
            relativeLayoutProducts.setVisibility(View.GONE);
            relativeLayoutNoProducts.setVisibility(View.VISIBLE);
        } else {
            relativeLayoutProducts.setVisibility(View.VISIBLE);
            relativeLayoutNoProducts.setVisibility(View.GONE);

            List<ProductDetailsDataModel> productDetailsDataModels = new ArrayList<>();

            for (ProductDataModel productDataModel : productDataModels) {
                ProductDetailsDataModel productDetailsDataModel = new ProductDetailsDataModel();
                productDetailsDataModel.setRetailerID(getUserID());
                productDetailsDataModel.setShopID(getShopID());
                productDetailsDataModel.setWhishlisted(false);
                productDetailsDataModel.setProductDataModel(productDataModel);
                productDetailsDataModels.add(productDetailsDataModel);
            }

            products = productDetailsDataModels;
            List<String> categories = new ArrayList<>();
            for (ProductDetailsDataModel productDetailsDataModel : productDetailsDataModels) {
                if (!categories.contains(productDetailsDataModel.getProductDataModel().getProductCategory())) {
                    categories.add(productDetailsDataModel.getProductDataModel().getProductCategory());
                }
            }

            List<CategoryDataModel> activeCategories = new ArrayList<>();
            if (activeCategories.size() != 0) {
                activeCategories.clear();
            }
            for (int i = 0; i < categories.size(); i++) {
                for (int j = 0; j < categoryDataModels.size(); j++) {
                    if (categories.get(i).equals(categoryDataModels.get(j).getCategoryName())) {
                        activeCategories.add(categoryDataModels.get(j));
                    }
                }
            }

            CategoryDataModel categoryDataModel = new CategoryDataModel();
            categoryDataModel.setCategoryImageURL(null);
            categoryDataModel.setCategoryName("All");
            activeCategories.add(0, categoryDataModel);

            customerSingleShopCategoryAdapter = new CustomerSingleShopCategoryAdapter(this, activeCategories, this);
            recyclerViewCategoryList.setHasFixedSize(true);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerViewCategoryList.setLayoutManager(linearLayout);
            recyclerViewCategoryList.setAdapter(customerSingleShopCategoryAdapter);
            customerSingleShopCategoryAdapter.notifyDataSetChanged();

            loadProducts();

            Log.d("CustomerSingleShopPro__", "" + categories.toString());
        }
    }

    private void loadProducts() {
        if (resultProducts.size() != 0)
            resultProducts.clear();
        for (ProductDetailsDataModel productDetailsDataModel : products) {
            if (selectedCategory.toLowerCase().equals("all")) {
                resultProducts.add(productDetailsDataModel);
            } else {
                if (productDetailsDataModel.getProductDataModel().getProductCategory().equals(selectedCategory)) {
                    resultProducts.add(productDetailsDataModel);
                }
            }
        }

        count = resultProducts.size() - 1;

        getMylist();
//        checkProductChecklisted();

        Log.d("CustomerSingleShopPr___", "Size : " + resultProducts.size() + "  - Count : " + count);

//        customerSingleShopProductsAdapter = new CustomerSingleShopProductsAdapter(resultProducts, this, this);
//        recyclerViewProductList.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        recyclerViewProductList.setLayoutManager(gridLayoutManager);
//        recyclerViewProductList.setAdapter(customerSingleShopProductsAdapter);
//        customerSingleShopProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMylist() {
        customerSingleShopProductsPresenter.getMylist();
    }

    @SuppressLint("RxLeakedSubscription")
    private void checkProductChecklisted() {

//        productDatabase.daoAccess().checkAvailability(resultProducts.get(count).getProductDataModel().getProductID())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(cartProductDataModel1 -> handleResponse(cartProductDataModel1), throwable -> handleError(throwable));

        productDatabase.daoAccess().loadAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartProductDataModel1 -> handleResponse(cartProductDataModel1), throwable -> handleError(throwable));
    }

    @Override
    public void loadMylist(List<MasterProductDataModel> products) {
        for (int i = 0; i < resultProducts.size(); i++) {
            for (int j = 0; j < products.size(); j++) {
                if (resultProducts.get(i).getProductDataModel().getProductID().equals(products.get(j).getProductID())) {
                    resultProducts.get(i).setWhishlisted(true);
                }
            }
        }
        showProducts();
    }

    private void handleResponse(List<CartProductDataModel> cartProductDataModels) {
        for (int i = 0; i < resultProducts.size(); i++) {
            for (int j = 0; j < cartProductDataModels.size(); j++) {
                if (resultProducts.get(i).getProductDataModel().getProductID().equals(cartProductDataModels.get(j).getProductID())) {
                    resultProducts.get(i).setWhishlisted(true);
                }
            }
        }
        showProducts();
    }
//    private void handleResponse(CartProductDataModel cartProductDataModel1) {
//        Log.d("CustomerSingleProduct__", " : " + cartProductDataModel1);
//        resultProducts.get(count).setMylisted(true);
//
//        if (count > 0) {
//            --count;
//            checkProductChecklisted();
//        } else {
//            showProducts();
//        }
//    }

    private void handleError(Throwable throwable) {
        Log.d("CustomerSingleProduct__", "Error : " + throwable.getMessage());
        if (count > 0) {
            --count;
            getMylist();
//            checkProductChecklisted();
        } else {
            showProducts();
            Log.d("CustomerSingleProduct__", "Error : " + throwable.getMessage());
        }
    }

    private void showProducts() {
        customerSingleShopProductsAdapter = new CustomerSingleShopProductsAdapter(resultProducts, this, this);
        recyclerViewProductList.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewProductList.setLayoutManager(gridLayoutManager);
        recyclerViewProductList.setAdapter(customerSingleShopProductsAdapter);
        customerSingleShopProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public void categoryClicked(String categoryName) {
        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();
        selectedCategory = categoryName;
        loadProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
//            Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CustomerCartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void addToMylist(ProductDetailsDataModel productDetailsDataModel) {
        MasterProductDataModel masterProductDataModel = new MasterProductDataModel();
        masterProductDataModel.setProductID(productDetailsDataModel.getProductDataModel().getProductID());
        masterProductDataModel.setProductName(productDetailsDataModel.getProductDataModel().getProductName());
        masterProductDataModel.setProductDescription(productDetailsDataModel.getProductDataModel().getProductDescription());
        masterProductDataModel.setProductCategory(productDetailsDataModel.getProductDataModel().getProductCategory());
        masterProductDataModel.setProductImageURL(productDetailsDataModel.getProductDataModel().getProductImageURL());
        customerSingleShopProductsPresenter.addToMyList(masterProductDataModel);
//        int index = resultProducts.indexOf(productDetailsDataModel);
//        resultProducts.get(index).setMylisted(true);
//        CartProductDataModel cartProductDataModel = new CartProductDataModel();
//        cartProductDataModel.setRetailerID(productDetailsDataModel.getRetailerID());
//        cartProductDataModel.setShopID(productDetailsDataModel.getShopID());
//        cartProductDataModel.setProductID(productDetailsDataModel.getProductDataModel().getProductID());
//        cartProductDataModel.setProductName(productDetailsDataModel.getProductDataModel().getProductName());
//        cartProductDataModel.setProductImageURL(productDetailsDataModel.getProductDataModel().getProductImageURL());
//        cartProductDataModel.setProductMRP(productDetailsDataModel.getProductDataModel().getProductMRP());
//        cartProductDataModel.setProductCategory(productDetailsDataModel.getProductDataModel().getProductCategory());
//        cartProductDataModel.setProductDescription(productDetailsDataModel.getProductDataModel().getProductDescription());
//        cartProductDataModel.setProductStockStatus(productDetailsDataModel.getProductDataModel().getProductStockStatus());
//        cartProductDataModel.setProductOfferStatus(productDetailsDataModel.getProductDataModel().getProductOfferStatus());
//        if (productDetailsDataModel.getProductDataModel().getProductOffer() != null) {
//            cartProductDataModel.setOfferID(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferID());
//            cartProductDataModel.setOfferDiscount(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount());
//            cartProductDataModel.setOfferStartDate(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferStartDate());
//            cartProductDataModel.setOfferEndDate(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferEndDate());
//            cartProductDataModel.setOfferName(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferName());
//            cartProductDataModel.setSun(productDetailsDataModel.getProductDataModel().getProductOffer().isSun());
//            cartProductDataModel.setMon(productDetailsDataModel.getProductDataModel().getProductOffer().isMon());
//            cartProductDataModel.setTue(productDetailsDataModel.getProductDataModel().getProductOffer().isTue());
//            cartProductDataModel.setWed(productDetailsDataModel.getProductDataModel().getProductOffer().isWed());
//            cartProductDataModel.setThu(productDetailsDataModel.getProductDataModel().getProductOffer().isThu());
//            cartProductDataModel.setFri(productDetailsDataModel.getProductDataModel().getProductOffer().isFri());
//            cartProductDataModel.setSat(productDetailsDataModel.getProductDataModel().getProductOffer().isSat());
//            cartProductDataModel.setOfferStatus(productDetailsDataModel.getProductDataModel().getProductOffer().isOfferStatus());
//        }
//        Log.d("QWERTYUIOPASDFGHJKL", "" + cartProductDataModel.toString());
////
//        Observable.fromCallable(() -> productDatabase.daoAccess().insertProduct(cartProductDataModel))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposabele -> showProgressDialog("Adding"))
//                .subscribe(id -> handleAddProductToMylistProgress(id), throwable -> handleAddOrRemoveError(throwable));

//        CartProducts.getCartProducts().addProductMaster(productDetailsDataModel);
//        Log.d("CustomerSingleShopP____", "Size : " + CartProducts.getCartProducts().getAllMasterProducts().size());
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void removeFromMylist(ProductDetailsDataModel productDetailsDataModel) {
        customerSingleShopProductsPresenter.removeFromMylist(productDetailsDataModel.getProductDataModel().getProductID());
//        int index = resultProducts.indexOf(productDetailsDataModel);
//        resultProducts.get(index).setMylisted(false);
//        CartProductDataModel cartProductDataModel = new CartProductDataModel();
//        cartProductDataModel.setRetailerID(productDetailsDataModel.getRetailerID());
//        cartProductDataModel.setShopID(productDetailsDataModel.getShopID());
//        cartProductDataModel.setProductID(productDetailsDataModel.getProductDataModel().getProductID());
//        cartProductDataModel.setProductName(productDetailsDataModel.getProductDataModel().getProductName());
//        cartProductDataModel.setProductImageURL(productDetailsDataModel.getProductDataModel().getProductImageURL());
//        cartProductDataModel.setProductMRP(productDetailsDataModel.getProductDataModel().getProductMRP());
//        cartProductDataModel.setProductCategory(productDetailsDataModel.getProductDataModel().getProductCategory());
//        cartProductDataModel.setProductDescription(productDetailsDataModel.getProductDataModel().getProductDescription());
//        cartProductDataModel.setProductStockStatus(productDetailsDataModel.getProductDataModel().getProductStockStatus());
//        cartProductDataModel.setProductOfferStatus(productDetailsDataModel.getProductDataModel().getProductOfferStatus());
//        if (productDetailsDataModel.getProductDataModel().getProductOffer() != null) {
//            cartProductDataModel.setOfferID(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferID());
//            cartProductDataModel.setOfferDiscount(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferDiscount());
//            cartProductDataModel.setOfferStartDate(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferStartDate());
//            cartProductDataModel.setOfferEndDate(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferEndDate());
//            cartProductDataModel.setOfferName(productDetailsDataModel.getProductDataModel().getProductOffer().getOfferName());
//            cartProductDataModel.setSun(productDetailsDataModel.getProductDataModel().getProductOffer().isSun());
//            cartProductDataModel.setMon(productDetailsDataModel.getProductDataModel().getProductOffer().isMon());
//            cartProductDataModel.setTue(productDetailsDataModel.getProductDataModel().getProductOffer().isTue());
//            cartProductDataModel.setWed(productDetailsDataModel.getProductDataModel().getProductOffer().isWed());
//            cartProductDataModel.setThu(productDetailsDataModel.getProductDataModel().getProductOffer().isThu());
//            cartProductDataModel.setFri(productDetailsDataModel.getProductDataModel().getProductOffer().isFri());
//            cartProductDataModel.setSat(productDetailsDataModel.getProductDataModel().getProductOffer().isSat());
//            cartProductDataModel.setOfferStatus(productDetailsDataModel.getProductDataModel().getProductOffer().isOfferStatus());
//        }
//        Log.d("QWERTYUIOPASDFGHJKL", "" + cartProductDataModel.toString());
////
//        Observable.fromCallable(() -> productDatabase.daoAccess().removeProduct(cartProductDataModel))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposabele -> showProgressDialog("Removing"))
//                .subscribe(id -> handleRemoveProductToMylistProgress(id), throwable -> handleAddOrRemoveError(throwable));
    }

    private void handleAddProductToMylistProgress(Long id) {
        Log.d("CustomerSingleProduct__", "Added : " + id);
        dismissProgressDialog();
    }

    private void handleRemoveProductToMylistProgress(int id) {
        Log.d("CustomerSingleProduct__", "Removed : " + id);
        dismissProgressDialog();
    }

    private void handleAddOrRemoveError(Throwable throwable) {
        Log.d("CustomerSingleProduct__", "Error : " + throwable.getMessage());
        dismissProgressDialog();
        Toast.makeText(this, "Sorry, there seems to be a problem.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean flag, String msg) {
        if (flag) {
            showProgressDialog(msg);
        } else {
            dismissProgressDialog();
        }
    }

}
