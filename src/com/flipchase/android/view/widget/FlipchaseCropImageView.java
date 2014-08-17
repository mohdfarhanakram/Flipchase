package com.flipchase.android.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.edmodo.cropper.CropImageView;


/**
 * @author Farhan
 *
 */
public class FlipchaseCropImageView extends CropImageView{

	public FlipchaseCropImageView(Context context) {
		super(context);
	}
	
	public FlipchaseCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
	}
	
	public void setImageBitmapWithFrame(Bitmap bitmap, Bitmap frameImage) {
		super.setImageBitmapWithFrame(bitmap, frameImage);
	}
	
	@Override
	public Bitmap getCroppedImage() {

		return super.getCroppedImage();
	}

}

