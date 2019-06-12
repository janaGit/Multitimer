package de.softinva.multitimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class AppBroadcastReceiverImageNameUpdated extends BroadcastReceiver {
    public static final String ACTION_UPDATE_IMAGE_NAME = "ACTION_TIMER_FINISHED";
    Context receiverContext;

    AppBroadcastReceiverImageNameUpdated(Context receiverContext) {
        this.receiverContext = receiverContext;

    }

    public static void sendImageName(String imageName, Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_IMAGE_NAME);
        intent.putExtra("data", imageName);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    public static AppBroadcastReceiverImageNameUpdated registerReceiverForImageNameUpdates(Context context) {

        if (!(context instanceof UpdateImageName)) {
            throw new RuntimeException(context.toString() + " must implement EditDurationFieldsFocusChangeListener");
        }

        AppBroadcastReceiverImageNameUpdated broadcastReceiver = new AppBroadcastReceiverImageNameUpdated(context);
        IntentFilter filter = new IntentFilter(AppBroadcastReceiverImageNameUpdated.ACTION_UPDATE_IMAGE_NAME);

        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, filter);
        return broadcastReceiver;
    }

    public static void unregisterReceiver(Context context, AppBroadcastReceiverImageNameUpdated receiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_UPDATE_IMAGE_NAME:
                onUpdateImageName(intent.getStringExtra("data"));
                break;
            default:
                throw new Error("action not supported: " + action);

        }
    }

    public interface UpdateImageName {
        void updateImageName(String imageName);
    }

    private void onUpdateImageName(String imageName) {
        ((UpdateImageName) receiverContext).updateImageName(imageName);
    }
}
