package com.cremy.shared.di.app.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cremy.shared.App;
import com.cremy.shared.data.ServiceFactory;
import com.cremy.shared.data.local.TaskServiceLocal;
import com.cremy.shared.data.remote.AuthService;
import com.cremy.shared.data.remote.BucketService;
import com.cremy.shared.data.remote.TaskService;
import com.cremy.shared.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by remychantenay on 23/05/2016.
 */
@Module
public class AppModule {
    private App app;

    public AppModule(App _app) {
        this.app = _app;
    }

    @Provides
    @ApplicationScope
    public App provideApplication() {
        return app;
    }

    @Provides
    @ApplicationScope
    public Context provideContext() {
        return app;
    }

    //region Data
    @Provides
    @ApplicationScope
    TaskService provideTaskService() {
        return ServiceFactory.makeTaskService();
    }

    @Provides
    @ApplicationScope
    BucketService provideBucketService() {
        return ServiceFactory.makeBucketService();
    }

    @Provides
    @ApplicationScope
    TaskServiceLocal provideTaskServiceLocal() {
        return ServiceFactory.makeTaskServiceLocal();
    }

    @Provides
    @ApplicationScope
    AuthService provideAuthService() {
        return ServiceFactory.makeAuthService();
    }

    //endregion

    //region SharedPreferences
    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(Context _context) {
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
        return mPrefs;
    }

    @Provides
    @ApplicationScope
    SharedPreferences.Editor provideSharedPreferencesEditor(SharedPreferences _sharedPreferences) {
        return _sharedPreferences.edit();
    }
    //endregion
}
