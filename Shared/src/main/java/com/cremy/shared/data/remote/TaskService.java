package com.cremy.shared.data.remote;

import com.cremy.shared.data.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class TaskService extends BaseFirebaseDatabaseService {

    @Inject
    public TaskService(FirebaseDatabase _firebaseDatabase,
                       FirebaseAuth _firebaseAuth) {
        super(_firebaseDatabase, _firebaseAuth);

        this.childReference = this.firebaseDatabase.
                getReference()
                .child(FIREBASE_CHILD_KEY_USERS)
                .child(this.firebaseAuth.getCurrentUser().getUid())
                .child(FIREBASE_CHILD_KEY_TASKS);
    }

    /**
     * Allows to create/write a task in the database
     * @param _task
     * @param _onCompleteListener
     */
    public void writeTaskInDatabase(Task _task, OnCompleteListener _onCompleteListener) {

        // 1. We push and get the child key
        String key = this.childReference
                .push().getKey();

        // 3. We now set the new task
        this.childReference.child(key)
                .setValue(_task).addOnCompleteListener(_onCompleteListener);
    }
    /**
     * Allows to _remove_ a given task from the database
     * @param _task
     */
   /* public void removeTask(Task _task) {

        // 1. We add the task
        this.firebaseDatabase.getReference()
                .child(FIREBASE_CHILD_KEY_TASK_LIST)
                .child(FIREBASE_CHILD_KEY_TASK)
                .child(_task.getTitle())
                .removeValue();

        // 2. We update the timeLastUpdate
        this.updateTimeLastUpdate(_firebase);

        Log.d(TAG, "Task removed.");
    }*/
    //endregion
}