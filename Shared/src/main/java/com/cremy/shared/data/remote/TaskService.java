package com.cremy.shared.data.remote;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class TaskService extends BaseFirebaseDatabaseService {


    public TaskService(FirebaseDatabase _firebaseDatabase) {
        super(_firebaseDatabase);
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