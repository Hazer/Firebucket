package com.cremy.shared.data;

import com.cremy.shared.BuildConfig;
import com.cremy.shared.data.local.TaskServiceLocal;
import com.cremy.shared.data.remote.TaskService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class allows to get the TaskService
 * Based on Firebase
 */
public class ServiceFactory {


    /**
     * Allows to make the task service
     * @return
     */
    public static TaskService makeService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        TaskService taskService = new TaskService(firebaseDatabase);
        return taskService;
    }

    /**
     * Allows to make the task service
     * @return
     */
    public static TaskServiceLocal makeServiceLocal() {
        TaskServiceLocal taskServiceLocal = new TaskServiceLocal();
        return taskServiceLocal;
    }
}