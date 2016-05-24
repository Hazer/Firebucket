package com.cremy.shared.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * Created by remychantenay on 06/10/2015.
 */
public class FirebucketTextInputLayout extends TextInputLayout {
    protected static final String FONT_PATH = "fonts/robotoregular.ttf";

    public FirebucketTextInputLayout(Context context) {
        super(context);
        applyTypeface(context);
    }

    public FirebucketTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyTypeface(context);
    }

    protected void applyTypeface(Context _ctx)
    {
        if (!isInEditMode())
        {
            try {
                // Retrieve the CollapsingTextHelper Field
                final Field cthf = TextInputLayout.class.getDeclaredField("mCollapsingTextHelper");
                cthf.setAccessible(true);

                // Retrieve an instance of CollapsingTextHelper and its TextPaint
                final Object cth = cthf.get(this);
                final Field tpf = cth.getClass().getDeclaredField("mTextPaint");
                tpf.setAccessible(true);

                // Apply your Typeface to the CollapsingTextHelper TextPaint
                ((TextPaint) tpf.get(cth)).setTypeface(Typeface.createFromAsset(_ctx.getAssets(), FONT_PATH));
            } catch (Exception ignored) {
                // Nothing to do
            }
        }
    }
}
