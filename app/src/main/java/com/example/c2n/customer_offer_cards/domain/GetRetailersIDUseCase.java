package com.example.c2n.customer_offer_cards.domain;

import android.content.Context;

import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_offer_cards.data.CustomerOfferRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shivani.singh on 28-08-2018.
 */

public class GetRetailersIDUseCase extends UseCase<Void,List<String>> {

    CustomerOfferRepository customerOfferRepository;

    @Inject
    protected GetRetailersIDUseCase(UseCaseComposer useCaseComposer,CustomerOfferRepository customerOfferRepository) {
        super(useCaseComposer);
        this.customerOfferRepository = customerOfferRepository;
    }

    @Override
    protected Observable<List<String>> createUseCaseObservable(Void param, Context context) {
        return customerOfferRepository.getRetailersIDs();
    }
}
