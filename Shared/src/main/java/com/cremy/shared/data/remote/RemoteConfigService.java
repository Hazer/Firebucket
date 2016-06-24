package com.cremy.shared.data.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Inject;

import rx.Single;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class RemoteConfigService extends BaseFirebaseDatabaseService{
    public static final String KEY_IS_DATABASE_WRITE_ACCESSIBLE = "is_database_write_accessible";
    public static final String KEY_INFO_LATEST_APP_VERSION_AVAILABLE = "info_latest_app_version_available";

    public FirebaseRemoteConfig firebaseRemoteConfig;

    @Inject
    public RemoteConfigService(FirebaseDatabase _firebaseDatabase,
                               FirebaseAuth _firebaseAuth,
                               FirebaseRemoteConfig _firebaseRemoteConfig) {
        super(_firebaseDatabase, _firebaseAuth);
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
        return observeSingleValue(this.firebaseRemoteConfig.fetch(cache));
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