package com.cremy.shared.utils;

import com.cremy.shared.di.scope.ApplicationScope;
import com.google.firebase.crash.FirebaseCrash;

/**
 * This class allow us to gather all the reports in the same place, so it would be easy to switch
 * to something different than Firebase Crash Report
 * Created by remychantenay on 24/05/2016.
 */
@ApplicationScope
public class CrashReporter {

    public static void log(final String _msg) {
        FirebaseCrash.log(_msg);
    }
}
