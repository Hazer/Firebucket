package com.cremy.firebucket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cremy.firebucket.R;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
