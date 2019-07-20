package com.example.c2n.addshop.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.addshop.util.AddShopValidator;
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

public class AddShopFragment extends Fragment {

    final int REQUEST_IMAGE_CAPTURE = 1;
    private int PICK_IMAGE_REQUEST = 71;
    private String mCurrentPhotoPath;
    private Bitmap bitmap;
    private Uri filePath;
//    private boolean imageStatusFlag = false;

    @BindView(R.id.rl_shop_image)
    RelativeLayout relativeLayoutShopImage;

    @BindView(R.id.rl_shop_image_add)
    RelativeLayout relativeLayoutAddShopImage;

    @BindView(R.id.et_shop_name)
    EditText editTextShopName;

    @BindView(R.id.et_shop_email)
    EditText editTextShopEmail;

    @BindView(R.id.iv_shop_image)
    ImageView imageViewShopImage;

    @BindView(R.id.bt_next)
    AppCompatButton compatButton;

    AddShopValidator addShopValidator;

    Boolean isNextButtonActive = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_shop, container, false);
        ButterKnife.bind(this, view);
        deactivateNextButton();

        addShopValidator = new AddShopValidator();
        relativeLayoutShopImage.setVisibility(View.GONE);

        editTextShopName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    deactivateNextButton();

                } else {
                    activateNextButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextShopEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    deactivateNextButton();

                } else {
                    Log.d("onTextChangedEmaill", "" + count);
                    if (addShopValidator.isValidEmail(editTextShopEmail.getText().toString())) {
                        Log.d("onTextChangedEmaill", "isValidEmail");
                        activateNextButton();
                    } else {
                        setShopEmailError("Please enter valid shop email ");
                        deactivateNextButton();
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        selectImage();
        return view;
    }


    private void activateNextButton() {
//        isNextButtonActive = true;
        if ((!TextUtils.isEmpty(getShopEmail())) && (!TextUtils.isEmpty(getShopName()))) {
            isNextButtonActive = true;
            compatButton.setTextColor(getResources().getColor(R.color.white));
            compatButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_hollow));
            Log.d("nextButton", "activatenextbutton");
        }
    }


    private void deactivateNextButton() {
        isNextButtonActive = false;
        compatButton.setTextColor(getResources().getColor(R.color.white));
        compatButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.inactive_button_background_hollow));
        Log.d("deactivateappButton", "deactivatenext");

    }


    @OnClick(R.id.imageViewSelectImage)
    public void sselectImagehowImageAlertDialog() {
        selectImage();
    }

    @OnClick(R.id.rl_shop_image_add)
    public void addImagehowImageAlertDialog() {
        selectImage();
    }

    @OnClick(R.id.bt_next)
    public void nextFragment() {
//        if (TextUtils.isEmpty(editTextShopName.getText().toString().trim())) {
//            setShopNameError();
//        } else if (TextUtils.isEmpty(editTextShopEmail.getText().toString().trim())) {
//            setShopEmailError("Please enter shop email");
//        } else if (!addShopValidator.isValidEmail(editTextShopEmail.getText().toString().trim())) {
//            setShopEmailError("Please enter correct shop email");
//        } else {
        if (isNextButtonActive) {
            AddShop1Fragment addShop1Fragment = new AddShop1Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("shopName", getShopName());
            bundle.putString("shopEmail", getShopEmail());
            addShop1Fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.replace(R.id.frame, addShop1Fragment, "category");
            fragmentTransaction.commitAllowingStateLoss();

//        ((RetailerHomeActivity) getActivity()).setToolbatTitle("Category");
        }
    }

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
                                        cameraIntent();
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
                    galleryIntent();
                    Toast.makeText(getActivity(), "Choose from Library", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
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
                    getFragmentManager().popBackStackImmediate();
                }
                return true;
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST);
    }

    private void cameraIntent() {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
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
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
        }
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && mCurrentPhotoPath != null) {
//            filePath = CustomerSingleProductRepository.getData();
            try {
                filePath = Uri.parse(mCurrentPhotoPath);
                AddShopActivity addShopActivity = (AddShopActivity) getActivity();
                new Compressor(getActivity())
                        .compressToFileAsFlowable(FileUtil.from(getActivity(), filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
//                                compressedImage = file;
//                                setCompressedImage();
                                imageViewShopImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                addShopActivity.filePath = Uri.fromFile(file);
                                addShopActivity.shopImageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
//
//                AddShopActivity addShopActivity = (AddShopActivity) getActivity();
//                addShopActivity.shopImageBitmap = bitmap;
//                addShopActivity.filePath = Uri.parse(mCurrentPhotoPath);

                relativeLayoutShopImage.setVisibility(View.VISIBLE);
                relativeLayoutAddShopImage.setVisibility(View.GONE);

//                imageStatusFlag = true;

                imageViewShopImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                AddShopActivity addShopActivity = (AddShopActivity) getActivity();

                new Compressor(getActivity())
                        .compressToFileAsFlowable(FileUtil.from(getActivity(), filePath))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) {
//                                compressedImage = file;
//                                setCompressedImage();
                                imageViewShopImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                                addShopActivity.filePath = Uri.fromFile(file);
                                addShopActivity.shopImageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Toast.makeText(getActivity(), "Image Size : " + String.format("Size : %s", getReadableFileSize(file.length())), Toast.LENGTH_SHORT).show();
//                                storeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                throwable.printStackTrace();
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//
//                AddShopActivity addShopActivity = (AddShopActivity) getActivity();
//                addShopActivity.shopImageBitmap = bitmap;
//                addShopActivity.filePath = CustomerSingleProductRepository.getData();

                relativeLayoutShopImage.setVisibility(View.VISIBLE);
                relativeLayoutAddShopImage.setVisibility(View.GONE);

//                imageStatusFlag = true;

                imageViewShopImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d("PICK_IMAGE_REQUEST", "" + e.getMessage());
            }
        }
    }

    private void setShopEmailError(String msg) {
        editTextShopEmail.setError(msg);
        editTextShopEmail.requestFocus();
    }

    private void setShopNameError() {
        editTextShopName.setError("Please enter shop name");
        editTextShopName.requestFocus();
    }

    public String getShopName() {
        return editTextShopName.getText().toString().trim();
    }

    public String getShopEmail() {
        return editTextShopEmail.getText().toString().trim();
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
