package de.softinva.multitimer.repository.dummy;

import java.util.TreeMap;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.utility.UtilityMethods;

public class DummyNudelGericht {
    private static final TreeMap<Integer, RunningTimer> TIMER_LIST = new TreeMap<>();
    public static final TimerGroup TIMER_GROUP;
    public static final DetailedTimer TIMER_NUDELN;
    public static final DetailedTimer TIMER_Tomatensoße;


    static {
        String timerGroupId = "0";
        String timerId_Nudeln = "Nudeln_0";
        String timerId_Tomatensoße = "Nudeln_1";

        TIMER_NUDELN = new DetailedTimer(timerId_Nudeln, "0", "Nudeln", 480, 0, true, UtilityMethods.createNameForImage(timerGroupId, timerId_Nudeln), 0, "Nudeln zum kochen bringen und dann auf mittlere Stufe herunter stellen.");
        TIMER_Tomatensoße = new DetailedTimer(timerId_Tomatensoße, "0", "Tomatensoße", 480, 0, true, UtilityMethods.createNameForImage(timerGroupId, timerId_Tomatensoße), 1, "Tomatensoße auf mittlerer Stufe erwärmen.");
        addItemToTimerList(TIMER_NUDELN);
        addItemToTimerList(TIMER_Tomatensoße);
        TIMER_GROUP = new TimerGroup(timerGroupId, "Nudel Gericht", false, UtilityMethods.createNameForImage(timerGroupId), "Leckere Nudeln mit Tomatensoße. Schnell gemacht und schmeckt.");
    }

    private static void addItemToTimerList(DetailedTimer timer) {
        TIMER_LIST.put(timer.getPositionInGroup(), new RunningTimer(timer));
    }

}
