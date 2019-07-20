package com.example.c2n.retailerhome.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c2n.R;
import com.example.c2n.addshop.presenter.AddShopActivity;
import com.example.c2n.core.FCMHandler;
import com.example.c2n.core.FirebaseConfig;
import com.example.c2n.core.checkconnection.ConnectivityReceiver;
import com.example.c2n.core.models.OfferDataModel;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.master_list.presenter.MasterListActivity;
import com.example.c2n.retailer_offer_products.presenter.presenter.presenter.OfferProductsActivity;
import com.example.c2n.retailercategory.presenter.RetailerCategoryActivity;
import com.example.c2n.retailerhome.di.RetailerHomeDI;
import com.example.c2n.retailerhome.presenter.adapter.ImageSliderAdapter;
import com.example.c2n.retailerhome.presenter.adapter.RetailerHomeRecentShopsAdapter;
import com.example.c2n.retailerhome.presenter.adapter.RetailerOfferCardsAdapter;
import com.example.c2n.viewshops.di.ViewShopsDI;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by vipul.singhal on 19-06-2018.
 */

public class RetailerHomeFragment extends Fragment implements RetailerHomeFragmentView, RetailerHomeRecentShopsAdapter.ShopRowInterface, RetailerOfferCardsAdapter.OfferCardInterface {

    private static final String TAG = RetailerHomeFragment.class.getSimpleName();
//    private SharedPreferences sharedPref;

    private ProgressDialog progressDialog;

    private static int currentPage = 0;
    private static int currentOfferPage = 0;
    private static int currentTestimonialPage = 0;

    //    private static final Integer[] XMEN = {R.drawable.membership_card, R.drawable.shop_premium_image};
    private ArrayList<StorageReference> XMENArray = new ArrayList<StorageReference>();
    private String token;

    @BindView(R.id.offercards_view_pager)
    ViewPager offerCardsSlider;

    @BindView(R.id.view_pager_indicator)
    CircleIndicator imageSliderIndicator;

    @BindView(R.id.btn_link_shop_blink)
    AppCompatButton buttonLinkShop;

    @BindView(R.id.tv_new_retailer)
    TextView textViewNewRetailer;

    @BindView(R.id.tv_assist_new_retailer)
    TextView textViewAssistNewRetailer;

    private StorageReference mStorage;
    String[] imageReferenceNames = {"membership_card.png", "shop_image.png"};

    @BindView(R.id.recycler_view_recent_shops)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    List<OfferDataModel> offerCards = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate;
    List<OfferDataModel> expireOfferCards = new ArrayList<>();

    private RetailerOfferCardsAdapter retailerOfferCardsAdapter;
    private ImageSliderAdapter imageSliderAdapter;

//    @BindView(R.id.slider_view_pager)
//    ViewPager sliderViewPager;

    Handler handler2;

    //  List<RetailerDataModel> retailerDataModels = new ArrayList<>();

    @Inject
    RetailerHomeFragmentPresenter retailerHomeFragmentPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retailer_home, container, false);
        ButterKnife.bind(this, view);
        ViewShopsDI.getViewShopsComponent().inject(this);
        RetailerHomeDI.getRetailerHomeComponent().inject(this);
        retailerHomeFragmentPresenter.bind(this, getContext());
        mStorage = FirebaseStorage.getInstance().getReference().child("retailer_home_images");
        Animation mAlphaAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_alpha);
        buttonLinkShop.startAnimation(mAlphaAnimation);

//        sharedPref = getActivity().getSharedPreferences("token", MODE_PRIVATE);
//        Log.d(TAG, "onCreateView: " + sharedPref.getString("user_token", "empty"));
//        token = sharedPref.getString("user_token", "empty");
//        if (!token.equals("empty")) {
//            addToken();
//        }

        FCMHandler fcmHandler = new FCMHandler();
        fcmHandler.enableFCM();

//        if (retailerOfferCardsAdapter != null) {
//            retailerOfferCardsAdapter.clear();
//        }
//        if (imageSliderAdapter != null) {
//            imageSliderAdapter.clear();
//        }
//        initOfferCardsSlider();

//        initOfferCardsSlider();
//
//        swipeRefreshLayoutPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (retailerOfferCardsAdapter != null) {
//                    retailerOfferCardsAdapter.clear();
//                }
//                initOfferCardsSlider();
//                swipeRefreshLayoutPullToRefresh.setRefreshing(false);
//            }
//        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        MyApplication.getInstance().setConnectivityListener(this);
        showLoadingOffersProgress(true, "Loading...");
        if (retailerOfferCardsAdapter != null) {
            retailerOfferCardsAdapter.clear();
        }
        if (imageSliderAdapter != null) {
            imageSliderAdapter.clear();
        }
        initOfferCardsSlider();
    }

    private void initOfferCardsSlider() {
        retailerHomeFragmentPresenter.loadOfferCards();
    }

    @Override
    public void loadRecentShops() {
        retailerHomeFragmentPresenter.loadShops();
    }

    @Override
    public void initImageSlider() {
        for (int i = 0; i < imageReferenceNames.length; i++)
            XMENArray.add(mStorage.child(imageReferenceNames[i]));

        imageSliderAdapter = new ImageSliderAdapter(getContext(), XMENArray);
        offerCardsSlider.setAdapter(imageSliderAdapter);
        imageSliderIndicator.setViewPager(offerCardsSlider);

        // Auto star t of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageReferenceNames.length) {
                    currentPage = 0;
                }
                offerCardsSlider.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    @Override
    public void setAllOffersList(List<OfferDataModel> offerDataModels) {
        this.offerCards = offerDataModels;

    }

    @Override
    public List<OfferDataModel> getExpireOfferCardsList() {
        return expireOfferCards;
    }

    /*@Override
    public void loadTestimonialRetailerList() {
        retailerHomeFragmentPresenter.loadTestimonials();
    }
*/
    @Override
    public void enableFreshRetailerView() {
        textViewNewRetailer.setVisibility(View.VISIBLE);
        textViewAssistNewRetailer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecentShopsList(List<ShopDataModel> shops) {
        textViewNewRetailer.setText("Your Shops");
        textViewNewRetailer.setVisibility(View.VISIBLE);
        mAdapter = new RetailerHomeRecentShopsAdapter(getContext(), shops, this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        deleteExpireOffers();
    }

    private void deleteExpireOffers() {
        try {
            todayDate = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int today = new Date().getDay();
        if (offerCards != null && offerCards.size() != 0) {
            for (OfferDataModel offerCard : offerCards) {

                if (todayDate != null && todayDate.compareTo(offerCard.getOfferEndDate()) > 0) {
                    expireOfferCards.add(offerCard);
                    Log.d("expire_offers", offerCard.toString());

                }
            }
            retailerHomeFragmentPresenter.setExpiredOffersList();
            retailerHomeFragmentPresenter.expireOfferCards();
        }
    }
//    @Override
//    public void showRetailerTestimonialList(List<RetailerDataModel> retailerDataModels) {
//        List<RetailerDataModel> retailerDataModels1 = new ArrayList<>();

//        for (int i = 0; i < retailerDataModels.size(); i++) {
//            RetailerTestimonialAdapter testimonialAdapter = new RetailerTestimonialAdapter(retailerDataModels, getContext());
//            sliderViewPager.setAdapter(testimonialAdapter);
//            retailerDataModels1.add(new RetailerTestimonialAdapter(retailerDataModels, getContext()));
//            Log.d("showRetailerTest", "" + retailerDataModels.toString());

//        }
//        sliderViewPager.setVisibility(View.VISIBLE);
//        sliderViewPager.setAdapter(new RetailerTestimonialAdapter(retailerDataModels, getContext()));
//        final Handler handler1 = new Handler();
//        final Runnable Update1 = new Runnable() {
//            public void run() {
//
//                if (currentTestimonialPage >= retailerDataModels.size()) {
//                    currentTestimonialPage = 0;
//                }
//                sliderViewPager.setCurrentItem(currentTestimonialPage++, true);
//            }
//        };
//        Timer swipeTimer1 = new Timer();
//        swipeTimer1.schedule(new TimerTask() {
//            @Override`
//            public void run() {
//                handler1.post(Update1);
//            }
//        }, 4000, 4000);


//        Log.d("showRetailerTests", "" + retailerDataModels.toString());
//
//    }

    @Override
    public void showOfferCards(List<OfferDataModel> offerDataModels) {
        for (int i = 0; i < offerDataModels.size(); i++) {
//            RetailerTestimonialAdapter testimonialAdapter = new RetailerTestimonialAdapter(retailerDataModels, getContext());
//            sliderViewPager.setAdapter(testimonialAdapter);
//            retailerDataModels1.add(new RetailerTestimonialAdapter(retailerDataModels, getContext()));
            Log.d("showRetailerOffersTest", "" + offerDataModels.toString());

        }
        retailerOfferCardsAdapter = new RetailerOfferCardsAdapter(offerDataModels, getContext(), this);
        offerCardsSlider.setAdapter(retailerOfferCardsAdapter);
        imageSliderIndicator.setViewPager(offerCardsSlider);
        handler2 = new Handler();
        final Runnable Update2 = new Runnable() {
            public void run() {

                if (currentOfferPage >= offerDataModels.size()) {
                    currentOfferPage = 0;
                }
                offerCardsSlider.setCurrentItem(currentOfferPage++, true);
            }

        };
        Timer swipeTimer2 = new Timer();
        swipeTimer2.schedule(new TimerTask() {
            @Override
            public void run() {
                handler2.post(Update2);
            }

        }, 3000, 2500);


        Log.d("showRetailerTests", "" + offerDataModels.toString());
    }

    @OnClick(R.id.btn_link_shop_blink)
    public void linkShop() {
        startActivity(new Intent(getContext(), AddShopActivity.class));
    }

    @Override
    public void shopClicked(ShopDataModel shopDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getContext(), RetailerCategoryActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void addProductt(ShopDataModel shopDataModel) {
//        Intent intent = new Intent(getActivity(), AddProductActivity.class);
//        intent.putExtra("shopDataModel", shopDataModel);
//        startActivity(intent);
//    }

    @Override
    public void showShopProducts(ShopDataModel shopDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getContext(), RetailerCategoryActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addShopProduct(ShopDataModel shopDataModel) {
//        Intent intent = new Intent(getContext(), AddProductActivity.class);
//        intent.putExtra("shopDataModel", shopDataModel);
//        startActivity(intent);
        if (ConnectivityReceiver.isConnected()) {
            Intent intent = new Intent(getContext(), MasterListActivity.class);
            intent.putExtra("shopDataModel", shopDataModel);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        handler2.removeCallbacksAndMessages(null);
//    }

    @Override
    public void offerCardClicked(OfferDataModel offerDataModel) {
        if (ConnectivityReceiver.isConnected()) {
            Log.d(TAG, "OfferDataModel : " + offerDataModel.toString());
            Intent intent = new Intent(getActivity(), OfferProductsActivity.class);
            intent.putExtra("offerListDataModel", offerDataModel);
            intent.putExtra("flag", 0);
            startActivity(intent);
            Log.d("offer_clic_act.start", offerDataModel.toString());
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoadingOffersProgress(Boolean progress, String msg) {
        if (progress)
            showProgressDialog(msg);
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
}