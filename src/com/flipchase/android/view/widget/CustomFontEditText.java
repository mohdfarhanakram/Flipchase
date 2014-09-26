package com.flipchase.android.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author m.farhan
 *
 */
public class CustomFontEditText extends EditText{

    private String fontName;

    private void setFont(AttributeSet attrs) {
        fontName = "Roboto";
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont(attrs);
    }

    public void setTextColor(int color) {
        try {
            super.setTextColor(getResources().getColor(color));
        } catch (Exception ex) {
            super.setTextColor(color);
        }
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(attrs);
    }

    public CustomFontEditText(Context context) {
        super(context);
        fontName = "hel";
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        if (!isInEditMode()) {
            if (CustomTypeFace.customTypeFace == null) {
                CustomTypeFace.customTypeFace = new CustomTypeFace(getContext());
            }

            if (style == Typeface.BOLD) {
                super.setTypeface(CustomTypeFace.customTypeFace.bold);
            } else {
                super.setTypeface(CustomTypeFace.customTypeFace.normal);
            }
        }
    }
}


