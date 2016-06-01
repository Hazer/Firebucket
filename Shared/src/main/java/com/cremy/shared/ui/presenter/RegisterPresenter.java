package com.cremy.shared.ui.presenter;

import android.content.Context;

import com.cremy.shared.R;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.mvp.RegisterMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by remychantenay on 08/05/2016.
 */
public class RegisterPresenter extends BasePresenter<RegisterMVP.View>
        implements RegisterMVP.Presenter {
    private final static String TAG = "RegisterPresenter";

    //region DI
    DataManager dataManager;
    Context appContext;
    @Inject
    public RegisterPresenter(DataManager _dataManager,
                             Context _appContext) {
        this.dataManager = _dataManager;
        this.appContext = _appContext;
    }
    //endregion

    Observable<AuthResult> authObservable;

    //region Registration/Auth
    @Override
    public void createUser(String email, String password) {
        Observable<AuthResult> authObservable = this.dataManager.createUserWithEmailAndPassword(email, password);
            authObservable.observeOn(AndroidSchedulers.mainThread());
            authObservable.subscribe(new Subscriber<AuthResult>() {
            @Override
            public void onCompleted() {
                // Not needed here
            }

            @Override
            public void onError(Throwable e) {
                onAuthFail(e);
            }

            @Override
            public void onNext(AuthResult authResult) {
                onAuthSuccess(authResult.getUser());
            }
        });
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // 2. We tell to the view to go next
        this.view.next();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        CrashReporter.log("Register: onCancelled | "+ databaseError.getMessage());
        this.view.showMessage(databaseError.getMessage());
    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        checkViewAttached();
        final String username = usernameFromEmail(user.getEmail());

        // 1. We'll write the new user into the database
        this.dataManager.writeUserInDatabase(user.getUid(), username, this);
    }

    @Override
    public void onAuthFail(Throwable e) {
        checkViewAttached();
        CrashReporter.log("Register: onAuthFail | "+ e.getMessage());
        this.view.showMessage(this.appContext.getResources().getString(R.string.error_firebase_auth_register));
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