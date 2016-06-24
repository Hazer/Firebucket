package com.cremy.shared.data.remote;

import com.cremy.shared.data.FirebaseRxSingle;
import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class RemoteConfigService {
    public static final String KEY_IS_DATABASE_WRITE_ACCESSIBLE = "is_database_write_accessible";
    public static final String KEY_INFO_LATEST_APP_VERSION_AVAILABLE = "info_latest_app_version_available";

    public FirebaseRemoteConfig firebaseRemoteConfig;

    @Inject
    public RemoteConfigService(FirebaseRemoteConfig _firebaseRemoteConfig) {
        this.firebaseRemoteConfig = _firebaseRemoteConfig;
    }

    /**
     * Allows to fetch the RemoteConfig remote values
     */
    public Single<Void> fetch() {
        int cache = 0;
        if (!this.firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cache = 3600;
        }
        return FirebaseRxSingle.getSingle(this.firebaseRemoteConfig.fetch(cache));
    }

    /**
     * Allows to activate the fetched values
     */
    public void activateFetched() {
        this.firebaseRemoteConfig.activateFetched();
    }


    public boolean getBoolean(final String _key) {
        return this.firebaseRemoteConfig.getBoolean(_key);
    }

    public String getString(final String _key) {
        return this.firebaseRemoteConfig.getString(_key);
    }

    public Double getDouble(final String _key) {
        return this.firebaseRemoteConfig.getDouble(_key);
    }

}