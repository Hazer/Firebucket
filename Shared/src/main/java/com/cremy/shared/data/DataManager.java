package com.cremy.shared.data;

import android.content.Context;

import com.cremy.shared.data.local.TORENAMEServiceLocal;
import com.cremy.shared.data.remote.TORENAMEService;
import com.cremy.shared.di.scope.ApplicationScope;

import javax.inject.Inject;

/**
 * Created by remychantenay on 18/05/2016.
 */
@ApplicationScope
public final class DataManager {
    private final static String TAG = "DataManager";

    //region DI
    private final TORENAMEService torenameService;
    private final TORENAMEServiceLocal torenameServiceLocal;
    private Context appContext;

    @Inject
    public DataManager(TORENAMEService _service,
                       TORENAMEServiceLocal _serviceLocal,
                       Context _context) {
        this.torenameService = _service;
        this.torenameServiceLocal = _serviceLocal;
        this.appContext = _context;
    }
    //endregion

    //region Recents call
/*    public Single<Recents> getRecentMusic() {
        if (NetworkUtils.isNetworkEnabled(this.appContext)) {
            return this.recentService.getRecentMusic();
        }
        else {
            return this.recentServiceLocal.getRecentMusic(this.appContext);
        }

    }*/
    //endregion

    //region Local
    /**
     * Allows to save the recents locally
     * @param _context
     * @param _recents
     */
/*    public void setRecentMusicLocal(Context _context, Recents _recents) {
        GSONBaseModel.saveAsync(_context, Recents.TAG, Recents.getType(), _recents);
    }*/
    //endregion

}