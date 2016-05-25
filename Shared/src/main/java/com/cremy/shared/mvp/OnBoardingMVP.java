package com.cremy.shared.mvp;

import com.cremy.shared.mvp.base.BaseMvpView;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface OnBoardingMVP {

    //region View
    interface View extends BaseMvpView {
        void login();
        void register();
    }
    //endregion

    //region Presenter
    interface Presenter {
    }
    //endregion

    //region Model
    interface Model {

    }
    //endregion
}
