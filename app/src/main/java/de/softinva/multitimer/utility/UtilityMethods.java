package de.softinva.multitimer.utility;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Map;
import java.util.TreeMap;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.Timer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.repository.TimerRepository;

public class UtilityMethods {
    public static AppLogger createLogger(Object classObject) {
        AppLogger logger = new AppLogger(classObject);
        return logger;
    }

    public static MutableLiveData<TreeMap<Integer, RunningTimer>> updateTimerList(TreeMap<Integer, RunningTimer> timerMap, Application application) {
        MutableLiveData<TreeMap<String, RunningTimer>> runningTimerMap = new TimerRepository(application).getRunningTimerByIDMap();
        return (MutableLiveData<TreeMap<Integer, RunningTimer>>) Transformations.map(runningTimerMap, rTimerMap -> {
            for (Map.Entry<String, RunningTimer> runningTimer : rTimerMap.entrySet()) {
                updateRunningTimer(runningTimer.getValue(), timerMap);
            }
            return timerMap;
        });

    }

    protected static void updateRunningTimer(RunningTimer runningTimer, TreeMap<Integer, RunningTimer> tList) {
        for (Map.Entry<Integer, RunningTimer> timerEntry : tList.entrySet()) {
            RunningTimer runningTimerInList = timerEntry.getValue();
            Timer timerInstance = runningTimerInList.getTimer();
            if (timerInstance.getId().equals(runningTimer.getTimer().getId())) {
                runningTimer.setTimer(runningTimerInList.getTimer());
                tList.put(timerEntry.getKey(), runningTimer);
                return;
            }
        }

    }

    public static String createID() {
        return "1234" + Math.random();
    }

    public static String transformToTextString(Integer countDownInSec) {
        return UtilityMethods.transformToTextString(countDownInSec.longValue());
    }
    public static String transformToTextString(Long countDownInSec) {
        AppLogger logger = new AppLogger(new UtilityMethods());
        String countDownAsString;
        int hours = countDownInSec.intValue() / 3600;
        int minutes = (countDownInSec.intValue() - (hours * 3600)) / 60;
        int seconds = (countDownInSec.intValue() - (hours * 3600) - (minutes * 60));
        if (hours != 0) {
            countDownAsString = hours + "h " + minutes + " min " + seconds + " sec";
        } else {
            if (minutes != 0) {
                countDownAsString = minutes + " min " + seconds + " sec";
            } else {
                countDownAsString = seconds + " sec";
            }
        }

        logger.info("countDownInSec: "+countDownInSec+" countDownAsString: " + countDownAsString);
        return countDownAsString;
    }

}
