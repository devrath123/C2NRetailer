package com.example.c2n.addproduct.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.FileUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class AddProductDetailsFragment extends Fragment {

    private static final String TAG = AddProductDetailsFragment.class.getSimpleName();
    private AddProductActivity addProductActivity;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    private int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @BindView(R.id.et_product_name)
    EditText editTextProductName;

    @BindView(R.id.tv_product_category)
    TextView textViewProductCategory;

    @BindView(R.id.et_product_description)
    EditText editTextProductDescription;

    @BindView(R.id.iv_product_image)
    ImageView imageViewProductImage;

    @BindView(R.id.bt_next)
    Button buttonNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product_details, container, false);
        ButterKnife.bind(this, view);

        addProductActivity = (AddProductActivity) getActivity();
        Log.d(TAG, "onCreateView: " + addProductActivity.productImageBitmap);
        textViewProductCategory.setText(addProductActivity.productCategory);
        if (addProductActivity.productName != null) {
            editTextProductName.setText(addProductActivity.productName);
            buttonNext.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            buttonNext.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        }
        if (addProductActivity.productImageBitmap == null) {
            selectImage();
        } else {
            imageViewProductImage.setImageBitmap(addProductActivity.productImageBitmap);
        }

        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
        addProductActivity.setActionBarTitle("Fill Details");

        editTextProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addProductActivity.productName = s.toString();
                editTextProductName.setError(null);
                if (count > 0) {
                    buttonNext.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    buttonNext.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextProductDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addProductActivity.productDescription = s.toString();
                editTextProductDescription.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextProductName.getText().toString().trim().length() == 0) {
                    editTextProductName.setError("Please enter product name");
                } else if (editTextProductDescription.getText().toString().trim().length() == 0) {
                    focusOnView();
                    editTextProductDescription.setError("Please enter product description");
                } else if (addProductActivity.productImageBitmap == null) {
                    Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
                } else {
                    AddProductPriceFragment addProductPriceFragment = new AddProductPriceFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.product_fragment_container, addProductPriceFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }

    private final void focusOnView() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, editTextProductDescription.getBottom());
            }
        });
    }

    @OnClick(R.id.imageViewSelectImage)
    public void changeImage() {
        selectImage();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        AddProductActivity addProductActivity = (AddProductActivity) getActivity();
//        Log.d(TAG, "In onResume " + addProductActivity.productImageBitmap);
////        if (!imageFlag) {
//        selectImage();
////        } else {
////            imageViewProductImage.setImageBitmap(addProductActivity.productImageBitmap);
////        }
//    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    if (result)
                    Dexter.withActivity(getActivity())
                            .withPermissions(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        // do you work now
                                        clickPhoto();
                                    }

                                    // check for permanent denial of any permission
                                    if (report.isAnyPermissionPermanentlyDenied()) {
                                        // permission is denied permenantly, navigate user to app settings
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            })
                            .onSameThread()
                            .check();
                    Toast.makeText(getActivity(), "Take Photo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask = "Choose from Library";
//                    if (result)
                    pickPhoto();
                    Toast.makeText(getActivity(), "Choose from Library", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
//                    getActivity().finish();
//                    if (imageStatusFlag == false)
//                        relativeLayoutShopImage.setVisibility(View.GONE);
                }
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    getActivity().finish();
                    getFragmentManager().popBackStackImmediate();
                }
                return true;
            }
        });
        builder.show();
    }

    public void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST);
    }

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

    @SuppressLint({"RxLeakedSubscription", "CheckResult"})
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            filePath = CustomerSingleProductRepository.getData();
            try {
                filePath = Uri.parse(mCurrentPhotoPath);
                new Compressor(getContext())
                        .compressToFileAsFlowable(FileUtil.from(getActivity(), filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
                                addProductActivity.filePath = Uri.fromFile(file);
                                addProductActivity.productImageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
                                imageViewProductImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                new Compressor(getContext())
                        .compressToFileAsFlowable(FileUtil.from(getActivity(), filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
                                addProductActivity.filePath = Uri.fromFile(file);
                                addProductActivity.productImageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
                                imageViewProductImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (Exception e) {
                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
            }
        }
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
