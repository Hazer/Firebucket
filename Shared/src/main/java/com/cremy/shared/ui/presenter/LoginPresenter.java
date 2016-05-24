package com.cremy.shared.ui.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cremy.shared.R;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.mvp.LoginMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public class LoginPresenter extends BasePresenter<LoginMVP.View>
        implements LoginMVP.Presenter {
    private final static String TAG = "LoginPresenter";

    //region DI
    DataManager dataManager;
    Context appContext;
    @Inject
    public LoginPresenter(DataManager _dataManager,
                          Context _appContext) {
        this.dataManager = _dataManager;
        this.appContext = _appContext;
    }
    //endregion

    //region Login/Auth
    @Override
    public void signInUser(String email, String password) {
        this.dataManager.signInWithEmailAndPassword(email, password, this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            this.onAuthSuccess(task.getResult().getUser());
        } else {
            this.onAuthFail(task.getException());
        }
    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        checkViewAttached();

        // We tell to the view to go next
        this.view.next();
    }

    @Override
    public void onAuthFail(Exception e) {
        checkViewAttached();
        CrashReporter.log("Login: onAuthFail | "+ e.getMessage());
        this.view.showMessage(this.appContext.getResources().getString(R.string.error_firebase_auth_signin));
    }
    //endregion

    //region Other
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    //endregion
}