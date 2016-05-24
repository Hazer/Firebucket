package com.cremy.shared.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by remychantenay on 25/11/2015.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context) {
        super(context);
    }


    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void setCursorPosition(EditText _editText, int _position) {
        _editText.setSelection(_position);
    }

    public static void setCursorPositionToEnd(EditText _editText) {
        _editText.setSelection(_editText.getText().length());
    }
}
