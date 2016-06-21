package com.cremy.firebucket.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.model.Bucket;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * LaunchScreen is just here as a Branded LaunchScreen
 * {@see https://www.google.com/design/spec/patterns/launch-screens.html}
 */
public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finish();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            BucketActivity.startMe(this);
        } else {
            OnBoardingActivity.startMe(this);
        }
    }
}
