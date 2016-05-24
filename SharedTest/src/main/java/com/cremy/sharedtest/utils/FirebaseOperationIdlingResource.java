package com.cremy.sharedtest.utils;

import android.support.test.espresso.IdlingResource;

/**
 * Espresso IdlingResource to use for every asynchronous firebase operations
 */
public class FirebaseOperationIdlingResource implements IdlingResource {

    private boolean idleNow = true;
    private ResourceCallback callback;
    private String name = null;

    public FirebaseOperationIdlingResource(final String _name) {
        this.name = "FirebaseOperationIdlingResource".concat(_name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void onStart() {
        idleNow = false;
    }

    public void onStop() {
        idleNow = true;
        if (callback != null) {
            callback.onTransitionToIdle();
        }
    }

    @Override
    public boolean isIdleNow() {
        return idleNow;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}