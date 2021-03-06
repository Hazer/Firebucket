package com.cremy.shared.ui.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cremy.shared.R;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.mvp.LoginMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public class LoginPresenter extends BasePresenter<LoginMVP.View>
        implements LoginMVP.Presenter {
    private final static String TAG = "LoginPresenter";

    //region DI
    DataManager dataManager;

    @Inject
    Context appContext;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Inject
    public LoginPresenter(DataManager _dataManager) {
        this.dataManager = _dataManager;
    }
    //endregion

    //region Login/Auth
    @Override
    public void signInUser(String email, String password) {

        Single<AuthResult> authSingle = this.dataManager.signInWithEmailAndPassword(email, password);
        // https://github.com/trello/RxLifecycle/issues/39#issuecomment-144194621
        authSingle.toObservable().compose(this.view.bindUntilEvent(ActivityEvent.DESTROY));
        authSingle.subscribe(new SingleSubscriber<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                onAuthSuccess(authResult.getUser());
            }

            @Override
            public void onError(Throwable e) {
                onAuthFail(e);
            }
        });
    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        this.onAuthSuccessTracking(user);

        checkViewAttached();
        this.view.next();
    }

    @Override
    public void onAuthFail(Throwable e) {
        this.onAuthFailTracking(e);

        checkViewAttached();
        CrashReporter.log("Login: onAuthFail | "+ e.getMessage());
        this.view.showMessage(this.appContext.getResources().getString(R.string.error_firebase_auth_signin));
    }

    @Override
    public void onAuthSuccessTracking(FirebaseUser user) {
        Bundle bundle = new Bundle();
        bundle.putString("user_uid", user.getUid());
        firebaseAnalytics.logEvent("login", bundle);
    }

    @Override
    public void onAuthFailTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString("message", e.getMessage());
        firebaseAnalytics.logEvent("login_fail", bundle);
    }
    //endregion
}