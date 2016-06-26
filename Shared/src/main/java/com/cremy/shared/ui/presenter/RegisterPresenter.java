package com.cremy.shared.ui.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.cremy.shared.R;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.mvp.RegisterMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.trello.rxlifecycle.ActivityEvent;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by remychantenay on 08/05/2016.
 */
public class RegisterPresenter extends BasePresenter<RegisterMVP.View>
        implements RegisterMVP.Presenter {
    private final static String TAG = "RegisterPresenter";

    //region DI
    DataManager dataManager;

    @Inject
    Context appContext;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Inject
    public RegisterPresenter(DataManager _dataManager) {
        this.dataManager = _dataManager;
    }
    //endregion


    //region Registration/Auth
    @Override
    public void createUser(String email, String password) {
        Single<AuthResult> authSingle = this.dataManager.createUserWithEmailAndPassword(email, password);
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
        final String username = usernameFromEmail(user.getEmail());

        // 1. We'll write the new user into the database
        Single<Void> single = this.dataManager.writeUserInDatabase(user.getUid(), username);
        // https://github.com/trello/RxLifecycle/issues/39#issuecomment-144194621
        single.toObservable().compose(this.view.bindUntilEvent(ActivityEvent.DESTROY));
        single.subscribe(new SingleSubscriber<Void>() {

            @Override
            public void onSuccess(Void value) {
                view.next();
            }

            @Override
            public void onError(Throwable e) {
                CrashReporter.log("Register: onCancelled | "+ e.getMessage());
                view.showMessage(e.getMessage());
            }

        });
    }

    @Override
    public void onAuthFail(Throwable e) {
        this.onAuthFailTracking(e);

        checkViewAttached();
        CrashReporter.log("Register: onAuthFail | "+ e.getMessage());
        this.view.showMessage(this.appContext.getResources().getString(R.string.error_firebase_auth_register));
    }

    @Override
    public void onAuthSuccessTracking(FirebaseUser user) {
        Bundle bundle = new Bundle();
        bundle.putString("user_uid", user.getUid());
        firebaseAnalytics.logEvent("register", bundle);
    }

    @Override
    public void onAuthFailTracking(Throwable e) {
        Bundle bundle = new Bundle();
        bundle.putString("message", e.getMessage());
        firebaseAnalytics.logEvent("register_fail", bundle);
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