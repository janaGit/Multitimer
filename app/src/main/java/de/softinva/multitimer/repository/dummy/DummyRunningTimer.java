package de.softinva.multitimer.repository.dummy;

import java.util.TreeMap;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;

public class DummyRunningTimer {
    public static final TreeMap<Long, RunningTimer> RUNNING_TIMER;

    static {
        RUNNING_TIMER = new TreeMap<>();
        addTimer(DummyNudelGericht.TIMER_Tomatensoße);
        addTimer(DummyTempTimer.TEMP_TIMER_1);
        addTimer(DummyTempTimer.TEMP_TIMER_2);
        addTimer(DummyTempTimer.TEMP_TIMER_3);
    }

    protected static void addTimer(Timer timer) {
        RunningTimer runningTimer = new RunningTimer(timer);
        runningTimer.startCountDown();
        RUNNING_TIMER.put(runningTimer.getFinishTimeCountDownInSec(), runningTimer);
    }
}
