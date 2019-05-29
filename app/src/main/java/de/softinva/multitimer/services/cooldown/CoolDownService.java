package de.softinva.multitimer.services.cooldown;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.TreeMap;

import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;

public class CoolDownService extends Service {
    public static final String TIMER = "de.softinva.multitimer.services.countdown.CountDownService.Timer";
    public static final String ACTION_START_TIMER = "de.softinva.multitimer.services.countdown.CountDownService.ActionStartTimer";
    public static final String ACTION_CANCEL_TIMER = "de.softinva.multitimer.services.countdown.CountDownService.ActionCancelTimer";

    public static MutableLiveData<TreeMap<String, RunningTimer>> runningTimerByIDMap = new MutableLiveData<>();

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


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.info("Start CountDownService: onStartCommand()");
        String action = intent.getAction();
        Timer timer = intent.getParcelableExtra(TIMER);
        if (!(timer instanceof DetailedTimer)) {
            throw new Error("timer not instance of DetailedTimer! " + timer.getClass());
        }
        DetailedTimer detailedTimer = (DetailedTimer) timer;
        switch (action) {
            case ACTION_START_TIMER:
                addNewTimer(detailedTimer);
                break;
            case ACTION_CANCEL_TIMER:
                cancelTimer(detailedTimer);
                break;
            default:
                throw new Error("Intent with action " + action + " not supported!");
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void addNewTimer(DetailedTimer timer) {
        String timerMapId = returnTimerMapId(timer.getGroupId(), timer.getId());
        if (runningTimerMapByID.get(timerMapId) == null) {
            RunningTimer runningTimer = new RunningTimer(timer);

            AppCoolDown appCountDown = new AppCoolDown(runningTimer, this);
            appCoolDownTimerTreeMap.put(timerMapId, appCountDown);

            appCountDown.run();
            if (runningTimer.isCoolDownRunning().getValue() != null && runningTimer.isCoolDownRunning().getValue()) {
                runningTimerMapByID.put(timerMapId, runningTimer);
                updateLiveData();
            } else {
                throw new Error("timer should be running!");
            }

        } else {
            throw new Error("Timer is running!");
        }
    }

    protected void cancelTimer(DetailedTimer timer) {
        String timerMapId = returnTimerMapId(timer.getGroupId(), timer.getId());
        AppCoolDown countDown = appCoolDownTimerTreeMap.get(timerMapId);
        if (countDown != null) {
            countDown.cancel();
        } else {
            throw new Error("count down should not be null!");
        }
    }

    public synchronized void onStopTimer(DetailedTimer timer) {
        String timerMapId = returnTimerMapId(timer.getGroupId(), timer.getId());
        RunningTimer runningTimer = runningTimerMapByID.get(timerMapId);
        if (runningTimer != null) {
            runningTimer.stopCoolDown();
            removeTimer(timer);
            updateLiveData();
        } else {
            throw new Error("running Timer should not be null!");
        }
    }


    protected void removeTimer(DetailedTimer timer) {
        String timerMapId = returnTimerMapId(timer.getGroupId(), timer.getId());
        runningTimerMapByID.remove(timerMapId);
        appCoolDownTimerTreeMap.remove(timerMapId);
    }

    protected void updateLiveData() {
        runningTimerByIDMap.setValue(runningTimerMapByID);
    }

    public void onFinishTimer(DetailedTimer detailedTimer) {
        new TimerRepository(getApplication()).enableDetailedTimer(detailedTimer.getGroupId(), detailedTimer.getId());
    }

    public static String returnTimerMapId(String timerGroupId, String timerId) {
        return timerGroupId + timerId;
    }
}
