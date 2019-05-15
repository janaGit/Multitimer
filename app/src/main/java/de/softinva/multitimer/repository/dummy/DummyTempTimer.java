package de.softinva.multitimer.repository.dummy;

import java.util.TreeMap;

import de.softinva.multitimer.model.RunningTimer;
import de.softinva.multitimer.model.TempTimer;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyTempTimer {
    public static final TreeMap<Integer, TempTimer> TEMP_TIMERS;
    public static final TempTimer TEMP_TIMER_1;
    public static final TempTimer TEMP_TIMER_2;
    public static final TempTimer TEMP_TIMER_3;


    static {
        TEMP_TIMERS = new TreeMap<>();
        TEMP_TIMER_1 = new TempTimer("0", "Temp Timer 1", 600);
        TEMP_TIMER_2 = new TempTimer("1", "Temp Timer 2", 600);
        TEMP_TIMER_3 = new TempTimer("2", "Temp Timer 3", 600);
        TEMP_TIMERS.put(0,TEMP_TIMER_1);
        TEMP_TIMERS.put(1,TEMP_TIMER_2);
        TEMP_TIMERS.put(2, TEMP_TIMER_3);
    }


}
