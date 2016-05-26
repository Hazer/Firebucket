package com.cremy.firebucket.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.cremy.firebucket.BuildConfig;
import com.cremy.firebucket.R;
import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.shared.data.model.User;
import com.cremy.shared.mvp.LoginMVP;
import com.cremy.shared.ui.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements
        LoginMVP.View {


    //region View binding
    @BindView(R.id.rootViewLogin)
    FrameLayout rootViewLogin;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.loginForm)
    LinearLayout loginForm;

    @BindView(R.id.loginFormEmailTextInputLayout)
    TextInputLayout loginFormEmailTextInputLayout;
    @BindView(R.id.loginFormPasswordTextInputLayout)
    TextInputLayout loginFormPasswordTextInputLayout;
    //endregion

    //region View events
    @OnClick(R.id.loginFormButton)
    public void clickLoginFormButton() {
        if (this.checkForm()) {
            this.showLoading();
            final String email = this.loginFormEmailTextInputLayout.getEditText().getText().toString().trim();
            final String password = this.loginFormPasswordTextInputLayout.getEditText().getText().toString().trim();
            this.presenter.signInUser(email, password);
        }
    }
    //endregion

    //region DI
    @Inject
    LoginPresenter presenter;
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

    /**
     * Allows to start this activity
     * @param _context
     */
    public static void startMe(Context _context) {
        Intent intent = new Intent(_context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        _context.startActivity(intent);
    }

    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.injectDependencies();
        this.attachToPresenter();

        this.getExtras(getIntent());
        this.setUpToolbar();

        if (BuildConfig.LOG) {
            this.loginFormEmailTextInputLayout.getEditText().setText("test@test.com");
            this.loginFormPasswordTextInputLayout.getEditText().setText("testpassword");
        }
    }

    @Override
    protected void onDestroy() {
        this.detachFromPresenter();
        super.onDestroy();
    }

    //endregion


    @Override
    public void onLandscape() {

    }

    @Override
    public void onPortrait() {

    }

    @Override
    public void getExtras(Intent _intent) {

    }

    @Override
    public void closeActivity() {
        this.finish();
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public Fragment getAttachedFragment(int _id) {
        return getSupportFragmentManager().findFragmentById(_id);
    }

    @Override
    public void showLoading() {
        this.progressBar.setVisibility(View.VISIBLE);
        this.loginForm.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        this.progressBar.setVisibility(View.GONE);
        this.loginForm.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoNetwork() {
        SnackBarUtils.showActionSnackbar(this.rootViewLogin,
                getResources().getString(R.string.no_network),
                getResources().getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickLoginFormButton();
                    }
                }
        );
    }

    @Override
    public void showMessage(String _message) {
        this.hideLoading();
        SnackBarUtils.showSimpleSnackbar(this.rootViewLogin, _message);
    }


    @Override
    public void next() {
        this.hideLoading();
        MainActivity.startMe(this);
        this.closeActivity();
    }

    @Override
    public void previous() {
        OnBoardingActivity.startMe(this);
        this.closeActivity();
    }

    @Override
    public boolean checkForm() {
        final String email = this.loginFormEmailTextInputLayout.getEditText().getText().toString().trim();
        final String password = this.loginFormPasswordTextInputLayout.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            this.loginFormEmailTextInputLayout.setError(getResources().getString(R.string.error_login_invalid_email));
            this.loginFormEmailTextInputLayout.setErrorEnabled(true);
            return false;
        }
        if (password.isEmpty()
                || password.length() < User.RULE_LOGIN_PASSWORD_MIN_CHARS) {
            this.loginFormPasswordTextInputLayout.setError(getResources().getString(R.string.error_login_invalid_password));
            this.loginFormPasswordTextInputLayout.setErrorEnabled(true);
            return false;
        }

        return true;
    }


    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
        if ((paramKeyEvent.getAction() != 0) || ((paramInt != 4) && (paramInt != 3))) {
            // Volume keys, ...
        }
	  /* back button pressed */
        else {
            this.previous();
            return false;
        }

        return super.onKeyDown(paramInt, paramKeyEvent);
    }

}