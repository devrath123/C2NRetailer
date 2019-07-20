package com.example.c2n.customer_offer_cards.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.c2n.R;
import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.customer_offer_cards.presenter.adapter.SingleOfferProductsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerSingleOfferProducts extends AppCompatActivity implements SingleOfferProductsAdapter.SingleOfferProductsAdapterInterface {

    private OfferDataModel offerDataModel;
    private List<ProductDataModel> productDataModels = new ArrayList<>();
    @BindView(R.id.recycler_view_single_offer_products)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_single_offer_products);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        int count = 0;
        if (bundle != null) {
            count = bundle.getInt("count");
        }
        Intent intent = getIntent();
        if (intent != null) {
            for (int i = 0; i < count; i++) {
                productDataModels.add((ProductDataModel) intent.getSerializableExtra("offeredProductsList" + i));
            }
            offerDataModel = (OfferDataModel) intent.getSerializableExtra("offerDatModel");
        }
        getSupportActionBar().setTitle(offerDataModel.getOfferName() + " - " + offerDataModel.getOfferDiscount() + " %");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(offerDataModel.getOfferName() + " - " + offerDataModel.getOfferDiscount() + "% OFF");
        showOfferedProducts();
    }

    public void showOfferedProducts() {
        SingleOfferProductsAdapter singleOfferProductsAdapter = new SingleOfferProductsAdapter(offerDataModel, productDataModels, this, this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(singleOfferProductsAdapter);
        singleOfferProductsAdapter.notifyDataSetChanged();

    }

    @Override
    public void offeredProductClicked(ProductDataModel productDataModel) {
        Log.d("offered_product- ", productDataModel.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

