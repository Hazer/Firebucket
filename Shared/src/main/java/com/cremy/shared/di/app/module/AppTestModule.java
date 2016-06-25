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
public class AppTestModule extends AppModule {

    public AppTestModule(App _app) {
        super(_app);
    }

    //region Data
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
}