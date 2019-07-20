package com.example.c2n.make_offer_card.presenter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.core.base.BaseActivity;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.make_offer_card.di.MakeOfferCardDI;
import com.example.c2n.offer_cards_list.presenter.OffersListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MakeOfferCardActivity extends BaseActivity implements MakeOfferCardView {

    @BindView(R.id.et_offercard_name)
    EditText offerCardName;

    @BindView(R.id.bg_make_offercard_startdate)
    TextView offerCardStartDate;

    @BindView(R.id.bg_make_offer_date_to_text)
    TextView offerCardToText;

    @BindView(R.id.bg_make__offercard_enddate)
    TextView offerCardEndDate;

    @BindView(R.id.tv_make_offercard_discount_perc)
    TextView offercard_discount;

    @BindView(R.id.tv_text_make_offercard_OFF)
    TextView offercard_text_OFF;

    @BindView(R.id.tv_text_make_offercard_PERC)
    TextView offercard_text_PERC;

    @BindView(R.id.seekbar_make_offercard_discount)
    SeekBar discountSeekbar;

    @BindView(R.id.tv_make_offercard_startdate)
    TextView offerStartDate;

    @BindView(R.id.tv_make_offercard_enddate)
    TextView offerEndDate;

//    @BindView(R.id.iv_offercard_startdate)
//    ImageView selectStartDate;

//    @BindView(R.id.iv_offercard_enddate)
//    ImageView selectEndDate;

    @BindView(R.id.btn_offercard_sunday)
    AppCompatButton buttonSunday;

    @BindView(R.id.btn_offercard_monday)
    AppCompatButton buttonMonday;

    @BindView(R.id.btn_offercard_tuesday)
    AppCompatButton buttonTuesday;

    @BindView(R.id.btn_offercard_wednesday)
    AppCompatButton buttonWednesday;

    @BindView(R.id.btn_offercard_thursday)
    AppCompatButton buttonThursday;

    @BindView(R.id.btn_offercard_friday)
    AppCompatButton buttonFriday;

    @BindView(R.id.btn_offercard_saturday)
    AppCompatButton buttonSaturday;

    @BindView(R.id.layout_offer_applicable_days)
    LinearLayout offerDaysLayout;

    @BindView(R.id.layout_make_offercard)
    LinearLayout offerCardLayout;

//    @BindView(R.id.layout_offercard_default_background)
//    LinearLayout defaultBackground;
//
//    @BindView(R.id.recycler_view_choose_make_offercard_background)
//    RecyclerView recyclerViewBackgrounds;

    Boolean includeSunday;
    Boolean includeMonday;
    Boolean includeTuesday;
    Boolean includeWednesday;
    Boolean includeThursday;
    Boolean includeFriday;
    Boolean includeSaturday;

    //    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    Date startDate;
    Date endDate;
    String selectedEndDate;
    Boolean isSaveButtonActive = false;

    int offerDuration = 0;

    @BindView(R.id.btn_save_make_offercard)
    AppCompatButton button_done;

    @Inject
    MakeOfferCardPresenter makeOfferCardPresenter;
    OfferDataModel offerDataModel;
    int discountApplied;

    AppCompatButton dayButtonArray[];
    Boolean activeOfferDays[] = new Boolean[]{false, false, false, false, false, false, false};

    Boolean appliedActiveOfferDays[];
    Boolean isNavigateToAddProduct = false;

//    private StorageReference mStorage;
//    private RecyclerView.Adapter mAdapter;
//    Boolean isBackgroundDefault = true;
//
//    private ArrayList<StorageReference> BgImagesArray = new ArrayList<StorageReference>();
//    String[] imageReferenceNames = {"offer0.jpg", "offer1.png", "offer2.jpg", "offer3.jpg", "offer4.jpg", "offer5.jpg", "offer6.jpg", "offer7.jpg", "offer8.jpg", "offer9.jpg", "offer10.jpg", "offer12.jpg", "offer13.jpg", "offer14.jpg"};
//
//    int selectedBackgroundPosition = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_make_offer_card;
    }

    @Override
    protected void initActivity() {
        ButterKnife.bind(MakeOfferCardActivity.this);
        MakeOfferCardDI.getMakeOfferCardComponent().inject(this);
        makeOfferCardPresenter.bind(this, this);
        Log.d("MakeOfferCardActivity_", "Success");

//        mStorage = FirebaseStorage.getInstance().getReference().child("offer_templates");
        dayButtonArray = new AppCompatButton[]{buttonSunday, buttonMonday, buttonTuesday, buttonWednesday, buttonThursday, buttonFriday, buttonSaturday};

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isNavigateToAddProduct = bundle.getBoolean("addProductMaster");
        }
        Intent intent = getIntent();
        if (intent != null) {
            offerDataModel = (OfferDataModel) intent.getSerializableExtra("offerDataModel");
            if (offerDataModel != null) {
                Log.d("edit_offercard", offerDataModel.toString());
                offerCardName.setText(offerDataModel.getOfferName());
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                discountApplied = offerDataModel.getOfferDiscount();
                offercard_discount.setText(discountApplied + "");
                setDiscountText(discountApplied);
                discountSeekbar.setProgress(discountApplied);
                offerCardStartDate.setText(format.format(offerDataModel.getOfferStartDate()));
                offerCardEndDate.setText(format.format(offerDataModel.getOfferEndDate()));

                offerStartDate.setText(format.format(offerDataModel.getOfferStartDate()));
                offerEndDate.setText(format.format(offerDataModel.getOfferEndDate()));

                try {
                    startDate = format.parse(offerStartDate.getText().toString());
                    endDate = format.parse(offerEndDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                offerDaysLayout.setVisibility(View.VISIBLE);
                appliedActiveOfferDays = new Boolean[]{offerDataModel.isSun(), offerDataModel.isMon(), offerDataModel.isTue(), offerDataModel.isWed(), offerDataModel.isThu(), offerDataModel.isFri(), offerDataModel.isSat()};

                manageEditDays(startDate, endDate);
                isSaveButtonActive = true;
                button_done.setText("Update");
                buttonDone(true);
            }

        }

        discountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("progress: ", progress + "");

//                if (isBackgroundDefault)
                setDiscountText(progress);

                offercard_discount.setText(progress + "");
                if (progress != 0)
                    buttonDone(true);
                else
                    buttonDone(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

//    @OnClick(R.id.layout_offercard_default_background)
//    public void expandBackgroundList() {
//        defaultBackground.setVisibility(View.GONE);
//        recyclerViewBackgrounds.setVisibility(View.VISIBLE);
//    }

    //    private void initOfferBackgroundOptions() {
//        for (int i = 0; i < imageReferenceNames.length; i++)
//            BgImagesArray.add(mStorage.child(imageReferenceNames[i]));
//
//        mAdapter = new OfferCardBackgroundAdapter(this, BgImagesArray, this);
//        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//        linearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerViewBackgrounds.setHasFixedSize(true);
//        recyclerViewBackgrounds.setLayoutManager(linearLayout);
//        recyclerViewBackgrounds.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
//    }

    private void setDiscountText(int progress) {
        if (progress >= 0 && progress <= 10)
            setOfferCardBackground(1);
        else {
            if (progress > 10 && progress <= 20)
                setOfferCardBackground(2);
            else {
                if (progress > 20 && progress <= 30)
                    setOfferCardBackground(3);
                else {
                    if (progress > 30 && progress <= 50)
                        setOfferCardBackground(4);
                    else {
                        if (progress > 50 && progress <= 75)
                            setOfferCardBackground(5);
                        else {
                            if (progress > 75 && progress <= 100)
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
                offerCardLayout.setBackgroundResource(R.drawable.home_offer1_bg);
                return;
            }
            case 2: {
                setOfferCardTextColor(1);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer2_bg);
                return;
            }
            case 3: {
                setOfferCardTextColor(1);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer3_bg);
                return;
            }
            case 4: {
                setOfferCardTextColor(1);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer4_bg);
                return;
            }
            case 5: {
                setOfferCardTextColor(2);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer5_bg);
                return;
            }
            case 6: {
                setOfferCardTextColor(2);
                offerCardLayout.setBackgroundResource(R.drawable.home_offer6_bg);
                return;
            }
        }
    }

    public void setOfferCardTextColor(int color) {
        switch (color) {
            case 1: {
                offerCardName.setTextColor(Color.BLACK);
                offercard_discount.setTextColor(Color.BLACK);
                offercard_text_OFF.setTextColor(Color.BLACK);
                offercard_text_PERC.setTextColor(Color.BLACK);
                offerCardStartDate.setTextColor(Color.BLACK);
                offerCardEndDate.setTextColor(Color.BLACK);
                offerCardToText.setTextColor(Color.BLACK);
                break;
            }
            case 2: {
                offerCardName.setTextColor(Color.WHITE);
                offercard_discount.setTextColor(Color.WHITE);
                offercard_text_PERC.setTextColor(Color.WHITE);
                offercard_text_OFF.setTextColor(Color.WHITE);
                offerCardStartDate.setTextColor(Color.WHITE);
                offerCardEndDate.setTextColor(Color.WHITE);
                offerCardToText.setTextColor(Color.WHITE);
                break;
            }
//            case 3: {
//                offerCardName.setTextColor(getResources().getColor(R.color.offerNameColor));
//                offercard_discount.setTextColor(Color.BLACK);
//                offercard_text_OFF.setTextColor(Color.BLACK);
//                offercard_text_PERC.setTextColor(Color.BLACK);
//                offerCardStartDate.setTextColor(Color.BLACK);
//                offerCardEndDate.setTextColor(Color.BLACK);
//                offerCardToText.setTextColor(Color.BLACK);
//                break;
//            }
//            case 4: {
//                offerCardName.setTextColor(Color.WHITE);
//                offercard_discount.setTextColor(Color.WHITE);
//                offercard_text_PERC.setTextColor(Color.WHITE);
//                offercard_text_OFF.setTextColor(Color.WHITE);
//                offerCardStartDate.setTextColor(Color.BLACK);
//                offerCardEndDate.setTextColor(Color.BLACK);
//                offerCardToText.setTextColor(Color.BLACK);
//                break;
//            }
//            case 5: {
//                offerCardName.setTextColor(Color.WHITE);
//                offercard_discount.setTextColor(Color.BLACK);
//                offercard_text_OFF.setTextColor(Color.BLACK);
//                offercard_text_PERC.setTextColor(Color.BLACK);
//                offerCardStartDate.setTextColor(Color.BLACK);
//                offerCardEndDate.setTextColor(Color.BLACK);
//                offerCardToText.setTextColor(Color.BLACK);
//                break;
//            }
//            case 6: {
//                offerCardName.setTextColor(Color.BLACK);
//                offercard_discount.setTextColor(Color.BLACK);
//                offercard_text_PERC.setTextColor(Color.BLACK);
//                offercard_text_OFF.setTextColor(Color.BLACK);
//                offerCardStartDate.setTextColor(Color.WHITE);
//                offerCardEndDate.setTextColor(Color.WHITE);
//                offerCardToText.setTextColor(Color.WHITE);
//                break;
//            }
//            case 7: {
//                offerCardName.setTextColor(Color.BLACK);
//                offercard_discount.setTextColor(Color.RED);
//                offercard_text_OFF.setTextColor(Color.RED);
//                offercard_text_PERC.setTextColor(Color.RED);
//                offerCardStartDate.setTextColor(Color.BLACK);
//                offerCardEndDate.setTextColor(Color.BLACK);
//                offerCardToText.setTextColor(Color.BLACK);
//                break;
//            }
//            case 8: {
//                offerCardName.setTextColor(Color.BLACK);
//                offercard_discount.setTextColor(Color.WHITE);
//                offercard_text_OFF.setTextColor(Color.WHITE);
//                offercard_text_PERC.setTextColor(Color.WHITE);
//                offerCardStartDate.setTextColor(Color.BLACK);
//                offerCardEndDate.setTextColor(Color.BLACK);
//                offerCardToText.setTextColor(Color.BLACK);
//                break;
//            }
        }
    }


    @OnClick(R.id.btn_offercard_sunday)
    public void sundayClicked() {
        offerDayButtonClicked(0);
    }

    @OnClick(R.id.btn_offercard_monday)
    public void mondayClicked() {
        offerDayButtonClicked(1);
    }

    @OnClick(R.id.btn_offercard_tuesday)
    public void tuesdayClicked() {
        offerDayButtonClicked(2);
    }

    @OnClick(R.id.btn_offercard_wednesday)
    public void wednesdayClicked() {
        offerDayButtonClicked(3);
    }

    @OnClick(R.id.btn_offercard_thursday)
    public void thurdsdayClicked() {
        offerDayButtonClicked(4);
    }

    @OnClick(R.id.btn_offercard_friday)
    public void fridayClicked() {
        offerDayButtonClicked(5);
    }

    @OnClick(R.id.btn_offercard_saturday)
    public void saturdayClicked() {
        offerDayButtonClicked(6);
    }

    private void offerDayButtonClicked(int dayPosition) {
        if (activeOfferDays[dayPosition] == false) {
            dayButtonArray[dayPosition].setBackgroundResource(R.drawable.offercard_day_button_background);
            dayButtonArray[dayPosition].setTextColor(Color.WHITE);
            activeOfferDays[dayPosition] = true;
        } else if (activeOfferDays[dayPosition] == true) {
            activeOfferDays[dayPosition] = false;

            Boolean isAnyDayButtonActive = false;
            for (int i = 0; i < 7; i++) {
                if (activeOfferDays[i] == true)
                    isAnyDayButtonActive = true;
            }
            if (isAnyDayButtonActive) {
                dayButtonArray[dayPosition].setBackgroundColor(Color.TRANSPARENT);
                dayButtonArray[dayPosition].setTextColor(Color.BLACK);
            } else if (!isAnyDayButtonActive)
                activeOfferDays[dayPosition] = true;
        }
    }

    @OnClick({R.id.iv_offercard_startdate, R.id.tv_make_offercard_startdate})
    public void selectStartDate() {
        final Calendar cal = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                MakeOfferCardActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        String date = year + "/" + String.valueOf(month + 1) + "/" + dayOfMonth;
                        String date = dayOfMonth + "/" + String.valueOf(month + 1) + "/" + year;

                        offerStartDate.setText(date);
                        offerCardStartDate.setText(date);
                        try {
                            startDate = format.parse(date);
                            Log.d("date_start_day", startDate.getDay() + "");
                        } catch (Exception e) {
                            Log.d("exception_date", e.getMessage());
                        }
                        if (endDate != null) {
                            if (endDate.before(startDate)) {
                                offerEndDate.setText(date);
                                offerCardEndDate.setText(date);
                            }
                            manageDays(startDate, endDate);
                        }
                        if (endDate == null) {
                            selectEndDate();
                        }
                        buttonDone(true);
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        if (endDate != null) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (endDate.getTime() - System.currentTimeMillis()));
        }
//         datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 86400000 * 31);
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000 * 10);

        datePickerDialog.setTitle("       Offer starts on :");
        datePickerDialog.show();
    }

    @OnClick({R.id.iv_offercard_enddate, R.id.tv_make_offercard_enddate})
    public void selectEndDate() {
//        deactivateDayButtons();
        final Calendar cal = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                MakeOfferCardActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        selectedEndDate = year + "/" + String.valueOf(month + 1) + "/" + dayOfMonth;
                        selectedEndDate = dayOfMonth + "/" + String.valueOf(month + 1) + "/" + year;
                        try {
                            endDate = format.parse(selectedEndDate);
                            Log.d("date_end_day", endDate.getDay() + "");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        offerEndDate.setText(selectedEndDate);
                        offerCardEndDate.setText(selectedEndDate);
                        if (startDate == null) {
                            try {
                                startDate = format.parse(format.format(new Date()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            offerStartDate.setText(format.format(new Date()));
                            offerCardStartDate.setText(format.format(new Date()));

                        }
                        offerDaysLayout.setVisibility(View.VISIBLE);
                        manageDays(startDate, endDate);
                        buttonDone(true);
                        Log.d("offer_days", activeOfferDays.toString());
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

//        Date date = new Date();
//
//        Date date1 = new Date();
//        try {
//            date1 = format.parse(format.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
////        long diff = date1.getTime() - startDate.getTime();
        Log.d("offer_date", " " + startDate);
        if (startDate != null) {
            datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
            Date focusDate = startDate;
            try {
                focusDate = format.parse(format.format(startDate.getTime() + 86400000));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            datePickerDialog.getDatePicker().updateDate(focusDate.getYear() + 1900, focusDate.getMonth(), focusDate.getDate());


            Log.d("date-", "" + (focusDate.getYear() + 1900) + "-" + focusDate.getMonth() + "-" + focusDate.getDate());
//            if (endDate != null)
//                manageDays(startDate, endDate);
        } else {
            Date focusDate = new Date();
            try {
                focusDate = format.parse(format.format(new Date().getTime() + 86400000));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            datePickerDialog.getDatePicker().updateDate(focusDate.getYear() + 1900, focusDate.getMonth(), focusDate.getDate());


            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        }
        datePickerDialog.setTitle("       Offer ends on :");
        datePickerDialog.show();

    }


    private void manageEditDays(Date start, Date end) {
        deactivateDayButtons();
        offerDuration = (int) (1 + ((end.getTime() - start.getTime()) / 86400000));
        Log.d("offer_time- ", offerDuration + "");

        if (offerDuration >= 7) {
            if (offerDataModel != null) {
                for (int i = 0; i < 7; i++) {
                    if (appliedActiveOfferDays[i])
                        activateDay(i);
                    else
                        dayButtonArray[i].setClickable(true);
                }
            }
        } else {
            int activeDay = start.getDay();

            if (offerDataModel != null) {
                for (int i = 0; i < 7; i++) {
                    if (appliedActiveOfferDays[i])
                        activateDay(i);
                }

                for (int i = 0; i < offerDuration; i++) {
                    dayButtonArray[activeDay].setClickable(true);
                    activeDay = (activeDay + 1) % 7;
                }

            }
        }

        if (offerDuration == 1) {
            deactivateDayButtons();
            activateDay(start.getDay());
        }
    }


    private void manageDays(Date start, Date end) {
        deactivateDayButtons();
        offerDuration = (int) (1 + ((end.getTime() - start.getTime()) / 86400000));
        Log.d("offer_time- ", offerDuration + "");

        if (offerDuration >= 7) {
            for (int i = 0; i < 7; i++) {
                activateDay(i);
            }
        } else {
            int activeDay = start.getDay();
            for (int i = 0; i < offerDuration; i++) {
                activateDay(activeDay);
                activeDay = (activeDay + 1) % 7;
            }
            Log.d("offer_time_diff", "equal 7");
        }

        if (offerDuration == 1) {
            deactivateDayButtons();
            activateDay(start.getDay());
        }
    }

    private void activateDay(int activeDay) {
        dayButtonArray[activeDay].setBackgroundResource(R.drawable.offercard_day_button_background);
        dayButtonArray[activeDay].setTextColor(Color.WHITE);
        activeOfferDays[activeDay] = true;
        if (offerDuration == 1)
            dayButtonArray[activeDay].setClickable(false);
        else
            dayButtonArray[activeDay].setClickable(true);
    }


    public void deactivateDayButtons() {
        for (int i = 0; i < 7; i++) {
            dayButtonArray[i].setBackgroundColor(Color.TRANSPARENT);
            dayButtonArray[i].setTextColor(Color.BLACK);
            activeOfferDays[i] = false;
            dayButtonArray[i].setClickable(false);
        }
    }
//    private void activeDays(int day, int totalDays) {
//        for(int i=0;i<totalDays;i)
//    }

    public void buttonDone(Boolean active) {
        if (active && !TextUtils.isEmpty(offerStartDate.getText()) && !TextUtils.isEmpty(offerEndDate.getText()) && !offercard_discount.getText().toString().equals("0")) {
            isSaveButtonActive = true;
            if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button_done.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                button_done.setTextColor(Color.WHITE);
            }
        } else {
            isSaveButtonActive = false;
            if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button_done.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                button_done.setTextColor(getResources().getColor(R.color.inactiveText));
            }
        }
    }

    @OnClick(R.id.btn_save_make_offercard)
    public void addOfferCard() {

        if (ConnectivityReceiver.isConnected()) {
            if (offerDataModel != null && offerDataModel.getOfferID() != null) {
                makeOfferCardPresenter.updateOfferCard();
                for (int i = 0; i < 7; i++) {
                    Log.d("offer_day-", activeOfferDays[i] + "");
                }
            } else {
                if (isSaveButtonActive)
                    makeOfferCardPresenter.addOfferCard();
//            for (int i = 0; i < 7; i++) {
//                Log.d("offer_day-", activeOfferDays[i] + "");
//            }
            }
        } else {
            Toast.makeText(MakeOfferCardActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getOfferCardName() {
        if (TextUtils.isEmpty(offerCardName.getText()))
            return "";
        return offerCardName.getText().toString();
    }


//    @Override
//    public int getOfferCardBackground() {
//        return selectedBackgroundPosition;
//    }

    @Override
    public int getOfferCardDiscount() {
        return Integer.parseInt(offercard_discount.getText().toString());
    }

    @Override
    public Date getOfferStartDate() {
        return startDate;
    }

    @Override
    public Date getOfferEndDate() {
        return endDate;
    }

    @Override
    public Boolean getIsSundayIncludes() {
        return activeOfferDays[0];
    }

    @Override
    public Boolean getIsMondayIncludes() {
        return activeOfferDays[1];
    }

    @Override
    public Boolean getIsTuesdayIncludes() {
        return activeOfferDays[2];
    }

    @Override
    public Boolean getIsWednesdayIncludes() {
        return activeOfferDays[3];
    }

    @Override
    public Boolean getIsThursdayIncludes() {
        return activeOfferDays[4];
    }

    @Override
    public Boolean getIsFridayIncludes() {
        return activeOfferDays[5];
    }

    @Override
    public Boolean getIsSaturdayIncludes() {
        return activeOfferDays[6];
    }

    @Override
    public Boolean getOfferStatus() {
        return offerDataModel.isOfferStatus();
    }

    @Override
    public String getOfferId() {
        if (offerDataModel != null)
            return offerDataModel.getOfferID();
        return null;
    }


    @Override
    public void showOfferCardProgress(Boolean progress, int type) {
        if (progress) {
            if (type == 0)
                showProgressDialog("Making Offer Card...");
            else
                showProgressDialog("Updating Offer Card...");
        } else
            dismissProgressDialog();
    }

    @Override
    public void isOfferCardAddingSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {
            if (isNavigateToAddProduct != null && isNavigateToAddProduct) {
                finish();
                return;
            }
            showToast("Offer Card Added Succesfully.");
            startActivity(new Intent(this, OffersListActivity.class));
            finish();
        } else
            showToast("Please Retry...");
    }


    @Override
    public void isOfferCardUpdatingSuccess(Boolean success) {
        dismissProgressDialog();
        if (success) {
            showToast("Offer Card Updated Succesfully.");
            startActivity(new Intent(this, OffersListActivity.class));
            finish();
        } else
            showToast("Please Retry...");
    }


    //    @Override
//    public void backgroundSelected(StorageReference selectedBackground, int position) {
//        Log.d("offer_bg_Selected", selectedBackground.getName() + "");
//        if (position == 0) {
//            isBackgroundDefault = true;
//            defaultBackground.setVisibility(View.VISIBLE);
//            recyclerViewBackgrounds.setVisibility(View.GONE);
//            setDiscountText(Integer.parseInt(offercard_discount.getText().toString()));
//        } else {
//            isBackgroundDefault = false;
//            Glide.with(this).using(new FirebaseImageLoader()).load(selectedBackground).asBitmap().into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    Drawable drawable = new BitmapDrawable(getResources(), resource);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                        offerCardLayout.setBackground(drawable);
//                    setCustomOfferCardTextColor(position);
//                }
//
//            });
//
//        }
//        this.selectedBackgroundPosition = position;
//    }

//    private void setCustomOfferCardTextColor(int position) {
//        switch (position) {
//            case 1: {
//                setOfferCardTextColor(2);
//                break;
//            }
//            case 2: {
//                setOfferCardTextColor(1);
//                break;
//            }
//            case 3: {
//                setOfferCardTextColor(1);
//                break;
//            }
//            case 4: {
//                setOfferCardTextColor(3);
//                break;
//            }
//            case 5: {
//                setOfferCardTextColor(2);
//                break;
//            }
//            case 6: {
//                setOfferCardTextColor(4);
//                break;
//            }
//            case 7: {
//                setOfferCardTextColor(5);
//                break;
//            }
//            case 8: {
//                setOfferCardTextColor(6);
//                break;
//            }
//            case 9: {
//                setOfferCardTextColor(1);
//                break;
//            }
//            case 10: {
//                setOfferCardTextColor(7);
//                break;
//            }
//            case 11: {
//                setOfferCardTextColor(1);
//                break;
//            }
//            case 12: {
//                setOfferCardTextColor(8);
//                break;
//            }
//            case 13: {
//                setOfferCardTextColor(8);
//                break;
//            }
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @OnClick(R.id.ll_make_offer)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
    }

}
