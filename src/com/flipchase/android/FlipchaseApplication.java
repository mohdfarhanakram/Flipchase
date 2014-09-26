/**
 * 
 */
package com.flipchase.android;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.flipchase.android.cache.BitmapLruCache;

/**
 * @author FARHAN
 *
 */

public class FlipchaseApplication extends Application {

    private BitmapLruCache mCache;

    @Override
    public void onCreate() {
        super.onCreate();

        File cacheLocation;

        // If we have external storage use it for the disk cache. Otherwise we use
        // the cache dir
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cacheLocation = new File(
                    Environment.getExternalStorageDirectory() + "/Flipchase-BitmapCache");
        } else {
            cacheLocation = new File(getFilesDir() + "/Flipchase-BitmapCache");
        }
        cacheLocation.mkdirs();

        BitmapLruCache.Builder builder = new BitmapLruCache.Builder(this);
        builder.setMemoryCacheEnabled(true).setMemoryCacheMaxSizeUsingHeapSize();
        builder.setDiskCacheEnabled(true).setDiskCacheLocation(cacheLocation);

        mCache = builder.build();
    }

    public BitmapLruCache getBitmapCache() {
        return mCache;
    }

    public static FlipchaseApplication getApplication(Context context) {
        return (FlipchaseApplication) context.getApplicationContext();
    }
}