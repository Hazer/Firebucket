package com.cremy.shared.mvp;

import com.cremy.shared.mvp.base.BaseMvpView;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface MainMVP {

    //region View
    interface View extends BaseMvpView {
        void nextCreateTask();
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
