package com.cremy.firebucket.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.cremy.firebucket.R;
import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.firebucket.ui.adapter.BucketAdapter;
import com.cremy.firebucket.util.OrientationUtils;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.mvp.BucketMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.ui.presenter.BucketPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BucketActivity extends BaseActivity implements
        BucketMVP.View {


    //region View binding
    @BindView(R.id.rootViewMain)
    FrameLayout rootViewMain;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //endregion

    //region View events
    @OnClick(R.id.fab)
    public void clickFab() {
        this.nextCreateTask();
    }
    //endregion

    //region DI
    @Inject
    BucketPresenter presenter;
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

    private BucketAdapter adapter = null;

    /**
     * Allows to start this activity
     * @param _context
     */
    public static void startMe(Context _context) {
        Intent intent = new Intent(_context, BucketActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        _context.startActivity(intent);
    }

    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.injectDependencies();
        this.attachToPresenter();

        this.getExtras(getIntent());
        this.setUpToolbar();

        OrientationUtils.setUpOrientation(getResources().getConfiguration(), this);
    }

    @Override
    protected void onDestroy() {
        this.detachFromPresenter();
        super.onDestroy();
    }

    //endregion


    @Override
    public void onLandscape() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onPortrait() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        OrientationUtils.setUpOrientation(newConfig, this);
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoNetwork() {
        SnackBarUtils.showActionSnackbar(this.rootViewMain,
                getResources().getString(R.string.no_network),
                getResources().getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                    }
                }
        );
    }

    @Override
    public void showMessage(String _message) {
        SnackBarUtils.showSimpleSnackbar(this.rootViewMain, _message);
    }


    @Override
    public void nextCreateTask() {
        CreateTaskActivity.startMe(this);
    }

    @Override
    public void loadData() {
        try {
            // 1. We check that the view is correctly attached to the presenter
            this.presenter.checkViewAttached();

            // 2. We load the data
            this.showLoading();
            this.presenter.loadBucket();
        } catch (BasePresenter.ViewNotAttachedException e) {
            e.printStackTrace();
            this.showMessage(e.getMessage());
        }
    }

    @Override
    public void showBucket(ArrayList<Task> _tasks) {
        if (this.adapter==null) {
            this.adapter = new BucketAdapter(this, _tasks);
            this.recyclerView.setAdapter(this.adapter);
        }
        else {
            this.adapter.setItems(_tasks);
        }
    }

    @Override
    public void showBucketEmpty() {

    }

    @Override
    public void showError() {

    }

}