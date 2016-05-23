package com.cremy.androidapptemplate.ui.presenter;

import com.cremy.androidapptemplate.mvp.MainMVP;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.mvp.base.presenter.BasePresenter;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class MainPresenter extends BasePresenter<MainMVP.View>
        implements MainMVP.Presenter {
    private final static String TAG = "MainPresenter";

    //region DI
    DataManager dataManager;
    @Inject
    public MainPresenter(DataManager _dataManager) {
        dataManager = _dataManager;
    }
    //endregion
}