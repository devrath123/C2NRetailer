package com.example.c2n.add_product.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.model1.CategoryDataModel;
import com.example.c2n.core.model1.ProductDataModel;
import com.example.c2n.core.model1.ShopDataModel;
import com.example.c2n.initial_phase.MainActivity;

import butterknife.ButterKnife;

public class AddProductActivity extends BaseActivity {

    //    @BindView(R.id.product_toolbar)
//    Toolbar productToolbar;
//    @BindView(R.id.product_toolbar_icon_back)
//    ImageView backIcon;
//    @BindView(R.id.product_toolbar_textview)
//    TextView toolbarText;
    int fragmentStackCount = 0;
    public ShopDataModel shopDataModel;
    public ProductDataModel productDataModel;
    public Bitmap productImageBitmap;
    public Uri filePath;
    public String productImagePath = "";
    CategoryDataModel selectedCategoryDataModel;
    String productName;
    Boolean status = false;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_product;
    }

    @Override
    protected void initActivity() {

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
        }
//        if (intent != null) {
//            try {
//                productDataModel = (ProductDataModel) intent.getSerializableExtra("productDataModel");
//
//                AddProductFragment addProductFragment = new AddProductFragment();
//                Bundle args = new Bundle();
//                args.putString("productName", productDataModel.getProductName());
//                args.putString("productImageUrl", productDataModel.getProductPhotoUrl());
//                args.putString("productMRP", productDataModel.getProductMRP());
//                args.putString("productScheme", productDataModel.getProductScheme());
//                args.putString("productDescription", productDataModel.getProductDescription());
//                addProductFragment.setArguments(args);
//                replaceFragment(addProductFragment, "", false);
//            } catch (Exception e) {
//                Log.d("product_view_exception", e.getMessage());
//            }
//        } else
        replaceFragment(new ProductPhotoFragment(), "", true);
    }

    public void replaceFragment(Fragment fragment, String tag, Boolean isTransparent) {

        if (isTransparent) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setToolbarTitle(tag);
        } else
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.themecolor));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setToolbarTitle(tag);
//        closeNavigationDrawer();

        fragmentStackCount++;
        fragmentCount++;

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.product_fragment_container, fragment, "productprice")
                .commit();

//        if (!isInitial) {
//            toolbarIcon.setVisibility(View.INVISIBLE);
//            toolbarIconBack.setVisibility(View.VISIBLE);
//        } else {
//            toolbarIconBack.setVisibility(View.INVISIBLE);
//            toolbarIcon.setVisibility(View.VISIBLE);
//        }
//
    }


//    public void updatePrice() {
//        AddProductPriceFragment addProductPriceFragment = (AddProductPriceFragment) getSupportFragmentManager().findFragmentByTag("productprice");
//        addProductPriceFragment.setPriceDetails();
//    }


    private void setToolbarTransparent() {
//        productToolbar.getBackground().setAlpha(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setToolbarTitle(String title) {
//        toolbarText.setText(title);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("options", "in_options");
        if (item.getItemId() == android.R.id.home) {
            Log.d("options_in_method", "in_options");
            if (!status)
                popFragment();
            else {
                startActivity(new Intent(this, MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void popFragment() {
        fragmentStackCount--;
        if (fragmentStackCount <= 0) {
            finish();
            return;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {

        Log.d("on_back", "addproduct_back_pressed");


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddProductActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Discard adding Product...");

        // Setting Dialog Message
        alertDialog.setMessage("Want to add product Later?");

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                finish();
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        // Showing Alert Message
//        alertDialog.show();

    }
}
