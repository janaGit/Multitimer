package de.softinva.multitimer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.lifecycle.MutableLiveData;

import java.util.TreeMap;

import de.softinva.multitimer.classes.AppCoolDown;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;

public class CoolDownService extends Service {
    public static final String TIMER = "de.softinva.multitimer.CountDownService.Timer";
    public static final String ACTION_START_TIMER = "de.softinva.multitimer.CountDownService.ActionStartTimer";
    public static final String ACTION_CANCEL_TIMER = "de.softinva.multitimer.CountDownService.ActionCancelTimer";

    public static MutableLiveData<TreeMap<String, RunningTimer>> runningTimerByIDMap = new MutableLiveData<>();

    protected final IBinder binder = new LocalBinder();
    protected final AppLogger logger = UtilityMethods.createLogger(this);


    protected TreeMap<String, RunningTimer> runningTimerMapByID;
    protected TreeMap<String, AppCoolDown> appCoolDownTimerTreeMap;


    static {
        runningTimerByIDMap.setValue(new TreeMap<>());
    }

    public static void startNewTimer(Timer timer, Context context) {
        Intent intent = new Intent(context, CoolDownService.class);
        intent.setAction(CoolDownService.ACTION_START_TIMER);
        intent.putExtra(CoolDownService.TIMER, timer);
        context.startService(intent);
    }


    public static void cancelTimer(Timer timer, Context context) {
        Intent intent = new Intent(context, CoolDownService.class);
        intent.setAction(CoolDownService.ACTION_CANCEL_TIMER);
        intent.putExtra(CoolDownService.TIMER, timer);
        context.startService(intent);
    }


    @Override
    public void onCreate() {
        runningTimerMapByID = new TreeMap<>();
        appCoolDownTimerTreeMap = new TreeMap<>();

    }

    public class LocalBinder extends Binder {
        public CoolDownService getService() {
            return CoolDownService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.info("Start CountDownService: onStartCommand()");
        String action = intent.getAction();
        Timer timer = intent.getParcelableExtra(TIMER);
        switch (action) {
            case ACTION_START_TIMER:
                addNewTimer(timer);
                break;
            case ACTION_CANCEL_TIMER:
                cancelTimer(timer);
                break;
            default:
                throw new Error("Intent with action " + action + " not supported!");
        }

        return START_STICKY;
    }

    protected void addNewTimer(Timer timer) {
        if (runningTimerMapByID.get(timer.getId()) == null) {
            RunningTimer runningTimer = new RunningTimer(timer);

            AppCoolDown appCountDown = new AppCoolDown(runningTimer, this);
            appCoolDownTimerTreeMap.put(timer.getId(), appCountDown);

            appCountDown.run();
            if (runningTimer.isCoolDownRunning().getValue() != null && runningTimer.isCoolDownRunning().getValue()) {
                runningTimerMapByID.put(timer.getId(), runningTimer);
                updateLiveData();
            } else {
                throw new Error("timer should be running!");
            }

        } else {
            throw new Error("Timer is running!");
        }
    }

    protected void cancelTimer(Timer timer) {
        AppCoolDown countDown = appCoolDownTimerTreeMap.get(timer.getId());
        if (countDown != null) {
            countDown.cancel();
        } else {
            throw new Error("count down should not be null!");
        }
    }

    public synchronized void onStopTimer(Timer timer) {
        RunningTimer runningTimer = runningTimerMapByID.get(timer.getId());
        if (runningTimer != null) {
            runningTimer.stopCoolDown();
            removeTimer(timer);
            updateLiveData();
        } else {
            throw new Error("running Timer should not be null!");
        }
    }


    protected void removeTimer(Timer timer) {
        runningTimerMapByID.remove(timer.getId());
        appCoolDownTimerTreeMap.remove(timer.getId());
    }

    protected void updateLiveData() {
        runningTimerByIDMap.setValue(runningTimerMapByID);
    }

    public void onFinishTimer(Timer timer) {

    }
}
