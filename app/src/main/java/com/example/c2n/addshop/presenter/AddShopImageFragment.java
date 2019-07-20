package com.example.c2n.addshop.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c2n.R;
import com.example.c2n.add_product.presenter.AddProductActivity;
import com.example.c2n.add_product.presenter.AddProductFragment;
import com.example.c2n.core.base.BaseFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vipul.singhal on 28-05-2018.
 */

public class AddShopImageFragment extends BaseFragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    private int PICK_IMAGE_REQUEST = 71;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_photo, container, false);
        ButterKnife.bind(this, view);

        ((AddShopActivity) getActivity()).setToolbarTitle("Pick Image");

        return view;
    }

    @OnClick(R.id.choose_product_photo)
    public void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST);
    }

    @OnClick(R.id.take_product_photo)
    public void clickPhoto() {
//        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
//                Log.i(TAG, "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            filePath = CustomerSingleProductRepository.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
                AddShopActivity addShopActivity = (AddShopActivity) getActivity();
                addShopActivity.shopImageBitmap = bitmap;
                addShopActivity.filePath = Uri.parse(mCurrentPhotoPath);

//                AddShopFragment addShopFragment = new AddShopFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                        android.R.anim.fade_out);
//                fragmentTransaction.addToBackStack("");
//                fragmentTransaction.replace(R.id.frame, addShopFragment, "category");
//                fragmentTransaction.commitAllowingStateLoss();

//                AddProductFragment addProductFragment = new AddProductFragment();
//                Bundle args = new Bundle();
//                args.putString("productImage",filePath.getPath());
//                args.putString("productImage", CustomerSingleProductRepository.getData().toString());
//                args.putBoolean("initial", true);
//                productImagePath = filePath.toString();
//                Log.d("path_filepath", filePath.getPath());
//                Log.d("path", productImagePath);
//                Log.d("path_bitmap", bitmap.toString());
//                addProductFragment.setArguments(args);
//                ((AddShopActivity) getActivity()).replaceFragment(addProductFragment, "", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                AddShopActivity addShopActivity = (AddShopActivity) getActivity();
                addShopActivity.shopImageBitmap = bitmap;
                addShopActivity.filePath = this.filePath;

//                AddShopFragment addShopFragment = new AddShopFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                        android.R.anim.fade_out);
//                fragmentTransaction.addToBackStack("");
//                fragmentTransaction.replace(R.id.frame, addShopFragment, "category");
//                fragmentTransaction.commitAllowingStateLoss();

//                AddProductFragment addProductFragment = new AddProductFragment();
//                Bundle args = new Bundle();
//                args.putString("productImage",filePath.getPath());
//                args.putString("productImage", CustomerSingleProductRepository.getData().toString());
//                args.putBoolean("initial", true);
//                productImagePath = filePath.toString();
//                Log.d("path_filepath", filePath.getPath());
//                Log.d("path", productImagePath);
//                Log.d("path_bitmap", bitmap.toString());
//                addProductFragment.setArguments(args);
//                ((AddShopActivity) getActivity()).replaceFragment(addProductFragment, "", true);
                //  imageViewProfilePic.setImageBitmap(bitmap);
//                uploadImage();
            } catch (Exception e) {
                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
            }
        }
    }

}
