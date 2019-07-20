package com.example.c2n.retailer_shop_products_list.presenter;

import android.widget.SearchView;

import com.example.c2n.core.base.BaseFragment;
import com.example.c2n.retailer_shop_products_list.presenter.adapter.RetailerShopProductsAdapter;

import javax.inject.Inject;

/**
 * Created by vipul.singhal on 21-06-2018.
 */

public class RetailerShopProductsFragment extends BaseFragment {

    @Inject
    RetailerShopProductsPresenter retailerShopProductsPresenter;
    RetailerShopProductsAdapter mAdapter;
    String shopName;
    String categorySelected;
    String shopEmail;
    SearchView searchView;

//    @BindView(R.id.fab_add_shop)
//    FloatingActionButton floatingActionButtonAddShop;
//
//    @BindView(R.id.recycler_view_retailer_shop_products)
//    RecyclerView mRecyclerView;
//
//    @BindView(R.id.tv_add_shop_hint)
//    TextView textViewAddShopHint;
//
//    private ShopDataModel shopDataModel;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_retailer_shop_products, container, false);
//        ButterKnife.bind(this, view);
////        RetailerShopProductsDI.getRetailerShopProductsComponent().inject(this);
//        retailerShopProductsPresenter.bind(this, getContext());
////        shopName = this.getArguments().getString("shopSelected");
//        categorySelected = this.getArguments().getString("categorySelected");
////        shopEmail = this.getArguments().getString("shopEmail");
//        Intent intent = getActivity().getIntent();
//        if (intent != null) {
//            shopDataModel = (ShopDataModel) intent.getSerializableExtra("shopDataModel");
//            shopName = shopDataModel.getShopName();
//            shopEmail = shopDataModel.getShopEmail();
//        }
//
//        textViewAddShopHint.setVisibility(View.GONE);
//
//        loadProducts();
//
//        whiteNotificationBar(mRecyclerView);
//
//        return view;
//    }
//
//    private void loadProducts() {
//        retailerShopProductsPresenter.loadProducts(shopEmail, categorySelected);
//    }
//
//    @Override
//    public void showProgress(Boolean progress) {
//        if (progress)
//            showProgressDialog("Loading Products...");
//        else
//            dismissProgressDialog();
//    }
//
//    @Override
//    public void showProductsList(List<ProductDataModel> products) {
//        Log.d("products_list", products.toString());
//        if (products.size() != 0) {
////            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//            mAdapter = new RetailerShopProductsAdapter(getContext(), products, this);
//            LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
//            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
//            mRecyclerView.setHasFixedSize(true);
//            mRecyclerView.setLayoutManager(linearLayout);
//            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
//        } else
//            textViewAddShopHint.setVisibility(View.VISIBLE);
//    }
//
//
//    @Override
//    public void productSelected(ProductDataModel productDataModel) {
//
//        Intent intent = new Intent(getActivity(), ViewProductActivity.class);
//        intent.putExtra("shopDataModel", shopDataModel);
//        intent.putExtra("productDataModel", productDataModel);
//        intent.putExtra("categorySelected", categorySelected);
//        intent.putExtra("shopEmail", shopEmail);
//        startActivity(intent);
//
//    }
//
//    @Override
//    public void addOrChangeOffer(ProductDataModel productDataModel) {
//
//        if (productDataModel.getProductScheme() == null || productDataModel.getProductScheme().equals("")) {
//
//            Intent intent = new Intent(getActivity(), ViewProductActivity.class);
//            intent.putExtra("productDataModel", productDataModel);
//            intent.putExtra("categorySelected", categorySelected);
//            intent.putExtra("shopEmail", shopEmail);
//            intent.putExtra("selected", "yes");
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(getActivity(), UpdateProductOfferActivity.class);
//            intent.putExtra("productDataModel", productDataModel);
//            intent.putExtra("categorySelected", categorySelected);
//            intent.putExtra("shopEmail", shopEmail);
//            startActivity(intent);
//        }
//    }
//
//    @OnClick(R.id.fab_add_shop)
//    @Override
//    public void addProductMaster() {
//        Intent intent = new Intent(getActivity(), AddProductActivity.class);
//        intent.putExtra("shopDataModel", shopDataModel);
//        startActivity(intent);
//    }
//
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        getActivity().getMenuInflater().inflate(R.menu.menu_search_products, menu);
//
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search)
//                .getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getActivity().getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        // listening to search query text change
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//                mAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                // filter recycler view when text is changed
//                mAdapter.getFilter().filter(query);
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void whiteNotificationBar(View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int flags = view.getSystemUiVisibility();
//            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//            view.setSystemUiVisibility(flags);
//            getActivity().getWindow().setStatusBarColor(Color.WHITE);
//        }
//    }
}
