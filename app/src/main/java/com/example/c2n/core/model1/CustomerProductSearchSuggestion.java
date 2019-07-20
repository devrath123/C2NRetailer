package com.example.c2n.core.model1;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

@SuppressLint("ParcelCreator")
public class CustomerProductSearchSuggestion implements SearchSuggestion {

    private String productName;

    public CustomerProductSearchSuggestion(String productName) {
        this.productName = productName;
    }

    public CustomerProductSearchSuggestion(Parcel source) {
        this.productName = source.readString();
    }

    @Override
    public String getBody() {
        return productName;
    }

    public static final Creator<CustomerProductSearchSuggestion> CREATER = new Creator<CustomerProductSearchSuggestion>() {
        @Override
        public CustomerProductSearchSuggestion createFromParcel(Parcel source) {
            return new CustomerProductSearchSuggestion(source);
        }

        @Override
        public CustomerProductSearchSuggestion[] newArray(int size) {
            return new CustomerProductSearchSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
    }
}
