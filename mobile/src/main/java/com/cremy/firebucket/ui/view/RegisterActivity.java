package com.cremy.firebucket.ui.view;

import android.app.ProgressDialog;
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

import com.cremy.firebucket.R;
import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.firebucket.mvp.base.view.rx.BaseRxActivity;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.shared.data.model.User;
import com.cremy.shared.mvp.RegisterMVP;
import com.cremy.shared.ui.presenter.RegisterPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseRxActivity implements
        RegisterMVP.View {
    public ProgressDialog progress;

    //region View binding
    @BindView(R.id.rootViewRegister)
    FrameLayout rootViewRegister;

    @BindView(R.id.registerForm)
    LinearLayout registerForm;

    @BindView(R.id.registerFormEmailTextInputLayout)
    TextInputLayout registerFormEmailTextInputLayout;
    @BindView(R.id.registerFormPasswordTextInputLayout)
    TextInputLayout registerFormPasswordTextInputLayout;
    //endregion

    //region View listeners
    @OnClick(R.id.registerFormButton)
    public void clickRegisterFormButton() {
        if (this.checkForm()) {
            this.showLoading();

            final String email = this.registerFormEmailTextInputLayout.getEditText().getText().toString().trim();
            final String password = this.registerFormPasswordTextInputLayout.getEditText().getText().toString().trim();
            this.presenter.createUser(email, password);
        }
    }
    //endregion

    //region DI
    @Inject
    RegisterPresenter presenter;
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
        Intent intent = new Intent(_context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        _context.startActivity(intent);
    }

    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        this.injectDependencies();
        this.attachToPresenter();

        this.getExtras(getIntent());
        this.setUpToolbar();
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
        progress = ProgressDialog.show(this, getResources().getString(R.string.general_progress_dialog_title), getResources().getString(R.string.general_progress_dialog_content), true);
    }

    @Override
    public void hideLoading() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    @Override
    public void showNoNetwork() {
        SnackBarUtils.showActionSnackbar(this.rootViewRegister,
                getResources().getString(R.string.no_network),
                getResources().getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickRegisterFormButton();
                    }
                }
        );
    }

    @Override
    public void showMessage(String _message) {
        this.hideLoading();
        SnackBarUtils.showSimpleSnackbar(this.rootViewRegister, _message);
    }


    @Override
    public void next() {
        this.hideLoading();

        BucketActivity.startMe(this);
        this.finish();
    }

    @Override
    public void previous() {
        OnBoardingActivity.startMe(this);
        this.closeActivity();
    }

    @Override
    public boolean checkForm() {
        final String email = this.registerFormEmailTextInputLayout.getEditText().getText().toString().trim();
        final String password = this.registerFormPasswordTextInputLayout.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            this.registerFormEmailTextInputLayout.setError(getResources().getString(R.string.error_login_invalid_email));
            this.registerFormEmailTextInputLayout.setErrorEnabled(true);
            return false;
        }
        if (password.isEmpty()
                || password.length() < User.RULE_LOGIN_PASSWORD_MIN_CHARS) {
            this.registerFormPasswordTextInputLayout.setError(getResources().getString(R.string.error_login_invalid_password));
            this.registerFormPasswordTextInputLayout.setErrorEnabled(true);
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