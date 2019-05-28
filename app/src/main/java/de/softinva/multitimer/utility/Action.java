package de.softinva.multitimer.utility;

import android.app.Application;

import java.util.LinkedList;
import java.util.TreeMap;

import de.softinva.multitimer.CoolDownService;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;

public class Action {


    public static void cancelCoolDownIfRunning(Application application, String timerGroupId, String timerId) {
        RunningTimer runningTimer = CoolDownService.runningTimerByIDMap.getValue().get(CoolDownService.returnTimerMapId(timerGroupId, timerId));
        if (runningTimer != null) {
            CoolDownService.cancelTimer(runningTimer.getTimer(), application);
        }
    }

    public static void cancelCoolDownOfAllRunningTimerInGroup(Application application, String timerGroupId) {
        TreeMap<String, RunningTimer> map = CoolDownService.runningTimerByIDMap.getValue();
        if (map != null) {
            LinkedList<DetailedTimer> detailedTimerList = UtilityMethods.getDetailedTimerOfTimerGroupInMap(map, timerGroupId);
            for (DetailedTimer detailedTimer : detailedTimerList) {
                CoolDownService.cancelTimer(detailedTimer, application);
            }
        } else {
            throw new Error("map is null!");
        }

    }
}
