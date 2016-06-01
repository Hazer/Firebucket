package com.cremy.firebucket.util.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cremy.firebucket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MaterialDesignFlatButton extends FrameLayout {

    @BindView(R.id.materialDesignFlatButton)
    FrameLayout materialDesignFlatButton;
    @BindView(R.id.materialDesignFlatButtonText)
    TextView materialDesignFlatButtonText;

    public MaterialDesignFlatButton(Context context) {
        super(context);
        init();
    }

    public MaterialDesignFlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialDesignFlatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialDesignFlatButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_material_design_flat_button, this, true);
        ButterKnife.bind(this);
    }


    /**
     * Allows to set the button text
     * @param _text
     */
    public void setText(String _text) {
        this.materialDesignFlatButtonText.setText(_text);
    }


}