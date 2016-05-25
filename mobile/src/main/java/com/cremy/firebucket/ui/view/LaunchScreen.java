package com.cremy.firebucket.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * LaunchScreen is just here as a Branded LaunchScreen
 * {@see https://www.google.com/design/spec/patterns/launch-screens.html}
 */
public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finish();

        OnBoardingActivity.startMe(this);
    }
}
