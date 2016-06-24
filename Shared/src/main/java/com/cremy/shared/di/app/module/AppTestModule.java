package com.cremy.shared.di.app.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cremy.shared.App;
import com.cremy.shared.BuildConfig;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.remote.AuthService;
import com.cremy.shared.di.scope.ApplicationScope;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

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
    public FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        return firebaseDatabase;
    }

    @Provides
    @ApplicationScope
    public FirebaseAuth provideFirebaseAuth() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    @Provides
    @ApplicationScope
    public FirebaseRemoteConfig provideFirebaseRemoteConfig() {
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        return firebaseRemoteConfig;
    }

    @Provides
    @ApplicationScope
    AuthService provideAuthService() {
        return  Mockito.mock(AuthService.class);
    }

    @Provides
    @ApplicationScope
    public DataManager provideDataHelper() {
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