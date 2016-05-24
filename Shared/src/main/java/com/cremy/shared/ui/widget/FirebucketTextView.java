package com.cremy.shared.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public final class FirebucketTextView extends TextView {

    protected static final String FONT_PATH = "fonts/fabada-regular.ttf";

    private static Typeface TYPEFACE = null;

        public FirebucketTextView(Context context) {
              super(context);
              applyTypeface(context);
        }

	    public FirebucketTextView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        applyTypeface(context);

	    }

	    public FirebucketTextView(Context context, AttributeSet attrs, int defStyle) {
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
