package com.example.c2n.customer_deal.presenter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.c2n.R;
import com.example.c2n.core.models.CustomerDealDataModel;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ProductDataModel;
import com.example.c2n.core.models.ShopDealDataModel;
import com.example.c2n.customer_deal.di.CustomerDealDI;
import com.example.c2n.customer_deal.presenter.adapter.CartAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerDealFragment extends Fragment implements CustomerDealView {

    private ProgressDialog progressDialog;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @BindView(R.id.recycler_view_product_list)
    RecyclerView recyclerViewProductList;

    @BindView(R.id.no_product)
    LinearLayout linearLayoutNoProduct;

    @Inject
    CustomerDealPresenter customerDealPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_deal, container, false);
        ButterKnife.bind(this, view);
        CustomerDealDI.getCustomerDealComponent().inject(this);
        customerDealPresenter.bind(this, getContext());

        recyclerViewProductList.setVisibility(View.GONE);
        linearLayoutNoProduct.setVisibility(View.GONE);

        customerDealPresenter.loadDeals();

        return view;
    }

    @Override
    public void showProgress(boolean status) {
        if (status) {
            showProgressDialog("Loading...");
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    public void loadDeals(List<CustomerDealDataModel> customerDealDataModels) {
        if (customerDealDataModels.size() != 0) {
            recyclerViewProductList.setVisibility(View.VISIBLE);
            linearLayoutNoProduct.setVisibility(View.GONE);
            ArrayList<ParentObject> parentObjects = new ArrayList<>();
            for (CustomerDealDataModel customerDealDataModel : customerDealDataModels) {
                ArrayList<Object> objects = new ArrayList<>();
                double totalBill = 0;
                int todysDay = new Date().getDay();
                Date todayDate = null;
                try {
                    todayDate = format.parse(format.format(new Date()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < customerDealDataModel.getShopDataModel().getProductsList().size(); i++) {
                    objects.add(customerDealDataModel.getShopDataModel().getProductsList().get(i));
                    ProductDataModel productDataModel = customerDealDataModel.getShopDataModel().getProductsList().get(i);
                    if (productDataModel.getProductOffer() != null) {
                        if (productDataModel.getProductOffer().isOfferStatus()) {
                            if (isOfferCardActive(todysDay, productDataModel.getProductOffer())) {
                                if (productDataModel.getProductOffer().getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(productDataModel.getProductOffer().getOfferEndDate()) >= 0) {
                                    totalBill = totalBill + round(productDataModel.getProductMRP() - (productDataModel.getProductMRP() * productDataModel.getProductOffer().getOfferDiscount()) / 100, 2);
                                } else {
                                    totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                                }
                            } else {
                                totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                            }
                        } else {
                            totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                        }
                    } else {
                        totalBill = totalBill + round(productDataModel.getProductMRP(), 2);
                    }
                }
                ShopDealDataModel shopDealDataModel = new ShopDealDataModel();
                shopDealDataModel.setProductDataModel(objects);
                shopDealDataModel.setTotalBill(totalBill);
                shopDealDataModel.setDiscount(String.valueOf(customerDealDataModel.getPercent()));
                shopDealDataModel.setShopDataModel(customerDealDataModel.getShopDataModel());
                shopDealDataModel.setStatus(customerDealDataModel.getStatus());
                parentObjects.add(shopDealDataModel);

                CartAdapter cartAdapter = new CartAdapter(getContext(), parentObjects);
                cartAdapter.setCustomParentAnimationViewId(R.id.cart_arrow_down);
                cartAdapter.setParentClickableViewAnimationDefaultDuration();
                cartAdapter.setParentAndIconExpandOnClick(true);

                recyclerViewProductList.setAdapter(cartAdapter);
                recyclerViewProductList.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        } else {
            recyclerViewProductList.setVisibility(View.GONE);
            linearLayoutNoProduct.setVisibility(View.VISIBLE);
        }
    }

    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
                break;
            case 1:
                if (offerCard.isMon())
                    return true;
                break;
            case 2:
                if (offerCard.isTue())
                    return true;
                break;
            case 3:
                if (offerCard.isWed())
                    return true;
                break;
            case 4:
                if (offerCard.isThu())
                    return true;
                break;
            case 5:
                if (offerCard.isFri())
                    return true;
                break;
            case 6:
                if (offerCard.isSat())
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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
}