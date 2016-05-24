package com.cremy.firebucket.util;

import android.content.res.Configuration;
import android.util.Log;

import com.cremy.firebucket.mvp.base.view.BaseViewMobile;

/**
 * Created by remychantenay on 02/05/2016.
 */
public final class OrientationUtils {
    private final static String TAG = "OrientationUtils";


    /**
     * Allows to get the configuration change, will trigger:
     * BaseActivityView.onPortrait() or BaseView.onLandscape()
     * @param _config
     * @param _view
     */
    public static void setUpOrientation(Configuration _config,
                                              BaseViewMobile _view) {
        Log.d(TAG, "onConfigurationChanged");
        if (_config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            _view.onLandscape();
        } else if (_config.orientation == Configuration.ORIENTATION_PORTRAIT){
            _view.onPortrait();
        }
    }

}