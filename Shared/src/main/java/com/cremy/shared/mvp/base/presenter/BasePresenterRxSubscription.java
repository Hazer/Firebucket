package com.cremy.shared.mvp.base.presenter;

import com.cremy.shared.mvp.base.BaseMvpView;

import rx.Subscription;

/**
 * Mother of all presenters where a rxJava subscription is needed
 * Note: must extend {@link BasePresenter}
 * Created by remychantenay on 14/05/2016.
 */
public class BasePresenterRxSubscription<T extends BaseMvpView> extends BasePresenter<T> {

    protected T view;

    //region rxJava subscription
    protected Subscription rxSubscription;
    //endregion

    @Override
    public void detachView() {
        if (this.rxSubscription != null) {
            rxSubscription.unsubscribe();
        }
        this.view = null;
    }
}