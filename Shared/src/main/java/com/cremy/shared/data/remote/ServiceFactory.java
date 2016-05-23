package com.cremy.shared.data.remote;

import com.cremy.shared.BuildConfig;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class must be inherited from all the other service providers
 * Based on Retrofit
 */
public class ServiceFactory {

    /**
     * Allows to get the HttpLoggingInterceptor (to use with okhttp3)
     * @return
     */
    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.LOG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }


    /**
     * Allows to add make the okHttp client
     * @param httpLoggingInterceptor
     * @return
     */
    public static OkHttpClient makeOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }



}