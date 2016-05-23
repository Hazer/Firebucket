package com.cremy.shared.mvp.base.view;

import android.content.Context;

/**
 * This interface must be implemented by:
 * BaseViewMobile
 * BaseViewWear
 * BaseViewTv
 * Created by remychantenay on 19/05/2016.
 */
public interface BaseView {

    //region DI
    void injectDependencies();
    //endregion

    //region Presenter
    void attachToPresenter();
    void detachFromPresenter();
    //endregion

    //region Loading/ProgressBar
    void showLoading();
    void hideLoading();
    //endregion

    //region Messages
    void showMessage(String _message);
    void showNoNetwork();
    //endregion

    //region Context
    Context getContext();
    //endregion
}