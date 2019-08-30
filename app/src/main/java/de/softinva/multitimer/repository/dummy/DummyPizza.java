package de.softinva.multitimer.repository.dummy;

import java.util.TreeMap;

import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TimerGroup;
import de.softinva.multitimer.utility.UtilityMethods;

public class DummyPizza {
    private static final TreeMap<Integer, RunningTimer> TIMER_LIST = new TreeMap<>();
    public static final TimerGroup TIMER_GROUP;
    public static final DetailedTimer TIMER_PIZZA;


    static {
        String timerGroupId = "1";
        String timerId_pizza = "pizza_0";

        TIMER_PIZZA = new DetailedTimer(timerId_pizza, "1", "Pizza backen", 900, 0, true, UtilityMethods.createNameForImage(timerGroupId, timerId_pizza), 0, "Tiefgefrorene Pizza in den Ofen tun. Ofen muss nicht vorgeheizt werden.");
        addItemToTimerList(TIMER_PIZZA);
        TIMER_GROUP = new TimerGroup(timerGroupId, "Pizza", false, UtilityMethods.createNameForImage(timerGroupId), "Schnelle Fertig Pizza. Sehr lecker.");
    }

    private static void addItemToTimerList(DetailedTimer timer) {
        TIMER_LIST.put(timer.getPositionInGroup(), new RunningTimer(timer));
    }

}
