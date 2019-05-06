package de.softinva.multitimer.repository.dummy;

import java.util.TreeMap;

import de.softinva.multitimer.model.DetailedTimer;
import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TimerGroup;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyPizza {
    private static final TreeMap<Integer, RunningTimer> TIMER_LIST = new TreeMap<>();
    public static final TimerGroup TIMER_GROUP;
    public static final DetailedTimer TIMER_PIZZA;


    static {
        TIMER_PIZZA = new DetailedTimer("Pizza_0", "1", "Pizza backen", 900, "pizza.png", 0, "Tiefgefrorene Pizza in den Ofen tun. Ofen muss nicht vorgeheizt werden.");
        addItemToTimerList(TIMER_PIZZA);
        TIMER_GROUP = new TimerGroup("1", "Pizza", false, "Piza.png", "Schnelle Fertig Pizza. Sehr lecker.");
    }

    private static void addItemToTimerList(DetailedTimer timer) {
        TIMER_LIST.put(timer.getPositionInGroup(), new RunningTimer(timer));
    }

}
