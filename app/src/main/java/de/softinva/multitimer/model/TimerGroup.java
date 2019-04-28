package de.softinva.multitimer.model;

import java.util.Map;
import java.util.TreeMap;

import de.softinva.multitimer.R;
import de.softinva.multitimer.utility.UtilityMethods;

public class TimerGroup {
    protected final String id;
    protected String title;
    protected TreeMap<Integer, RunningTimer> timerMapByPosition;
    protected TreeMap<String, RunningTimer> timerMapByTimerId = new TreeMap<>();
    protected boolean isZipped;
    protected String imageName;
    protected String description;

    public TimerGroup() {
        id = UtilityMethods.createID();
        this.title = "";
        this.timerMapByPosition = new TreeMap<>();
        this.isZipped = false;
        this.imageName = "";
        this.description = "";
        createTimerMapByTimerId();
    }

    public TimerGroup(String id, String title, boolean isZipped, String imageName, String description, TreeMap<Integer, RunningTimer> timerMap) {
        this.id = id;
        this.title = title;
        this.timerMapByPosition = timerMap;
        this.isZipped = isZipped;
        this.imageName = imageName;
        this.description = description;
        createTimerMapByTimerId();
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public TreeMap<Integer, RunningTimer> getTimerMapByPosition() {
        return timerMapByPosition;
    }

    public TreeMap<String, RunningTimer> getTimerMapByTimerId() {
        return timerMapByTimerId;
    }

    public boolean isZipped() {
        return isZipped;
    }

    public String getImageName() {
        return imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTimerMapByPosition(TreeMap<Integer, RunningTimer> timerMapByPosition) {
        this.timerMapByPosition = timerMapByPosition;
    }

    public void setTimerMapByTimerId(TreeMap<String, RunningTimer> timerMapByTimerId) {
        this.timerMapByTimerId = timerMapByTimerId;
    }

    public void setZipped(boolean zipped) {
        isZipped = zipped;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDescription(String description) {
        this.description = description;
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
