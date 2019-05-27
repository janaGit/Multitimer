package de.softinva.multitimer.utility;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Map;
import java.util.TreeMap;

import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;

public class LiveDataZipRunningTimer extends MediatorLiveData<TreeMap<Integer, RunningTimer>> {
    TreeMap<Integer, RunningTimer> timerMap;

    public LiveDataZipRunningTimer(TreeMap<Integer, RunningTimer> map, Application application) {
        timerMap = map;
        LiveData<TreeMap<String, RunningTimer>> runningTimerMap = new TimerRepository(application).getRunningTimerByIDMap();
        LiveData<TreeMap<String, RunningTimer>> coolDownTimerTimerByIDMap = new TimerRepository(application).getCoolDownTimerTimerByIDMap();
        addSource(runningTimerMap, newRunningTimerMap -> {
            for (Map.Entry<String, RunningTimer> runningTimer : newRunningTimerMap.entrySet()) {
                updateCountDownLiveDataInRunningTimer(runningTimer.getValue(), timerMap);
            }
            setValue(timerMap);
        });
        addSource(coolDownTimerTimerByIDMap, newRunningTimerMap -> {
            for (Map.Entry<String, RunningTimer> runningTimer : newRunningTimerMap.entrySet()) {
                updateCoolDownLiveDataInRunningTimer(runningTimer.getValue(), timerMap);
            }
            setValue(timerMap);
        });
    }

    protected void updateCountDownLiveDataInRunningTimer(RunningTimer runningTimer, TreeMap<Integer, RunningTimer> tList) {

        for (Map.Entry<Integer, RunningTimer> timerEntry : tList.entrySet()) {
            RunningTimer runningTimerInList = timerEntry.getValue();
            Timer timerInstance = runningTimerInList.getTimer();
            if (timerInstance.getId().equals(runningTimer.getTimer().getId())) {
                runningTimerInList.setCountDownInSec(runningTimer.getCountDownInSec());
                runningTimerInList.setIsCountDownRunning(runningTimer.isCountDownRunning());
                if (runningTimerInList.getTimer() instanceof DetailedTimer) {
                    DetailedTimer detailedTimer = (DetailedTimer) runningTimerInList.getTimer();
                    tList.put(detailedTimer.getPositionInGroup(), runningTimerInList);
                } else {
                    tList.put(timerEntry.getKey(), runningTimerInList);
                }

                return;
            }
        }

    }

    protected void updateCoolDownLiveDataInRunningTimer(RunningTimer runningTimer, TreeMap<Integer, RunningTimer> tList) {

        for (Map.Entry<Integer, RunningTimer> timerEntry : tList.entrySet()) {
            RunningTimer runningTimerInList = timerEntry.getValue();
            Timer timerInstance = runningTimerInList.getTimer();
            if (timerInstance.getId().equals(runningTimer.getTimer().getId())) {
                runningTimerInList.setCoolDownInSec(runningTimer.getCoolDownInSec());
                runningTimerInList.setIsCoolDownRunning(runningTimer.isCoolDownRunning());
                if (runningTimerInList.getTimer() instanceof DetailedTimer) {
                    DetailedTimer detailedTimer = (DetailedTimer) runningTimerInList.getTimer();
                    tList.put(detailedTimer.getPositionInGroup(), runningTimerInList);
                } else {
                    tList.put(timerEntry.getKey(), runningTimerInList);
                }

                return;
            }
        }

    }


}
