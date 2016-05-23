package com.cremy.shared.di.app.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cremy.shared.App;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.local.TORENAMEServiceLocal;
import com.cremy.shared.data.remote.TORENAMEService;
import com.cremy.shared.data.remote.TORENAMEServiceFactory;
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
    TORENAMEService provideTORENAMEService() {
        return TORENAMEServiceFactory.makeService();
    }

    @Provides
    @ApplicationScope
    TORENAMEServiceLocal provideTORENAMEServiceLocal() {
        return new TORENAMEServiceLocal();
    }

    @Provides
    @ApplicationScope
    public DataManager provideDataHelper(TORENAMEService _TORENAMEService,
                                         TORENAMEServiceLocal _TORENAMEServiceLocal,
                                         Context _context) {
        return new DataManager(_TORENAMEService,
                _TORENAMEServiceLocal,
                _context);
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
