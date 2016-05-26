package com.cremy.shared.ui.presenter;

import com.cremy.shared.data.DataManager;
import com.cremy.shared.mvp.CreateTaskMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class CreateTaskPresenter extends BasePresenter<CreateTaskMVP.View>
        implements CreateTaskMVP.Presenter {
    private final static String TAG = "CreateTaskPresenter";

    //region DI
    DataManager dataManager;
    @Inject
    public CreateTaskPresenter(DataManager _dataManager) {
        dataManager = _dataManager;
    }
    //endregion
}