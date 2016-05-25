package com.cremy.shared.ui.presenter;

import android.content.Context;

import com.cremy.shared.data.DataManager;
import com.cremy.shared.mvp.OnBoardingMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public class OnBoardingPresenter extends BasePresenter<OnBoardingMVP.View>
        implements OnBoardingMVP.Presenter {
    private final static String TAG = "OnBoardingPresenter";

    //region DI
    DataManager dataManager;
    Context appContext;
    @Inject
    public OnBoardingPresenter(DataManager _dataManager,
                               Context _appContext) {
        this.dataManager = _dataManager;
        this.appContext = _appContext;
    }
    //endregion
}