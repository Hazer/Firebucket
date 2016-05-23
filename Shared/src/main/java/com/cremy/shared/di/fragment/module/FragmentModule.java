package com.cremy.shared.di.fragment.module;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment _fragment) {
        fragment = _fragment;
    }

    @Provides
    Fragment provideFragment() {
        return fragment;
    }
}