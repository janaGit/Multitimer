package de.softinva.multitimer.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.MainActivity;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.services.DeleteNotificationService;
import de.softinva.multitimer.utility.ImageSize;
import de.softinva.multitimer.utility.UtilityMethods;


public class TimerFinishedNotification {
    public static final String CHANNEL_ID = "TIMER_FINISHED";
    private static final int REQUESTCODE_CONENT_INTENT = 1;
    private Context context;
    private Service service;
    private Timer timer;
    private int notificationId;

    public TimerFinishedNotification(Service service) {
        this.service = service;
        context = service.getApplicationContext();
    }

    public void notifyTimerFinished(Timer timer, int notificationId) {
        this.timer = timer;
        this.notificationId = notificationId;
        if (timer instanceof DetailedTimer) {
            executeCodeForDetailedTimer(timer);
        } else {
            executeCode(timer.getTitle());
        }

    }

    private Bitmap getBitmap(Context context, Timer timer) {
        String imageName = null;
        if (timer instanceof DetailedTimer) {
            imageName = ((DetailedTimer) timer).getImageName();
            return UtilityMethods.getBitmap(context, imageName, ImageSize.thumbnail);
        }
        return UtilityMethods.getBitmapFromVectorDrawable(context, R.drawable.ic_launcher_foreground, 500, 500);

    }

    private void executeCodeForDetailedTimer(Timer timer) {
        LiveData<TimerGroup> groupId$ = TimerRepository.getInstance(service.getApplication()).getTimerGroup(((DetailedTimer) timer).getGroupId());
        Observer<TimerGroup> observer = new Observer<TimerGroup>() {
            @Override
            public void onChanged(@Nullable TimerGroup timerGroup) {
                String title = timerGroup.getTitle() + ": " + timer.getTitle();
                executeCode(title);
                groupId$.removeObserver(this);
            }
        };
        groupId$.observeForever(observer);


    }

    private void executeCode(String title) {
        Intent intent = new Intent(service, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(service, REQUESTCODE_CONENT_INTENT,
                intent, 0);

        PendingIntent pendingIntentDeleteNotification = DeleteNotificationService.returnPendingIntent(notificationId, context, notificationId);


        Bitmap bigIcon = getBitmap(service.getApplicationContext(), timer);
        createNotificationChannel(service.getApplicationContext());

        NotificationManager manager =
                (NotificationManager) service.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(service, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setContentTitle(title)
                .setContentText(service.getApplicationContext().getResources().getString(R.string.alarm_dialog_message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLargeIcon(bigIcon)
                .addAction(R.mipmap.ic_launcher, context.getString(R.string.button_delete),
                        pendingIntentDeleteNotification);


        Notification incomingCallNotification = notificationBuilder.build();
        incomingCallNotification.flags |= Notification.FLAG_INSISTENT;
        manager.notify(notificationId, incomingCallNotification);
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri alarmUri = returnSoundUri(context);
            CharSequence name = context.getResources().getString(R.string.notification_channel_timer_finished_title);
            String description = context.getResources().getString(R.string.notification_channel_timer_finished_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setSound(alarmUri, null);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private Uri returnSoundUri(Context context) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getValidRingtoneUri(context);
        }
        return alarmUri;
    }


}
