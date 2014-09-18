package com.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.flipchase.android.R;
import com.flipchase.android.view.activity.AlertsActivityForExpiredCatalogues;
import com.flipchase.android.view.activity.AlertsActivityForNewCatalogues;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString(), "");
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString(), "");
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				for (int i = 0; i < 3; i++) {
					Log.i(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

				sendNotification("" + extras.get(Config.MESSAGE_TITLE), "" + extras.get(Config.MESSAGE_KEY));
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String title, String msg) {
		Log.d(TAG, "Preparing to send notification...: " + msg);
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent alertActivity = null;
		if(title.equalsIgnoreCase("NEW_CATALOGUES_ARRIVED_GCM_KEY")) {
			alertActivity = new Intent(this,
					AlertsActivityForNewCatalogues.class);
			alertActivity.putExtra("alerts", msg);
		} else if(title.equalsIgnoreCase("EXPIRED_CATALOGUES_GCM_KEY")) {
			alertActivity = new Intent(this,
					AlertsActivityForExpiredCatalogues.class);
			alertActivity.putExtra("alerts", msg);
		}
		  
    	
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				alertActivity, 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.gcm_cloud)
				.setContentTitle("Flipchase GCM Notification")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(title))
				.setContentText(title);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		Log.d(TAG, "Notification sent successfully.");
	}
}
