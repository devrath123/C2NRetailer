package com.example.c2n.retailer_offer_products.presenter.presenter.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.master_list.presenter.MasterListActivity;
import com.example.c2n.offer_cards_list.presenter.OffersListActivity;
import com.example.c2n.retailer_offer_products.presenter.presenter.di.OfferProductsDI;
import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.adapter.OfferProductsCategoryAdapter;
import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.adapter.OfferProductsProductsAdapter;
import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.adapter.OfferTodaysProductsProductsAdapter;
import com.example.c2n.retailer_shop_products_list.di.RetailerShopProductsDI;
import com.example.c2n.retailercategory.di.RetailerCategoryDI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfferProductsActivity extends BaseActivity implements OfferProductsProductsAdapter.OfferProductsProductsInterface, OfferProductsView, OfferProductsCategoryAdapter.OfferProductsCategoryInterface {

    private final String TAG = "OfferProductsActivity";

    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerViewCategory;

    @BindView(R.id.recycler_view_products)
    RecyclerView recyclerViewProducts;

    @BindView(R.id.spinner_shop_list)
    Spinner spinnerShopList;

    @BindView(R.id.layout_no_product)
    RelativeLayout relativeLayoutNoProduct;

    @BindView(R.id.bt_apply_offer)
    Button buttonApplyOffer;

    @Inject
    OfferProductsPresenter offerProductsPresenter;

    private ShopDataModel selectedShop;

    private int categoryClickedPosition;
    private List<ProductDataModel> selectedProducts;
    private List<String> selectedProductIDs;
    private List<String> deselectedProductIDs;
    private List<ProductDataModel> productDataModels;
    private List<String> categories;
    private List<String> shopList;
    private List<String> shopIDList;

    private OfferProductsCategoryAdapter offerProductsCategoryAdapter;
    private OfferProductsProductsAdapter offerProductsProductsAdapter;
    private OfferTodaysProductsProductsAdapter offerTodaysProductsProductsAdapter;
    private OfferDataModel offerDataModelCard;
    List<ShopDataModel> allShops = new ArrayList<>();
    int selectedShopPosition = 0;
    Boolean isAddProductButton = false;

    private int flag;
    private String userID, shopID;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_offer_products;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        RetailerCategoryDI.getRetailerCategoryComponent().inject(this);
        RetailerShopProductsDI.getRetailerShopProductsComponent().inject(this);
        OfferProductsDI.getOfferProductsComponent().inject(this);
        offerProductsPresenter.bind(this, this);
        relativeLayoutNoProduct.setVisibility(View.GONE);

        selectedProducts = new ArrayList<>();
        selectedProductIDs = new ArrayList<>();
        deselectedProductIDs = new ArrayList<>();
        categories = new ArrayList<>();

        SharedPrefManager.Init(this);
        SharedPrefManager.LoadFromPref();
        userID = SharedPrefManager.get_userDocumentID();

        Intent intent = getIntent();
        if (intent != null) {
            offerDataModelCard = (OfferDataModel) intent.getSerializableExtra("offerListDataModel");
        }

        Bundle bundle = getIntent().getExtras();
        flag = bundle.getInt("flag");

        getSupportActionBar().setTitle(offerDataModelCard.getOfferName() + " (" + offerDataModelCard.getOfferDiscount() + "% off" + ")");

        productDataModels = new ArrayList<>();
//        showShopList(offerDataModelCard.getShopDataModels());
        if (flag == 1) {
            getShopList();
        } else {
            showShopList(offerDataModelCard.getShopDataModels());
        }
//        getShopCategory();

        spinnerShopList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("OfferProductsActivity_", shopList.get(position));
                if (selectedProductIDs.size() != 0) {
                    selectedProductIDs.clear();
                }
                if (deselectedProductIDs.size() != 0) {
                    deselectedProductIDs.clear();
                }
                selectedShop = allShops.get(position);
                selectedShopPosition = position;
                shopID = shopIDList.get(position);
                shopCategoryList(selectedShop.getProductsList());
//                if (flag == 1)
//                    offerProductsPresenter.loadProducts(getUserID(), shopIDList.get(position));
//                else
//                    offerProductsPresenter.loadTodaysProducts(shopIDList.get(position), categoryDataModel.getCategory(), offerDataModelCard.getOfferID());

//                for (int i = 0; i < categoryProductsList.length; i++) {
//                    categoryProductsList[i].clear();
//                }
//                productDataModels.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void getShopCategory() {
        offerProductsPresenter.getCategoryList();
    }

    @Override
    public void showShopCategory(List<String> categories) {
        Log.d("showShopCategory_", "size : " + categories.size());
//        categoryProductsList = new List[categoryDataModels.size()];
//        for (int i = 0; i < categoryDataModels.size(); i++) {
//            categoryProductsList[i] = new ArrayList<>();
//        }
//        getShopList();
//        this.categoryDataModel = categoryDataModels.get(0);
        offerProductsCategoryAdapter = new OfferProductsCategoryAdapter(categories, this, this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setLayoutManager(linearLayout);
        recyclerViewCategory.setAdapter(offerProductsCategoryAdapter);
        offerProductsCategoryAdapter.notifyDataSetChanged();

        categoryClicked(categories.get(0), 0);
    }

    @Override
    public void getShopList() {
        offerProductsPresenter.loadShops();
    }

    @Override
    public void showShopList(List<ShopDataModel> shopDataModels) {
        Log.d(TAG, "showShopList Shop : " + shopDataModels.get(0));
        this.allShops = shopDataModels;
        shopList = new ArrayList<>();
        shopIDList = new ArrayList<>();
        for (ShopDataModel shopDataModel : shopDataModels) {
            shopList.add(shopDataModel.getShopName());
            shopIDList.add(shopDataModel.getShopID());
        }
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, shopList);
//        spinnerShopList.setAdapter(dataAdapter);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, shopList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerShopList.setAdapter(spinnerArrayAdapter);
        spinnerShopList.performClick();
    }

    @Override
    public String getShopID() {
        return shopID;
    }

    @Override
    public void shopClicked() {

    }

    @Override
    public void categoryClicked(String categoryDataModel, int categoryPosition) {
//        selectedCategory = categoryDataModel.getCategory();
        this.categoryClickedPosition = categoryPosition;
//        this.categoryDataModel = categoryDataModel;
        int position = spinnerShopList.getSelectedItemPosition();

        List<ProductDataModel> currentCategoryProducts = new ArrayList<>();
        if (currentCategoryProducts.size() != 0)
            currentCategoryProducts.clear();
        for (int i = 0; i < productDataModels.size(); i++) {
            ProductDataModel productDataModel = productDataModels.get(i);
            if (productDataModel.getProductCategory().equals(categoryDataModel)) {
                currentCategoryProducts.add(productDataModel);
            }
        }
        if (flag == 1) {
            relativeLayoutNoProduct.setVisibility(View.GONE);
            offerProductsProductsAdapter = new OfferProductsProductsAdapter(currentCategoryProducts, this, this, offerDataModelCard.getOfferID());
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewProducts.removeAllViews();
            recyclerViewProducts.setHasFixedSize(true);
            recyclerViewProducts.setLayoutManager(linearLayout);
            recyclerViewProducts.setAdapter(offerProductsProductsAdapter);
            offerProductsProductsAdapter.notifyDataSetChanged();
        } else {

            List<ProductDataModel> productDataModels = new ArrayList<>();
            for (ProductDataModel pdm : this.productDataModels) {
                if (pdm.getProductCategory().equals(categoryDataModel)) {
                    productDataModels.add(pdm);
                }
            }

            relativeLayoutNoProduct.setVisibility(View.GONE);
            buttonApplyOffer.setVisibility(View.GONE);
            offerTodaysProductsProductsAdapter = new OfferTodaysProductsProductsAdapter(productDataModels, this, offerDataModelCard);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewProducts.removeAllViews();
            recyclerViewProducts.setHasFixedSize(true);
            recyclerViewProducts.setLayoutManager(linearLayout);
            recyclerViewProducts.setAdapter(offerTodaysProductsProductsAdapter);
            offerTodaysProductsProductsAdapter.notifyDataSetChanged();
        }
//        if (categoryProductsList[categoryPosition].size() == 0) {
//            if (flag == 1)
//        offerProductsPresenter.loadProducts(shopIDList.get(position), categoryDataModel.getCategory());
//            else
//                offerProductsPresenter.loadTodaysProducts(shopIDList.get(position), categoryDataModel.getCategory(), offerDataModelCard.getOfferID());
        Log.d("OfferProductsActivity", "in if");
//        } else {
//            shopProductsList(categoryProductsList[categoryPosition]);
//            Log.d("OfferProductsActivity", "in else");
//        }
    }

    public void showProductList(List<ProductDataModel> productDataModels) {

    }

    @Override
    public void shopCategoryList(List<ProductDataModel> productDataModels) {
        if (productDataModels != null) {
            recyclerViewCategory.setVisibility(View.VISIBLE);
            this.productDataModels = productDataModels;
            buttonApplyOffer.setText("Apply");
            isAddProductButton = false;
//        if (categoryProductsList[categoryClickedPosition].size() == 0) {
//            categoryProductsList[categoryClickedPosition].addAll(productDataModels);
//            for (int i = 0; i < categoryProductsList[categoryClickedPosition].size(); i++) {
//                categoryProductsList[categoryClickedPosition].get(i).setChecked(false);
//                categoryProductsList[categoryClickedPosition].get(i).setProductDescription("");
//            }
//        }

            if (flag == 1) {
                if (categories.size() != 0) {
                    categories.clear();
                }
                for (ProductDataModel productDataModel : productDataModels) {
                    if (!categories.contains(productDataModel.getProductCategory())) {
                        categories.add(productDataModel.getProductCategory());
                    }
                }
                if (categories.size() == 0) {
                    setNoProductBackground();
                } else {
                    showShopCategory(categories);
                }
            } else {
                if (categories.size() != 0) {
                    categories.clear();
                }
//                for (ProductDataModel productDataModel : productDataModels) {
//                    if (productDataModel.getProductOffer() != null) {
//                        if (productDataModel.getProductOffer().getOfferID().equals(offerDataModelCard.getOfferID())) {
//                            if (!categories.contains(productDataModel.getProductCategory())) {
//                                categories.add(productDataModel.getProductCategory());
//                            }
//                        }
//                    }
//                }
                for (ProductDataModel productDataModel : productDataModels) {
                    if (!categories.contains(productDataModel.getProductCategory())) {
                        categories.add(productDataModel.getProductCategory());
                    }
                }
                if (categories.size() == 0) {
                    setNoProductBackground();
                } else {
                    showShopCategory(categories);
                }
            }
        } else {
            setNoProductBackground();
        }


//        relativeLayoutNoProduct.setVisibility(View.GONE);
//        offerProductsProductsAdapter = new OfferProductsProductsAdapter(productDataModels, this, this, offerDataModelCard.getOfferDocumentId());
//        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
//        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerViewProducts.removeAllViews();
//        recyclerViewProducts.setHasFixedSize(true);
//        recyclerViewProducts.setLayoutManager(linearLayout);
//        recyclerViewProducts.setAdapter(offerProductsProductsAdapter);
//        offerProductsProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(Boolean progress) {
        if (progress)
            showProgressDialog("Loading Products...");
        else
            dismissProgressDialog();
    }

    @Override
    public void showUpdateProductOffer(Boolean progress) {
        if (progress)
            showProgressDialog("Updating Offers...");
        else
            dismissProgressDialog();
    }

    @Override
    public void openOfferCardsActivity() {
        startActivity(new Intent(OfferProductsActivity.this, OffersListActivity.class));
    }

    @Override
    public void setNoProductBackground() {
        buttonApplyOffer.setVisibility(View.VISIBLE);
        recyclerViewCategory.setVisibility(View.GONE);
        relativeLayoutNoProduct.setVisibility(View.VISIBLE);
        buttonApplyOffer.setVisibility(View.VISIBLE);
        buttonApplyOffer.setText("Add Product");
        isAddProductButton = true;
    }

    @OnClick(R.id.bt_apply_offer)
    public void applyOffer() {

        if (isAddProductButton) {
            Log.d("selectedShop", allShops.get(selectedShopPosition).toString());
            Intent intent = new Intent(this, MasterListActivity.class);
            intent.putExtra("shopDataModel", allShops.get(selectedShopPosition));
            startActivity(intent);
        } else {
//            Toast.makeText(OfferProductsActivity.this, "" + selectedProductIDs.size(), Toast.LENGTH_SHORT).show();
//        if (selectedProducts.size() != 0)
//        showUpdateProductOffer(true);
//            offerProductsPresenter.setProductList();
//            offerProductsPresenter.removeOffer();
//            showAlertDialog();
//        offerProductsPresenter.updateOffers();
//        else
//            offerProductsPresenter.removeOffers();

//            applyOffer method should be here

//            offerProductsPresenter.applyOffers();
            if (selectedProductIDs.size() == 0 && deselectedProductIDs.size() == 0) {
                Toast.makeText(this, "No change in offers", Toast.LENGTH_SHORT).show();
            } else {
                offerProductsPresenter.removeOffers();
            }

        }
    }

    @Override
    public Object[] getProductIDs() {
        return new Object[]{selectedProductIDs, selectedShop.getShopID(), offerDataModelCard, deselectedProductIDs, selectedShop, selectedProducts};
    }

    @Override
    public String getOfferCardDocumentID() {
        return offerDataModelCard.getOfferID();
    }

    @Override
    public List<ProductDataModel> getProductList() {
//        Log.d("OfferProductsActivity_", "" + productDataModels.size());
//        if (productDataModels.size() != 0)
//            return productDataModels;
//        else {
//            for (int i = 0; i < categoryProductsList.length; i++) {
////                productDataModels.addAll(categoryProductsList[i]);
//                for (int j = 0; j < categoryProductsList[i].size(); j++) {
//                    categoryProductsList[i].get(j).setProductDescription("");
//                    categoryProductsList[i].get(j).setChecked(false);
//                }
//            }
//        }
//        Log.d("OfferProductsActivity_", "" + productDataModels.size());
//        return productDataModels;
//    }
//        productDataModels.clear();
//        for (int i = 0; i < categoryProductsList.length; i++) {
//            productDataModels.addAll(categoryProductsList[i]);
//            for (int j = 0; j < categoryProductsList[i].size(); j++) {
//                categoryProductsList[i].get(j).setProductDescription("");
//                categoryProductsList[i].get(j).setChecked(false);
//            }
//        }
        for (int i = 0; i < selectedProductIDs.size(); i++) {
            for (int j = 0; j < productDataModels.size(); j++) {
                if (selectedProductIDs.get(i).equals(productDataModels.get(j).getProductID())) {
                    productDataModels.get(j).setProductOffer(offerDataModelCard);
                    productDataModels.get(j).getProductOffer().setShopDataModels(null);
                }
            }
        }
        Log.d("OfferProductsActivity__", "" + productDataModels.size());
        return productDataModels;
    }

    @Override
    public void productSelected(ProductDataModel productDataModel, int position) {
//        Log.d("OfferProductsActivity_", productDataModel.toString());
//        productDataModel.setChecked(true);
//        productDataModel.setProductDescription(offerDataModelCard.getOfferDocumentId());
//        categoryProductsList[categoryClickedPosition].get(position).setChecked(true);
//        selectedProducts.add(productDataModel);
//        Toast.makeText(OfferProductsActivity.this, "Added", Toast.LENGTH_SHORT).show();
//        int pos = productDataModels.indexOf(productDataModel);
//        offerDataModelCard.setOfferProducts(null);
//        productDataModels.get(pos).setProductOffer(offerDataModelCard);

        selectedProductIDs.add(productDataModel.getProductID());
        selectedProducts.add(productDataModel);

//        productDataModel.setProductOffer(offerDataModelCard);
//        selectedProductIDs.add(productDataModel.getProductID());
//        Log.d("OfferProductsActivity_", "array size : " + selectedProductIDs.size());

//        int index = categoryProductsList[categoryClickedPosition].indexOf(productDataModel);
//        categoryProductsList[categoryClickedPosition].get(index).setProductOfferID(offerDataModelCard.getOfferID());
//        categoryProductsList[categoryClickedPosition].get(index).setChecked(true);
//        Log.d("OfferProductsActivity_", "selected " + index + " " + categoryProductsList[categoryClickedPosition].get(index).getProductOfferID());
    }

    @Override
    public void productDeselected(ProductDataModel productDataModel, int position) {
//        productDataModel.setChecked(false);
//        productDataModel.setProductDescription("");
//        selectedProducts.remove(selectedProducts.indexOf(productDataModel));
//        Toast.makeText(OfferProductsActivity.this, "Removed", Toast.LENGTH_SHORT).show();
//        int pos = productDataModels.indexOf(productDataModel);
//        offerDataModelCard.setOfferProducts(null);
//        productDataModels.get(pos).setProductOffer(null);

        if (selectedProductIDs.contains(productDataModel.getProductID())) {
            selectedProductIDs.remove(selectedProductIDs.indexOf(productDataModel.getProductID()));
            selectedProducts.remove(selectedProducts.indexOf(productDataModel));
//            Log.d("OfferProductsActivity_", "array size : " + selectedProductIDs.size());
        } else {
            deselectedProductIDs.add(productDataModel.getProductID());
        }
//        if (!selectedProductIDs.contains(productDataModel)) {
//            int index = productDataModels.indexOf(productDataModel);
//            productDataModels.get(index).setProductOffer(null);
//            Log.d("OfferProductsActivity_", "unchecked : " + productDataModels.get(index));
//        }

//        int index = categoryProductsList[categoryClickedPosition].indexOf(productDataModel);
//        categoryProductsList[categoryClickedPosition].get(index).setProductOfferID("");
//        categoryProductsList[categoryClickedPosition].get(index).setChecked(false);
//        Log.d("OfferProductsActivity_", "deselected " + index + " " + categoryProductsList[categoryClickedPosition].get(index).getProductOfferID());

    }

    @Override
    public void startOfferCardActivity() {
        startActivity(new Intent(OfferProductsActivity.this, OffersListActivity.class));
        finish();
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public void datahanged(List<ProductDataModel> productDataModels) {

    }

    @Override
    public void notifyDataSetChnaged() {

    }

    @Override
    public void startApplyOffer() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                offerProductsPresenter.setNewProductList();
                offerProductsPresenter.updateOffers();

            }
        }, 300);
//        offerProductsPresenter.updateOffers();
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Your request is submitted. It will take few minutes and will be reflected soon.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(OfferProductsActivity.this, OffersListActivity.class));
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
