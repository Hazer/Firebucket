package com.cremy.shared.mvp;

import com.cremy.shared.mvp.base.BaseMvpView;
import com.cremy.shared.mvp.base.view.rx.IBaseRxActivity;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface LaunchScreenMVP {

    //region View
    interface View extends BaseMvpView, IBaseRxActivity {
        void next();
    }
    //endregion

    //region Presenter
    interface Presenter {
        boolean isUserExist();
        void fetchRemoteConfigData();

        void onFetchRemoteConfigSuccess();
        void onFetchRemoteConfigFail(Throwable e);
    }
    //endregion

    //region Model
    interface Model {

    }
    //endregion
}
