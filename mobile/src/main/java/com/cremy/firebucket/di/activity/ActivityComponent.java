package com.cremy.firebucket.di.activity;

import com.cremy.firebucket.ui.view.LoginActivity;
import com.cremy.firebucket.ui.view.MainActivity;
import com.cremy.firebucket.ui.view.RegisterActivity;
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
    void inject(MainActivity view);
    void inject(LoginActivity view);
    void inject(RegisterActivity view);
}