package com.cremy.shared.di.app.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cremy.shared.App;
import com.cremy.shared.BuildConfig;
import com.cremy.shared.R;
import com.cremy.shared.di.scope.ApplicationScope;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

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
                // The cache will refresh more frequently in DEBUG mode
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        return firebaseRemoteConfig;
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
