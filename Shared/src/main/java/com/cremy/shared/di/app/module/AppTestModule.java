package com.cremy.shared.di.app.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cremy.shared.App;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.local.TORENAMEServiceLocal;
import com.cremy.shared.data.remote.TORENAMEService;
import com.cremy.shared.di.scope.ApplicationScope;

import org.mockito.Mockito;

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
    @Provides
    @ApplicationScope
    TORENAMEService provideTORENAMEService() {
        return Mockito.mock(TORENAMEService.class);
    }


    @Provides
    @ApplicationScope
    TORENAMEServiceLocal provideTORENAMEServiceLocal() {
        return Mockito.mock(TORENAMEServiceLocal.class);
    }

    @Provides
    @ApplicationScope
    public DataManager provideDataHelper(TORENAMEService _toRenameService,
                                         TORENAMEServiceLocal _toRenameServiceLocal,
                                         Context _context) {
        return Mockito.mock(DataManager.class);
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