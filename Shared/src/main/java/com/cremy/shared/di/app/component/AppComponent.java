package com.cremy.shared.di.app.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.cremy.shared.App;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.remote.AuthService;
import com.cremy.shared.data.remote.BucketService;
import com.cremy.shared.data.remote.RemoteConfigService;
import com.cremy.shared.data.remote.TagListService;
import com.cremy.shared.data.remote.TaskService;
import com.cremy.shared.di.app.module.AppModule;
import com.cremy.shared.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by remychantenay on 23/05/2016.
 */
@ApplicationScope
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {

    //region Injectors
    void inject(App app);
    // TODO: Add presenters here
    //endregion

    //region Providers
    Context provideContext();
    App provideApp();
    SharedPreferences provideSharedPreferences();
    DataManager provideDataManager();

    TaskService provideTaskService();
    AuthService provideAuthService();
    BucketService provideBucketService();
    TagListService provideTagListService();
    RemoteConfigService provideRemoteConfigService();
    //endregion

}
