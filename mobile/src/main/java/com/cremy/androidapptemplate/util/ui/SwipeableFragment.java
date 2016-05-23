package com.cremy.androidapptemplate.util.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.cremy.androidapptemplate.mvp.base.view.BaseFragment;

/**
 * Created by Remy on 02/09/2014.
 */
public abstract class SwipeableFragment extends BaseFragment {

    // SwipeRefreshLayout things
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected static final int SWIPE_REFRESH_CHECK_DELAY = 1000; // In ms
    protected Handler handler = new Handler();
    protected boolean isRefreshing = false;
    public final Runnable refreshingRunnable = new Runnable() {
        public void run() {
            try {
                if(SwipeableFragment.this.isRefreshing) {
                    SwipeableFragment.this.handler.postDelayed(this, 1000L);
                    SwipeableFragment.this.stopRefreshing();
                } else {
                    SwipeableFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                }
            } catch (Exception var2) {
                var2.printStackTrace();
            }

        }
    };

    abstract public void setUpSwipeRefreshLayout(View _rootView);


    /**
     * Allows to stop the refreshing bar effect
     *
     */
    public void stopRefreshing() {
        isRefreshing = false;
    }

    /**
     * Allows to start the refreshing bar effect
     */
    public void startRefreshing() {
        isRefreshing = true;
    }


    /**
     * Allows to set-up the refresh bar colors
     * @param _color1
     * @param _color2
     * @param _color3
     * @param _color4
     */
    public void setRefreshBarColors(int _color1, int _color2, int _color3, int _color4){
        // We set the different colors for the animation
        mSwipeRefreshLayout.setColorSchemeResources(_color1, _color2,
                _color3, _color4);
    }

    /**
     * Allows to force the view to refresh itself
     */
    public void forceStartRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (!isRefreshing) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }
        });
    }


    public void forceStopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

}