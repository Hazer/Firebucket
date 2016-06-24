package com.cremy.firebucket.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cremy.firebucket.mvp.base.view.rx.BaseRxActivity;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.mvp.LaunchScreenMVP;
import com.cremy.shared.mvp.LoginMVP;
import com.cremy.shared.ui.presenter.LaunchScreenPresenter;
import com.cremy.shared.ui.presenter.LoginPresenter;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * LaunchScreen is just here as a Branded LaunchScreen
 * {@see https://www.google.com/design/spec/patterns/launch-screens.html}
 */
public class LaunchScreen extends BaseRxActivity implements
        LaunchScreenMVP.View {

    //region DI
    @Inject
    LaunchScreenPresenter presenter;
    @Override
    public void injectDependencies() {
        activityComponent().inject(this);
    }
    //endregion

    //region Presenter
    public void attachToPresenter() {
        this.presenter.attachView(this);
    }
    public void detachFromPresenter() {
        this.presenter.detachView();
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.injectDependencies();
        this.attachToPresenter();
        this.getExtras(getIntent());

        this.next();
    }

    @Override
    public void getExtras(Intent _intent) {

    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public Fragment getAttachedFragment(int _id) {
        return null;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String _message) {

    }

    @Override
    public void showNoNetwork() {

    }

    @Override
    public void onLandscape() {

    }

    @Override
    public void onPortrait() {

    }

    @Override
    public void next() {

        this.closeActivity();
        if (this.presenter.isUserExist()) {
            BucketActivity.startMe(this);
        } else {
            OnBoardingActivity.startMe(this);
        }
    }
}
