package de.softinva.multitimer.services.countdown;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.TreeMap;

import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;
import de.softinva.multitimer.services.cooldown.CoolDownService;
import de.softinva.multitimer.utility.AppLogger;
import de.softinva.multitimer.utility.UtilityMethods;


public class CountDownService extends Service {
    public static final String TIMER = "de.softinva.multitimer.services.countdown.CountDownService.Timer";
    public static final String ACTION_START_TIMER = "de.softinva.multitimer.services.countdown.CountDownService.ActionStartTimer";
    public static final String ACTION_CANCEL_TIMER = "de.softinva.multitimer.services.countdown.CountDownService.ActionCancelTimer";

    public static MutableLiveData<TreeMap<Long, RunningTimer>> runningTimerByFinishTimeMap = new MutableLiveData<>();
    public static MutableLiveData<TreeMap<String, RunningTimer>> runningTimerByIDMap = new MutableLiveData<>();

    protected final AppLogger logger = UtilityMethods.createLogger(this);


    protected TreeMap<Long, RunningTimer> runningTimerMapByFinishTime;
    protected TreeMap<String, RunningTimer> runningTimerMapByID;
    protected TreeMap<String, AppCountDown> appCountDownTimerTreeMap;


    static {
        runningTimerByFinishTimeMap.setValue(new TreeMap<>());
        runningTimerByIDMap.setValue(new TreeMap<>());
    }

    public static void startNewTimer(Timer timer, Context context) {
        Intent intent = new Intent(context, CountDownService.class);
        intent.setAction(CountDownService.ACTION_START_TIMER);
        intent.putExtra(CountDownService.TIMER, timer);
        context.startService(intent);
    }


    public static void cancelTimer(Timer timer, Context context) {
        Intent intent = new Intent(context, CountDownService.class);
        intent.setAction(CountDownService.ACTION_CANCEL_TIMER);
        intent.putExtra(CountDownService.TIMER, timer);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        runningTimerMapByFinishTime = new TreeMap<>();
        runningTimerMapByID = new TreeMap<>();
        appCountDownTimerTreeMap = new TreeMap<>();

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void addNewTimer(Timer timer) {
        if (runningTimerMapByID.get(timer.getId()) == null) {
            String timerMapId = returnTimerMapId(timer);
            RunningTimer runningTimer = new RunningTimer(timer);

            AppCountDown appCountDown = new AppCountDown(runningTimer, this);
            appCountDownTimerTreeMap.put(timerMapId, appCountDown);

            appCountDown.run();
            if (runningTimer.isCountDownRunning().getValue() != null && runningTimer.isCountDownRunning().getValue()) {
                runningTimerMapByFinishTime.put(runningTimer.getFinishTimeCountDownInSec(), runningTimer);
                runningTimerMapByID.put(timerMapId, runningTimer);
                updateLiveData();
            } else {
                throw new Error("timer ist not running, but should!");
            }

        } else {
            throw new Error("Timer is running!");
        }
    }

    protected void cancelTimer(Timer timer) {
        String timerMapId = returnTimerMapId(timer);
        AppCountDown countDown = appCountDownTimerTreeMap.get(timerMapId);
        if (countDown != null) {
            countDown.cancel();
        } else {
            throw new Error("count down should not be null!");
        }
    }

    public synchronized void onStopTimer(Timer timer) {
        String timerMapId = returnTimerMapId(timer);
        RunningTimer runningTimer = runningTimerMapByID.get(timerMapId);
        if (runningTimer != null) {
            Long finishTime = runningTimer.stopCountDown();
            removeTimer(timer, finishTime);
            updateLiveData();
        } else {
            throw new Error("running Timer should not be null!");
        }
    }

    protected void removeTimer(Timer timer, Long finishTimeInSec) {
        String timerMapId = returnTimerMapId(timer);
        if (finishTimeInSec != null) {
            runningTimerMapByFinishTime.remove(finishTimeInSec);
        } else {
            throw new Error("finishTime should not be null!");
        }
        runningTimerMapByID.remove(timerMapId);

        appCountDownTimerTreeMap.remove(timerMapId);
    }

    protected void updateLiveData() {
        runningTimerByFinishTimeMap.setValue(runningTimerMapByFinishTime);
        runningTimerByIDMap.setValue(runningTimerMapByID);
    }

    public void onFinishTimer(Timer timer) {
        if (timer instanceof DetailedTimer) {
            DetailedTimer detailedTimer = (DetailedTimer) timer;
            if (detailedTimer.getCoolDownInSec() > 0) {
                LiveData<DetailedTimer> liveData = new TimerRepository(this.getApplication()).getDetailedTimer(detailedTimer.getGroupId(), detailedTimer.getId());
                CountDownService service = this;
                Observer<DetailedTimer> observer = new Observer<DetailedTimer>() {
                    @Override
                    public void onChanged(@Nullable DetailedTimer detailedTimerFromDatabase) {

                        if (detailedTimerFromDatabase != null && !detailedTimerFromDatabase.getIsEnabled()) {
                            CoolDownService.startNewTimer(detailedTimerFromDatabase, service);
                        }

                        liveData.removeObserver(this);
                    }
                };

                liveData.observeForever(observer);
            }

        }
    }

    public String returnTimerMapId(Timer timer) {
        if (timer instanceof DetailedTimer) {
            DetailedTimer detailedTimer = (DetailedTimer) timer;
            return detailedTimer.getGroupId() + detailedTimer.getId();
        } else {
            return "00" + timer.getId();
        }
    }
}
