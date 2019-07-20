package com.example.c2n.add_product.presenter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.add_product.di.AddProductDI;
import com.example.c2n.add_product.presenter.adapter.AddProductAdapter;
import com.example.c2n.add_product.presenter.view.AddProductView;
import com.example.c2n.core.base.BaseFragment;
import com.example.c2n.core.model1.CategoryDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul.singhal on 29-05-2018.
 */

public class ProductCategoryNameBottomFragment extends BottomSheetDialogFragment implements AddProductView, AddProductAdapter.CategoryRowInterface {

    @Inject
    GetProductCategoriesPresenter getProductCategoriesPresenter;

    List<CategoryDataModel> categoriesList;
    CategoryDataModel selectedCategory;

    @BindView(R.id.recycler_view_horizontal_list)
    RecyclerView recyclerView;

    @BindView(R.id.selected_product_category_layout)
    LinearLayout selectedCategoryLayout;

    @BindView(R.id.selected_category_image)
    ImageView selectedCategoryImage;

    @BindView(R.id.selected_category_name)
    TextView selectedCategoryName;

    @BindView(R.id.et_add_product_bottom_name)
    EditText etProductName;

    @BindView(R.id.btn_add_product_category_done)
    AppCompatButton buttonDone;

    @BindView(R.id.tv_label_product_category)
    TextView labelSelectCategory;

    Boolean isButtonActive;
    String productImagePath;

    @OnClick(R.id.selected_product_category_layout)
    public void selectedCategoryClicked() {
        Toast.makeText(getContext(), "selected_product is clicked", Toast.LENGTH_SHORT).show();
        selectedCategoryLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        labelSelectCategory.setText("Select Product Category");
        doneButton(false);
    }

    public ProductCategoryNameBottomFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_product_category_name, container, false);
        ButterKnife.bind(this, view);
//        productImagePath = this.getArguments().getString("productImage");
//        this.productImagePath = BaseFragment.productImagePath;
        AddProductDI.getAddProductComponent().inject(this);
        getProductCategoriesPresenter.bind(this, getContext());
        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
        if (addProductActivity.selectedCategoryDataModel != null) {
            categoryClicked(addProductActivity.selectedCategoryDataModel);
            if (addProductActivity.productName != null && !addProductActivity.productName.equals("")) {
                etProductName.setText(addProductActivity.productName);
                doneButton(true);
            }
        }

        etProductName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (start == 0 && count == 0) {
                    Log.d("success", "issue resolved- " + start);
                    doneButton(false);
                } else {
                    if (selectedCategory == null)
                        doneButton(false);
                    else
                        doneButton(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        requestCategoryList();
        return view;
    }

    @OnClick(R.id.btn_add_product_category_done)
    public void doneButtonClicked() {
        if (isButtonActive) {
            Log.d("selected_P_category", selectedCategory.getCategoryName());
            Log.d("selected_P_name", etProductName.getText().toString());
//            dismiss();
            AddProductActivity addProductActivity = (AddProductActivity) getActivity();
            if (selectedCategory != null)
                addProductActivity.selectedCategoryDataModel = selectedCategory;
            addProductActivity.productName = etProductName.getText().toString();
            AddProductPriceFragment addProductPriceFragment = new AddProductPriceFragment();
            Bundle args = new Bundle();
//            args.putString("productImage", productImagePath);
//            Log.d("bitmap_imgpath", productImagePath);
            addProductPriceFragment.setArguments(args);
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.product_fragment_container, addProductPriceFragment, "")
//                    .commit();

            BaseFragment.productName = etProductName.getText().toString();
            BaseFragment.productCategory = selectedCategoryName.getText().toString();
            dismiss();
            addProductActivity.replaceFragment(addProductPriceFragment, etProductName.getText().toString(), true);

//            AddProductPriceFragment productCategoryNameBottomFragment = new AddProductPriceFragment();
//            productCategoryNameBottomFragment.setCancelable(false);
//            productCategoryNameBottomFragment.show(getFragmentManager(), productCategoryNameBottomFragment.getTag());

        } else if (!isButtonActive)
            Log.d("selected_P", " Details Not Complete");

    }

    private void doneButton(boolean buttonActive) {
        if (buttonActive) {
            buttonDone.setBackgroundResource(R.drawable.active_button_background);
            isButtonActive = true;
//            buttonDone.setClickable(true);
        } else {
            buttonDone.setBackgroundResource(R.drawable.inactive_button_background);
            isButtonActive = false;
        }
//        buttonDone.setClickable(false);
    }

    private void requestCategoryList() {
        getProductCategoriesPresenter.getCategoryList();
    }

    @Override
    public void postCategoryList(List<CategoryDataModel> categoriesList, String error) {
//        showProgress(false);
        if (error != null) {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.categoriesList != null)
            this.categoriesList.clear();
        this.categoriesList = categoriesList;
        AddProductAdapter adapter = new AddProductAdapter(this.categoriesList, getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void categoryClicked(CategoryDataModel categoryDataModel) {
        recyclerView.setVisibility(View.GONE);
        selectedCategoryLayout.setVisibility(View.VISIBLE);
        labelSelectCategory.setText(categoryDataModel.getCategoryName());
        selectedCategoryName.setText(categoryDataModel.getCategoryName());
        Picasso.get().load(categoryDataModel.getCategoryImageURL()).fit().into(selectedCategoryImage);
        selectedCategory = categoryDataModel;
        if (!etProductName.getText().toString().trim().equals(""))
            doneButton(true);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("bottom_", "dismiss");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("bottom", "detach");
    }

    //    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.selected_category_image) {
//            Toast.makeText(getContext(), "selected_product is clicked", Toast.LENGTH_SHORT).show();
//            selectedCategoryLayout.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//    }


}
