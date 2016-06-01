package com.cremy.shared.mvp;

import com.cremy.shared.mvp.base.BaseMvpView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface RegisterMVP {

    //region View
    interface View extends BaseMvpView {
        void next();
        void previous();
        boolean checkForm();
    }
    //endregion

    //region Presenter
    interface Presenter extends ValueEventListener {
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
