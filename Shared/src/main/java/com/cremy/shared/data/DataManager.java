package com.cremy.shared.data;

import android.content.Context;

import com.cremy.shared.data.local.TaskServiceLocal;
import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.data.remote.AuthService;
import com.cremy.shared.data.remote.BucketService;
import com.cremy.shared.data.remote.TaskService;
import com.cremy.shared.di.scope.ApplicationScope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by remychantenay on 18/05/2016.
 */
@ApplicationScope
public class DataManager {
    private final static String TAG = "DataManager";

    //region DI
    private final TaskService taskService;
    private final TaskServiceLocal taskServiceLocal;
    private final BucketService bucketService;
    private final AuthService authService;
    private Context appContext;

    @Inject
    public DataManager(TaskService _service,
                       TaskServiceLocal _serviceLocal,
                       BucketService _bucketService,
                       AuthService _authService,
                       Context _context) {
        this.taskService = _service;
        this.taskServiceLocal = _serviceLocal;
        this.bucketService = _bucketService;
        this.authService = _authService;
        this.appContext = _context;
    }
    //endregion

    //region Auth
    public Observable<AuthResult> createUserWithEmailAndPassword(final String _email,
                                               final String _password) {
        return this.authService.createUserWithEmailAndPassword(_email, _password);
    }

    public Observable<AuthResult> signInWithEmailAndPassword(final String _email,
                                                             final String _password) {
        return this.authService.signInWithEmailAndPassword(_email, _password);
    }

    public boolean ifUserExists() {
       return this.authService.ifUserExists();
    }

    public Observable<Bucket> writeUserInDatabase(final String _userId,
                                    final String _name) {
        return this.authService.writeUserInDatabase(_userId, _name);
    }
    //endregion

    //region Task
    public Observable<Void> writeTaskInDatabase(final Task _task) {
        return this.taskService.writeTaskInDatabase(_task);
    }

    public void removeTaskFromDatabase(final Task _task) {
        this.taskService.removeTask(_task);
    }
    //endregion

    //region Bucket
    public void startBucketListening(ValueEventListener _valueEventListener) {
        this.bucketService.startListening(_valueEventListener);
    }

    public void stopBucketListening(ValueEventListener _valueEventListener) {
        this.bucketService.stopListening(_valueEventListener);
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