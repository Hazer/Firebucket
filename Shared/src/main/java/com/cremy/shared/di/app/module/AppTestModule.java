package com.cremy.shared.di.app.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cremy.shared.App;
import com.cremy.shared.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class AppTestModule {
    private final App app;

    public AppTestModule(App _app) {
        app = _app;
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
/*    @Provides
    @ApplicationScope
    RecentService provideRecentService() {
        return Mockito.mock(RecentService.class);
    }


    @Provides
    @ApplicationScope
    RecentServiceLocal provideRecentServiceLocal() {
        return Mockito.mock(RecentServiceLocal.class);
    }

    @Provides
    @ApplicationScope
    public DataHelper provideDataHelper(RecentService _recentService,
                                        RecentServiceLocal _recentServiceLocal,
                                        Context _context) {
        return Mockito.mock(DataHelper.class);
    }*/
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