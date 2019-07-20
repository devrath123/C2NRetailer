package com.example.c2n.offer_cards_list.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.make_offer_card.di.MakeOfferCardDI;
import com.example.c2n.make_offer_card.presenter.MakeOfferCardActivity;
import com.example.c2n.offer_cards_list.di.OffersListDI;
import com.example.c2n.offer_cards_list.presenter.adapter.OffersListAdapter;
import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.OfferProductsActivity;
import com.example.c2n.retailerhome.presenter.RetailerHomeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffersListActivity extends BaseActivity implements OffersListView, OffersListAdapter.OfferCardSelected {

    @BindView(R.id.recycler_offer_view)
    RecyclerView recyclerViewOffer;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.layout_add_offercard)
    LinearLayout addOffercardLayout;

    @Inject
    OffersListPresenter offersListPresenter;

    OffersListAdapter offersListAdapter;

    List<OfferDataModel> offersList = new ArrayList<>();
    OfferDataModel offerDataModel;
    Boolean backToAddProduct = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_offers_list;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(this);
        OffersListDI.getOfferComponent().inject(this);
        MakeOfferCardDI.getMakeOfferCardComponent().inject(this);
        offersListPresenter.bind(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            backToAddProduct = bundle.getBoolean("activateCard");
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Offer Cards");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPrefManager.Init(this);
        offersListAdapter = new OffersListAdapter(offersList, this, this);
        recyclerViewOffer.setAdapter(offersListAdapter);
        loadOfferListScheme();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        clearAdapter();
//        loadOfferListScheme();
//        if (offersListAdapter != null)
//            offersListAdapter.notifyDataSetChanged();
    }
//
//    private void clearAdapter() {
//        if (offersList != null) {
//            offersList.clear();
//        }
//    }

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
        super.onBackPressed();
//        if (backToAddProduct)
//            finish();
//        else
        startActivity(new Intent(this, RetailerHomeActivity.class));
        finish();
    }

    @Override
    public String getOffercardDocumentId() {
        if (offerDataModel != null)
            return offerDataModel.getOfferID();
        return "";
    }


    @Override
    public void showProgress(Boolean progress) {
        if (progress)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDeletionProgress(Boolean progress) {
        if (progress)
            showProgressDialog("Deleting Offer Card...");
        else
            dismissProgressDialog();
    }

    @Override
    public void showActivationProgress(Boolean progress) {
        if (progress)
            showProgressDialog("Activating Offer...");
        else
            dismissProgressDialog();
    }

    @Override
    public void isOfferCardActivationSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {
            showToast("Offer Card Activated.");
            finish();
            startActivity(new Intent(this, OffersListActivity.class));

        } else
            showToast("Please Retry...");
    }

    @Override
    public void showDeactivationProgress(Boolean progress) {
        if (progress)
            showProgressDialog("Deactivating Offer...");
        else
            dismissProgressDialog();
    }

    @Override
    public void isOfferCardDeactivationSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {
            showToast("Offer Card Deactivated.");
            finish();
            startActivity(new Intent(this, OffersListActivity.class));

//            onResume();
        } else
            showToast("Please Retry...");
    }

    public void loadOfferListScheme() {
        offersListPresenter.loadOffersList();
    }

    @Override
    public void showOffersList(List<OfferDataModel> offerDataModels) {
        if (offerDataModels != null && offerDataModels.size() != 0) {
            offersList = offerDataModels;
            offersListAdapter = new OffersListAdapter(offersList, this, this);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewOffer.setHasFixedSize(true);
            recyclerViewOffer.setLayoutManager(linearLayout);
            recyclerViewOffer.setAdapter(offersListAdapter);
        } else
            addOffercardLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void isOfferCardDeletionSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {
            showToast("Offer Card Deleted.");
            finish();
            startActivity(new Intent(this, OffersListActivity.class));
//            onResume();
        } else
            showToast("Please Retry...");
    }

    @OnClick(R.id.btn_add_offercard)
    public void addOfferCard() {
        startActivity(new Intent(OffersListActivity.this, MakeOfferCardActivity.class));
    }


    @OnClick(R.id.fab_make_offercard)
    @Override
    public void addNewOffers() {
        if (ConnectivityReceiver.isConnected()) {
            startActivity(new Intent(OffersListActivity.this, MakeOfferCardActivity.class));
        } else {
            Toast.makeText(OffersListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void offerCardSelected(OfferDataModel offerDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(this, OfferProductsActivity.class);
//        Intent intent = new Intent(this, ViewShopsFragment.class);
            intent.putExtra("offerListDataModel", offerDataModel);
            intent.putExtra("flag", 1);
            startActivity(intent);
            Log.d("offerListAct._sele", offerDataModel.toString());
        } else {
            Toast.makeText(OffersListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void editOfferCard(OfferDataModel offerDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(this, MakeOfferCardActivity.class);
            intent.putExtra("offerDataModel", offerDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(OffersListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteOfferCard(OfferDataModel offerDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            this.offerDataModel = offerDataModel;
            offersListPresenter.deleteOfferCard();
        } else {
            Toast.makeText(OffersListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void activateOfferCard(OfferDataModel offerDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            this.offerDataModel = offerDataModel;
            offersListPresenter.activateOfferCard();
        } else {
            Toast.makeText(OffersListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deactivateOfferCard(OfferDataModel offerDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            this.offerDataModel = offerDataModel;
            offersListPresenter.deactivateOfferCard();
        } else {
            Toast.makeText(OffersListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
