package com.example.c2n.customer_offer_cards.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c2n.R;
import com.example.c2n.core.model1.OfferDetailsDataModel;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivani.singh on 30-08-2018.
 */

public class CustomerOffersAdapter extends RecyclerView.Adapter<CustomerOffersAdapter.ViewHolder> {
    private List<OfferDetailsDataModel> offerDetailsDataModels;
    private CustomerOffersAdapter.OfferCardAdapterInterface offerCardAdapterInterface;
    Context context;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Inject
    public CustomerOffersAdapter(List<OfferDetailsDataModel> offerDetailsDataModels, Context context,OfferCardAdapterInterface offerCardAdapterInterface) {
        this.offerDetailsDataModels = offerDetailsDataModels;
        this.context = context;
        this.offerCardAdapterInterface = offerCardAdapterInterface;
    }

    @NonNull
    @Override
    public CustomerOffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycler_view_customer_offer, parent, false);
        return new CustomerOffersAdapter.ViewHolder(v, offerCardAdapterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOffersAdapter.ViewHolder holder, int position) {
        OfferDetailsDataModel offerDetailsDataModel = offerDetailsDataModels.get(position);
        Log.d("onBindViewHolder",offerDetailsDataModel.toString());
        holder.productName.setText(offerDetailsDataModel.getOfferDataModel().getOfferName());
        holder.discountPercent.setText(offerDetailsDataModel.getOfferDataModel().getOfferDiscount()+" ");
//        holder.offerName.setText(offerDetailsDataModel.getOfferDataModel().getOfferName());
        holder.date.setText(format.format(offerDetailsDataModel.getOfferDataModel().getOfferStartDate()) +" To "+format.format(offerDetailsDataModel.getOfferDataModel().getOfferEndDate()));
//        holder.productPrice.setText(offerDetailsDataModel.getOfferDataModel().getOfferProducts().get());

      /*  List<HashMap<String, List<String>>> offerProductsList = (List<HashMap<String, List<String>>>)offerDetailsDataModel.getOfferDataModel().getOfferProducts();
        for (int i = 0; i < offerProductsList.size(); i++) {
            HashMap<String, List<String>> shopHashmap = (HashMap<String, List<String>>) offerProductsList.get(i);
            Log.d("HashMap", offerProductsList.toString());
            Set<String> shopKeys = shopHashmap.keySet();
            String shopKey = shopKeys.iterator().next();
            Log.d("shopkey", shopKey);
            List<String> productsIdList = new ArrayList<>();
            List<String> productsId;
            productsId = shopHashmap.get(shopKey);
            Log.d("productsId", productsId.toString());
            productsIdList.addAll(productsId);
            shopHashmap.put(shopKey, productsIdList);
            offerProductsList.add(shopHashmap);
            Log.d("offerProductsList",offerProductsList.toString());
        }
*/

    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount",offerDetailsDataModels.toString());
        return offerDetailsDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       private CustomerOffersAdapter.OfferCardAdapterInterface offerCardAdapterInterface;

        @BindView(R.id.product_image)
        ImageView productImage;

        @BindView(R.id.tv_product_name)
        TextView productName;

        @BindView(R.id.tv_price)
        TextView productPrice;

        @BindView(R.id.tv_discount_price)
        TextView discountPercent;

//        @BindView(R.id.tv_offer_name)
//        TextView offerName;

        @BindView(R.id.tv_date)
        TextView date;



        public ViewHolder(View itemView,CustomerOffersAdapter.OfferCardAdapterInterface offerCardAdapterInterface) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            Log.d("getItemCount",offerDetailsDataModels.toString());

            this.offerCardAdapterInterface = offerCardAdapterInterface;
        }
    }

    public interface OfferCardAdapterInterface {
        void offerClicked(OfferDetailsDataModel offerDetailsDataModel);
    }


}
