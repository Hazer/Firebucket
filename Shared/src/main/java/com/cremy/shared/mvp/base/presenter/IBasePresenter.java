package com.cremy.shared.mvp.base.presenter;

import com.cremy.shared.mvp.base.BaseMvpView;

/**
 * This interface must be inherited from  {@link BasePresenter}
 * Created by remychantenay on 26/04/2016.
 */
public interface IBasePresenter<V extends BaseMvpView> {

    //region View
    void attachView(V _view);
    void detachView();
    boolean isViewAttached();
    void checkViewAttached();
    //endregion
}