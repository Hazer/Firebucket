package com.cremy.androidapptemplate.mvp.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.cremy.androidapptemplate.di.fragment.DaggerFragmentComponent;
import com.cremy.androidapptemplate.di.fragment.FragmentComponent;
import com.cremy.shared.App;
import com.cremy.shared.di.fragment.module.FragmentModule;

/**
 * This class must implement {@link BaseViewMobile}
 * Created by remychantenay on 18/05/2016.
 */
public abstract class BaseFragment
        extends Fragment
        implements BaseViewMobile {

    //region DI
    private FragmentComponent fragmentComponent;
    public FragmentComponent fragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(this))
                    .appComponent(App.get(getActivity()).getComponent())
                    .build();
        }
        return fragmentComponent;
    }
    //endregion

    //region Data
    public abstract void loadData();
    //endregion

    //region Other
    public FragmentActivity getFragmentActivity() {
        return getActivity();
    }
    public Fragment getFragment() {
        return this;
    }
    public abstract void getArgs(Bundle _bundle);
    //endregion

    //region Context
    public Context getContext() {
        return getActivity();
    }
    //endregion
}