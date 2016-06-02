package com.cremy.shared.data.local;

import javax.inject.Inject;

/**
 * This local service ensure a fallback if the user has no connectivity
 * allowing to get local stored data
 * Created by remychantenay on 18/05/2016.
 */

public class TaskServiceLocal {

    @Inject
    public TaskServiceLocal() {

    }

/*    public Single<Recents> getRecentMusic(Context _context) {
        Recents recents = (Recents) GSONBaseModel.load(_context,
                Recents.TAG,
                Recents.getType());

        return Single.just(recents);
    }*/
}