package com.cremy.shared.data;

import android.content.Context;

import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.TagList;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.data.remote.AuthService;
import com.cremy.shared.data.remote.BucketService;
import com.cremy.shared.data.remote.RemoteConfigService;
import com.cremy.shared.data.remote.TagListService;
import com.cremy.shared.data.remote.TaskService;
import com.cremy.shared.di.scope.ApplicationScope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;

/**
 * Created by remychantenay on 18/05/2016.
 */
@ApplicationScope
public class DataManager {
    private final static String TAG = "DataManager";

    //region DI
    private final TaskService taskService;
    private final BucketService bucketService;
    private final AuthService authService;
    private final RemoteConfigService remoteConfigService;
    private final TagListService tagListService;
    private Context appContext;

    @Inject
    public DataManager(TaskService _service,
                       BucketService _bucketService,
                       AuthService _authService,
                       RemoteConfigService _remoteConfigService,
                       TagListService _tagListService,
                       Context _context) {
        this.taskService = _service;
        this.bucketService = _bucketService;
        this.authService = _authService;
        this.remoteConfigService = _remoteConfigService;
        this.tagListService = _tagListService;
        this.appContext = _context;
    }
    //endregion

    //region Auth
    public Single<AuthResult> createUserWithEmailAndPassword(final String _email,
                                               final String _password) {
        return this.authService.createUserWithEmailAndPassword(_email, _password);
    }

    public Single<AuthResult> signInWithEmailAndPassword(final String _email,
                                                         final String _password) {
        return this.authService.signInWithEmailAndPassword(_email, _password);
    }

    public boolean ifUserExists() {
       return this.authService.ifUserExists();
    }

    public Single<Void> writeUserInDatabase(final String _userId,
                                    final String _name) {
        return this.authService.writeUserInDatabase(_userId, _name);
    }
    //endregion

    //region Task
    public Single<Void> writeTaskInDatabase(final Task _task) {
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

    //region Remote Config
    public Single<Void> fetchRemoteConfigValues() {
        return this.remoteConfigService.fetch();
    }

    public void activateFetched() {
        this.remoteConfigService.activateFetched();
    }

    public boolean getRemoteConfigBooleanValue(final String _key) {
        return this.remoteConfigService.getBoolean(_key);
    }

    public String getRemoteConfigStringValue(final String _key) {
        return this.remoteConfigService.getString(_key);
    }

    public Double getRemoteConfigDoubleValue(final String _key) {
        return this.remoteConfigService.getDouble(_key);
    }
    //endregion

    //region Tags
    public Single<TagList> getTagList() {
        return this.tagListService.getTagList();
    }
    //endregion

}