package de.softinva.multitimer.repository.dummy;
import java.util.TreeMap;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.TimerGroup;

public class DummyNudelGericht {
    private static final TreeMap<Integer,RunningTimer> TIMER_LIST = new TreeMap<>();
    public static final TimerGroup TIMER_GROUP;
    public static final DetailedTimer TIMER_NUDELN;
    public static final DetailedTimer TIMER_Tomatensoße;


    static {
        TIMER_NUDELN = new DetailedTimer("Nudeln_0","0", "Nudeln", 480,0, true,  "nudeln",0,"Nudeln zum kochen bringen und dann auf mittlere Stufe herunter stellen.");
        TIMER_Tomatensoße = new DetailedTimer("Nudeln_1","0", "Tomatensoße", 480,0, true, "gemuese",1,"Tomatensoße auf mittlerer Stufe erwärmen.");
        addItemToTimerList(TIMER_NUDELN);
        addItemToTimerList(TIMER_Tomatensoße);
        TIMER_GROUP = new TimerGroup("0", "Nudel Gericht", false, "nudelsalat","Leckere Nudeln mit Tomatensoße. Schnell gemacht und schmeckt.");
    }

    private static void addItemToTimerList(DetailedTimer timer) {
        TIMER_LIST.put(timer.getPositionInGroup(), new RunningTimer(timer));
    }

}
