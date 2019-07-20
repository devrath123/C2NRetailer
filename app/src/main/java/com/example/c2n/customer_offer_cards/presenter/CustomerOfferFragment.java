package com.example.c2n.customer_offer_cards.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.c2n.R;
import com.example.c2n.core.model.ShopDataModel;
import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferedProductsDataModel;
import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.customer_offer_cards.di.CustomerOfferDI;
import com.example.c2n.customer_offer_cards.presenter.adapter.SectionProductsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class CustomerOfferFragment extends Fragment implements CustomerOfferView, SectionProductsRecyclerViewAdapter.OfferedProductsAdapterInterface {

    int REQUEST_CODE = 2323;

    @BindView(R.id.recycler_view_customer_offers)
    RecyclerView recyclerView;

    @BindView(R.id.relative_layout_offer_products_progress)
    RelativeLayout relativeLayoutProgress;

    @Inject
    CustomerOfferPresenter customerOfferPresenter;

//    CustomerOfferAdapter customerOfferAdapter;

    SectionProductsRecyclerViewAdapter sectionProductsRecyclerViewAdapter;
    List<HashMap<OfferDataModel, List<ProductDataModel>>> offeredProducts;


    ShopDataModel shopDataModel;
    ProductDataModel productDataModel;
    ProgressDialog progressDialog;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_offer, container, false);
        ButterKnife.bind(this, view);
        CustomerOfferDI.getComponent().inject(this);
        customerOfferPresenter.bind(this, context);
        recyclerView.setVisibility(View.GONE);
        relativeLayoutProgress.setVisibility(View.VISIBLE);
        getAllOffers();
        return view;
    }

    private void getAllOffers() {
        customerOfferPresenter.getRetailersCount();
    }

    @Override
    public void loadOffers(List<HashMap<OfferDataModel, List<ProductDataModel>>> offeredProducts) {
        this.offeredProducts = offeredProducts;
        recyclerView.setVisibility(View.VISIBLE);
        relativeLayoutProgress.setVisibility(View.GONE);
        ArrayList<OfferedProductsDataModel> offeredProductsDataModels = new ArrayList<>();
        for (HashMap<OfferDataModel, List<ProductDataModel>> offeredProductsHashmap : offeredProducts) {
            OfferedProductsDataModel offeredProductDataModel = new OfferedProductsDataModel();
            offeredProductDataModel.setOfferDataModel(offeredProductsHashmap.keySet().iterator().next());
            offeredProductDataModel.setProducts(offeredProductsHashmap.get(offeredProductsHashmap.keySet().iterator().next()));
//            allOfferedProductsList = offeredProductsHashmap.get(offeredProductsHashmap.keySet().iterator().next());
//            if (allOfferedProductsList.size() > 2) {
//                for (int i = 0; i < 2; i++) {
//                    offeredProductsList.add(allOfferedProductsList.get(i));
//                }
//                offeredProductDataModel.setProducts(offeredProductsList);
//            } else
//                offeredProductDataModel.setProducts(allOfferedProductsList);
//
            offeredProductsDataModels.add(offeredProductDataModel);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
//        Log.d("loadOffers",""+this.offerDetailsDataModels.size());
//        Log.d("loadOffer",offerDetailsDataModels.toString());

        sectionProductsRecyclerViewAdapter = new SectionProductsRecyclerViewAdapter(getContext(), offeredProductsDataModels, this);

//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(sectionProductsRecyclerViewAdapter);
        sectionProductsRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress(Boolean flag) {
        if (flag) {
            showProgressDialog("Loading offers");
        } else
            dismissProgressDialog();

    }

    @Override
    public String shopId() {
        return shopDataModel.getShopEmail();
    }

    @Override
    public String productID() {
        return productDataModel.getProductID();
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
    public void showAllOfferedProducts(OfferDataModel offerDataModel, List<ProductDataModel> allOfferedProductsList) {
        Intent intent = new Intent(getContext(), CustomerSingleOfferProducts.class);
        intent.putExtra("offerDatModel", offerDataModel);
        intent.putExtra("count", allOfferedProductsList.size());
        for (int i = 0; i < allOfferedProductsList.size(); i++) {
            intent.putExtra("offeredProductsList" + i, allOfferedProductsList.get(i));
        }
        startActivity(intent);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent CustomerSingleProductRepository) {
//        super.onActivityResult(requestCode, resultCode, CustomerSingleProductRepository);
//        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
//            int count = (int) CustomerSingleProductRepository.getExtras().get("count");
//            List<ProductDataModel> productDataModels = new ArrayList<>();
//            for (int i = 0; i < count; i++) {
//                productDataModels.add((ProductDataModel) CustomerSingleProductRepository.getExtras().get("offeredProductsList" + i));
//            }
//            Log.d("QWERTY_", "Size : " + count);
//        }
//    }
}
