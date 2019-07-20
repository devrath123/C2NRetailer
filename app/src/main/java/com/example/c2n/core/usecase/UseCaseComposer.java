package com.example.c2n.core.usecase;

import io.reactivex.ObservableTransformer;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public interface UseCaseComposer {
    <T> ObservableTransformer<T, T> apply();
}
