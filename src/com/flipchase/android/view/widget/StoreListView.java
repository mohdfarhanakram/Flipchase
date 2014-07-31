package com.flipchase.android.view.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class StoreListView extends ListView {
	
    private OnPointerMoveDirectionListener mPointerMoveMotionListener;
    
    public StoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    float prevY=0;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        String DEBUG_TAG="onTouchEvent";


       /* switch(action) {
            case (MotionEvent.ACTION_DOWN) :

                prevY=ev.getY();
                break;
            case (MotionEvent.ACTION_MOVE) :

                if(ev.getY()-prevY<0)
                    mPointerMoveMotionListener.onMove(OnPointerMoveDirectionListener.VERTICAL_DOWN);
                else
                    mPointerMoveMotionListener.onMove(OnPointerMoveDirectionListener.VERTICAL_UP);

                break;
            case (MotionEvent.ACTION_UP) :

                prevY=0;
                break;

        }*/
        return super.onTouchEvent(ev);
    }

    public void setPointerMoveMotionListener(OnPointerMoveDirectionListener mFlingListener) {
        this.mPointerMoveMotionListener = mFlingListener;
    }

    public interface OnPointerMoveDirectionListener
    {
        int VERTICAL_UP=1;
        int VERTICAL_DOWN=2;
        void onMove(int direction);
    }
}
