package de.softinva.multitimer;

import android.content.Context;
import android.content.Intent;

import de.softinva.multitimer.classes.AbstractCountDownService;
import de.softinva.multitimer.model.Timer;

public class CoolDownService extends AbstractCountDownService {

    public static void startNewTimer(Timer timer, Context context) {
        Intent intent = new Intent(context, CoolDownService.class);
        intent.setAction(AbstractCountDownService.ACTION_START_TIMER);
        intent.putExtra(AbstractCountDownService.TIMER, timer);
        context.startService(intent);
    }


    public static void cancelTimer(Timer timer, Context context) {
        Intent intent = new Intent(context, CoolDownService.class);
        intent.setAction(AbstractCountDownService.ACTION_CANCEL_TIMER);
        intent.putExtra(AbstractCountDownService.TIMER, timer);
        context.startService(intent);
    }

}
