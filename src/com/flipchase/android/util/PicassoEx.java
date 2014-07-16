/**
 * 
 */
package com.flipchase.android.util;

import android.content.Context;

import com.squareup.picasso.Picasso;

/**
 * @author m.farhan
 *
 */
public class PicassoEx {
    private static Picasso mPicasso;

    public static Picasso getPicasso(Context context) {
        if (null == mPicasso) {
         //   Picasso.Builder builder = new Picasso.Builder(context);
          //  builder.executor(PicassoThreadExecutor.getThreadPoolExecutor());
          //  mPicasso = builder.build();
            mPicasso=Picasso.with(context);

        }
        return mPicasso;
    }

}
