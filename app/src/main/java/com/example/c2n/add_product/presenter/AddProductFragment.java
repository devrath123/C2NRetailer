package com.example.c2n.add_product.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vipul.singhal on 28-05-2018.
 */

public class AddProductFragment extends BaseFragment {

    @BindView(R.id.iv_set_product_image)
    ImageView productImage;
    Boolean isInitial;
    String productPrice;
    @Nullable
    @BindView(R.id.et_product_mrp_price)
    TextView productMRP;

    @Nullable
    @BindView(R.id.et_edit_product_name)
    EditText productName;

    @Nullable
    @BindView(R.id.layout_discounted_price)
    LinearLayout discountedLayout;

    @Nullable
    @BindView(R.id.et_discounted_product_price)
    TextView discountedPrice;
//
//    @Nullable
//    @BindView(R.id.card_edit_product_offer)
//    CardView offerCard;
//
//    @Nullable
//    @BindView(R.id.tv_edit_offer)
//    TextView offerCardText;
//
//    @Nullable
//    @BindView(R.id.iv_remove_product_offer)
//    ImageView removeOfferButton;

    @Nullable
    @BindView(R.id.et_product_description)
    EditText productDescription;
    private int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private String productImageUrl = "";

    FirebaseStorage storage;
    StorageReference reference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        ButterKnife.bind(this, view);
//        imgPath = this.getArguments().getString("productImage");
//        isInitial = this.getArguments().getBoolean("initial");
//        Log.d("addproduct_" +
//                " path", productImagePath);
//        Log.d("addproduct_" + "path_imgpath", imgPath);


        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
        Bitmap bitmap = addProductActivity.productImageBitmap;
        if (bitmap != null) {
            productImage.setImageBitmap(bitmap);
        }
//        try {
//            String[] FILE = {MediaStore.Images.Media.DATA};
//
//            Uri uri = Uri.parse(imgPath);
//
//            Log.d("Image_uri", uri.toString());
//            Cursor cursor = getActivity().getContentResolver().query(uri,
//                    FILE, null, null, null);
//
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(FILE[0]);
//            String ImageDecode = cursor.getString(columnIndex);
//            cursor.close();
//            Log.d("Image_decode", ImageDecode);
//            Bitmap bitmap = BitmapFactory.decodeFile(ImageDecode);
//            Log.d("Image_bitmap", bitmap + "");
//            productImage.setImageBitmap(bitmap);
//
//        } catch (Exception e) {
//            Log.d("Image", e.getMessage());
//            Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG)
//                    .show();
//        }

//        productPrice = this.getArguments().getString("productPrice");
//        if (productPrice == null)
//            callProductCategoryNameBottomFragment();
//        if (isInitial == true)
        callProductCategoryNameBottomFragment();
//        else
//            setProductDetails();


//        addProductActivity.updatePrice();
        getActivity().onBackPressed();


        return view;
    }


    public void callProductCategoryNameBottomFragment() {
        ProductCategoryNameBottomFragment productCategoryNameBottomFragment = new ProductCategoryNameBottomFragment();
        Bundle args = new Bundle();
//        args.putString("productImage", imgPath);
        productCategoryNameBottomFragment.setArguments(args);

        productCategoryNameBottomFragment.setCancelable(false);
        if (productCategoryNameBottomFragment.isDetached()) {
            Log.d("bottom", "detached");
        }

        productCategoryNameBottomFragment.show(getFragmentManager(), productCategoryNameBottomFragment.getTag());
    }
}
