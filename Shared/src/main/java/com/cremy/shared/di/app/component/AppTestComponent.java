package com.cremy.shared.di.app.component;

import com.cremy.shared.di.app.module.AppTestModule;
import com.cremy.shared.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by remychantenay on 23/05/2016.
 */
@ApplicationScope
@Component(modules = AppTestModule.class)
public interface AppTestComponent extends AppComponent {
}
