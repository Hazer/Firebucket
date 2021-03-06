package com.cremy.firebucket.di.activity;

import com.cremy.shared.di.activity.module.ActivityModule;
import com.cremy.shared.di.app.component.AppComponent;
import com.cremy.shared.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by remychantenay on 18/05/2016.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {

    /*
        !!! IMPORTANT !!!
        Every activity must be referenced below
     */
/*    void inject(BucketActivity view);
    void inject(CreateTaskActivity view);*/
}