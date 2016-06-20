package com.cremy.firebucket.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cremy.firebucket.R;
import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.firebucket.ui.adapter.BucketAdapter;
import com.cremy.firebucket.util.OrientationUtils;
import com.cremy.firebucket.util.ui.widget.MaterialDesignFlatButton;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.greenrobotutils.library.ui.recyclerview.RecyclerViewUtils;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.mvp.BucketMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.ui.presenter.BucketPresenter;

import java.util.ArrayList;
import java.util.logging.Logger;

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

    @BindView(R.id.placeholderLayout)
    FrameLayout placeholderLayout;
    @BindView(R.id.placeholderImage)
    ImageView placeholderImage;
    @BindView(R.id.placeholderTitle)
    TextView placeholderTitle;
    @BindView(R.id.placeholderButton)
    MaterialDesignFlatButton placeholderButton;
    //endregion

    //region View events
    @OnClick(R.id.fab)
    public void clickFab() {
        this.nextCreateTask();
    }
    @OnClick(R.id.placeholderButton)
    public void onClicklaceholderButton(View view) {
        this.loadData();
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
        setContentView(R.layout.activity_bucket);
        ButterKnife.bind(this);

        this.injectDependencies();
        this.attachToPresenter();

        this.getExtras(getIntent());
        this.setUpToolbar();

        OrientationUtils.setUpOrientation(getResources().getConfiguration(), this);

        this.loadData();

        this.initRecyclerView();
    }

    @Override
    protected void onDestroy() {
        this.detachFromPresenter();
        super.onDestroy();
    }

    //endregion


    @Override
    public void initRecyclerView() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                if (adapter != null) {
                    if (adapter.isSwipeable(viewHolder.getAdapterPosition())) {
                        presenter.removeTask(adapter.getItem(viewHolder.getAdapterPosition()));
                    }
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (adapter != null) {
                        if (adapter.isSwipeable(viewHolder.getAdapterPosition())) {
                            // Get RecyclerView item from the ViewHolder
                            View itemView = viewHolder.itemView;

                            // 1. We draw a rectangle with a filled color
                            Paint p = RecyclerViewUtils.drawRectOnSwipe(itemView,
                                    c,
                                    adapter.getItem(viewHolder.getAdapterPosition()).getPriority().getColor(BucketActivity.this),
                                    dX);

                            if (dX > 0) // If RIGHT swiped
                            {
/*                                // 2. And and this color rectangle, draw a resource bitmap
                                RecyclerViewUtils.drawBitmap(itemView,
                                        c,
                                        getResources(),
                                        R.drawable.icon_seen_white,
                                        p);*/
                            }

                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        }
                    }
                }
            }
        };

        RecyclerViewUtils.setItemTouchCallback(this.recyclerView, simpleItemTouchCallback);
    }

    @Override
    public void onLandscape() {
/*        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));*/
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        this.recyclerView.setVisibility(View.VISIBLE);
        this.placeholderLayout.setVisibility(View.GONE);
    }

    @Override
    public void showBucketEmpty() {
        this.hideLoading();
        this.recyclerView.setVisibility(View.GONE);
        this.placeholderLayout.setVisibility(View.VISIBLE);
        this.placeholderImage.setImageDrawable(getResources().getDrawable(R.drawable.image_bucket_empty));
        this.placeholderButton.setText(getResources().getString(R.string.bucket_activity_placeholder_button_text));
        this.placeholderTitle.setText(getResources().getString(R.string.bucket_activity_placeholder_title_text_empty));
    }

    @Override
    public void showError() {
        this.hideLoading();
        this.recyclerView.setVisibility(View.GONE);
        this.placeholderLayout.setVisibility(View.VISIBLE);
        this.placeholderImage.setImageDrawable(getResources().getDrawable(R.drawable.image_bucket_error));
        this.placeholderButton.setText(getResources().getString(R.string.bucket_activity_placeholder_button_text));
        this.placeholderTitle.setText(getResources().getString(R.string.bucket_activity_placeholder_title_text_error));
    }

}