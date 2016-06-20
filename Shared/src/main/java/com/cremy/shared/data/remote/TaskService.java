package com.cremy.shared.data.remote;

import com.cremy.shared.data.FirebaseRxHandler;
import com.cremy.shared.data.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class TaskService extends BaseFirebaseDatabaseService {

    public DatabaseReference getChildReference() {
        if (this.childReference==null) {
            this.childReference = this.firebaseDatabase.
                    getReference()
                    .child(FIREBASE_CHILD_KEY_USERS)
                    .child(this.firebaseAuth.getCurrentUser().getUid())
                    .child(FIREBASE_CHILD_KEY_TASKS);
        }

        return childReference;
    }

    @Inject
    public TaskService(FirebaseDatabase _firebaseDatabase,
                       FirebaseAuth _firebaseAuth) {
        super(_firebaseDatabase, _firebaseAuth);
    }

    /**
     * Allows to create/write a task in the database
     * @param _task
     */
    public Observable<Void> writeTaskInDatabase(final Task _task) {

        // 1. We push and get the child key
        final String key = this.getChildReference()
                .push().getKey();

        // 2. We add the key as id within the model as well
        _task.setId(key);

        // 3. We now set the new task
/*
        this.getChildReference().child(key)
                .setValue(_task).addOnCompleteListener(_onCompleteListener);
*/

        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                FirebaseRxHandler.assignOnTask(subscriber, getChildReference().child(key)
                        .setValue(_task));
            }
        });
    }
   /* *
     * Allows to remove a given task from the database
     * @param _task
     */
    public void removeTask(Task _task) {

        this.getChildReference()
                .child(_task.getId())
                .removeValue();
    }
    //endregion
}