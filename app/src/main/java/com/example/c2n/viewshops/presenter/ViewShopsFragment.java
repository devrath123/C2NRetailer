package com.example.c2n.viewshops.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.addshop.presenter.AddShopActivity;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.master_list.presenter.MasterListActivity;
import com.example.c2n.retailercategory.presenter.RetailerCategoryActivity;
import com.example.c2n.viewshopdetails.presenter.ViewShopActivity;
import com.example.c2n.viewshops.di.ViewShopsDI;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewShopsFragment extends Fragment implements ViewShopsView, RecyclerViewAdapter.ShopRowInterface {

    public ProgressDialog progressDialog;

    private List<ShopDataModel> shopDataModels;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_no_shop)
    TextView textViewNoShop;

    private RecyclerViewAdapter mAdapter;
    private String userID;

    @Inject
    ViewShopsPresenter viewShopsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_shops, container, false);
        ButterKnife.bind(this, view);
        ViewShopsDI.getViewShopsComponent().inject(this);
        viewShopsPresenter.bind(this, getContext());

        textViewNoShop.setVisibility(View.GONE);

        if (shopDataModels != null)
            shopDataModels.clear();
        SharedPrefManager.Init(getContext());
        SharedPrefManager.LoadFromPref();
        userID = SharedPrefManager.get_userDocumentID();
        Log.d("ViewShopsFragment", userID + " - " + SharedPrefManager.get_userDocumentID() + " - " + SharedPrefManager.get_userEmail());

        loadShops();
        return view;
    }

    @Override
    public void showNoShopScreen() {
        textViewNoShop.setVisibility(View.VISIBLE);
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public void loadShops() {
        viewShopsPresenter.loadShops();
    }

    @Override
    public void showViewShopProgress(Boolean bool) {
        if (bool)
            showProgressDialog("Loading Shops");
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
    public void showShopsList(List<ShopDataModel> shopDataModels) {
        this.shopDataModels = shopDataModels;
//        this.categoriesDataModels = categoriesDataModels;
        if (this.shopDataModels.size() == 0 || this.shopDataModels == null) {
            startActivity(new Intent(getContext(), AddShopActivity.class));
        } else {
            mAdapter = new RecyclerViewAdapter(getContext(), this.shopDataModels, this);
            LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(linearLayout);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.logout, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
////            case R.id.action_add_shop:
////                Toast.makeText(EditProfileActivity.this, "" + SharedPrefManager.is_skippedEditProfile(), Toast.LENGTH_SHORT).show();
////                if (currentUser.getUserType().equals("C"))
////                startActivity(new Intent(ViewShopsFragment.this, AddShop1Fragment.class));
////                else
////                    startActivity(new Intent(EditProfileActivity.this, ViewShopsFragment.class));
////                break;
//            case R.id.logout:
//                if (googleSignInClient != null)
//                    googleSignInClient.signOut();
//
//                AccessToken accessToken;
//                accessToken = AccessToken.getCurrentAccessToken();
//                if (accessToken != null)
//                    LoginManager.getInstance().logOut();
//                SharedPrefManager.DeleteAllEntriesFromPref();
//                startActivity(new Intent(ViewShopsFragment.this, LoginActivity.class));
//        }
//        return true;
//    }

    @Override
    public void shopProducts(ShopDataModel shopDataModel) {
//        Toast.makeText(ViewShopsFragment.this, "" + shopDataModel.getShopDocumentID(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getContext(), ViewShopActivity.class);
//        intent.putExtra("shopDocumentId", shopDataModel.getShopDocumentID());
//        startActivity(intent);

//        RetailerCategoryFragment retailerCategoryFragment = new RetailerCategoryFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.addToBackStack("");
//        fragmentTransaction.replace(R.id.frame, retailerCategoryFragment, "category");
//        fragmentTransaction.commitAllowingStateLoss();
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getContext(), RetailerCategoryActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

//        ((RetailerHomeActivity) getActivity()).setToolbatTitle("Category");
    }

    @Override
    public void editShop(ShopDataModel shopDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getContext(), ViewShopActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addProduct(ShopDataModel shopDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getContext(), MasterListActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fab_add_shop)
    @Override
    public void addShop() {
//        startActivity(new Intent(getContext(), AddShop1Fragment.class));

//        AddShopFragment addShopFragment = new AddShopFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.addToBackStack("");
//        fragmentTransaction.replace(R.id.frame, addShopFragment, "category");
//        fragmentTransaction.commitAllowingStateLoss();
        if (ConnectivityReceiver.isConnected()) {
            startActivity(new Intent(getContext(), AddShopActivity.class));
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
