package com.example.c2n.master_list.presenter;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.c2n.R;
import com.example.c2n.addproduct.presenter.AddProductActivity;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.models.MasterProductDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.master_list.di.MasterListDI;
import com.example.c2n.master_list.presenter.adapter.MasterListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListActivity extends BaseActivity implements MasterListView, MasterListAdapter.MasterListAdapterInterface {

    private static final String TAG = MasterListActivity.class.getSimpleName();

    private MasterListAdapter masterListAdapter;
    private ShopDataModel shopDataModel;

    @Inject
    MasterListPresenter masterListPresenter;

    @BindView(R.id.master_list)
    RecyclerView recyclerViewMasterList;

    @BindView(R.id.button_add_product)
    Button buttonAddProduct;

    @BindView(R.id.rl_master_products)
    RelativeLayout relativeLayoutMasterProducts;

    @BindView(R.id.rl_no_product)
    RelativeLayout relativeLayoutNoProducts;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_master_list;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        MasterListDI.getMasterListComponent().inject(this);
        masterListPresenter.bind(this, this);

        Intent intent = getIntent();
        if (intent != null) {
            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
//            if (intent.getSerializableExtra("productDataModel") != null) {
//                productDataModel = (ProductDataModel) intent.getSerializableExtra("productDataModel");
//                Log.d(TAG, "initActivity: ProductDataModel : " + productDataModel.toString());
//            }
        }

        getAllProducts();

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MasterListActivity.this, "Add Product", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MasterListActivity.this, AddProductActivity.class);
                intent.putExtra("shopDataModel", shopDataModel);
                startActivity(intent);
                finish();
            }
        });

        getSupportActionBar().setTitle("Select Product");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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

    void getAllProducts() {
        masterListPresenter.getAllProducts();
    }

    @Override
    public void loadAllProducts(List<MasterProductDataModel> masterProductList) {
        if (masterProductList.size() != 0) {
            relativeLayoutNoProducts.setVisibility(View.GONE);
            relativeLayoutMasterProducts.setVisibility(View.VISIBLE);
            masterListAdapter = new MasterListAdapter(masterProductList, this, this);
            recyclerViewMasterList.setHasFixedSize(true);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewMasterList.setLayoutManager(linearLayout);
            recyclerViewMasterList.setAdapter(masterListAdapter);
            masterListAdapter.notifyDataSetChanged();
        } else {
            relativeLayoutNoProducts.setVisibility(View.VISIBLE);
            relativeLayoutMasterProducts.setVisibility(View.GONE);
        }
    }

    @Override
    public void productClicked(MasterProductDataModel masterProductDataModel) {
//        Toast.makeText(MasterListActivity.this, masterProductDataModel.getProductName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MasterListActivity.this, AddProductActivity.class);
        intent.putExtra("shopDataModel", shopDataModel);
        intent.putExtra("masterProductDataModel", masterProductDataModel);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgress(boolean flag) {
        if (flag) {
            showProgressDialog("Loading master list...");
        } else {
            dismissProgressDialog();
        }
    }
}
