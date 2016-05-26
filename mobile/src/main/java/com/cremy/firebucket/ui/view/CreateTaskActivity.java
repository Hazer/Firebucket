package com.cremy.firebucket.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cremy.firebucket.R;
import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.greenrobotutils.library.ui.ActivityUtils;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.shared.mvp.CreateTaskMVP;
import com.cremy.shared.ui.presenter.CreateTaskPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTaskActivity extends BaseActivity implements
        CreateTaskMVP.View {


    //region View binding
    @BindView(R.id.rootViewCreateTask)
    CoordinatorLayout rootViewCreateTask;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //endregion

    //region View events
    @OnClick(R.id.fabCreateTask)
    public void clickFabCreateTask() {
        // todo
    }
    //endregion

    //region DI
    @Inject
    CreateTaskPresenter presenter;
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
        Intent intent = new Intent(_context, CreateTaskActivity.class);
        _context.startActivity(intent);
    }

    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
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
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setTitle("");
        ActivityUtils.setToolbarCustomWhiteBackArrow(this, this.getSupportActionBar());
    }

    @Override
    public Fragment getAttachedFragment(int _id) {
        return getSupportFragmentManager().findFragmentById(_id);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoNetwork() {
        SnackBarUtils.showActionSnackbar(this.rootViewCreateTask,
                getResources().getString(R.string.no_network),
                getResources().getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO
                    }
                }
        );
    }

    @Override
    public void showMessage(String _message) {
        SnackBarUtils.showSimpleSnackbar(this.rootViewCreateTask, _message);
    }
}