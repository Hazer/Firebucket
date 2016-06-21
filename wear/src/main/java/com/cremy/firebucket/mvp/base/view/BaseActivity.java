package com.cremy.firebucket.mvp.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cremy.firebucket.di.activity.ActivityComponent;
import com.cremy.firebucket.di.activity.DaggerActivityComponent;
import com.cremy.shared.App;
import com.cremy.shared.di.activity.module.ActivityModule;

/**
 * This class must implement {@link BaseViewWear}
 * Created by remychantenay on 18/05/2016.
 */
public abstract class BaseActivity
        extends Activity
        implements BaseViewWear {

    //region DI
    private ActivityComponent activityComponent;
    public ActivityComponent activityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .appComponent(App.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }
    //endregion

    //region Other
    public abstract void getExtras(Intent _intent);
    public abstract void closeActivity();
    public abstract Fragment getAttachedFragment(int _id);

    public abstract void showNoNetwork();
    //endregion

    //region Context
    public Context getContext() {
        return this;
    }
    //endregion
}