package com.example.c2n.add_product.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.add_product.presenter.adapter.OfferCardsAdapter;
import com.example.c2n.core.base.BaseFragment;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.make_offer_card.presenter.MakeOfferCardActivity;
import com.example.c2n.retailerhome.di.RetailerHomeDI;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vipul.singhal on 30-05-2018.
 */

public class AddProductPriceFragment extends BaseFragment implements AddProductPriceView, OfferCardsAdapter.OfferCardAdapterInterface {

    @BindView(R.id.et_product_mrp)
    EditText productMRP;
    @BindView(R.id.add_scheme_layout)
    LinearLayout addSchemeLayout;
    @BindView(R.id.discount_layout)
    LinearLayout applyDiscountLayout;
    @BindView(R.id.tv_percent_discount)
    TextView discountPercentage;
    @BindView(R.id.et_discounted_price)
    EditText discountedPrice;
    @BindView(R.id.buy_get_offer_layout)
    LinearLayout applyBuyGetSchemeLayout;
    @BindView(R.id.et_buy_quantity_offer)
    EditText buyOfferQuantity;
    @BindView(R.id.et_get_quantity_offer)
    EditText getOfferQuantity;
    @BindView(R.id.discount_seekbar)
    SeekBar discountSeekbar;
    @BindView(R.id.btn_set_product_price_discount_done)
    AppCompatButton buttonDone;
    Boolean isButtonActive = false;
    /*@BindView(R.id.product_bg_image)
    ImageView bgProductImage;*/
    @BindView(R.id.tv_any_scheme_text)
    TextView anySchemeText;

    @BindView(R.id.tv_applied_offercard_discount)
    TextView textViewAppliedOffer;

    @BindView(R.id.layout_applied_offercard)
    LinearLayout layoutAppliedOfferCard;

    @BindView(R.id.layout_view_applied_offercard)
    LinearLayout layoutViewAppliedOffercard;

    @BindView(R.id.tv_product_discounted_price)
    TextView discountedPriceText;

    @BindView(R.id.product_applied_offercard)
    CardView appliedCard;

    @BindView(R.id.offer_picker)
    DiscreteScrollView discreteScrollViewOfferPicker;

    Boolean isNewCardMade = false;
    Boolean isCardActivated = false;
    Boolean navigateToCreateOffer = true;
    @Inject
    AddProductPricePresenter addProductPricePresenter;

    private InfiniteScrollAdapter infiniteAdapter;
    int offerApplied;
    OfferDataModel offerDataModel;
    InputMethodManager imm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_add_product_price, container, false);
        ButterKnife.bind(this, view);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        RetailerHomeDI.getRetailerHomeComponent().inject(this);
        addProductPricePresenter.bind(this, getContext());
        Bundle args = this.getArguments();
//        String imgPath = this.getArguments().getString("productImage");
//        Bitmap bitmap = BitmapFactory.decodeFile(productImagePath);

        try {
//            String[] FILE = {MediaStore.Images.Media.DATA};
//
//            Uri uri = Uri.parse(productImagePath);
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
//            bgProductImage.setImageBitmap(bitmap);
//            bgProductImage.setColorFilter(R.color.bgImageTransparentt);
            AddProductActivity addProductActivity = (AddProductActivity) getActivity();
            Bitmap bitmap = addProductActivity.productImageBitmap;
            if (bitmap != null) {
              /*  bgProductImage.setImageBitmap(bitmap);
                bgProductImage.setColorFilter(R.color.bgImageTransparentt);*/
            }


        } catch (Exception e) {
            Log.d("Image", e.getMessage());
            Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

        offerApplied = args.getInt("offer_background_gradient");
        if (offerApplied == 1) {
            addSchemeLayout.setVisibility(View.GONE);
            applyDiscountLayout.setVisibility(View.VISIBLE);
        } else if (offerApplied == 2) {
            addSchemeLayout.setVisibility(View.GONE);
            applyBuyGetSchemeLayout.setVisibility(View.VISIBLE);
        }

        productMRP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (start == 0 && count == 0) {
                    addSchemeLayout.setVisibility(View.GONE);
                    buttonDone.setText("Set");
                    if (offerApplied == 1) {
                        discountPercentage.setText("0");
                        discountedPrice.setText("0");
                    }
                    doneButton(false);
                    addSchemeLayout.setVisibility(View.GONE);
                } else {
                    if (offerApplied == 1) {
                        if (TextUtils.isEmpty(discountPercentage.getText()) || TextUtils.isEmpty(discountedPrice.getText().toString()))
                            doneButton(false);
                        else if (!TextUtils.isEmpty(discountedPrice.getText().toString()))
                            setDiscountPercent(s.toString(), discountedPrice.getText().toString());
                        doneButton(true);
                        addSchemeLayout.setVisibility(View.GONE);
                        return;
                    } else if (offerApplied == 2) {
                        addSchemeLayout.setVisibility(View.GONE);
                        doneButton(true);
                        return;
                    }
                    if (layoutViewAppliedOffercard.getVisibility() != View.VISIBLE) {
                        addSchemeLayout.setVisibility(View.VISIBLE);
                        discreteScrollViewOfferPicker.setVisibility(View.GONE);
                        buttonDone.setText("No Offer");

                    } else {
                        discountedPriceText.setText("" + (Integer.parseInt(s.toString()) - Integer.parseInt((Integer.parseInt(s.toString()) * offerDataModel.getOfferDiscount()) / 100 + "")));
                    }
                    doneButton(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        if (productMRPText != null) {
//            productMRP.setText(productMRPText);
//            discountedPrice.setText(productMRPText);
//            discountPercentage.setText("0");
//        }

        discountedPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (start == 0 && count == 0) {
                    doneButton(false);
                } else if (!TextUtils.isEmpty(productMRP.getText().toString()))
                    doneButton(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (!TextUtils.isEmpty(productMRP.getText().toString())) {

//                        setDiscountPercent(productMRP.getText().toString(), s.toString());
                        if (Integer.parseInt(s.toString()) > Integer.parseInt(productMRP.getText().toString())) {
                            discountedPrice.setText(productMRP.getText().toString());
                        }
                        Log.d("discount_perc", "" + ((Integer.parseInt(discountedPrice.getText().toString()) * 100) / Integer.parseInt(productMRP.getText().toString())));
                        discountPercentage.setText("" + (100 - ((Integer.parseInt(discountedPrice.getText().toString()) * 100) / Integer.parseInt(productMRP.getText().toString()))));
                        discountSeekbar.setProgress(100 - ((Integer.parseInt(discountedPrice.getText().toString())) * 100) / Integer.parseInt(productMRP.getText().toString()));
                    }
                } else {
                    discountPercentage.setText("0");
                    discountedPrice.setText(productMRP.getText().toString());
                }
            }
        });


        discountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("discount", "" + (Integer.parseInt(productMRP.getText().toString()) - (Integer.parseInt(productMRP.getText().toString()) * seekBar.getProgress() / 100)));
                discountedPrice.setText("" + (Integer.parseInt(productMRP.getText().toString()) - Integer.parseInt((Integer.parseInt(productMRP.getText().toString()) * seekBar.getProgress()) / 100 + "")));
                discountPercentage.setText("" + seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!TextUtils.isEmpty(productMRP.getText().toString()))
                    doneButton(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!discountSeekbar.isPressed())
                            discountSeekbar.setVisibility(View.GONE);

                    }
                }, 2000);
            }
        });

        SpannableString content2 = new SpannableString("Apply Offer?");
        content2.setSpan(new UnderlineSpan(), 0, 11, 0);
        anySchemeText.setText(content2);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
//        isNewCardMade = true;
//        Log.d("onPause", isNewCardMade + "");

    }

    @Override
    public void onResume() {
        super.onResume();
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        if (isNewCardMade || isCardActivated) {
            getOffersCards();
            navigateToCreateOffer = false;
            Log.d("onResume", isNewCardMade + "-" + isCardActivated + "");
        }
    }

    @OnClick(R.id.add_scheme_layout)
    public void getOffersCards() {
        navigateToCreateOffer = true;
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        addSchemeLayout.setVisibility(View.GONE);
//        discreteScrollViewOfferPicker.setVisibility(View.VISIBLE);
        addProductPricePresenter.loadOfferCards();
    }

    @Override
    public void showOfferCards(List<OfferDataModel> offerDataModels) {
        if (offerDataModels == null || offerDataModels.size() == 0) {
            if (navigateToCreateOffer) {
                Intent intent = new Intent(getActivity(), MakeOfferCardActivity.class);
                intent.putExtra("addProductMaster", true);
                isNewCardMade = true;
                startActivity(intent);
                return;
            } else
                addSchemeLayout.setVisibility(View.VISIBLE);
        }

//        Boolean isAnyOfferCardActive = false;
//        for (OfferDataModel offerCard : offerDataModels) {
//            if (offerCard.isOfferStatus()) {
//                isAnyOfferCardActive = true;
//            }
//        }
//        if (!isAnyOfferCardActive) {
//
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//
//            alertDialog.setTitle("It seems you have not activated any Offer Card.");
//            alertDialog.setMessage("Do you want to activate Offer Card now?");
//
//            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//
//                    Intent intent = new Intent(getActivity(), OffersListActivity.class);
//                    intent.putExtra("activateCard", true);
//                    isCardActivated = true;
//                    startActivity(intent);
//                    return;
//                }
//            });
//
//            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    addSchemeLayout.setVisibility(View.VISIBLE);
//                    dialog.cancel();
//                }
//            });
//
//            alertDialog.show();
//
//       }
        else {

            Log.d("AddProductPriceFragment", "" + offerDataModels.size());
            discreteScrollViewOfferPicker.setVisibility(View.VISIBLE);
            discreteScrollViewOfferPicker.setOrientation(DSVOrientation.HORIZONTAL);
            infiniteAdapter = InfiniteScrollAdapter.wrap(new OfferCardsAdapter(offerDataModels, getContext(), this));
            discreteScrollViewOfferPicker.setAdapter(infiniteAdapter);
            discreteScrollViewOfferPicker.setItemTransitionTimeMillis(250);
            discreteScrollViewOfferPicker.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
            infiniteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoadingOffersProgress(Boolean progress) {
        if (progress)
            showProgressDialog("Loading Offer Cards...");
        else
            dismissProgressDialog();
    }

    private void setDiscountPercent(String mrpPrice, String finalPrice) {
//        if (Integer.parseInt(finalPrice) > Integer.parseInt(mrpPrice)) {
//            finalPrice = mrpPrice;
//            discountedPrice.setText(mrpPrice);
//        }
//        Log.d("discount_perc", "" + ((Integer.parseInt(finalPrice) * 100) / Integer.parseInt(mrpPrice)));
//        discountPercentage.setText("" + (100 - ((Integer.parseInt(finalPrice)) * 100) / Integer.parseInt(mrpPrice)));
//        discountSeekbar.setProgress(100 - ((Integer.parseInt(finalPrice)) * 100) / Integer.parseInt(mrpPrice));
    }

    private void doneButton(boolean buttonActive) {
        if (buttonActive) {
            buttonDone.setBackgroundResource(R.drawable.active_button_background);
            isButtonActive = true;
        } else {
            buttonDone.setBackgroundResource(R.drawable.inactive_button_background);
            isButtonActive = false;
        }
    }

    //    @OnClick(R.id.add_scheme_layout)
    public void addScheme() {
        productMRPText = Double.parseDouble(productMRP.getText().toString());
        SelectProductOfferBottomFragment selectProductOfferBottomFragment = new SelectProductOfferBottomFragment();
        selectProductOfferBottomFragment.show(getFragmentManager(), selectProductOfferBottomFragment.getTag());
    }

    @OnClick(R.id.tv_percent_discount)
    public void showDiscountSeekBar() {

        discountSeekbar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!discountSeekbar.isPressed())
                    discountSeekbar.setVisibility(View.GONE);

            }
        }, 2000);
    }

    @OnClick(R.id.btn_set_product_price_discount_done)
    public void setPriceDetails() {

        Log.d("Price", "in_method");
        if (isButtonActive) {
            Log.d("Price", "button_active_next");
            if (offerApplied == 1) {
                Log.d("Price", "in_offer");
                if (!TextUtils.isEmpty(productMRP.getText()) && !TextUtils.isEmpty(discountPercentage.getText()) && !TextUtils.isEmpty(discountedPrice.getText())) {
                    Toast.makeText(getContext(), "" + productMRP.getText() + discountPercentage.getText() + discountedPrice.getText(), Toast.LENGTH_SHORT).show();
                    Log.d("Price_details", "" + productMRP.getText() + discountPercentage.getText() + discountedPrice.getText());
                    productPrice = productMRP.getText().toString();
                    productDiscountPercent = discountPercentage.getText().toString();
                    productFinalPrice = discountedPrice.getText().toString();
                    // ((AddProductActivity) getActivity()).replaceFragment(addProductPriceFragment, "");
                }
            } else if (offerApplied == 2) {
                if (!TextUtils.isEmpty(productMRP.getText()) && !TextUtils.isEmpty(buyOfferQuantity.getText()) && !TextUtils.isEmpty(getOfferQuantity.getText())) {
                    Toast.makeText(getContext(), "" + productMRP.getText() + "--> " + buyOfferQuantity.getText() + "-" + getOfferQuantity.getText(), Toast.LENGTH_SHORT).show();
                    Log.d("Price_details", "" + productMRP.getText() + "--> " + buyOfferQuantity.getText() + "-" + getOfferQuantity.getText());
                    productPrice = productMRP.getText().toString();
                    productBuyOfferQuantity = buyOfferQuantity.getText().toString();
                    productGetOfferQuantity = getOfferQuantity.getText().toString();
                    // ((AddProductActivity) getActivity()).replaceFragment(addProductPriceFragment, "");
                }
            } else {
                if (offerDataModel != null)
                    productOffer = offerDataModel;

//                    productOfferId = "";
                productMRPText = Double.parseDouble(productMRP.getText().toString());
                AddProductResultFragment addProductResultFragment = new AddProductResultFragment();
                ((AddProductActivity) getActivity()).replaceFragment(addProductResultFragment, productName, false);
            }

        }

       /* AddProductFragment fragment = new AddProductFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.frame, fragment, "productprice");
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commitAllowingStateLoss();*/
    }

    @Override
    public void offerClicked(OfferDataModel offerDataModel) {
        this.offerDataModel = offerDataModel;
        changeButtonText(true);
        discreteScrollViewOfferPicker.setVisibility(View.GONE);
        layoutViewAppliedOffercard.setVisibility(View.VISIBLE);
        productMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        discountedPriceText.setText("" + (Integer.parseInt(productMRP.getText().toString()) - Integer.parseInt((Integer.parseInt(productMRP.getText().toString()) * offerDataModel.getOfferDiscount()) / 100 + "")));
        textViewAppliedOffer.setText(offerDataModel.getOfferDiscount() + "% OFF");
        setOfferApplied(offerDataModel.getOfferDiscount());
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        Log.d("offer_id", offerDataModel.getOfferID());
    }

    public void setOfferApplied(int discount) {
        if (discount > 0 && discount <= 10)
            setOfferCardBackground(1);
        else {
            if (discount > 10 && discount <= 20)
                setOfferCardBackground(2);
            else {
                if (discount > 20 && discount <= 30)
                    setOfferCardBackground(3);
                else {
                    if (discount > 30 && discount <= 50)
                        setOfferCardBackground(4);
                    else {
                        if (discount > 50 && discount <= 75)
                            setOfferCardBackground(5);
                        else {
                            if (discount > 75 && discount <= 100)
                                setOfferCardBackground(6);
                        }
                    }
                }
            }
        }

    }

    private void setOfferCardBackground(int offerRange) {
        switch (offerRange) {
            case 1: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer1_background_gradient);
                return;
            }
            case 2: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer2_background_gradient);
                return;
            }
            case 3: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer3_background_gradient);
                return;
            }
            case 4: {
                setOfferCardTextColor(1);
                appliedCard.setBackgroundResource(R.drawable.offer4_background_gradient);
                return;
            }
            case 5: {
                setOfferCardTextColor(2);
                appliedCard.setBackgroundResource(R.drawable.offer5_background_gradient);
                return;
            }
            case 6: {
                setOfferCardTextColor(2);
                appliedCard.setBackgroundResource(R.drawable.offer6_background_gradient);
                return;
            }
        }

    }

    private void setOfferCardTextColor(int color) {
        if (color == 1) {
            textViewAppliedOffer.setTextColor(Color.BLACK);
        } else if (color == 2) {
            textViewAppliedOffer.setTextColor(Color.WHITE);
        }
    }

    private void changeButtonText(Boolean isOffer) {
        if (isOffer)
            buttonDone.setText("Done");
        else
            buttonDone.setText("No Offer");
    }

    @OnClick(R.id.iv_remove_offercard)
    public void removeAppliedOfferCard() {
        layoutViewAppliedOffercard.setVisibility(View.GONE);
        offerDataModel = null;
        changeButtonText(false);
        productMRP.setPaintFlags(productMRP.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        addSchemeLayout.setVisibility(View.VISIBLE);
        discreteScrollViewOfferPicker.setVisibility(View.GONE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
