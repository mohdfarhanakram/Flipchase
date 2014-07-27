package com.flipchase.android.view.widget;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomFontButton extends Button {

    private String fontName;

    private void setFont(AttributeSet attrs) {
        fontName = attrs.getAttributeValue(null, "font");
    }

    public CustomFontButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont(attrs);
    }

    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(attrs);
    }

    public CustomFontButton(Context context) {
        super(context);
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
