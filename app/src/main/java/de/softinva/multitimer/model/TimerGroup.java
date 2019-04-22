package de.softinva.multitimer.model;

import java.util.Map;
import java.util.TreeMap;

public class TimerGroup {
    public final String id;
    public final String title;
    public final TreeMap<Integer, RunningTimer> timerMapByPosition;
    public final TreeMap<String, RunningTimer> timerMapByTimerId = new TreeMap<>();
    public final boolean isZipped;
    public final String imageName;
    public final String description;

    public TimerGroup(String id, String title, boolean isZipped, String imageName, String description, TreeMap<Integer, RunningTimer> timerMap) {
        this.id = id;
        this.title = title;
        this.timerMapByPosition = timerMap;
        this.isZipped = isZipped;
        this.imageName = imageName;
        this.description = description;
        createTimerMapByTimerId();
    }

    @Override
    public String toString() {
        return this.title;
    }

    protected void createTimerMapByTimerId() {
        for (Map.Entry<Integer, RunningTimer> entry : timerMapByPosition.entrySet()) {
            timerMapByTimerId.put(entry.getValue().timer.id, entry.getValue());
        }
    }
}
