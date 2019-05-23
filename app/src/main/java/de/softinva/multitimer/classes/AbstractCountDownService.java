package de.softinva.multitimer.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.lifecycle.MutableLiveData;

import java.util.TreeMap;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;


public abstract class AbstractCountDownService extends Service {
    public static final String TIMER = "de.softinva.multitimer.CountDownService.Timer";
    public static final String ACTION_START_TIMER = "de.softinva.multitimer.CountDownService.ActionStartTimer";
    public static final String ACTION_CANCEL_TIMER = "de.softinva.multitimer.CountDownService.ActionCancelTimer";

    public static MutableLiveData<TreeMap<Long, RunningTimer>> runningTimerByFinishTimeMap = new MutableLiveData<>();
    public static MutableLiveData<TreeMap<String, RunningTimer>> runningTimerByIDMap = new MutableLiveData<>();

    protected final IBinder binder = new LocalBinder();
    protected final AppLogger logger = UtilityMethods.createLogger(this);


    protected TreeMap<Long, RunningTimer> runningTimerMapByFinishTime;
    protected TreeMap<String, RunningTimer> runningTimerMapByID;
    protected TreeMap<String, AppCountDown> appCountDownTimerTreeMap;


    static {
        runningTimerByFinishTimeMap.setValue(new TreeMap<>());
        runningTimerByIDMap.setValue(new TreeMap<>());
    }

    @Override
    public void onCreate() {
        runningTimerMapByFinishTime = new TreeMap<>();
        runningTimerMapByID = new TreeMap<>();
        appCountDownTimerTreeMap = new TreeMap<>();

    }


    public class LocalBinder extends Binder {
        public AbstractCountDownService getService() {
            return AbstractCountDownService.this;
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

            AppCountDown appCountDown = new AppCountDown(runningTimer, this);
            appCountDownTimerTreeMap.put(timer.getId(), appCountDown);

            appCountDown.run();
            if (runningTimer.isRunning().getValue()) {
                runningTimerMapByFinishTime.put(runningTimer.getFinishTimeInSec(), runningTimer);
                runningTimerMapByID.put(timer.getId(), runningTimer);
                updateLiveData();
            }

        } else {
            cancelTimer(timer);
        }
    }

    protected void cancelTimer(Timer timer) {
        RunningTimer runningTimer = runningTimerMapByID.get(timer.getId());
        if (runningTimer != null) {
            Long finishTimeInSec = runningTimer.getFinishTimeInSec();
            if (finishTimeInSec != null) {
                runningTimerMapByFinishTime.remove(finishTimeInSec);
            }
        }
        runningTimerMapByID.remove(timer.getId());

        AppCountDown countDown = appCountDownTimerTreeMap.get(timer.getId());
        if (countDown != null) {
            countDown.cancel();
        }
        appCountDownTimerTreeMap.remove(timer.getId());

        updateLiveData();
    }

    public synchronized void removeTimer(Timer timer) {
        RunningTimer runningTimer = runningTimerMapByID.get(timer.getId());
        if (runningTimer != null) {
            Long finishTimeInSec = runningTimer.getFinishTimeInSec();
            if (finishTimeInSec != null) {
                runningTimerMapByFinishTime.remove(finishTimeInSec);
            }
        }
        runningTimerMapByID.remove(timer.getId());

        appCountDownTimerTreeMap.remove(timer.getId());

        updateLiveData();
    }

    protected void updateLiveData() {
        runningTimerByFinishTimeMap.setValue(runningTimerMapByFinishTime);
        runningTimerByIDMap.setValue(runningTimerMapByID);
    }
}
