package de.softinva.multitimer.utility;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.repository.TimerRepository;

public class UtilityMethods {
    public static AppLogger createLogger(Object classObject) {
        AppLogger logger = new AppLogger(classObject);
        return logger;
    }

    public static MutableLiveData<TreeMap<Integer, RunningTimer>> updateTimerList(TreeMap<Integer, RunningTimer> timerMap, Application application) {
        LiveData<TreeMap<String, RunningTimer>> runningTimerMap = new TimerRepository(application).getRunningTimerByIDMap();
        return (MutableLiveData<TreeMap<Integer, RunningTimer>>) Transformations.map(runningTimerMap, rTimerMap -> {
            for (Map.Entry<String, RunningTimer> runningTimer : rTimerMap.entrySet()) {
                setTimer(runningTimer.getValue(), timerMap);
            }
            return timerMap;
        });

    }

    public static TreeMap<Integer, RunningTimer> createRunningTimerListForDetailedTimer(TreeMap<Integer, DetailedTimer> timerMap) {
        TreeMap<Integer, RunningTimer> treeMap = new TreeMap();
        for (Map.Entry<Integer, DetailedTimer> entry : timerMap.entrySet()) {
            treeMap.put(entry.getKey(), new RunningTimer(entry.getValue()));
        }
        return treeMap;
    }

    public static TreeMap<Integer, RunningTimer> createRunningTimerListForTempTimer(TreeMap<Integer, TempTimer> timerMap) {
        TreeMap<Integer, RunningTimer> treeMap = new TreeMap();
        for (Map.Entry<Integer, TempTimer> entry : timerMap.entrySet()) {
            treeMap.put(entry.getKey(), new RunningTimer(entry.getValue()));
        }
        return treeMap;
    }

    protected static void setTimer(RunningTimer runningTimer, TreeMap<Integer, RunningTimer> tList) {

        for (Map.Entry<Integer, RunningTimer> timerEntry : tList.entrySet()) {
            RunningTimer runningTimerInList = timerEntry.getValue();
            Timer timerInstance = runningTimerInList.getTimer();
            if (timerInstance.getId().equals(runningTimer.getTimer().getId())) {
                runningTimer.setTimer(runningTimerInList.getTimer());
                if (runningTimerInList.getTimer() instanceof DetailedTimer) {
                    DetailedTimer detailedTimer = (DetailedTimer) runningTimerInList.getTimer();
                    tList.put(detailedTimer.getPositionInGroup(), runningTimer);
                } else {
                    tList.put(timerEntry.getKey(), runningTimer);
                }

                return;
            }
        }

    }

    public static String createID() {
        return UUID.randomUUID().toString();
    }

    public static String transformToTextString(Integer countDownInSec) {
        if (countDownInSec == null) {
            return "";
        }
        return UtilityMethods.transformToTextString(countDownInSec.longValue());
    }

    public static String transformToTextString(Long countDownInSec) {
        if (countDownInSec == null) {
            return "";
        }
        AppLogger logger = new AppLogger(new UtilityMethods());
        String countDownAsString = "";
        int hours = countDownInSec.intValue() / 3600;
        int minutes = (countDownInSec.intValue() - (hours * 3600)) / 60;
        int seconds = (countDownInSec.intValue() - (hours * 3600) - (minutes * 60));
        if (hours != 0) {
            countDownAsString += hours + " h ";
        }
        if (minutes != 0) {
            countDownAsString += minutes + " min ";
        }
        if (seconds != 0) {
            countDownAsString += seconds + " sec";
        }

        logger.info("countDownInSec: " + countDownInSec + " countDownAsString: " + countDownAsString);
        return countDownAsString;
    }

}
