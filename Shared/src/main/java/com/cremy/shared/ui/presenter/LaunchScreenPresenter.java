package com.cremy.shared.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.cremy.shared.R;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.remote.RemoteConfigService;
import com.cremy.shared.mvp.LaunchScreenMVP;
import com.cremy.shared.mvp.LoginMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;

/**
 * Created by remychantenay on 08/05/2016.
 */
public class LaunchScreenPresenter extends BasePresenter<LaunchScreenMVP.View>
        implements LaunchScreenMVP.Presenter {
    private final static String TAG = "LaunchScreenPresenter";

    //region DI
    DataManager dataManager;

    @Inject
    Context appContext;
    @Inject
    public LaunchScreenPresenter(DataManager _dataManager) {
        this.dataManager = _dataManager;
    }
    //endregion

    //region User
    @Override
    public boolean isUserExist() {
        return this.dataManager.ifUserExists();
    }
    //endregion

    //region Remote Config
    @Override
    public void fetchRemoteConfigData() {
        Single<Void> remoteConfigSingle = this.dataManager.fetchRemoteConfigValues();
        // https://github.com/trello/RxLifecycle/issues/39#issuecomment-144194621
        remoteConfigSingle.toObservable().compose(this.view.bindUntilEvent(ActivityEvent.DESTROY));
        remoteConfigSingle.subscribe(new SingleSubscriber<Void>() {
            @Override
            public void onSuccess(Void result) {
                    onFetchRemoteConfigSuccess();
            }

            @Override
            public void onError(Throwable e) {
                onFetchRemoteConfigFail(e);
            }
        });

    }

    @Override
    public void onFetchRemoteConfigSuccess() {
        Log.d(TAG, "Fetch Remote Config Succeeded");
        checkViewAttached();
        this.dataManager.activateFetched();

        this.view.next();
    }

    @Override
    public void onFetchRemoteConfigFail(Throwable e) {
        Log.d(TAG, "Fetch Remote Config Failed "+e.getMessage());
        CrashReporter.log("Remote Config: onFetchRemoteConfigFail | "+ e.getMessage());
        checkViewAttached();
        this.view.next();
    }
    //endregion
}