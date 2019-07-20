package com.example.c2n.edit_profile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.c2n.R;
import com.example.c2n.core.model1.CustomerDataModel;
import com.example.c2n.core.model1.RetailerDataModel;
import com.example.c2n.edit_profile.domain.EditProfileGetCustomerUseCase;
import com.example.c2n.edit_profile.domain.EditProfileGetRetailerUseCase;
import com.example.c2n.edit_profile.domain.EditProfileUseCase;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

/**
 * Created by roshan.nimje on 11-05-2018.
 */

public class EditProfilePresenter {

    EditProfileUseCase editProfileUseCase;
    EditProfileGetRetailerUseCase editProfileGetRetailerUseCase;
    EditProfileGetCustomerUseCase editProfileGetCustomerUseCase;
    EditProfileView editProfileView;
    Context context;

    @Inject
    EditProfilePresenter(EditProfileUseCase editProfileUseCase, EditProfileGetRetailerUseCase editProfileGetRetailerUseCase, EditProfileGetCustomerUseCase editProfileGetCustomerUseCase) {
        this.editProfileUseCase = editProfileUseCase;
        this.editProfileGetRetailerUseCase = editProfileGetRetailerUseCase;
        this.editProfileGetCustomerUseCase = editProfileGetCustomerUseCase;
    }

    public void bind(EditProfileView editProfileView, Context context) {
        this.editProfileView = editProfileView;
        this.context = context;
    }

    public void checkProfileFields() {
        if (!editProfileView.isDOBEmpty()) {
            if (!editProfileView.isMobileNoEmpty()) {
                if (!editProfileView.isAddressEmpty())
                    editProfileView.updateProfile();
            }
        }
    }

    @SuppressLint("RxLeakedSubscription")
    public void updateProfile() {
        if (editProfileView.getUserType().equals("R")) {
            RetailerDataModel retailerDataModel = new RetailerDataModel();
            retailerDataModel.setRetailerAddress(editProfileView.getAddress());
            retailerDataModel.setRetailerID(editProfileView.getEmail());
            retailerDataModel.setRetailerDOB(editProfileView.getDOB());
            retailerDataModel.setRetailerImageURL(editProfileView.getProfilePicURL());
            retailerDataModel.setRetailerLatitude(editProfileView.getLatitude());
            retailerDataModel.setRetailerLongitude(editProfileView.getLongitude());
            retailerDataModel.setRetailerName(editProfileView.getUserName());
            retailerDataModel.setRetailerMobileNo(editProfileView.getMobileNo());
//        editProfileUseCase.execute(new RetailerDataModel(editProfileView.getUserID(), editProfileView.getUserName(),editProfileView.getAddress(), editProfileView.getMobileNo(),editProfileView.getLatitude(),editProfileView.getLongitude(),editProfileView.getDOB(),editProfileView.getProfilePicURL(), context)
            editProfileUseCase.execute(new Object[]{retailerDataModel, null}, context)
                    .doOnSubscribe(disposable -> editProfileView.showEditProfileProgress(true))
                    .subscribe(this::handleResponse, throwable -> handleError(throwable));
        } else {
            CustomerDataModel customerDataModel = new CustomerDataModel();
            customerDataModel.setCustomerAddress(editProfileView.getAddress());
            customerDataModel.setCustomerID(editProfileView.getEmail());
            customerDataModel.setCustomerDOB(editProfileView.getDOB());
            customerDataModel.setCustomerImageURL(editProfileView.getProfilePicURL());
            customerDataModel.setCustomerLatitude(editProfileView.getLatitude());
            customerDataModel.setCustomerLongitude(editProfileView.getLongitude());
            customerDataModel.setCustomerName(editProfileView.getUserName());
            customerDataModel.setCustomerMobileNo(editProfileView.getMobileNo());
            editProfileUseCase.execute(new Object[]{null, customerDataModel}, context)
                    .doOnSubscribe(disposable -> editProfileView.showEditProfileProgress(true))
                    .subscribe(this::handleResponse, throwable -> handleError(throwable));
        }
    }

    /* @SuppressLint("RxLeakedSubscription")
     public void updateProfile() {
         editProfileUseCase.execute(new UserDataModel(editProfileView.getUserID(), editProfileView.getUserName(), editProfileView.getMobileNo(), editProfileView.getAddress(), editProfileView.getDOB(), editProfileView.getGender(), editProfileView.getProfilePicURL(), editProfileView.getLatitude(), editProfileView.getLongitude()), context)
                 .doOnSubscribe(disposable -> editProfileView.showEditProfileProgress(true))
                 .subscribe(this::handleResponse, throwable -> handleError(throwable));
     }*/
    private void handleResponse(DocumentReference documentReference) {
        Log.d("handleResponse..", "success");
        editProfileView.showEditProfileProgress(false);
//        editProfileView.openPreferences();
    }


    @SuppressLint("RxLeakedSubscription")
    public void getUser() {
        if (editProfileView.getUserType().equals("R")) {
            RetailerDataModel retailerDataModel = new RetailerDataModel();
            retailerDataModel.setRetailerID(editProfileView.getUserDocumentID());
            Log.d("getUserPresenter", "" + editProfileView.getUserDocumentID());
            editProfileGetRetailerUseCase.execute(retailerDataModel, context)
//                .doOnSubscribe(disposable -> editProfileView.showEditProfileProgress(true))
                    .subscribe(this::handleRetailerResponse, throwable -> handleError(throwable));
        } else {
            CustomerDataModel customerDataModel = new CustomerDataModel();
            customerDataModel.setCustomerID(editProfileView.getUserDocumentID());
            Log.d("getUserPresenter", "" + editProfileView.getUserDocumentID());
            editProfileGetCustomerUseCase.execute(customerDataModel, context)
//                .doOnSubscribe(disposable -> editProfileView.showEditProfileProgress(true))
                    .subscribe(this::handleCustomerResponse, throwable -> handleError(throwable));
        }
    }


    /*   @SuppressLint("RxLeakedSubscription")
       public void getUser() {
           Log.d("getUserPresenter", "" + editProfileView.getUserID());
           editProfileGetUserUseCase.execute(new UserDataModel(editProfileView.getUserID()), context)
   //                .doOnSubscribe(disposable -> editProfileView.showEditProfileProgress(true))
                   .subscribe(this::handleResponse, throwable -> handleError(throwable));
       }
   */
    private void handleRetailerResponse(RetailerDataModel retailerDataModel) {
        Log.d("handleResponse", "success");
//        editProfileView.showEditProfileProgress(false);
        editProfileView.setMobileNo(retailerDataModel.getRetailerMobileNo());
        editProfileView.setDOB(retailerDataModel.getRetailerDOB());
        editProfileView.setUserName(retailerDataModel.getRetailerName());
        editProfileView.setEmail(retailerDataModel.getRetailerID());
        editProfileView.setAddress(retailerDataModel.getRetailerAddress());
        editProfileView.setProfilePicURL(retailerDataModel.getRetailerImageURL());
        editProfileView.setLatitude(retailerDataModel.getRetailerLatitude());
        editProfileView.setLongitude(retailerDataModel.getRetailerLongitude());
    }

    private void handleError(Throwable throwable) {
        Log.d("EditProfilePresenter", "" + throwable.getMessage());
//        editProfileView.showEditProfileProgress(false);
    }

    private void handleCustomerResponse(CustomerDataModel customerDataModel) {
        Log.d("handleResponse", "success");
//        editProfileView.showEditProfileProgress(false);
        editProfileView.setMobileNo(customerDataModel.getCustomerMobileNo());
        editProfileView.setDOB(customerDataModel.getCustomerDOB());
        editProfileView.setUserName(customerDataModel.getCustomerName());
        editProfileView.setEmail(customerDataModel.getCustomerID());
        editProfileView.setAddress(customerDataModel.getCustomerAddress());
        editProfileView.setProfilePicURL(customerDataModel.getCustomerImageURL());
        editProfileView.setLatitude(customerDataModel.getCustomerLatitude());
        editProfileView.setLongitude(customerDataModel.getCustomerLongitude());
    }

}
