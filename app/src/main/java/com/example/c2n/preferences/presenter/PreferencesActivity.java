package com.example.c2n.preferences.presenter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.model.CategoryDataModel;
import com.example.c2n.customer_home.presenter.CustomerHomeActivity;
import com.example.c2n.preferences.di.PreferencesDI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class PreferencesActivity extends BaseActivity implements PreferencesView, RecyclerViewAdapter.MyInterface {

    private List<CategoryDataModel> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<CategoryDataModel> selectedModelList;

    @Inject
    PreferencesPresenter preferencesPresenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_preferences;
    }

    @Override
    protected void initActivity() {

        ButterKnife.bind(this);
        PreferencesDI.getPreferencesComponent().inject(this);
        preferencesPresenter.bind(this, this);

        selectedModelList = new ArrayList<>();

        loadPreferences();


    }


    @Override
    public void addCategory(CategoryDataModel categoryDataModel) {
        if (selectedModelList.contains(categoryDataModel)) {
            selectedModelList.remove(categoryDataModel);
            Toast.makeText(PreferencesActivity.this, "Removed", Toast.LENGTH_SHORT).show();
        } else {
            selectedModelList.add(categoryDataModel);
            Toast.makeText(PreferencesActivity.this, "Added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
//                SharedPrefManager.set_skippedEditProfile(true);
//                SharedPrefManager.StoreToPref();
                Toast.makeText(PreferencesActivity.this, "" + selectedModelList, Toast.LENGTH_LONG).show();
                startActivity(new Intent(PreferencesActivity.this, CustomerHomeActivity.class));
                finish();
        }
        return true;
    }

    @Override
    public void loadPreferences() {
        preferencesPresenter.loadPreferences();
    }

    @Override
    public void showPreferenceProgress(Boolean bool) {
        if (bool) {
            showProgressDialog("Please wait...");
        } else
            dismissProgressDialog();
    }

    @Override
    public void isshowPreferenceSuccess(Boolean success) {
        if (progressDialog.isShowing())
            dismissProgressDialog();
        if (success) {
            Toast.makeText(this, "Data loaded", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(EditProfileActivity.this, PreferenceActivity.class));
        } else
            Toast.makeText(this, "An error occured in Loading Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPreferencesList(List<CategoryDataModel> categoryDataModels) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(this, this, categoryDataModels);
        GridLayoutManager manager = new GridLayoutManager(PreferencesActivity.this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
