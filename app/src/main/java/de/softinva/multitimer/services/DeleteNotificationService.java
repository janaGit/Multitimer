package de.softinva.multitimer.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import de.softinva.multitimer.utility.AppLogger;


public class DeleteNotificationService extends IntentService {
    public static final String ACTION_DELETE_NOTIFICATION = "de.softinva.multitimer.DeleteNotificationService.DeleteNotofication";
    public static final String NOTIFICATION_ID = "de.softinva.multitimer.DeleteNotificationService.NotificationID";
    private AppLogger logger = new AppLogger(this);

    public static PendingIntent returnPendingIntent(int notificationId, Context context, int requestCode) {
        Intent intent = new Intent(context, DeleteNotificationService.class);
        intent.setAction(DeleteNotificationService.ACTION_DELETE_NOTIFICATION);
        intent.putExtra(DeleteNotificationService.NOTIFICATION_ID, notificationId);

        return PendingIntent.getService(context, requestCode, intent, 0);
    }

    public DeleteNotificationService() {
        super("DeleteNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, -1);
        if (notificationId != -1) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.cancel(notificationId);
            } else {
                logger.error("notificationManager is null!");
            }

        } else {
            throw new Error("notificationId is null!");
        }
    }
}
