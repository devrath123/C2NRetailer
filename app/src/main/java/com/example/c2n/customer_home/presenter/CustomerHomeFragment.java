package com.example.c2n.customer_home.presenter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.c2n.R;
import com.example.c2n.core.FCMHandler;
import com.example.c2n.core.FirebaseConfig;
import com.example.c2n.core.database.ProductDatabase;
import com.example.c2n.core.model1.CustomerProductSearchSuggestion;
import com.example.c2n.core.models.CartProductDataModel;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.MasterProductDetailsDataModel;
import com.example.c2n.customer_cart.presenter.CustomerCartActivity;
import com.example.c2n.customer_home.di.CustomerHomeDI;
import com.example.c2n.customer_home.presenter.adapter.CustomerHomeCategoryAdapter;
import com.example.c2n.customer_home.presenter.adapter.CustomerHomeSearchProductAdapter;
import com.example.c2n.customer_home.presenter.adapter.StartSnapHelper;
import com.example.c2n.customer_single_product.presenter.CustomerSingleProductActivity;
import com.example.c2n.retailercategory.di.RetailerCategoryDI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vipul.singhal on 19-06-2018.
 */

public class CustomerHomeFragment extends Fragment implements CustomerHomeView, CustomerHomeCategoryAdapter.CustomerHomeCategoryInterface, CustomerHomeSearchProductAdapter.CustomerHomeSearchProductInterface {

    private static final String TAG = CustomerHomeFragment.class.getSimpleName();

    private String token;
//    private SharedPreferences sharedPref;

    private int REQUEST_CODE = 2323;
    private ProgressDialog progressDialog;
    private static final String DATABASE_NAME = "product_db";
    private ProductDatabase productDatabase;
    private int count = 0;

    private List<MasterProductDetailsDataModel> searchResult = new ArrayList<>();
    private List<MasterProductDetailsDataModel> allProducts;
    private List<CategoryDataModel> allCategories;
    private InputMethodManager imm;
    private String selectedCategory = "all";
    private String searchProductName = "";

    @Inject
    CustomerHomePresenter customerHomePresenter;

    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.relative_layout_progress)
    RelativeLayout relativeLayoutProgress;

    @BindView(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;

    @BindView(R.id.recycler_view_category_list)
    RecyclerView recyclerViewCategoryList;

    @BindView(R.id.recycler_view_product_list)
    RecyclerView recyclerViewProductList;

    @BindView(R.id.layout_no_product)
    RelativeLayout relativeLayoutNoProducts;

    private CustomerHomeSearchProductAdapter customerHomeSearchProductAdapter;
    private CustomerHomeCategoryAdapter customerHomeCategoryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RxLeakedSubscription")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        ButterKnife.bind(this, view);
        CustomerHomeDI.getCustomerHomeComponent().inject(this);
        RetailerCategoryDI.getRetailerCategoryComponent().inject(this);
        customerHomePresenter.bind(this, getContext());

        progressDialog = new ProgressDialog(getContext());

        setHasOptionsMenu(true);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        productDatabase = Room.databaseBuilder(this.getActivity(), ProductDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        FCMHandler fcmHandler = new FCMHandler();
        fcmHandler.enableFCM();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fcmHandler.disableFCM();
//            }
//        }, 20000);

//        sharedPref = getActivity().getSharedPreferences("token", MODE_PRIVATE);
//        Log.d(TAG, "onCreateView: " + sharedPref.getString("user_token", "empty"));
//        token = sharedPref.getString("user_token", "empty");
//        if (!token.equals("empty")) {
//            addToken();
//        }

//        getCategories();

        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
//                if (!newQuery.equals("")) {
                searchProductName = newQuery;
                searchProduct(searchProductName);
//                } else {
//                    recyclerViewProductList.setVisibility(View.GONE);
//                    floatingSearchView.clearSuggestions();
//                }
            }
        });

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                Log.d("CustomerHomeFragment_", "onSearchAction");
                searchProductName = currentQuery;
                searchProduct(searchProductName);
            }
        });

        floatingSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {
//                recyclerViewProductList.setVisibility(View.GONE);
                try {
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    searchProduct("");
                } catch (Exception e) {
                    Log.d("CustomerHomeFragment_", "Error : " + e.getMessage());
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        relativeLayout.setVisibility(View.GONE);
        relativeLayoutProgress.setVisibility(View.VISIBLE);
        if (searchResult != null) {
            searchResult.clear();
        }
        if (allProducts != null) {
            allProducts.clear();
        }
        if (allCategories != null) {
            allCategories.clear();
        }
        getCategories();
    }

    @Override
    public void getAllMasterProducts() {
        customerHomePresenter.getAllMasterProducts();
    }

    @Override
    public void loadMasterProducts(List<MasterProductDetailsDataModel> masterProductDetailsDataModels) {
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayoutProgress.setVisibility(View.GONE);
        allProducts = masterProductDetailsDataModels;
        searchProduct(searchProductName);
    }

    private void searchProduct(String s) {
        if (searchResult.size() != 0) {
            searchResult.clear();
        }

        List<CustomerProductSearchSuggestion> strings = new ArrayList<>();

        for (int i = 0; i < allProducts.size(); i++) {
            MasterProductDetailsDataModel masterProductDetailsDataModel = allProducts.get(i);
            if (selectedCategory.toLowerCase().equals("all")) {
                Log.d("CustomerHomeF___", "Product Name : " + masterProductDetailsDataModel.getMasterProductDataModel().getProductName());
                if (masterProductDetailsDataModel.getMasterProductDataModel().getProductName().toLowerCase().contains(s.toLowerCase())) {
                    searchResult.add(allProducts.get(i));
                    strings.add(new CustomerProductSearchSuggestion(masterProductDetailsDataModel.getMasterProductDataModel().getProductName()));
                }
            } else {
                if (masterProductDetailsDataModel.getMasterProductDataModel().getProductName().toLowerCase().contains(s.toLowerCase()) && masterProductDetailsDataModel.getMasterProductDataModel().getProductCategory().equals(selectedCategory)) {
                    searchResult.add(allProducts.get(i));
                    strings.add(new CustomerProductSearchSuggestion(masterProductDetailsDataModel.getMasterProductDataModel().getProductName()));
                }
            }
        }
        if (searchResult.size() != 0) {
//            floatingSearchView.swapSuggestions(strings);
//            recyclerViewProductList.setVisibility(View.VISIBLE);
            relativeLayoutNoProducts.setVisibility(View.GONE);
            count = searchResult.size() - 1;
//            showProducts();
//            checkProductChecklisted();
            getMylist();
            Log.d("CustomerHomeFragment_", "Search Result Count : " + strings.size());
        } else {
//            recyclerViewProductList.setVisibility(View.GONE);
            relativeLayoutNoProducts.setVisibility(View.VISIBLE);
            Log.d("CustomerHomeFragment_", "Search Result Count : No Product");
        }
    }

    @SuppressLint("RxLeakedSubscription")
    private void checkProductChecklisted() {

//        productDatabase.daoAccess().checkAvailability(searchResult.get(count).getMasterProductDataModel().getProductID())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(cartProductDataModel1 -> handleResponse(cartProductDataModel1), throwable -> handleError(throwable));

        productDatabase.daoAccess().loadAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartProductDataModel1 -> handleResponse(cartProductDataModel1), throwable -> handleError(throwable));
    }

    private void handleResponse(List<CartProductDataModel> cartProductDataModels) {
        for (int i = 0; i < searchResult.size(); i++) {
            for (int j = 0; j < cartProductDataModels.size(); j++) {
                if (searchResult.get(i).getMasterProductDataModel().getProductID().equals(cartProductDataModels.get(j).getProductID())) {
                    searchResult.get(i).setMylisted(true);
                }
            }
        }
        showProducts();
    }

    @Override
    public void getMylist() {
        customerHomePresenter.getMylist();
    }

    @Override
    public void loadMylist(List<MasterProductDataModel> products) {
        for (int i = 0; i < searchResult.size(); i++) {
            for (int j = 0; j < products.size(); j++) {
                if (searchResult.get(i).getMasterProductDataModel().getProductID().equals(products.get(j).getProductID())) {
                    searchResult.get(i).setMylisted(true);
                }
            }
        }
        showProducts();
    }

//    private void handleResponse(CartProductDataModel cartProductDataModel1) {
//        Log.d("CustomerSingleProduct__", " : " + cartProductDataModel1);
//        searchResult.get(count).setMylisted(true);
//
//        if (count > 0) {
//            --count;
//            checkProductChecklisted();
//        } else {
//            showProducts();
//        }
//    }

    private void handleError(Throwable throwable) {
        searchResult.get(count).setMylisted(false);
        Log.d("CustomerSingleProduct__", "Error : " + throwable.getMessage());
        if (count > 0) {
            --count;
//            checkProductChecklisted();
        } else {
            showProducts();
            Log.d("CustomerSingleProduct__", "Error : " + throwable.getMessage());
        }
    }

    @Override
    public void loadAllProducts(List<MasterProductDetailsDataModel> masterProductDetailsDataModels) {
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayoutProgress.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Products Loaded", Toast.LENGTH_SHORT).show();
//        dismissProgressDialog();
        allProducts = masterProductDetailsDataModels;
        searchProduct(searchProductName);
    }

    @SuppressLint("ResourceType")
    @Override
    public void showProducts() {
        customerHomeSearchProductAdapter = new CustomerHomeSearchProductAdapter(searchResult, getContext(), this);
        recyclerViewProductList.setHasFixedSize(true);
        recyclerViewProductList.setOnFlingListener(null);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewProductList.setLayoutManager(gridLayoutManager);
        recyclerViewProductList.setAdapter(customerHomeSearchProductAdapter);
        customerHomeSearchProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadCategories(List<CategoryDataModel> categoryDataModels) {
        recyclerViewCategoryList.setVisibility(View.VISIBLE);
        CategoryDataModel categoryDataModel = new CategoryDataModel();
        categoryDataModel.setCategoryImageURL(null);
        categoryDataModel.setCategoryName("All");
        categoryDataModels.add(0, categoryDataModel);
//        getAllMasterProducts();
//        Toast.makeText(getContext(), "Category Loaded", Toast.LENGTH_SHORT).show();
        this.allCategories = categoryDataModels;
        customerHomeCategoryAdapter = new CustomerHomeCategoryAdapter(this.allCategories, getContext(), this);
        recyclerViewCategoryList.setHasFixedSize(true);
        recyclerViewCategoryList.setOnFlingListener(null);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategoryList.setLayoutManager(linearLayout);
        recyclerViewCategoryList.setAdapter(customerHomeCategoryAdapter);
        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(recyclerViewCategoryList);
        customerHomeCategoryAdapter.notifyDataSetChanged();


        getAllMasterProducts();

    }

    @Override
    public void getCategories() {
        customerHomePresenter.getCategories();
    }

    @Override
    public void categoryClicked(String categoryName) {
//        Toast.makeText(getContext(), categoryName, Toast.LENGTH_SHORT).show();
        selectedCategory = categoryName;
//        if (searchProductName != null) {
        searchProduct(searchProductName);
//        }
    }

    @Override
    public void productClicked(MasterProductDetailsDataModel masterProductDetailsDataModel) {
        Toast.makeText(getContext(), String.valueOf(masterProductDetailsDataModel.getMasterProductDataModel().getProductID()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), CustomerSingleProductActivity.class);
        intent.putExtra("productID", masterProductDetailsDataModel.getMasterProductDataModel().getProductID());
        startActivity(intent);
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void addToMylist(MasterProductDetailsDataModel masterProductDetailsDataModel) {

        customerHomePresenter.addToMyList(masterProductDetailsDataModel.getMasterProductDataModel());

        int index = searchResult.indexOf(masterProductDetailsDataModel);
        searchResult.get(index).setMylisted(true);
//        CartProductDataModel cartProductDataModel = new CartProductDataModel();
//        cartProductDataModel.setProductID(masterProductDetailsDataModel.getMasterProductDataModel().getProductID());
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
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                productDatabase.daoAccess().insertProduct(cartProductDataModel);
//            }
//        }).start();
//        Observable.fromCallable(() -> productDatabase.daoAccess().insertProduct(cartProductDataModel))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposabele -> showProgressDialog("Adding"))
//                .subscribe(id -> handleAddProductToMylistProgress(id), throwable -> handleAddOrRemoveError(throwable));
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void removeFromMylist(MasterProductDetailsDataModel masterProductDetailsDataModel) {

        customerHomePresenter.removeFromMylist(masterProductDetailsDataModel.getMasterProductDataModel().getProductID());

        int index = searchResult.indexOf(masterProductDetailsDataModel);
        searchResult.get(index).setMylisted(false);
//        CartProductDataModel cartProductDataModel = new CartProductDataModel();
//        cartProductDataModel.setProductID(masterProductDetailsDataModel.getMasterProductDataModel().getProductID());
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
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                productDatabase.daoAccess().removeProduct(cartProductDataModel);
//            }
//        }).start();

//        productDatabase.daoAccess().removeProduct(cartProductDataModel)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposabele -> showProgressDialog("Removing"))
//                .subscribe(id -> handleAddProductToMylistProgress(id), throwable -> handleError(throwable));

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
        Toast.makeText(getContext(), "Sorry, there seems to be a problem.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.cart, menu);
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
            startActivity(new Intent(getContext(), CustomerCartActivity.class));
//            startActivityForResult(new Intent(getContext(), CustomerCartActivity.class), REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            this.count = searchResult.size() - 1;
//            checkProductChecklisted();
            getMylist();
            customerHomeSearchProductAdapter.notifyDataSetChanged();
        }
    }
}