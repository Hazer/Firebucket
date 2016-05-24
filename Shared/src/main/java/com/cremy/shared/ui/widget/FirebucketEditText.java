package com.cremy.shared.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * IMPORTANT : Right now, using this widget directly from the layout don't allow AppCompat to give
 * the colorAccent tint to widgets for pre-lollipop devices, so the best is to get the typeface
 * dynamically
 */
public final class FirebucketEditText extends CustomEditText {

    protected static final String FONT_PATH = "fonts/robotoregular.ttf";

    private static Typeface TYPEFACE = null;

        public FirebucketEditText(Context context) {
              super(context);
              applyTypeface(context);
        }

	    public FirebucketEditText(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        applyTypeface(context);

	    }

	    public FirebucketEditText(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        applyTypeface(context);
	     	
	    }
	    
	    
	    protected void applyTypeface(Context _ctx)
	    {
	    	if (!isInEditMode())
	        {
	        	 this.setTypeface(getTypeface(_ctx));
	        }
	    }


	    protected Typeface getTypeface(Context _ctx)
	    {
	    	if (TYPEFACE == null)
	    	{
                TYPEFACE = Typeface.createFromAsset(_ctx.getAssets(), FONT_PATH);
	    	}
	    	return TYPEFACE;
	    }

        public static Typeface getTypefaceStatic(Context _ctx)
        {
            if (TYPEFACE == null)
            {
                TYPEFACE = Typeface.createFromAsset(_ctx.getAssets(), FONT_PATH);
            }
            return TYPEFACE;
        }

}
