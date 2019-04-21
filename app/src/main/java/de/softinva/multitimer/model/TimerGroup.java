package de.softinva.multitimer.model;

import java.util.TreeMap;

import de.softinva.multitimer.fragments.list.timergroup.TimerGroupViewObject;

public class TimerGroup {
    public final String id;
    public final String title;
    public final TreeMap<Integer, RunningTimer> timerMap;
    public final boolean isZipped;
    public final String imageName;
    public final String description;

    public TimerGroup(String id, String title, boolean isZipped, String imageName, String description, TreeMap<Integer, RunningTimer> timerMap) {
        this.id = id;
        this.title = title;
        this.timerMap = timerMap;
        this.isZipped = isZipped;
        this.imageName = imageName;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
