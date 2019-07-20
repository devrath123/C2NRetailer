package com.example.c2n.retailerhome.presenter;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.FCMHandler;
import com.example.c2n.core.SharedPrefManager;
import com.example.c2n.edit_profile.presenter.EditProfileFragment;
import com.example.c2n.initial_phase.MainActivity;
import com.example.c2n.offer_cards_list.presenter.OffersListActivity;
import com.example.c2n.retailer_deal.presenter.RetailerDealFragment;
import com.example.c2n.viewshops.presenter.ViewShopsFragment;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class RetailerHomeActivity extends AppCompatActivity {

    private static final String TAG = RetailerHomeActivity.class.getSimpleName();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;
    private ImageView imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_SHOPS = "shops";
    private static final String TAG_OFFERS = "offers";
    private static final String TAG_DEALS = "deals";
    private static final String TAG_LOGOUT = "logout";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home2);

        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPrefManager.Init(this);

        mHandler = new Handler();

        Bundle intent = getIntent().getExtras();
        boolean status = intent.getBoolean("status");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        imgProfile = navHeader.findViewById(R.id.imageView);

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_PROFILE;
                setToolbarTitle();
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, editProfileFragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                drawer.closeDrawers();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_PROFILE;
                setToolbarTitle();
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, editProfileFragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                drawer.closeDrawers();
            }
        });

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        loadNavHeader();

        setUpNavigationView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                navItemIndex = Integer.parseInt(bundle.getString("navigation"));
                CURRENT_TAG = TAG_SHOPS;
                if (status) {
                    navItemIndex = 4;
                    CURRENT_TAG = TAG_DEALS;
                }
                loadHomeFragment();
            } catch (Exception e) {
                if (status) {
                    navItemIndex = 4;
                    CURRENT_TAG = TAG_DEALS;
                } else {
                    navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                }
                loadHomeFragment();
            }
        }
    }

    private void loadNavHeader() {

        SharedPrefManager.LoadFromPref();
        // name, website
        txtName.setText("Hi, " + SharedPrefManager.get_userFullName());
        if (!SharedPrefManager.get_userProfilePic().equals("")) {
            Log.d("RetailerHomeActivity", SharedPrefManager.get_userProfilePic());
            Picasso.get().load(SharedPrefManager.get_userProfilePic()).fit().into(imgProfile);
        }
    }

    private void loadHomeFragment() {
        selectNavMenu();

        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                RetailerHomeFragment retailerHomeFragment = new RetailerHomeFragment();
                return retailerHomeFragment;
            case 1:
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                return editProfileFragment;
            case 2:
                ViewShopsFragment viewShopsFragment = new ViewShopsFragment();
                return viewShopsFragment;
            case 3:

            case 4:
                RetailerDealFragment retailerDealFragment = new RetailerDealFragment();
                return retailerDealFragment;
            default:
                return new RetailerHomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    public void setToolbatTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_shops:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SHOPS;
                        break;
                    case R.id.nav_offers:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_OFFERS;
                        startActivity(new Intent(RetailerHomeActivity.this, OffersListActivity.class));
                        break;
                    case R.id.nav_deal:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_DEALS;
                        break;
                    case R.id.nav_logout:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RetailerHomeActivity.this);
                        alertDialog.setTitle("Confirm ");
                        alertDialog.setMessage("Are you sure you want to logout?");

                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                FCMHandler fcmHandler = new FCMHandler();
                                fcmHandler.disableFCM();

                                SharedPrefManager.DeleteAllEntriesFromPref();
                                startActivity(new Intent(RetailerHomeActivity.this, MainActivity.class));
                                finish();

                            }
                        });

                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        alertDialog.show();
                        break;
                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            } else {
                System.exit(0);
            }
        }
        super.onBackPressed();
    }
}