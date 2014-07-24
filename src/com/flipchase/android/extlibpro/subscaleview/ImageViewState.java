package com.flipchase.android.extlibpro.subscaleview;

import java.io.Serializable;

import android.graphics.PointF;

/**
 * Wraps the scale, center and orientation of a displayed image for easy restoration on screen rotate.
 */
public class ImageViewState implements Serializable {

	private static final long serialVersionUID = 1L;

	private float scale;

    private float centerX;

    private float centerY;

    private int orientation;

    public ImageViewState(float scale, PointF center, int orientation) {
        this.scale = scale;
        this.centerX = center.x;
        this.centerY = center.y;
        this.orientation = orientation;
    }

    public float getScale() {
        return scale;
    }

    public PointF getCenter() {
        return new PointF(centerX, centerY);
    }

    public int getOrientation() {
        return orientation;
    }

}
