package com.cremy.shared.data;

import com.cremy.shared.data.local.TaskServiceLocal;
import com.cremy.shared.data.remote.AuthService;
import com.cremy.shared.data.remote.TaskService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class allows to get the TaskService
 * Based on Firebase
 */
public class ServiceFactory {

    //region AuthServices
    /**
     * Allows to make the _auth_ service
     * @return
     */
    public static AuthService makeAuthService() {

        // 1. We get the database instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        // 2. We get the auth instance
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        AuthService authService = new AuthService(firebaseDatabase, firebaseAuth);
        return authService;
    }
    //endregion

    //region TaskServices
    /**
     * Allows to make the task service
     * @return
     */
    public static TaskService makeTaskService() {

        // 1. We get the database instance
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        TaskService taskService = new TaskService(firebaseDatabase);
        return taskService;
    }

    /**
     * Allows to make the task service
     * @return
     */
    public static TaskServiceLocal makeTaskServiceLocal() {
        TaskServiceLocal taskServiceLocal = new TaskServiceLocal();
        return taskServiceLocal;
    }
    //endregion
}