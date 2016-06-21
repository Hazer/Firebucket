package com.cremy.shared.mvp;

import com.cremy.shared.mvp.base.BaseMvpView;
import com.cremy.shared.mvp.base.view.rx.IBaseRxActivity;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface RegisterMVP {

    //region View
    interface View extends BaseMvpView, IBaseRxActivity {
        void next();
        void previous();
        boolean checkForm();
    }
    //endregion

    //region Presenter
    interface Presenter {
        void createUser(String email, String password);
        void onAuthSuccess(FirebaseUser user);
        void onAuthFail(Throwable e);
    }
    //endregion

    //region Model
    interface Model {

    }
    //endregion
}
