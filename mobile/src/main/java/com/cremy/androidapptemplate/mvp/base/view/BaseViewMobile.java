package com.cremy.androidapptemplate.mvp.base.view;

import com.cremy.shared.mvp.base.view.BaseView;

/**
 * This interface must extend {@link BaseView}
 * Created by remychantenay on 23/05/2016.
 */
public interface BaseViewMobile extends BaseView {

    //region Orientation
    void onLandscape();
    void onPortrait();
    //endregion

}
