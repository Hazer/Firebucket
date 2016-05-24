package com.cremy.firebucket.di.fragment;

import com.cremy.shared.di.app.component.AppComponent;
import com.cremy.shared.di.fragment.module.FragmentModule;
import com.cremy.shared.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by remychantenay on 18/05/2016.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = FragmentModule.class
)
public interface FragmentComponent {

    /*
    !!! IMPORTANT !!!
    Every fragment must be referenced below
    */
/*    void inject(RecentsFragment view);*/
}