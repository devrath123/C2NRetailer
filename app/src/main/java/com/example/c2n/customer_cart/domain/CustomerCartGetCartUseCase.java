package com.example.c2n.customer_cart.domain;

import android.content.Context;

import com.example.c2n.core.mappers.ShopEntityToDataModelMapper;
import com.example.c2n.core.models.ShopDataModel;
import com.example.c2n.core.usecase.UseCase;
import com.example.c2n.core.usecase.UseCaseComposer;
import com.example.c2n.customer_cart.data.CustomerCartRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CustomerCartGetCartUseCase extends UseCase<Object[], List<ShopDataModel>> {

    private CustomerCartRepository customerCartRepository;
    private ShopEntityToDataModelMapper shopEntityToDataModelMapper;

    @Inject
    protected CustomerCartGetCartUseCase(UseCaseComposer useCaseComposer, CustomerCartRepository customerCartRepository, ShopEntityToDataModelMapper shopEntityToDataModelMapper) {
        super(useCaseComposer);
        this.customerCartRepository = customerCartRepository;
        this.shopEntityToDataModelMapper = shopEntityToDataModelMapper;
    }

    @Override
    protected Observable<List<ShopDataModel>> createUseCaseObservable(Object[] param, Context context) {
        return customerCartRepository.getCart((double) param[0], (double) param[1], (String) param[2], (int) param[3]).map(shopEntities -> shopEntityToDataModelMapper.mapEntityToData(shopEntities));
//        return customerCartRepository.getCart((double) param[0], (double) param[1], "%5B%22gLbqYyCGH3bcmlFCkLo7%22%5D", (int) param[3]).map(shopEntities -> shopEntityToDataModelMapper.mapEntityToData(shopEntities));
    }
}
