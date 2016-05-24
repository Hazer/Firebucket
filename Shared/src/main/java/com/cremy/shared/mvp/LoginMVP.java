package com.cremy.shared.mvp;

import com.cremy.shared.mvp.base.BaseMvpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface LoginMVP {

    //region View
    interface View extends BaseMvpView {
        void next();
    }
    //endregion

    //region Presenter
    interface Presenter extends OnCompleteListener<AuthResult> {
        void onAuthSuccess(FirebaseUser user);
        void onAuthFail();
    }
    //endregion

    //region Model
    interface Model {

    }
    //endregion
}
