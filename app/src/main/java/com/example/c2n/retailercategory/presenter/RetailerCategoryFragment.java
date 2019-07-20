package com.example.c2n.retailercategory.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.core.models.CategoryDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.master_list.presenter.MasterListActivity;
import com.example.c2n.retailer_shop_products_list.presenter.RetailerShopProductsActivity;
import com.example.c2n.retailercategory.di.RetailerCategoryDI;
import com.example.c2n.viewshops.di.ViewShopsDI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RetailerCategoryFragment extends Fragment implements RetailerCategoryView, RetailerCategoryAdapter.RetailerCategoryInterface {

    public static final String TAG = RetailerCategoryFragment.class.getSimpleName();

    @Inject
    RetailerCategoryPresenter retailerCategoryPresenter;

    int REQUEST_CODE = 2323;
    public ProgressDialog progressDialog;
    private RetailerCategoryAdapter retailerCategoryAdapter;

    private List<CategoryDataModel> categoryDataModels;
    private List<String> categories;
    List<CategoryDataModel> activeCategories = new ArrayList<>();
    private String userID;
    private List<ProductDataModel> productDataModels;
    ShopDataModel shopDataModel;

    @BindView(R.id.rCategoryRecyclearView)
    RecyclerView recyclerView;

    @BindView(R.id.layout_no_product)
    RelativeLayout relativeLayoutNoProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retailer_category, container, false);
        ButterKnife.bind(this, view);
        RetailerCategoryDI.getRetailerCategoryComponent().inject(this);
        ViewShopsDI.getViewShopsComponent().inject(this);
        retailerCategoryPresenter.bind(this, getApplicationContext());
        relativeLayoutNoProduct.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(getContext());

        SharedPrefManager.Init(getActivity());
        SharedPrefManager.LoadFromPref();

        userID = SharedPrefManager.get_userDocumentID();


//        getCategory();
//        getCategories();

//        RetailerCategoryActivity retailerCategoryActivity = (RetailerCategoryActivity) getActivity();
//        shopDataModel = retailerCategoryActivity.shopDataModel;
//        getProducts();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        productDataModels = new ArrayList<>();
        categoryDataModels = new ArrayList<>();
        categories = new ArrayList<>();
        if (retailerCategoryAdapter != null) {
            retailerCategoryAdapter.clear();
        }
        getCategories();
    }

    public void getCategories() {
        retailerCategoryPresenter.getCategory();
    }

    @Override
    public void loadCategories(List<CategoryDataModel> categoryDataModels) {
        Log.d(TAG, "loadCategories: " + categoryDataModels.toString());
        this.categoryDataModels.clear();
        this.categoryDataModels = categoryDataModels;
//        getCategory();
        getShop();
    }

    public void getShop() {
        retailerCategoryPresenter.getProductCategory();
    }

    @Override
    public void loadProductCategory(ShopDataModel shopDataModel) {
        this.shopDataModel = shopDataModel;
        getCategory();
    }

    @OnClick(R.id.bt_add_new_product)
    public void openNewProductScreen() {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getActivity(), MasterListActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showCategory(List<ProductDataModel> productDataModels) {
//        retailerCategoryPresenter.getCategory();
//        if (productDataModels.size() == 0 || productDataModels == null) {
//            recyclerView.setVisibility(View.GONE);
//            relativeLayoutNoProduct.setVisibility(View.VISIBLE);
//        }
        Log.d("RetailerCategoryFragm__", "" + productDataModels.size());
        if (this.productDataModels.size() != 0)
            this.productDataModels.clear();
        this.productDataModels = productDataModels;
//        retailerCategoryAdapter = new RetailerCategoryAdapter(categoryDataModels, getApplicationContext(), this);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(retailerCategoryAdapter);
//        retailerCategoryAdapter.notifyDataSetChanged();
        if (categories.size() != 0) {
            categories.clear();
        }
        for (ProductDataModel productDataModel : productDataModels) {
            if (!categories.contains(productDataModel.getProductCategory())) {
                categories.add(productDataModel.getProductCategory());
            }
        }

        Log.d("RetailerCategoryFragme_", "Categories : " + categories.toString());

        if (activeCategories.size() != 0) {
            activeCategories.clear();
        }

        Log.d(TAG, "showCategory: CategoryDataModels : " + categoryDataModels.toString());

        for (int i = 0; i < categories.size(); i++) {
            Log.d(TAG, "showCategory: Categories : " + categories.size());
            for (int j = 0; j < categoryDataModels.size(); j++) {
                if (categories.get(i).equals(categoryDataModels.get(j).getCategoryName())) {
                    activeCategories.add(categoryDataModels.get(j));
                    Log.d(TAG, "showCategory: ActiveCategories : " + activeCategories.size());
                }
            }
        }

        Log.d("RetailerCategoryFragme_", "ActiveCategories : " + activeCategories.size());
        retailerCategoryAdapter = new RetailerCategoryAdapter(activeCategories, getContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(retailerCategoryAdapter);
        retailerCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public String getShopID() {
        RetailerCategoryActivity retailerCategoryActivity = (RetailerCategoryActivity) getActivity();
        return retailerCategoryActivity.shopDataModel.getShopID();
    }

    @Override
    public void getCategory() {
//        retailerCategoryPresenter.getProducts();
        if (shopDataModel.getProductsList() != null) {
            Log.d(TAG, "in if");
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayoutNoProduct.setVisibility(View.GONE);
            showCategory(shopDataModel.getProductsList());
        } else {
            Log.d(TAG, "in else");
            recyclerView.setVisibility(View.GONE);
            relativeLayoutNoProduct.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showProgress(boolean flag) {
        if (flag)
            showProgressDialog("Loading Category");
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
    public void categoryClicked(CategoryDataModel category) {
//        ViewProductsFragment viewProductsFragment = new ViewProductsFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.addToBackStack("");
//        fragmentTransaction.replace(R.id.frame, viewProductsFragment, "products");
//        fragmentTransaction.commitAllowingStateLoss();

        //---------------------------------------------------------

//        RetailerCategoryActivity retailerCategoryActivity = (RetailerCategoryActivity) getActivity();
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getContext(), RetailerShopProductsActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            intent.putExtra("count", productDataModels.size());
            for (int i = 0; i < productDataModels.size(); i++) {
                Log.d("RetailerCategoryFragmn_", "" + productDataModels.get(i).toString());
                intent.putExtra("productDataModel" + i, productDataModels.get(i));
            }
//        intent.putExtra("productDataModels", productDataModels);
//        intent.putExtra("shopSelected", retailerCategoryActivity.shopDataModel.getShopName());
            intent.putExtra("categorySelected", category.getCategoryName());
//        intent.putExtra("shopEmail", retailerCategoryActivity.shopDataModel.getShopEmail());
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            int count = (int) data.getExtras().get("count");
            List<ProductDataModel> updatedProductDataModels = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                updatedProductDataModels.add((ProductDataModel) data.getExtras().get("updatedProductDataModel" + i));
            }
            Log.d("RetailerCategoryFrag___", updatedProductDataModels.size() + " : " + updatedProductDataModels.toString());
            for (int i = 0; i < productDataModels.size(); i++) {
                for (int j = 0; j < updatedProductDataModels.size(); j++) {
                    if (updatedProductDataModels.get(j).getProductID().equals(productDataModels.get(i).getProductID())) {
                        productDataModels.set(i, updatedProductDataModels.get(j));
                    }
                }
            }
//            Log.d("RetailerShopProductsAc_", clickedPosition + " : " + updatedProductDataModel.toString());
////            ProductDataModel model = productDataModels.get(clickedPosition);
////            model = updatedProductDataModel;
//            this.productDataModelss.set(clickedPosition, updatedProductDataModel);
//            mAdapter.notifyItemChanged(clickedPosition);
        }
    }
}
