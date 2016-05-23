package com.cremy.shared.data.remote;

import com.cremy.shared.BuildConfig;
import com.cremy.shared.data.model.TORENAMEMODEL;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class must be inherited from all the other service providers
 * Based on Retrofit
 */
public class TORENAMEServiceFactory extends ServiceFactory {


    /**
     * Allows to make the chart service _without_ any HTTP client given
     * @return
     */
    public static TORENAMEService makeService() {
        OkHttpClient okHttpClient = makeOkHttpClient(getLoggingInterceptor());
        return makeService(okHttpClient);
    }


    /**
     * Allows to make the chart service _with_ an HTTP client given
     *
     * @param okHttpClient
     * @return
     */
    public static TORENAMEService makeService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL_BASE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit.create(TORENAMEService.class);
    }

}