package com.example.c2n.customer_single_shop_products.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.c2n.core.database.DaoAccess;
import com.example.c2n.core.database.ProductDatabase;
import com.example.c2n.core.usecase.AndroidUseCaseComposer;
import com.example.c2n.core.usecase.UseCaseComposer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomerSingleShopProductsModule {
    @Provides
    UseCaseComposer getUseCaseComposer() {
        return new AndroidUseCaseComposer();
    }

//    @Singleton
//    @Provides
//    ProductDatabase getDatabase(Application app){
//        return Room.databaseBuilder(app.getApplicationContext(), ProductDatabase.class, DATABASE_NAME)
//                .fallbackToDestructiveMigration().build();
//
//    }
//
//    @Singleton
//    @Provides
//    DaoAccess getDAO(ProductDatabase db){
//        return db.daoAccess();
//    }
}
