package com.cremy.shared;

import android.app.Application;
import android.content.Context;

import com.cremy.shared.di.app.component.AppComponent;
import com.cremy.shared.di.app.component.DaggerAppComponent;
import com.cremy.shared.di.app.module.AppModule;

/**
 * Created by remychantenay on 23/05/2016.
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();


    //region AppComponent
    private AppComponent component;
    public AppComponent getComponent() {
        return component;
    }
    private void setAppComponent() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    // Needed to replace the component with a test specific one
    // !!! For testing purpose !!!
    public void setComponent(AppComponent applicationComponent) {
        component = applicationComponent;
    }
    //endregion


    @Override
    public void onCreate() {
        super.onCreate();
        this.setAppComponent();
    }

    /**
     * Allows to get the Application class instance
     * @param context
     * @return
     */
    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }
}