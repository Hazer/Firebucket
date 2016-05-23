package com.cremy.shared.di.activity.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity _activity) {
        activity = _activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }
}