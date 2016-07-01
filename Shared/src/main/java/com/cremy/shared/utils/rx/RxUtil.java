package com.cremy.shared.utils.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This class allows to provide some handful stuff for Observables/Singles
 * Created by remychantenay on 30/06/2016.
 */
public class RxUtil {


    /**
     * Allows to apply the classic schedulers to a given observable
     * subscribeOn(Schedulers.io())
     * observeOn(AndroidSchedulers.mainThread())
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
