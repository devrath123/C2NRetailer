package com.example.c2n.core.mapper;


import android.util.Log;

import com.example.c2n.core.model1.OfferDataModel;
import com.example.c2n.core.model1.OfferDetailsDataModel;
import com.example.c2n.core.model1.OfferProductDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by shivani.singh on 29-08-2018.
 */

public class ListOfferDataModelToListAppliedOfferViewMapper {

    List<OfferProductDataModel> offeredProductsList;
    List<HashMap<OfferDataModel, List<OfferProductDataModel>>> offeredProductsHashmapList = new ArrayList<>();
    List<HashMap<String, List<String>>> shopIDs = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate;

    @Inject
    public ListOfferDataModelToListAppliedOfferViewMapper() {
    }

    public List<HashMap<OfferDataModel, List<OfferProductDataModel>>> mapOfferModelToShopModel(List<OfferDetailsDataModel> offerDataModelList) {
        try {
            todayDate = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (OfferDetailsDataModel offerDetailsDataModel : offerDataModelList) {

            int today = new Date().getDay();
            if (offerDetailsDataModel.getOfferDataModel().isOfferStatus()) {
                if (isOfferCardActive(today, offerDetailsDataModel.getOfferDataModel())) {
                    if (todayDate != null && offerDetailsDataModel.getOfferDataModel().getOfferStartDate().compareTo(todayDate) * todayDate.compareTo(offerDetailsDataModel.getOfferDataModel().getOfferEndDate()) >= 0) {
                        if (offerDetailsDataModel.getOfferDataModel().getOfferProducts() != null && offerDetailsDataModel.getOfferDataModel().getOfferProducts().size() != 0) {
                            HashMap<OfferDataModel, List<OfferProductDataModel>> productsHashmap = new HashMap<>();
                            offeredProductsList = new ArrayList<>();
                            shopIDs = offerDetailsDataModel.getOfferDataModel().getOfferProducts();
                            for (HashMap shopID : shopIDs) {
                                List<String> productIDs = (ArrayList<String>) shopID.get(shopID.keySet().iterator().next());
                                for (String productID : productIDs) {
                                    Log.d("offered_productids", "" + productID);
                                    OfferProductDataModel offerProductDataModel = new OfferProductDataModel();
                                    offerProductDataModel.setRetailerID(offerDetailsDataModel.getRetailerID());
                                    offerProductDataModel.setProductID(productID);
                                    offerProductDataModel.setShopID(shopID.keySet().iterator().next().toString());
                                    offeredProductsList.add(offerProductDataModel);
                                }
                            }
                            productsHashmap.put(offerDetailsDataModel.getOfferDataModel(), offeredProductsList);
                            offeredProductsHashmapList.add(productsHashmap);
                            Log.d("offered_products", offeredProductsList.toString());
                        }

                    }
                }
            }

        }
        Log.d("offered_pros_hshmp_list", offeredProductsHashmapList.toString());

        return offeredProductsHashmapList;
    }

    public boolean isOfferCardActive(int todaysDay, OfferDataModel offerCard) {
        switch (todaysDay) {
            case 0:
                if (offerCard.isSun())
                    return true;
            case 1:
                if (offerCard.isMon())
                    return true;
            case 2:
                if (offerCard.isTue())
                    return true;
            case 3:
                if (offerCard.isWed())
                    return true;
            case 4:
                if (offerCard.isThu())
                    return true;
            case 5:
                if (offerCard.isFri())
                    return true;
            case 6:
                if (offerCard.isSat())
                    return true;
        }
        return false;
    }
}
