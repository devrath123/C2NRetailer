package com.example.c2n.core.usecase;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * Created by vipul.singhal on 10-05-2018.
 */

public abstract class UseCase<P, R> {
    protected final UseCaseComposer useCaseComposer;

    protected final Map<P, Observable<R>> observablesMap = new HashMap<>();

    protected boolean cacheObservable = true;

    protected UseCase(final UseCaseComposer useCaseComposer) {
        this.useCaseComposer = useCaseComposer;
    }

    protected void setCacheObservable(final boolean cacheObservable) {
        this.cacheObservable = cacheObservable;
    }

    protected abstract Observable<R> createUseCaseObservable(final P param, Context context);

    public boolean isRunning(P param) {
        return observablesMap.containsKey(param);
    }

    public int getRunningCount() {
        return observablesMap.size();
    }

    public Observable<R> execute(final P param,Context context) {

        Observable<R> observable = observablesMap.get(param);

        if (observable == null || !cacheObservable) {

            try {
                observable = createUseCaseObservable(param,context);
            } catch (Exception e) {
                observable = Observable.error(e);
            }

            if (useCaseComposer != null) {
                observable = observable.compose(useCaseComposer.apply());
            }
            observable = observable.doOnDispose(new OnTerminateAction(param));
            observablesMap.put(param, observable);
        }

        return observable;
    }

    private class OnTerminateAction implements Action {

        private P param;

        OnTerminateAction(P param) {
            this.param = param;
        }

        @Override
        public void run() throws Exception {
            observablesMap.remove(param);
        }
    }

}
