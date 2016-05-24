package com.cremy.shared.data;

import android.content.Context;

import com.cremy.shared.data.local.TaskServiceLocal;
import com.cremy.shared.data.remote.AuthService;
import com.cremy.shared.data.remote.TaskService;
import com.cremy.shared.di.scope.ApplicationScope;
import com.google.android.gms.tasks.OnCompleteListener;

import javax.inject.Inject;

/**
 * Created by remychantenay on 18/05/2016.
 */
@ApplicationScope
public class DataManager {
    private final static String TAG = "DataManager";

    //region DI
    private final TaskService taskService;
    private final TaskServiceLocal taskServiceLocal;
    private final AuthService authService;
    private Context appContext;

    @Inject
    public DataManager(TaskService _service,
                       TaskServiceLocal _serviceLocal,
                       AuthService _authService,
                       Context _context) {
        this.taskService = _service;
        this.taskServiceLocal = _serviceLocal;
        this.authService = _authService;
        this.appContext = _context;
    }
    //endregion

    //region Auth
    public void createUserWithEmailAndPassword(final String _email,
                                               final String _password,
                                               OnCompleteListener _onCompleteListener) {
        this.authService.createUserWithEmailAndPassword(_email, _password, _onCompleteListener);
    }

    public boolean ifUserExists() {
       return this.authService.ifUserExists();
    }

    public void writeUserInDatabase(final String _userId,
                                    final String _name) {
        this.authService.writeUserInDatabase(_userId, _name);
    }
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