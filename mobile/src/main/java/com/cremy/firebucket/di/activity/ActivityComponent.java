package com.cremy.firebucket.di.activity;

import com.cremy.firebucket.ui.view.BucketActivity;
import com.cremy.firebucket.ui.view.CreateTaskActivity;
import com.cremy.firebucket.ui.view.LaunchScreen;
import com.cremy.firebucket.ui.view.LoginActivity;
import com.cremy.firebucket.ui.view.OnBoardingActivity;
import com.cremy.firebucket.ui.view.RegisterActivity;
import com.cremy.shared.di.activity.module.ActivityModule;
import com.cremy.shared.di.app.component.AppComponent;
import com.cremy.shared.di.scope.ActivityScope;
import com.cremy.shared.ui.presenter.CreateTaskPresenter;

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
        Activities must be referenced below
     */
    void inject(BucketActivity view);
    void inject(LoginActivity view);
    void inject(RegisterActivity view);
    void inject(OnBoardingActivity view);
    void inject(CreateTaskActivity view);
    void inject(LaunchScreen view);
}